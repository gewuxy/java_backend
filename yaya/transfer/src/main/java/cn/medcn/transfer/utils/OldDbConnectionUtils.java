package cn.medcn.transfer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/5/22.
 */
public class OldDbConnectionUtils {

    private static String userName;

    private static String passWord;

    private static String url;

    static {
        userName = PropertyUtils.readPropFromCache("old_db.properties", "transfer.jdbc.username");
        passWord = PropertyUtils.readPropFromCache("old_db.properties", "transfer.jdbc.password");
        url = PropertyUtils.readPropFromCache("old_db.properties", "transfer.jdbc.url");
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
