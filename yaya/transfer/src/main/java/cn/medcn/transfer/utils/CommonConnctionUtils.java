package cn.medcn.transfer.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/6/14.
 */
public class CommonConnctionUtils {

    private static ThreadLocal<Connection> readOnlyHolder;

    private static ThreadLocal<Connection> writeAbleHolder;

    static{
        readOnlyHolder = new ThreadLocal<>();
        writeAbleHolder = new ThreadLocal<>();
    }

    public static void setReadOnlyHolder(Connection connection){
        readOnlyHolder.set(connection);
    }

    public static void setWriteAbleHolder(Connection connection){
        writeAbleHolder.set(connection);
    }


    public static Connection getOldConnection(){

        Connection conn = readOnlyHolder.get();
        if(conn == null){
            conn = OldDbConnectionUtils.getConnection();
            setReadOnlyHolder(conn);
        }
        return conn;
    }


    public static Connection getNewConnection(){
        Connection conn = writeAbleHolder.get();
        if(conn == null){
            conn = NewDbConnectionUtils.getConnection();
            setWriteAbleHolder(conn);
        }
        return conn;
    }

    public static Connection getServerConnection(){
        return ServerDbConnectionUtils.getConnection();
    }


    public static void closeResources(Connection connection,PreparedStatement pstmt, ResultSet rs) {
            try {
                if(null != rs) {
                    rs.close();
                }
                if(null != pstmt) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }


    public static void closeConnection(){
        Connection writeAbleConn = writeAbleHolder.get();
        if(writeAbleConn != null){
            try {
                writeAbleConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Connection readOnlyConn = readOnlyHolder.get();
        if(readOnlyConn != null){
            try {
                readOnlyConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
