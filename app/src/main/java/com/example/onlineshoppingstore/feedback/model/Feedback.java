package com.example.onlineshoppingstore.feedback.model;

public class Feedback {
    private int id;
    private String authorId;
    private String authorName;
    private String feedback;
    private Integer overall;
    private String date;
    private String updatedAt;

    public Feedback() {
    }
    public Feedback(String authorId, String feedback, Integer overall) {
        this.authorId = authorId;
        this.feedback = feedback;
        this.overall = overall;
    }
    public Feedback(int id, String authorId, String feedback, Integer overall) {
        this.id = id;
        this.authorId = authorId;
        this.feedback = feedback;
        this.overall = overall;
    }

    public int getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getFeedback() {
        return feedback;
    }

    public Integer getOverall() {
        return overall;
    }

    public String getDate() {
        return date;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // setters

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setOverall(Integer overall) {
        this.overall = overall;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean isNull() {
        return this.authorId == null || this.authorId.isEmpty();
    }
}

