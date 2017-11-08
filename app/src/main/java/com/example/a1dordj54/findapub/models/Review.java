package com.example.a1dordj54.findapub.models;

public class Review {

    protected double rating;
    protected String review;

    public Review(double rating, String review){
        this.rating = rating;
        this.review = review;
    }

    public String getReview(){
        return this.review;
    }

    public double getRating(){
        return this.rating;
    }

}
