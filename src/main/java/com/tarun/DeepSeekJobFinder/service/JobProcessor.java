package com.tarun.DeepSeekJobFinder.service;
import com.tarun.DeepSeekJobFinder.constants.Constants;
import com.tarun.DeepSeekJobFinder.model.JobData;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.tarun.DeepSeekJobFinder.utils.Utils.*;

@Service
public class JobProcessor {
    private final ChatClient chatClient;

    @Autowired
    public JobProcessor(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public Optional<Map<String, Object>> processJob(JobData job) {
        String title = job.getPosition();
        String jobLink = job.getJob_link();

        if (title.matches("^[\\d,+]+.*")) {
            return Optional.empty();
        }

        String requirements = fetchJobDetails(jobLink);
        String generatedResume = generateResume(getResumeText(Constants.JobApplicant.KARTHIK.getFullName()), requirements);

        Map<String, Object> jobData = Map.of(
                "title", title,
                "link", jobLink,
                "resume", simplifyHtml(generatedResume),
                "requirements", requirements
        );

        System.out.println("Processed job: " + title);
        return Optional.of(jobData);
    }

    private String generateResume(String currentResume, String jobRequirements) {
        Prompt prompt = new Prompt(String.valueOf(List.of(
                "You are an intelligent assistant that generates professional resumes.",
                "Generate a resume for the candidate using their current resume: " + currentResume +
                        " and job requirements: " + jobRequirements +
                        ". Highlight relevant skills and experiences. Return the resume in a structured, professional HTML format." +
                        " Use semantic HTML elements such as <header>, <section>, <h2>, <ul>, <li>, <p>, and <strong> where appropriate." +
                        " Ensure proper spacing, indentation, and structure for readability." +
                        " Do not include any <think> tags, conclusions, or summary evaluations."
        )));

        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
        return chatResponse != null ? removeThinkTag(chatResponse) : "";
    }
}
