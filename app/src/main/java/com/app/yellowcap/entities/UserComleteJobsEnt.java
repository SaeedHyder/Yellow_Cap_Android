package com.app.yellowcap.entities;

/**
 * Created on 5/25/2017.
 */

public class UserComleteJobsEnt {
    private String Job;
    private String jobCompleted;
    private String TechnicianName;
    private String JobTitle;
    private float rating;
    private String amountPaid;
    private String description;

    public UserComleteJobsEnt(String job,
                              String jobCompleted,
                              String technicianName,
                              String jobTitle,
                              float rating,
                              String amountPaid,
                              String description) {
        Job = job;
        this.jobCompleted = jobCompleted;
        TechnicianName = technicianName;
        JobTitle = jobTitle;
        this.rating = rating;
        this.amountPaid = amountPaid;
        this.description = description;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getJobCompleted() {
        return jobCompleted;
    }

    public void setJobCompleted(String jobCompleted) {
        this.jobCompleted = jobCompleted;
    }

    public String getTechnicianName() {
        return TechnicianName;
    }

    public void setTechnicianName(String technicianName) {
        TechnicianName = technicianName;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
