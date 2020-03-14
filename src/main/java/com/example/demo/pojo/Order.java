package com.example.demo.pojo;

import java.util.Date;

public class Order {
    String order_id;
    String uid;
    Date date;
    String venue;//场馆
    Integer cost;//一般为负数
    Boolean status; //是否完成订单（使用场馆）

    public String getOrder_id() {
        return order_id;
    }

    public String getUid() {
        return uid;
    }

    public Date getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public Integer getCost() {
        return cost;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setVenue(String venue) {
        venue = venue;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
