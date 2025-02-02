package com.tarun.DeepSeekJobFinder.service;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RssService {

    private final ChatClient chatClient;

    public RssService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }


    public List<Map<String, Object>> fetchRssFeed(String feedUrl) {
        List<Map<String, Object>> transformedJobs = new ArrayList<>();

        try {
            // Fetch RSS feed
            Document rssDoc = Jsoup.connect(feedUrl)
                    .userAgent("Mozilla/5.0") // Bypass 403 errors
                    .timeout(5000)
                    .get();

            // Iterate through job listings in RSS feed
            Elements items = rssDoc.select("item");
            for (Element item : items) {
                String title = item.select("title").text();
                String jobLink = item.select("link").text();

                // Fetch job details from job link
                String requirements = fetchJobDetails(jobLink);

                String generatedResume = generateResume(getResumeText(), requirements);
                System.out.println("Generated Resume: " + generatedResume);

                // Construct job JSON object
                Map<String, Object> jobData = new HashMap<>();
                jobData.put("title", title);
                jobData.put("link", jobLink);
                jobData.put("requirements", requirements);
                jobData.put("resume", getResumeText()); // Predefined resume

                // Add to final response list
                transformedJobs.add(Map.of("json", jobData));
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transformedJobs;
    }
    private String fetchJobDetails(String jobLink) {
        try {
            Document jobPage = Jsoup.connect(jobLink)
                    .userAgent("Mozilla/5.0") // Mimic a browser
                    .timeout(5000)
                    .get();

            // Extract the 4th <ul> content
            return extractFourthUlContent(jobPage);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch job details";
        }
    }
    // Extracts the content of the 4th <ul> tag from job HTML page
    private String extractFourthUlContent(Document jobPage) {
        Elements ulElements = jobPage.select("ul");

        if (ulElements.size() >= 4) {
            String fourthUlContent = ulElements.get(4).html(); // Get 4th <ul> (index 3)
            return cleanHtml(fourthUlContent);
        }
        return "No fourth UL found";
    }
    // Cleans up HTML tags and converts to plain text
    private String cleanHtml(String html) {
        return html.replaceAll("<[^>]+>", " ")  // Remove all HTML tags
                .replace("&amp;", "&")         // Decode HTML entities
                .replaceAll("\\s+", " ")       // Collapse whitespace
                .trim();
    }
    // Predefined resume text
    private String getResumeText() {
        return """
                Name: Tarun Dhiman
                Location: Ambala, Haryana
                Email: tarundhiman8572@gmail.com
                Phone: 8572008222
                LinkedIn: https://www.linkedin.com/in/tarun-dhiman-83085a160/
                GitHub: https://github.com/tarundhiman85
                Summary
                A full-stack software engineer with extensive experience in Java backend development and React frontend applications. Adept at building enterprise-scale applications utilizing Spring Boot and React.js, with a focus on scalability and efficiency. Demonstrates expertise in developing time-management systems, user authentication frameworks, API integrations, and maintaining high code quality through test-driven development.
                
                Experience
                Byteridge
                SDE-II (Aug 2024 – Present)
                
                Developed a time management system using Java/Spring Boot microservices, including KPI tracking and employee time logging.
                Optimized PostgreSQL database schemas, improving query performance by 40%.
                Integrated Prometheus for real-time monitoring, reducing downtime by 25%.
                Achieved comprehensive test coverage with JUnit and Mockito, reducing bugs by 30%.
                Ensured seamless production releases through CI/CD pipelines.
                SDE-I (June 2022 – Aug 2024)
                
                Created responsive React.js frontends for enterprise applications.
                Built scalable Java Spring Boot backends with authentication and data aggregation features.
                Enhanced application performance by 25% using caching and optimized APIs.
                Designed reusable React components and dynamic routing with Redux for state management.
                Education
                B.Tech in Computer Science and Engineering – UIET Kurukshetra (2019 – 2022)
                CGPA: 8.63
                Achievements
                AWS Certified Developer Associate.
                Solved 850+ DSA questions on LeetCode.
                Earned the LeetCode Yearly Consistency Badge.
                Skills
                Languages & Frameworks: Java, JavaScript, TypeScript, React.js, Spring Boot, Node.js, Hibernate.
                Software Development: OOP, TDD, Distributed Systems, Design Patterns.
                Testing: JUnit, Mockito, Performance Testing.
                Databases: PostgreSQL, MongoDB, MySQL.
                DevOps & Cloud: Docker, Kubernetes, AWS, CI/CD.
                Core Competencies: Data Structures, Algorithms, Problem-Solving.
                Project Management: Agile Methodologies, Team Leadership.
                Projects
                Byteridge Hub - Time Tracking System
                Developed an employee time-tracking system with KPI modules.
                Integrated Teams-Bot for reminders and alerts.
                Utilized Docker and CI/CD pipelines for seamless deployments.
                CCMR3 - Financial Services Platform
                Designed Access Matrix and Routing functionality for secure user access.
                Implemented AWS Lambda cron jobs for notifications.
                Y-AXIS - Immigration Services Portal
                Developed forms and a ticketing system for user issue tracking.
                Built country-specific signup and login functionalities.
                Ottagivv - Payment Processing System
                Managed payment processes with KYC integration using Sila and Plaid.
                Scaled the application using Redis and NodeJS Worker Threads.
                JARVIS - Hiring Portal
                Enhanced search functionality and UI with reusable components.
                Optimized data processing with Kafka consumers and producers.
                Personal Projects
                Banking System: A web app integrating stock trading and banking features with secure session management.
                E-commerce Application: Developed an intuitive e-commerce platform with advanced features like cart management and order tracking.     \s
                """;
    }
    private String generateResume(String currentResume, String jobRequirements) {
        Prompt prompt = new Prompt(String.valueOf(List.of(
                "You are an intelligent assistant that generates professional resumes.",
                "Generate a resume for the candidate using their current resume: " + currentResume +
                        " and job requirements: " + jobRequirements +
                        ". Highlight relevant skills and experiences. Return the resume in plain text."
        )));
        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
        assert chatResponse != null;
        return chatResponse.getResults().get(0).getOutput().getContent();
    }
}