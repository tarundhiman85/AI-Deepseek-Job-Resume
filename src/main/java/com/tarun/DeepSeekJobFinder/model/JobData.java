package com.tarun.DeepSeekJobFinder.model;

public class JobData {
    private String job_id;
    private String company;
    private String position;
    private String location;
    private String description;
    private String job_link;  // <-- Ensure this field is correctly named

    // Getters and Setters
    public String getJob_id() { return job_id; }
    public void setJob_id(String job_id) { this.job_id = job_id; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getJob_link() { return job_link; }  // <-- Fix getter method name
    public void setJob_link(String job_link) { this.job_link = job_link; }  // <-- Fix setter method
}
