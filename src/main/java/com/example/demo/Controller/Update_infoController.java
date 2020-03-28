package com.example.demo.Controller;

import com.example.demo.pojo.DB_User;
import com.example.demo.pojo.JSONResult;
import com.example.demo.pojo.Notice;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/UpdateInfo")
@RestController
public class Update_infoController {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<String> list = new ArrayList<String>();

    @RequestMapping("/getInfo")
    @ResponseBody
    public JSONResult getInfo(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "uid") String uid) {
        String name = "";
        String sex = "";
        String phone = "";
        try {
            String sql = "select * from User where uid = ?";
            connection = DB_User.open();
            //预编译SQL
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            //设置参数值
            preparedStatement.setString(1, uid);
            //执行SQL
            resultSet = preparedStatement.executeQuery();
            list.clear();
            while (resultSet.next()) {//即使只有一条检索结果，也不能图省事去掉while，因为要判断resultSet，防止为null，用if判断应该也行
                name = resultSet.getString("name");
                sex = resultSet.getString("sex");
                phone = resultSet.getString("phone");
                list.add(name);
                list.add(phone);
                list.add(sex);//返回后a记得要清空
            }
            return JSONResult.ok(list);

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


    @RequestMapping("/updateInfo")
    @ResponseBody
    public JSONResult updateInfo(@RequestHeader(name = "SessionID") String SessionID, @RequestParam(name = "uid") String uid, @RequestParam(name = "pwd") String pwd, @RequestParam(name = "new_name") String new_name,
                              @RequestParam(name = "new_pwd")String new_pwd, @RequestParam(name = "new_phone") String new_phone, @RequestParam(name = "new_sex")String new_sex) {

        String password = "";

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
                password = resultSet.getString("password");
            }
            if (!password.equals(pwd)) {
                return JSONResult.errorMsg("密码错误！");
            }else {
                //密码验证完毕，开始进行update
                String sql2 = "update User set name = ?, password=?, sex=?, phone=? where uid = ?";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sql2);
                preparedStatement.setString(1, new_name);
                preparedStatement.setString(2, new_pwd);
                preparedStatement.setString(3, new_sex);
                preparedStatement.setString(4, new_phone);
                preparedStatement.setString(5, uid);
                preparedStatement.executeUpdate();//注意执行的方法名,insert和update时要特别注意

            }
            return JSONResult.ok(new_name);
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
        return JSONResult.ok(new_name);
    }

}
