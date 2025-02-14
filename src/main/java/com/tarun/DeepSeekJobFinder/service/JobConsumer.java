package com.tarun.DeepSeekJobFinder.service;

import com.google.gson.Gson;
import com.tarun.DeepSeekJobFinder.model.JobData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JobConsumer {
    private static final int BATCH_SIZE = 3;
    private static final String TOPIC_NAME = "jobTopic";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JobProcessor jobProcessor;
    private final List<JobData> jobQueue = new ArrayList<>();

    @Autowired
    public JobConsumer(KafkaTemplate<String, String> kafkaTemplate, JobProcessor jobProcessor) {
        this.kafkaTemplate = kafkaTemplate;
        this.jobProcessor = jobProcessor;
    }

    @KafkaListener(topics = "LinkedinJobs", groupId = "linkedin-jobs-group")
    public void consume(JobData job) {
        jobQueue.add(job);
        if (jobQueue.size() >= BATCH_SIZE) {
            jobQueue.forEach(this::processAndPublish);
            jobQueue.clear();
        }
    }

    private void processAndPublish(JobData job) {
        Optional<Map<String, Object>> processedJob = jobProcessor.processJob(job);
        processedJob.ifPresent(jobData -> {
            String payload = new Gson().toJson(jobData);
            kafkaTemplate.send(TOPIC_NAME, payload);
            System.out.println("Sent event to Kafka: " + payload);
        });
    }
}
