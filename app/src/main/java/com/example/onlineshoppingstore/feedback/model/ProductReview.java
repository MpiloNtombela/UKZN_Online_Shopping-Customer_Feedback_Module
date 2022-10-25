package com.example.onlineshoppingstore.feedback.model;

// this model was going to be used to store reviews, but I decided to to use it because we didn't have other modules to use it with from other groups
public class ProductReview {
    private Integer id;
    private Integer productId;
    private String authorId;
    private Double rating;
    private String comment;
    private String date;

    public ProductReview() {

    }

    public ProductReview(String authorId, Integer productId, Double rating, String comment) {
        this.authorId = authorId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }

    public ProductReview(Integer id, Integer productId, String authorId, Double rating, String comment, String date) {
        this.id = id;
        this.productId = productId;
        this.authorId = authorId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
