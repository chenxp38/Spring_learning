package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Student {
    private String name;
    private String studentID;

    @JsonIgnore
    private String password;
    private Integer balance;
    private String sex;

    public String getName() {
        return name;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getPassword() {
        return password;
    }

    public Integer getBalance() {
        return balance;
    }

    public String getSex() {
        return sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
