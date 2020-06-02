package com.example.demo.Controller;
import com.example.demo.DemoApplication;
import com.example.demo.pojo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.internal.util.StringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;

import static com.example.demo.Controller.RedisLinkTest.saveSession;

@RequestMapping("/Login")
@RestController
public class LoginController {
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Connection connection = null;

    @RequestMapping(path = "/sign_in")
    public JSONResult user_sign_in(@RequestHeader(name = "SessionID") String SessionID, @RequestBody User user) {

        try {
            String sql = "select * from User where uid = ?";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            preparedStatement.setString(1, user.getUid());
            //执行SQL
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //得到返回结果值
                String uid = resultSet.getString("uid");
                String password = resultSet.getString("password");
                System.out.println("user..........."+ uid + " " + password);
                if (uid.equals(user.getUid()) && password.equals(user.getPassword())) {
                    return JSONResult.ok(user);
                }else if (uid.equals(user.getUid()) && !password.equals(user.getPassword())){
                    return JSONResult.errorMsg("密码错误！");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                DB_User.close(connection);
            }
        }
        return JSONResult.errorMsg("没有该用户……");
    }

    /**
     * 获得openid和sessionId
     */
    @RequestMapping("/getOpenid_SessionId")
    @ResponseBody
    public List<String> getOpenid_SessionId(HttpServletRequest request, @RequestParam(name = "openid_code") String openid_code) throws JsonProcessingException {
        String result = "";
        String appid = "wx5fb04c9b878a70ee";
        String app_secret = "614fa56d4eb1dfdd01d35877b6cd82ad";
        String openid = "";
        String sessionID = "";
        List<String> list = new ArrayList<String>();
        try{//请求微信服务器，用code换取openid。HttpUtil是工具类，后面会给出实现，Configure类是小程序配置信息，后面会给出代码
            result = HttpUtil.doGet(
                    "https://api.weixin.qq.com/sns/jscode2session?appid="
                            + appid + "&secret="
                            + app_secret + "&js_code="
                            + openid_code
                            + "&grant_type=authorization_code", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        OpenIdJson openIdJson = mapper.readValue(result,OpenIdJson.class);
        System.out.println("getOpenid123:" + result.toString());
        // System.out.println("getOpenid123:" + openIdJson.getSession_key());
        System.out.println("getOpenid:" + openIdJson.getOpenid());
        openid = openIdJson.getOpenid();
        list.add(openid);
        sessionID = generateId();
        list.add(sessionID);
        try {
            HttpSession session = request.getSession(true);
            //session.setMaxInactiveInterval(6000); //方法体内的参数interval为秒。

            System.out.println("openid: " + openid + " sessionID: " + sessionID);
            saveSession(sessionID, openid);
            //sessionID = session.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("list[%d] = %s\n", i,list.get(i));
        }
        return list;
    }

    public String generateId() {
        System.out.println(UUID.randomUUID().toString());
        return UUID.randomUUID().toString();
    }



}
