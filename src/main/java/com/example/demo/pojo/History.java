package com.example.demo.pojo;

public class History {
    String uid;
    String order_id;
    String venue;
    String date;
    Integer cost;

    public History(String uid, String order_id, String dateString, String venue, Integer cost) {
        this.uid = uid;
        this.order_id = order_id;
        this.date = dateString;
        this.venue = venue;
        this.cost = cost;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Integer getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
