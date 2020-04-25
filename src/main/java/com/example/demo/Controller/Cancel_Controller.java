package com.example.demo.Controller;

import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


@RequestMapping("/Cancel")
@RestController
public class Cancel_Controller {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String venue = "", uid;
    Integer cost, balance, inventory;


    @RequestMapping("/gotoCancel")
    @ResponseBody
    public JSONResult updateInfo(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "order_id") String order_id) {
        //获取当前时间,H大写为24小时制，h小写为12小时制。
        String current_time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        Integer hour = (current_time.charAt(0) - 48)*10 + (current_time.charAt(1) - 48);

        //获取当前时间,H大写为24小时制，h小写为12小时制。
        String current_time2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String order_time = null;
        try {
            String sql = "select * from Order_table where order_id = ?";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            preparedStatement.setString(1, order_id);
            //执行SQL
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                uid = resultSet.getString("uid");
                cost = resultSet.getInt("cost");
                venue = resultSet.getString("venue");
                Date date = resultSet.getDate("date");
                order_time = new SimpleDateFormat("yyyy-MM-dd").format(date);
            }
            Integer is_bigger = is_bigger(current_time2, order_time);//返回值1， current_time2大， 0，相等， -1，current_time2小
            if (is_bigger == 1) { //order_time小于current_time2,在当前时间之前,过期不可退订
                return JSONResult.errorMsg("已过期，不能退订！");
            }else if(is_bigger == 0 && hour >= 12){
                return JSONResult.errorMsg("已过期，不能退订！");
            }else if(is_bigger == -1 || is_bigger == 0 && hour < 12){
                String sql2 = "update Order_table set status = ? where order_id = ?";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql2);
                preparedStatement.setInt(1, 0);
                preparedStatement.setString(2, order_id);
                preparedStatement.executeUpdate();
            }
            //退订成功,运动时退还,库存量加一
            //先查询余额和库存量
            String sql3 = "select * from User where uid = ?";
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql3);
            //设置参数值
            preparedStatement.setString(1, uid);
            //执行SQL
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                balance = resultSet.getInt("balance");
            }

            String sql4 = "select * from Inventory where location = ?";
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql4);
            //设置参数值
            preparedStatement.setString(1, venue);
            //执行SQL
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                inventory = resultSet.getInt("inventory");
            }



            String sql5 = "update Inventory set inventory = ? where location = ?";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql5);
            preparedStatement.setInt(1, inventory + 1);
            preparedStatement.setString(2, venue);
            preparedStatement.executeUpdate();

            String sql6 = "update User set balance = ? where uid = ?";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql6);
            preparedStatement.setInt(1, balance + cost);
            preparedStatement.setString(2, uid);
            preparedStatement.executeUpdate();

            return JSONResult.ok(order_id);
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
        return JSONResult.ok("");
    }

    public Integer is_bigger(String current_time2, String order_time) {
        //返回值1， current_time2大， 0，相等， -1，current_time2小
        if (order_time.charAt(0) < current_time2.charAt(0))
            return 1;
        if (order_time.charAt(0) > current_time2.charAt(0))
            return -1;

        if (order_time.charAt(1) < current_time2.charAt(1))
            return 1;
        if (order_time.charAt(1) > current_time2.charAt(1))
            return -1;

        if (order_time.charAt(2) < current_time2.charAt(2))
            return 1;
        if (order_time.charAt(2) > current_time2.charAt(2))
            return -1;

        if (order_time.charAt(3) < current_time2.charAt(3))
            return 1;
        if (order_time.charAt(3) > current_time2.charAt(3))
            return -1;

        if (order_time.charAt(5) < current_time2.charAt(5))
            return 1;
        if (order_time.charAt(5) > current_time2.charAt(5))
            return -1;

        if (order_time.charAt(6) < current_time2.charAt(6))
            return 1;
        if (order_time.charAt(6) > current_time2.charAt(6))
            return -1;

        if (order_time.charAt(8) < current_time2.charAt(8))
            return 1;
        if (order_time.charAt(8) > current_time2.charAt(8))
            return -1;

        if (order_time.charAt(9) < current_time2.charAt(9))
            return 1;
        if (order_time.charAt(9) > current_time2.charAt(9))
            return -1;

        return 0;
    }

}
