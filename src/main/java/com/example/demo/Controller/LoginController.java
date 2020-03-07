package com.example.demo.Controller;
import com.example.demo.DemoApplication;
import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RequestMapping("/Login")
@RestController
public class LoginController {

    @RequestMapping("/register")
    public JSONResult user_register(@RequestHeader(name = "SessionID") String SessionID, @RequestBody User user) {
        //System.out.println(SessionID+user.getName());
        System.out.println(SessionID+user.getName());
        return JSONResult.ok(user);

    }

    @RequestMapping(path = "/sign_in")
    public JSONResult user_sign_in(@RequestHeader(name = "SessionID") String SessionID, @RequestBody User user) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        System.out.println(user.getUid());
        try {
            // 加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            long start = System.currentTimeMillis();

            // 建立连接
            conn = DriverManager.getConnection("jdbc:mysql://101.132.157.149:3306/gym_database?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "root");
            long end = System.currentTimeMillis();
            System.out.println(conn);
            System.out.println("建立连接耗时： " + (end - start) + "ms 毫秒");

            // 创建Statement对象
            stmt = conn.createStatement();

            // 执行SQL语句
            rs = stmt.executeQuery("select * from User");
            while (rs.next()) {
                if (rs.getString(1).equals(user.getUid()) && rs.getString(4).equals(user.getPassword())) {
                    return JSONResult.ok(user);
                }
                else {
                    return JSONResult.errorMsg("Failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return JSONResult.ok(user);
    }

}
