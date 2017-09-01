package cn.medcn.transfer.support;

import cn.medcn.transfer.utils.LogUtils;
import cn.medcn.transfer.utils.OldDbConnectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by lixuan on 2017/6/23.
 */
public class ReadOnlyPool {

    public static LinkedList<Connection> readOnlyPool = new LinkedList<>();

    private static final int POOL_SIZE = 20;

    static {
        LogUtils.debug(ReadOnlyPool.class, "开始初始化只读数据库池 ...");
        initPool();
        LogUtils.debug(ReadOnlyPool.class, "初始化只读数据库池成功 !!!");
    }


    public static void initPool() {

        for (int i = 0; i < POOL_SIZE; i++) {
            final Connection conn = OldDbConnectionUtils.getConnection();
            Connection proxy = createProxy(conn);
            readOnlyPool.add(proxy);
        }
    }

    private static Connection createProxy(final Connection conn){
        Connection proxy = (Connection) Proxy.newProxyInstance(ReadOnlyPool.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object obj, Method m, Object[] arg)
                    throws Throwable {
                if ("close".equals(m.getName())) {
                    readOnlyPool.addLast(createProxy(conn));
                    return null;
                }
                return m.invoke(conn, arg);
            }
        });
        return proxy;
    }

    public static Connection getConnection() throws SQLException {
        if (readOnlyPool.size() <= 0) {
            LogUtils.debug(ReadOnlyPool.class, "数据库繁忙...等待1秒");
            try {
                Thread.sleep(1000);
                return getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return readOnlyPool.removeFirst();
    }


}
