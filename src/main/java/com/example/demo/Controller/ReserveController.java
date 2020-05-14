package com.example.demo.Controller;


import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.inventory;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.Controller.RedisLinkTest.getSessionValue;

@RequestMapping("/Reserve")
@RestController
public class ReserveController {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Integer> list = new ArrayList<Integer>();

    @RequestMapping("/getInventory")
    @ResponseBody
    public JSONResult getInventory(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "venue") String venue, @RequestParam(name = "year") Integer year, @RequestParam(name = "month") Integer month, @RequestParam(name = "day") Integer day) {
        list.clear();
        //Integer year = new Integer(2020);
        //Integer month = 5;
        //Integer day = 10;
        Date currentDate = new Date(year - 1900, month - 1, day);
        String venueTest = "东校区游泳池";
        SimpleDateFormat currentDayFomat = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = currentDayFomat.format(currentDate);
        System.out.println(venueTest + sDate);
        try {
            String sql = "select * from Inventory where location = ? and day = ?";

            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            preparedStatement.setString(1, venueTest);
            preparedStatement.setString(2, sDate);
            //执行SQL
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {//即使只有一条检索结果，也不能图省事去掉while，因为要判断resultSet，防止为null，用if判断应该也行
                Integer inventoryM = resultSet.getInt("inventoryM"); //库存量
                Integer inventoryN = resultSet.getInt("inventoryN"); //库存量
                Integer inventoryE = resultSet.getInt("inventoryE"); //库存量
                //boolean is_remainder = resultSet.getBoolean("is_remainder");
                //Integer cost = resultSet.getInt("cost");
                //inventory inventory1 = new inventory(id, location, inventory, is_remainder, cost);
                System.out.println(inventoryM + " " + inventoryN + " " + inventoryE);
                list.add(inventoryM);
                list.add(inventoryN);
                list.add(inventoryE);
            }
            Integer length = list.size();
            return JSONResult.ok2(list, length);
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

    @RequestMapping("/reserve")
    @ResponseBody
    public JSONResult userReserve(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "venue") String venue,  @RequestParam(name = "year") Integer year, @RequestParam(name = "month") Integer month,
                                  @RequestParam(name = "day")Integer day, @RequestParam(name = "array") Integer [] array) {

        //String venue = "东校区游泳池";
        //Integer year = 2020, month = 5, day = 13;
        //String [] array = {"6:30-8:00", "16:30-18:00", "19:30-21:00"};
        //String openid = "o_L8C5ZE8t2_ON_fPaHoZWFnEgnE";

        Date currentDate = new Date(year - 1900, month - 1, day);
        SimpleDateFormat currentDayFomat = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = currentDayFomat.format(currentDate);
        System.out.println(venue + sDate);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        Integer inventoryM = 0;
        Integer inventoryN = 0;
        Integer inventoryE = 0;
        String openid = null;
        String uid = null;
        Integer cost = 0;
        String date1 = sDate;
        Integer balance = 0;


        try {
            String sql = "select * from Inventory where location = ? and day = ?";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            preparedStatement.setString(1, venue);
            preparedStatement.setString(2, sDate);

            //执行SQL
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {//即使只有一条检索结果，也不能图省事去掉while，因为要判断resultSet，防止为null，用if判断应该也行
                inventoryM = resultSet.getInt("inventoryM"); //库存量
                inventoryN = resultSet.getInt("inventoryN"); //库存量
                inventoryE = resultSet.getInt("inventoryE"); //库存量
                cost = resultSet.getInt("cost");
                openid = getSessionValue(SessionID); //获取openid

            }
            Integer inventory = 0;
            System.out.println("库存：" + inventoryM + " " + inventoryN + " " + inventoryE + " " + array.length);
            for (int i = 0; i < array.length; i++) {
                if (array[i] == 0) {
                    inventory = inventoryM;
                    System.out.println("hh");
                } else if (array[i] == 1){
                    inventory = inventoryN;
                    System.out.println("hhh");
                } else if (array[i] == 2){
                    inventory = inventoryE;
                    System.out.println("hhhh");
                }
                System.out.println("查询库存: " + inventory);
                if (inventory > 0) {//检查是否还有库存
                    String sql2 = "select * from User where openid = ?";
                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql2);
                    //设置参数值
                    preparedStatement.setString(1, openid);
                    //执行SQL
                    resultSet = preparedStatement.executeQuery();
                    resultSet.last();//函数的返回结果是当前数据集的行号，而不是结果的行数
                    if (resultSet.getRow() == 0) {//跳转到 ResultSet 最后一行，然后获取该行行号，若行号为0，则 ResultSet 为空。
                        return JSONResult.errorMsg("用户未注册！");
                        //System.out.println("用户未注册！");
                    }
                    resultSet.beforeFirst();
                    while (resultSet.next()) {//用if判断不行，亲测
                        uid = resultSet.getString("uid");
                        balance = resultSet.getInt("balance");
                        System.out.println(uid + " " + balance + " " + inventory + " " + openid);
                    }
                    //有库存并且能找到该用户的注册信息
                    //检查该用户今天是否已经预定了该场馆
                    String sql3 = "select * from Order_table where uid = ? and venue = ? and date = ? and time = ?";
                    //预编译
                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql3);
                    //设置参数值
                    preparedStatement.setString(1, uid);
                    preparedStatement.setString(2, venue);
                    String time = null;
                    if (array[i] == 0) {
                        time = "06:30:00";
                    } else if (array[i] == 1) {
                        time = "16:30:00";
                    } else if (array[i] == 2) {
                        time = "19:30:00";
                    }
                    preparedStatement.setString(3, sDate);
                    System.out.println("reserveDate: " + sDate.toString());
                    preparedStatement.setString(4, time);
                    //执行SQL
                    resultSet = preparedStatement.executeQuery();
                    //判断是否重复预定
                    resultSet.last();
                    if (resultSet.getRow() == 0) {//行号为0，则 ResultSet 为空。
                        //可以预定，插入数据
                        resultSet.beforeFirst();
                        if (balance < cost) {
                            //return JSONResult.errorMsg("您的运动时余额不足啦！");
                            System.out.println("您的运动时余额不足啦！");
                        }
                        Integer count = getOrderCount();
                        String order_id = getOrderid(count);
                        //System.out.println("count: " + " " + count + " " + order_id);
                        String sql4 = "insert into Order_table(order_id, uid, venue, cost, status, time, date) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        preparedStatement = (PreparedStatement) connection.prepareStatement(sql4);
                        preparedStatement.setString(1, order_id);
                        preparedStatement.setString(2, uid);
                        preparedStatement.setString(3, venue);
                        preparedStatement.setInt(4, cost);
                        preparedStatement.setInt(5, 1);
                        preparedStatement.setString(6, time);
                        preparedStatement.setString(7, date1);

                        preparedStatement.executeUpdate();//注意执行的方法名,insert和update时要特别注意
                        //预定成功，对应场馆的库存量减一
                        String sql5 = null;
                        if (array[i] == 0) {
                            sql5 = "update Inventory set inventoryM = ? where location = ? and day = ?";
                        } else if (array[i] == 1) {
                            sql5 = "update Inventory set inventoryN = ? where location = ? and day = ?";
                        } else if (array[i] == 2) {
                            sql5 = "update Inventory set inventoryE = ? where location = ? and day = ?";
                        }
                        preparedStatement = (PreparedStatement) connection.prepareStatement(sql5);
                        preparedStatement.setInt(1, inventory - 1);
                        preparedStatement.setString(2, venue);
                        preparedStatement.setString(3, sDate);
                        preparedStatement.executeUpdate();
                        //用户运动时余额减去相应cost
                        String sql6 = "update User set balance = ? where uid = ?";
                        preparedStatement = (PreparedStatement) connection.prepareStatement(sql6);
                        preparedStatement.setInt(1, balance - cost);
                        preparedStatement.setString(2, uid);
                        preparedStatement.executeUpdate();
                    } else {
                        while (resultSet.next()) {
                            System.out.println("date:?" + resultSet.getDate("date"));
                        }
                        return JSONResult.errorMsg("今日您已预定该场馆！");
                        //System.out.println("今日您已预定该场馆！");
                    }
                } else {
                    return JSONResult.errorMsg("今日人数已满！");
                    //System.out.println("今日人数已满！");
                }
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

    public static Integer getOrderCount() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from Order_table";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            //执行SQL
            resultSet = preparedStatement.executeQuery();
            resultSet.last();
            return (resultSet.getRow() + 1);
        }catch (SQLException e) {
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
        return 0;
    }
    public static String getOrderid(Integer number) {//返回六位数的Order_id
        if (number <= 0 || number > 999999)
            return null;
        String s = "";//String.valueOf('c'); //效率最高的方法
        int num5 = number % 10;//个位
        char c5 = (char) (num5 + 48);
        int num0 = number / 100000;
        char c0 = (char) (num0 + 48);
        s += c0;

        number = number - num0 * 100000;
        int num1 = number / 10000;
        char c1 = (char) (num1 + 48);
        s += c1;

        number = number - num1 * 10000;
        int num2 = number / 1000;
        char c2 = (char) (num2 + 48);
        s += c2;

        number = number - num2 * 1000;
        int num3 = number / 100;
        char c3 = (char) (num3 + 48);
        s += c3;

        number = number - num3 * 100;
        int num4 = number / 10;
        char c4 = (char) (num4 + 48);
        s += c4;

        s += c5;

        return s;


    }

}
