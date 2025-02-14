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
        } else if("Karthik".equalsIgnoreCase(name)) {
            return """
                    HKarthik
                     karthik63254@gmail.com | 📞 +91 9810971369
                    
                          EDUCATION
                          GL Bajaj Institute of Technology and Management
                          B. Tech in Computer Science (2020-2024) | Greater Noida, UP
                          CGPA: 8.5
                    
                          Assisi Convent School
                          Intermediate (PCM) | 2020 | Noida, UP
                          Percentage: 90.8%
                    
                          High School | 2018 | Noida, UP
                          Percentage: 90.33%
                    
                          LINKS
                          Leetcode: https://leetcode.com/HK/
                          CodeChef: https://www.codechef.com/users/karthik6325
                          CodeForces: https://codeforces.com/profile/karthik
                          GitHub: https://github.com/karthik6325
                          LinkedIn: https://www.linkedin.com/in/karthik-h-3b6332192/
                          Portfolio: https://karthikhportfolio.netlify.app/
                          COURSEWORK
                          Object-Oriented Programming
                          Operating Systems
                          Database Management System
                          Computer Networks
                          Data Structures and Algorithms
                          SKILLS
                          Programming Languages:
                          Python
                          C/C++
                          JavaScript
                          Frameworks/Libraries:
                          .NET Core
                          Node.js
                          Express.js
                          React.js
                          Databases:
                          MongoDB
                          PostgreSQL
                          MySQL
                          Redis
                          Language Proficiency:
                          English
                          Hindi
                          Tamil
                          EXPERIENCE
                          BYTERIDGE | Software Development Engineer I
                          July 2024 - Present | Remote
                    
                          Developed Hub, a centralized platform for employee performance evaluations and mentoring, featuring a modular design for scalability.
                          Automated KPI submission and review processes using .NET Core, reducing evaluation time by 80% and achieving $20,000 in annual cost savings.
                          Worked on back-end development with PostgreSQL, implementing Graph APIs, database seeding, and migrations, while designing data models for optimal performance.
                          Enabled mentor requests and structured performance tracking, improving productivity by 15-20%.
                          BYTERIDGE | Software Developer Intern
                          Oct 2023 - Dec 2023 | Remote
                    
                          Worked on Scrum Poker Planning project involving full-stack development.
                          Integrated Redis as a caching mechanism with MongoDB to optimize the application’s memory usage.
                          Implemented admin controls, enhancing overall usability.
                          Used Socket.io for real-time voting, task updates, and participant management in a room. Also used Next.js for efficient server-side rendering.
                          Integrated Jira into the Scrum Poker Planning using OAuth authentication, allowing users to fetch and modify project details.
                          Developed proficiency in Next.js, Express, Node.js, React, TypeScript, MongoDB, Redis, and Tailwind CSS.
                          PROJECTS
                          HEALTHAI - DIET RECOMMENDATION
                          Developed a diet recommendation system using spaCy, Python, and pandas.
                          Utilized k-means clustering to categorize recipes into breakfast, lunch, and dinner.
                          Implemented parallel processing for efficient computation.
                          Integrated K-Nearest Neighbors (KNN) model for predicting recipes based on diet preference for weight gain, weight loss, and maintenance.
                          Implemented an NLP-based diet recommendation system by comparing recipes with recommended ingredients for specific health conditions.
                          Used Flask to create API endpoints for diet recommendations and developed a MERN stack-based UI.
                          Project Link: https://github.com/karthik6325/Diet-recommendation
                          FIND-DONOR
                          Developed a MERN web application that connects blood donors with those in need.
                          Users can create accounts and register as donors or seekers, storing their information in a database.
                          When seekers search for donors based on city and blood type, the application displays a list of matching donors.
                          Technologies used: HTML, CSS, JavaScript, React, Express, MongoDB.
                          Project Link: https://github.com/karthik6325/FindDonor
                          ACHIEVEMENTS
                          Solved 800+ questions on LeetCode, 1700+ rated.
                          3 stars on CodeChef (Max rating 1607).
                          Codeforces rating 1171 (Max rating).
                          Global Rank 1600 - Google Kickstart Round H 2022.
                          Solved 1200+ questions on various coding platforms.""";
        } else {
            return "Resume not found for " + name;
        }
    }
}
