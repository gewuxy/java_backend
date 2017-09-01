package cn.medcn.transfer.utils;

import java.sql.*;

/**
 * Created by lixuan on 2017/6/14.
 */
public class NewDbConnectionUtils {

    private static String userName;

    private static String passWord;

    private static String url;

    static {
        userName = PropertyUtils.readPropFromCache("new_db.properties", "newdb.jdbc.username");
        passWord = PropertyUtils.readPropFromCache("new_db.properties", "newdb.jdbc.password");
        url = PropertyUtils.readPropFromCache("new_db.properties", "newdb.jdbc.url");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, passWord);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


}
