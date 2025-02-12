package com.tarun.DeepSeekJobFinder.service;

import com.tarun.DeepSeekJobFinder.model.JobData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JobConsumer {

    @KafkaListener(topics = "LinkedinJobs", groupId = "linkedin-jobs-group")
    public void consume(JobData job) {
        System.out.println("Received job:");
        System.out.println("ID: " + job.getJob_id());
        System.out.println("Company: " + job.getCompany());
        System.out.println("Position: " + job.getPosition());
        System.out.println("Location: " + job.getLocation());
        System.out.println("Description: " + job.getDescription());
        System.out.println("Link: " + job.getJob_link());
        System.out.println("--------------------------------------------------");
    }
}
