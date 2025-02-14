package com.tarun.DeepSeekJobFinder.constants;

import lombok.Getter;

public class Constants {
    public enum JobApplicant {
        TARUN("Tarun"),
        PARTH("Parth"),
        KARTHIK("Karthik");

        private final String fullName;

        public String getFullName() {
            return fullName;
        }

        JobApplicant(String fullName) {
            this.fullName = fullName;
        }
    }
}