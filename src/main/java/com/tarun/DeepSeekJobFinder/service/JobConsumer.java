package com.tarun.DeepSeekJobFinder.service;

import com.google.gson.Gson;
import com.tarun.DeepSeekJobFinder.model.JobData;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tarun.DeepSeekJobFinder.utils.Utils.*;

@Service
public class JobConsumer {
    ArrayList<JobData> jobs = new ArrayList<>();
    List<Map<String, Object>> transformedJobs = new ArrayList<>();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final ChatClient chatClient;
    int count = 0;
    public JobConsumer(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @KafkaListener(topics = "LinkedinJobs", groupId = "linkedin-jobs-group")
    public void consume(JobData job) {
        jobs.add(job);
        count++;
        if (count >= 3) {
            for (JobData j : jobs) {
                processJobsAndSendToKafka(j);
            }
            jobs.clear();
            count = 0;
        }
    }
    public void processJobsAndSendToKafka(JobData job) {
        String title = job.getPosition();
        String JobLink = job.getJob_link();
        if (title.matches("^[\\d,+]+.*")) {
            return;
        }
        String requirements = fetchJobDetails(JobLink);
        String generatedResume = generateResume(getResumeText("Parth"), requirements);
        Map<String, Object> jobData = new HashMap<>();
        jobData.put("title", title);
        jobData.put("link", JobLink);
        jobData.put("resume", simplifyHtml(generatedResume));
        jobData.put("requirements", requirements);
        transformedJobs.add(Map.of("json", jobData));
        publishToKafka(jobData);
        System.out.println("Processed job: " + title);
    }

    private void publishToKafka(Map<String, Object> jobData) {
        String payload = new Gson().toJson(jobData);
        String TOPIC_NAME = "jobTopic";
        kafkaTemplate.send(TOPIC_NAME, payload);
        System.out.println("Sent event to Kafka: " + payload);
    }
    public String generateResume(String currentResume, String jobRequirements) {
        Prompt prompt = new Prompt(String.valueOf(List.of(
                "You are an intelligent assistant that generates professional resumes.",
                "Generate a resume for the candidate using their current resume: " + currentResume
                        + " and job requirements: " + jobRequirements
                        + ". Highlight relevant skills and experiences. Return the resume in a structured, professional HTML format."
                        + "Use semantic HTML elements such as <header>, <section>, <h2>, <ul>, <li>, <p>, and <strong> where appropriate."
                        + "Ensure proper spacing, indentation, and structure for readability."
                        + "Do not include any <think> tags, conclusions, or summary evaluations."
                        + "The last section of the resume should be 'Professional Affiliations'."
        )));
        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
        assert chatResponse != null;
        return removeThinkTag(chatResponse);
    }
}
