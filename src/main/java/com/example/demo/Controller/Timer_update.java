package com.example.demo.Controller;


import com.example.demo.pojo.DB_User;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class Timer_update{
    
    public static void timer_update(){
        System.out.println(getRestTime());
        int time = getRestTime();
        Timer timer = new Timer();//单位是毫秒
        timer.schedule(new MyTask(), time, 1000*60*60*24);//在1秒后执行此任务,每次间隔24 hours执行一次.
        while(true){//这个是用来停止此任务的,否则就一直循环执行此任务
            int count = 0;
            if(count > 0){
                timer.cancel();//使用这个方法退出任务
                break;
            }

        }


    }

    public static int getRestTime(){//返回当前时间距离00：00还有多少毫秒
        //获取当前时间
        String current_time = new SimpleDateFormat("hh:mm:ss").format(new Date());

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
            try {
                String sql = "update inventory set inventory = ? where id = ?";
                connection = DB_User.open();
                //预编译SQL

                Integer [] array = {200, 20, 30, 20, 12, 50};
                for (int i = 0; i < 4; i++) {
                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[0]);
                    preparedStatement.setString(2, IntToStr(6*i + 1));
                    System.out.println(IntToStr(6*i + 1));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[1]);
                    preparedStatement.setString(2, IntToStr(6*i + 2));
                    System.out.println(IntToStr(6*i + 2));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[2]);
                    preparedStatement.setString(2, IntToStr(6*i + 3));
                    System.out.println(IntToStr(6*i + 3));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[3]);
                    preparedStatement.setString(2, IntToStr(6*i + 4));
                    System.out.println(IntToStr(6*i + 4));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[4]);
                    preparedStatement.setString(2, IntToStr(6*i + 5));
                    System.out.println(IntToStr(6*i + 5));
                    preparedStatement.executeUpdate();

                    preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
                    preparedStatement.setInt(1, array[5]);
                    preparedStatement.setString(2, IntToStr(6*i + 6));
                    System.out.println(IntToStr(6*i + 6));
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
