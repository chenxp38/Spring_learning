package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Notice { //通知列表，不常更新
    String notice_id;
    String content;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date date;

    public Notice(String notice_id_, String content_, Date date_) {
        notice_id = notice_id_;
        content = content_;
        date = date_;
    }
    public String getNotice_id() {
        return notice_id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
