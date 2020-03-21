package com.example.demo.Controller;

import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequestMapping("/Advice")
@RestController
public class AdviceController {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Integer length = 0;

    @RequestMapping("/getAdvice")
    @ResponseBody
    public JSONResult getAdvice(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "advice") String advice, @RequestParam(name = "email") String email) {

        try {
            String sql = "select * from Advice";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            //preparedStatement.setString(1, uid);
            //执行SQL
            resultSet = preparedStatement.executeQuery();
            resultSet.last();//函数的返回结果是当前数据集的行号，而不是结果的行数
            length = resultSet.getRow();
            resultSet.beforeFirst();

            String sql2 = "insert into Advice(id, advice, email) VALUES (?, ?, ?)";
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql2);
            //设置参数值
            preparedStatement.setInt(1, length + 1);
            preparedStatement.setString(2, advice);
            preparedStatement.setString(3, email);
            //System.out.println("name:..........."+ user.getName());
            //执行SQL
            preparedStatement.executeUpdate();
            return JSONResult.ok(email);
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
        return JSONResult.ok(email);
    }

}
