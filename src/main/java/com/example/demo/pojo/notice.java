package com.example.demo.pojo;

import java.util.Date;

public class notice { //通知列表，不常更新
    String notice_id;
    String content;
    Date date;

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
