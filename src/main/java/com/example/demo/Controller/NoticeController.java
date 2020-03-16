package com.example.demo.Controller;

import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.Notice;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.Controller.RedisLinkTest.getSessionValue;

@RequestMapping("/Notice")
@RestController
public class NoticeController {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @RequestMapping("/notice")
    public JSONResult user_register(@RequestHeader(name = "SessionID") String SessionID) {
        System.out.println(SessionID);
        String openid = getSessionValue(SessionID);
        List<Notice> list = new ArrayList<Notice>();
        Integer length = 0;
        try {
            String sql = "select * from notice";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //执行SQL
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //得到返回结果值

                String notice_id = resultSet.getString("notice_id");
                String content = resultSet.getString("content");
                Timestamp date = resultSet.getTimestamp("date"); //这样子可以精确到秒而不是到天

                Notice notice = new Notice(notice_id, content, date);
                System.out.println("create..........."+ notice_id + " " + content + " " + date.toString());
                list.add(notice);
            }
            //list.add(new Notice("005", openid, new Date()));
            length = list.size();
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
}
