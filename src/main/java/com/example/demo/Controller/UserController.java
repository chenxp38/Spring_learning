package com.example.demo.Controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.User;

//@Controller
@RestController		// @RestController = @Controller + @ResponseBody
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/getUser")
//	@ResponseBody
	public User getUser() {
		
		User u = new User();
		u.setName("chen");
		u.setAge(18);
		u.setBirthday(new Date());
		u.setPassword("cxp");
		u.setDesc("getUser");
		
		return u;
	}
	
	@RequestMapping("/getUserJson")
//	@ResponseBody
	public JSONResult getUserJson() {
		
		User u = new User();
		u.setName("chen3");
		u.setAge(18);
		u.setBirthday(new Date());
		u.setPassword("cxp3");
		u.setDesc("getUserJson3");
		
		return JSONResult.ok(u);
	}
}
