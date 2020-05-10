package com.example.demo.Controller;


import com.example.demo.pojo.DB_User;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Timer;

public class Timer_update{

    public static void timer_update(){
        System.out.println(getRestTime());
        int time = getRestTime();
        Timer timer = new Timer();//单位是毫秒
        timer.schedule(new MyTask(), time, 1000*60*60*24);//在time秒后执行此任务,每次间隔24 hours执行一次.
        while(true){//这个是用来停止此任务的,否则就一直循环执行此任务
            int count = 0;
            if(count > 0){
                timer.cancel();//使用这个方法退出任务
                break;
            }

        }


    }

    public static int getRestTime(){//返回当前时间距离00：00还有多少毫秒
        //获取当前时间,H大写为24小时制，h小写为12小时制。
        String current_time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        Integer hour = (current_time.charAt(0) - 48) * 10 + (current_time.charAt(1) - 48);
        Integer min = (current_time.charAt(3) - 48) * 10 + (current_time.charAt(4) - 48);
        Integer sec = (current_time.charAt(6) - 48) * 10 + (current_time.charAt(7) - 48);
        System.out.println(hour + " " + min + " " + sec);

        //计算当前时间与23：59：59时间差,单位毫秒
        Integer time = ((23 - hour) * 3600 + (59 - min) * 60 + (59 - sec))*1000;
        return time;
    }

    static class MyTask extends java.util.TimerTask{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        public void run(){
            Integer inventoryM = 0;
            Integer inventoryN = 0;
            Integer inventoryE = 0;
            // 取当前日期
            LocalDate today = LocalDate.now();
            // 打印当前日期
            //System.out.println("日期: "+localDate);
            //当前对象减去指定的天数(一天)
            LocalDate tomorrow = today.minusDays(-1);
            LocalDate afterTomorrow = today.minusDays(-2);
            //打印减去一天的天数
            String str = afterTomorrow.toString();
            //System.out.println("日期的后天："+ str);
            try {
                for (int i = 1; i < 49; i++){
                    String sql1 = "select * from Inventory where id = ?";
                    connection = DB_User.open();
                    //预编译SQL
                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql1);
                    //设置参数值
                    preparedStatement.setString(1, IntToStr(24 + i));
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {//即使只有一条检索结果，也不能图省事去掉while，因为要判断resultSet，防止为null，用if判断应该也行
                        inventoryM = resultSet.getInt("inventoryM"); //库存量早
                        inventoryN = resultSet.getInt("inventoryN"); //库存量中
                        inventoryE = resultSet.getInt("inventoryE"); //库存量晚
                        //System.out.println(inventoryM + " " + inventoryN + " " + inventoryE);
                    }

                    String sql2 = "update Inventory set inventoryM = ?, inventoryN = ?, inventoryE = ?, day = ? where id = ?";
                    //预编译SQL
                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql2);
                    //设置参数值
                    preparedStatement.setInt(1, inventoryM);
                    preparedStatement.setInt(2, inventoryN);
                    preparedStatement.setInt(3, inventoryE);
                    if (i < 25){
                        preparedStatement.setString(4, today.toString());
                        //System.out.println(today.toString());
                    }else if (i < 49 && i > 24) {
                        preparedStatement.setString(4, tomorrow.toString());
                        //System.out.println(tomorrow.toString());
                    }
                    preparedStatement.setString(5, IntToStr(i));
                    preparedStatement.executeUpdate();
                }

                String sql = "update Inventory set inventoryM = ?, inventoryN = ?, inventoryE = ?, day = ? where id = ?";
                connection = DB_User.open();
                Integer [] array = {200, 20, 30, 20, 12, 50};

                for (int i = 0; i < 4; i++) {
                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[0]);
                    preparedStatement.setInt(2, array[0]);
                    preparedStatement.setInt(3, array[0]);
                    preparedStatement.setString(4, afterTomorrow.toString());
                    preparedStatement.setString(5, IntToStr(6*i + 1 + 48));
                    //System.out.println(IntToStr(6*i + 1 + 48));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[1]);
                    preparedStatement.setInt(2, array[1]);
                    preparedStatement.setInt(3, array[1]);
                    preparedStatement.setString(4, afterTomorrow.toString());
                    preparedStatement.setString(5, IntToStr(6*i + 2 + 48));
                    //System.out.println(IntToStr(6*i + 2 + 48));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[2]);
                    preparedStatement.setInt(2, array[2]);
                    preparedStatement.setInt(3, array[2]);
                    preparedStatement.setString(4, afterTomorrow.toString());
                    preparedStatement.setString(5, IntToStr(6*i + 3 + 48));
                    //System.out.println(IntToStr(6*i + 3 + 48));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[3]);
                    preparedStatement.setInt(2, array[3]);
                    preparedStatement.setInt(3, array[3]);
                    preparedStatement.setString(4, afterTomorrow.toString());
                    preparedStatement.setString(5, IntToStr(6*i + 4 + 48));
                    //System.out.println(IntToStr(6*i + 4 + 48));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[4]);
                    preparedStatement.setInt(2, array[4]);
                    preparedStatement.setInt(3, array[4]);
                    preparedStatement.setString(4, afterTomorrow.toString());
                    preparedStatement.setString(5, IntToStr(6*i + 5 + 48));
                    //System.out.println(IntToStr(6*i + 5 + 48));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[5]);
                    preparedStatement.setInt(2, array[5]);
                    preparedStatement.setInt(3, array[5]);
                    preparedStatement.setString(4, afterTomorrow.toString());
                    preparedStatement.setString(5, IntToStr(6*i + 6 + 48));
                    //System.out.println(IntToStr(6*i + 6 + 48));
                    preparedStatement.executeUpdate();
                }
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
        }

        public String IntToStr(Integer number) {
            if (number <= 0 || number > 999999)
                return null;
            String s = "0";
            int num2 = number % 10;
            char c2 = (char)(num2 + 48);
            number = number - num2;
            Integer num1 = number / 10;
            char c1 = (char)(num1 + 48);
            s += c1;
            s += c2;

            return s;
        }
    }


}
