package com.example.demo.pojo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_User {
    /*
     * 打开数据库
     */
    private static String driver;//连接数据库的驱动
    private static String url;
    private static String username;
    private static String password;

    static {
        driver="com.mysql.cj.jdbc.Driver";//需要的数据库驱动
        url="jdbc:mysql://101.132.157.149:3306/gym_database?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";//数据库名路径
        username="root";
        password="root";
    }
    public static Connection open()
    {
        try {
            Class.forName(driver);
            return (Connection) DriverManager.getConnection(url,username, password);
        } catch (Exception e) {
            System.out.println("数据库连接失败！");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//加载驱动

        return null;
    }

    /*
     * 关闭数据库
     */
    public static void close(Connection conn)
    {
        if(conn!=null)
        {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
