package com.example.demo.Controller;

import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequestMapping("/Balance")
@RestController
public class BalanceController {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Integer balance = 0;

    @RequestMapping("/getBalance")
    @ResponseBody
    public JSONResult getBalance(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "uid") String uid) {

        try {
            String sql = "select * from User where uid = ?";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            preparedStatement.setString(1, uid);
            //执行SQL
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {//即使只有一条检索结果，也不能图省事去掉while，因为要判断resultSet，防止为null，用if判断应该也行
                balance = resultSet.getInt("balance");
            }
            return JSONResult.ok(balance);
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
        return JSONResult.ok(balance);
    }

}
