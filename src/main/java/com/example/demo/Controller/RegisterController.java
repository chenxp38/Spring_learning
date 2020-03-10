package com.example.demo.Controller;
import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RequestMapping("/register")
@RestController
public class RegisterController {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @RequestMapping("/register")
    public JSONResult user_register(@RequestHeader(name = "SessionID") String SessionID, @RequestBody User user) {
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
                System.out.println("create..........."+ uid + " " + password);
                if (uid.equals(user.getUid())) {
                    return JSONResult.errorMsg("该用户已存在！");
                }
            }
            String sql2 = "select * from User where openid = ?";
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql2);
            //设置参数值
            preparedStatement.setString(1, user.getOpenid());
            //执行SQL
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //得到返回结果值
                String openid = resultSet.getString("openid");
                if (openid.equals(user.getOpenid())) {
                    return JSONResult.errorMsg("该微信已被注册过！");
                }
            }

            String sql3 = "insert into User(uid, openid, name, password, balance, sex) VALUES (?, ?, ?, ?, ?, ?)";
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql3);
            //设置参数值
            preparedStatement.setString(1, user.getUid());
            preparedStatement.setString(2, user.getOpenid());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getBalance());
            preparedStatement.setString(6, user.getSex());

            //执行SQL
            preparedStatement.executeUpdate();
            return JSONResult.ok(user);
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
        return JSONResult.ok(user);
    }
}
