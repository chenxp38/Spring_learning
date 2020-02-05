package com.example.demo.Controller;



import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/Modified")
@RestController
public class AcceptController {

    @GetMapping("/demo/{id}")
    public void demo(@PathVariable(name = "id") String id, @RequestParam(name = "name") String name) {
        System.out.println("id="+id);
        System.out.println("name="+name);
    }

    @RequestMapping("/getUser")
    public JSONResult getUser(@RequestHeader(name = "SessionID") String SessionID, @RequestBody User user) {
        System.out.println(SessionID+user.getName());
        return JSONResult.ok(user);
    }



}
