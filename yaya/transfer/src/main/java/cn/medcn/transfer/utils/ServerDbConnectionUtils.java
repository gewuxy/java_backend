package cn.medcn.transfer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/7/17.
 */
public class ServerDbConnectionUtils {
    private static String userName;

    private static String passWord;

    private static String url;

    static {
        userName = PropertyUtils.readPropFromCache("server_db.properties", "jdbc.username");
        passWord = PropertyUtils.readPropFromCache("server_db.properties", "jdbc.password");
        url = PropertyUtils.readPropFromCache("server_db.properties", "jdbc.url");
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
