package com.tarun.DeepSeekJobFinder.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.ai.chat.model.ChatResponse;

public class Utils {
    public static String removeThinkTag(ChatResponse chatResponse) {
        assert chatResponse != null;
        String rawResume = chatResponse.getResults().get(0).getOutput().getContent();

        return rawResume
                .replaceAll("(?s)<think>.*?</think>", "").trim();
    }
    public static String simplifyHtml(String htmlContent) {
        String minified = htmlContent.replaceAll("\\r|\\n", "").replaceAll("^```html\\s*|```$", "");
        minified = minified.replaceAll("\\s+", " ");
        minified = minified.replaceAll("> <", "><");
        return minified.trim();
    }
    public static String cleanHtml(String html) {
        return html.replaceAll("<[^>]+>", " ")
                .replace("&amp;", "&")
                .replaceAll("\\s+", " ")
                .trim();
    }
    private static String extractFourthUlContent(Document jobPage) {
        Elements ulElements = jobPage.select("ul");

        if (ulElements.size() >= 4) {
            String fourthUlContent = ulElements.get(4).html();
            return cleanHtml(fourthUlContent);
        }
        return "No fourth UL found";
    }
    public static String fetchJobDetails(String jobLink) {
        try {
            Document jobPage = Jsoup.connect(jobLink)
                    .userAgent("Mozilla/5.0") // Mimic a browser
                    .timeout(5000)
                    .get();
            return extractFourthUlContent(jobPage);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch job details";
        }
    }
    public static String getResumeText(String name) {
        if ("Tarun".equalsIgnoreCase(name)) {
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
        } else if ("Parth".equalsIgnoreCase(name)) {
            return """
Parth Sharma
Software Engineer, Haryana, India

Email: iparth@hotmail.com
LinkedIn: linkedin.com/in/parth
Phone: +91 816857823

Summary
Skilled and motivated Full Stack Developer with hands-on experience in ReactJs, Node.js, MongoDB, Firebase, and Stripe.
Proven ability to build and manage scalable applications with an emphasis on developing efficient, reliable, and user-friendly software solutions.

Education
UIET, Kurukshetra, Haryana, India
B.Tech [CSE] (2019 - 2022)
➢ CGPA: 7.7

Experience
SDE Intern at GaoTek Inc. (Aug 2023 - Dec 2024)
As a MERN stack intern at Gaotek Inc., I developed and maintained web applications using MongoDB, Express.js, React, and Node.js.
I contributed to front-end and back-end development, implementing features and fixing bugs.
I also collaborated with the team on design and code reviews.

Summer Intern at Wipro (Aug 2022 - Aug 2023)
• Worked in a team to develop a Food App with Payment Capabilities.
• Responsible for creating RESTFul API in Node.js.

Projects
Netflix-Clone App (ReactJs/NodeJS/Javascript/MongoDB/Firebase/HTML/CSS)
➢ A web application built using ReactJs, NodeJS, JavaScript, MongoDB, Firebase, HTML, and CSS.
➢ Backend powered by NodeJS and MongoDB, ensuring robust and scalable data storage.
➢ Firebase used for authentication and authorization.
🔗 Live App: Netflix Clone https://netflix-clone-fallnxt.web.app/
🔗 GitHub Repo: Netflix Clone GitHub https://github.com/ParthSharma00700/NetflixClone/tree/master

GoFood App (ReactJs/NodeJS/Javascript/MongoDB/Firebase/HTML/CSS)
➢ A web application where users can browse menus, add items to their cart, and checkout securely.
➢ Firebase is used for authentication and payment processing.
🔗 Live App: GoFood https://gofoodxt.web.app/
🔗 GitHub Repo: GoFood GitHub https://github.com/ParthSharma00700/GoFood

Skills
• Frontend: ReactJs, HTML, CSS
• Backend: NodeJs, JavaScript, MongoDB, Firebase

Accomplishments
• Web Development Training - Internshala https://drive.google.com/file/d/1Ptpbir1yohlv83BYLDizmaZ9qukBPF1u/view?usp=sharing
• Python Training - Internshala https://drive.google.com/file/d/1nCzarvzMPF52vaCDu-A2KSuIj9gSqLgU/view?usp=sharing
• Ethical Hacking Training - Internshala https://drive.google.com/file/d/1PO63HSBdtoc0u3kNRzi9M5-TUNYuD3CS/view?usp=sharing


Profiles
🔗 GitHub: https://github.com/ParthSharma00700
🔗 CodeChef: https://www.codechef.com/users/stark2077

 """;
        } else {
            return "Resume not found for " + name;
        }
    }
}
