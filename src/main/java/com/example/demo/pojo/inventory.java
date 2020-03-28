package com.example.demo.pojo;

public class inventory {
    String id;
    String location;
    Integer inventory;  //库存量
    boolean is_remainder; //是否剩余
    Integer cost;

    public inventory(String id_, String location_, Integer inventory_, boolean is_remainder_, Integer cost_){
        id = id_;
        location = location_;
        inventory = inventory_;
        is_remainder = is_remainder_;
        cost = cost_;
    }
    public String getId() {
        return id;
    }

    public Integer getInventory() {
        return inventory;
    }

    public String getLocation() {
        return location;
    }

    public boolean isIs_remainder() {
        return (inventory > 0);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public void setIs_remainder(boolean is_remainder) {
        this.is_remainder = is_remainder;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "inventory{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", inventory=" + inventory +
                ", is_remainder=" + is_remainder +
                ", cost=" + cost +
                '}';
    }
}
