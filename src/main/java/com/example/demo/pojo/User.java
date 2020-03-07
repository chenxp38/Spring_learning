package com.example.demo.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	private String uid;
	@JsonIgnore
	private String openid;
	@JsonInclude(Include.NON_NULL)
	private String name;
	

	private String password;//@JsonIgnore在post返回user时，忽略password的属性
	private Integer balance;
	@JsonInclude(Include.NON_NULL)
	private String sex;
	//@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss a", locale="zh", timezone="GMT+8")
	//private Date birthday;


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getBalance() {
		return balance;
	}

	public String getSex() {
		return sex;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
