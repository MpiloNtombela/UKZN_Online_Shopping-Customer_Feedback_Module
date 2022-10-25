package com.example.onlineshoppingstore.feedback.model;

import java.util.ArrayList;
import java.util.List;

public class ServicesRating {
    private int id;
    private String raterId;
    private Integer pricing;
    private Integer quality;
    private Integer delivery;
    private Integer customerService;
    private Integer productSelection;

    public ServicesRating() {
    }

    public ServicesRating(String raterId, Integer pricing, Integer quality, Integer delivery, Integer productSelection, Integer customerService) {
        this.raterId = raterId;
        this.pricing = pricing;
        this.quality = quality;
        this.delivery = delivery;
        this.customerService = customerService;
        this.productSelection = productSelection;
    }


    public int getId() {
        return id;
    }

    public String getRaterId() {
        return raterId;
    }

    public Integer getPricing() {
        return pricing;
    }

    public Integer getQuality() {
        return quality;
    }

    public Integer getDelivery() {
        return delivery;
    }

    public Integer getCustomerService() {
        return customerService;
    }

    public Integer getProductSelection() {
        return productSelection;
    }

    public void setId(int id) {
        this.id = id;
    }

    // setters

    public void setRaterId(String authorId) {
        this.raterId = authorId;
    }

    public void setPricing(Integer pricing) {
        this.pricing = pricing;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public void setCustomerService(Integer customerService) {
        this.customerService = customerService;
    }

    public void setProductSelection(Integer productSelection) {
        this.productSelection = productSelection;
    }

    public boolean isNull() {
        return this.raterId == null || this.raterId.isEmpty();
    }

}
