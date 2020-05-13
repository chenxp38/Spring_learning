package com.example.demo.Controller;

import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.History;
import com.example.demo.pojo.JSONResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/History")
@RestController
public class HistoryController {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Integer cost = 0;
    String order_id = "";
    String venue = "";
    String date = "";
    List<History> list = new ArrayList<History>();
    Integer count = 0;
    @RequestMapping("/getHistory")
    @ResponseBody
    public JSONResult getBalance(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "uid") String uid) {

        try {
            String sql = "select * from Order_table where uid = ? and status = 1 ORDER BY date desc, order_id";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            preparedStatement.setString(1, uid);
            //执行SQL
            resultSet = preparedStatement.executeQuery();
            count = 0;
            list.clear();

            while (resultSet.next()) {//即使只有一条检索结果，也不能图省事去掉while，因为要判断resultSet，防止为null，用if判断应该也行
                cost = resultSet.getInt("cost");
                order_id = resultSet.getString("order_id");
                venue = resultSet.getString("venue");
                Date date = resultSet.getDate("date");
                String time = resultSet.getString("time");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                dateString = dateString + " " + time;
                History history = new History(uid, order_id, dateString, venue, cost);
                list.add(history);
                count++;
            }
            return JSONResult.ok2(list, count);
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
        return JSONResult.ok(list);
    }

}
