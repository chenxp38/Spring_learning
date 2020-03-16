package com.example.demo.Controller;
import com.example.demo.DemoApplication;
import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.RedisUtils;
import com.example.demo.pojo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.Iterator;
import java.util.Set;

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
     * 获得sessionId
     */
    @RequestMapping("/getSessionId")
    @ResponseBody
    public Object getSessionId(HttpServletRequest request, @RequestParam(name = "openid") String openid) {
        try {
            HttpSession session = request.getSession();
            //session.setMaxInactiveInterval(6000); //方法体内的参数interval为秒。
            System.out.println(openid + session.getId());
            saveSession(session.getId(), openid);
            return session.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
