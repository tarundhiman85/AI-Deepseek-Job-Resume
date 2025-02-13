package com.tarun.DeepSeekJobFinder.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JobData {
    private String job_id;
    private String company;
    private String position;
    private String location;
    private String description;
    private String job_link;
}
