package cn.medcn.transfer.support;

import cn.medcn.transfer.utils.LogUtils;
import cn.medcn.transfer.utils.NewDbConnectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by lixuan on 2017/6/23.
 */
public class WriteAblePool {
    public static LinkedList<Connection> writeAblePool = new LinkedList<>();

    private static final int POOL_SIZE = 20;



    static {
        LogUtils.debug(WriteAblePool.class, "开始初始化writeAble数据库池 ....");
        initPool();
        LogUtils.debug(WriteAblePool.class, "初始化writeAble数据库池成功 !!!");
    }


    public static void initPool() {

        for (int i = 0; i < POOL_SIZE; i++) {
            final Connection conn = NewDbConnectionUtils.getConnection();
            Connection proxy = createProxy(conn);
            writeAblePool.add(proxy);
        }
    }

    private static Connection createProxy(final Connection conn){
        Connection proxy = (Connection) Proxy.newProxyInstance(ReadOnlyPool.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object obj, Method m, Object[] arg)
                    throws Throwable {
                if ("close".equals(m.getName())) {
                    writeAblePool.addLast(createProxy(conn));
                    return null;
                }
                return m.invoke(conn, arg);
            }
        });
        return proxy;
    }

    public static Connection getConnection() throws SQLException {
        if (writeAblePool.size() <= 0) {
            LogUtils.debug(WriteAblePool.class, "数据库繁忙...等待1秒");
            try {
                Thread.sleep(1000);
                return getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return writeAblePool.removeFirst();
    }
}
