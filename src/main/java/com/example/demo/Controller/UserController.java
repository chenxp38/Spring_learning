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
		u.setUid("001");
		u.setOpenid("openid001");
		u.setName("chen");
		u.setBalance(18);
		u.setPassword("cxp");
		u.setSex("man");
		
		return u;
	}
	
	@RequestMapping("/getUserJson")
//	@ResponseBody
	public JSONResult getUserJson() {
		
		User u = new User();
		u.setUid("001");
		u.setOpenid("openid001");
		u.setName("chen");
		u.setBalance(18);
		u.setPassword("cxp");
		u.setSex("man");
		
		return JSONResult.ok(u);
	}
}
