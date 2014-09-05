package com.wadpam.tracker.opengraph;

/**
 *
 * @author osandstrom
 */
public class FitnessRuns extends StandardObject {
    
    private String course;
    private String privacy;
    private String start_time;
    private Integer expires_in;
    
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPrivacy() {
        return privacy;
    }

    public String getStart_time() {
        return start_time;
    }

    public Integer getExpires_in() {
        return expires_in;
    }
    
    
}
