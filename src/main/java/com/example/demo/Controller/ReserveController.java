package com.example.demo.Controller;

import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.Notice;
import com.example.demo.pojo.User;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.Controller.RedisLinkTest.getSessionValue;

@RequestMapping("/Reserve")
@RestController
public class ReserveController {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @RequestMapping("/reserve")
    @ResponseBody
    public JSONResult user_reserve(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "venue") String venue) {
        //System.out.println(venue);
        Integer inventory = 0;
        String openid = null;
        String uid = null;


        try {
            String sql = "select * from inventory where location = ?";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            preparedStatement.setString(1, venue);
            //执行SQL
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {//即使只有一条检索结果，也不能图省事去掉while，因为要判断resultSet，防止为null，用if判断应该也行
                String location = resultSet.getString("location");
                inventory = resultSet.getInt("inventory"); //库存量
                openid = getSessionValue(SessionID); //获取openid
                System.out.println(location + " " + inventory + " " + openid);
            }
            if (inventory > 0) {
                String sql2 = "select * from User where openid = ?";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql2);
                //设置参数值
                preparedStatement.setString(1, openid);
                //执行SQL
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {//用if判断不行，亲测
                    uid = resultSet.getString("uid");

                }
            }else {

            }


            return JSONResult.ok(venue);
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
        return JSONResult.ok("user");
    }
}
