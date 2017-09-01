package cn.medcn.transfer.support;

/**
 * Created by lixuan on 2017/6/15.
 */
public class SqlBundle {

    private String sql ;

    private Object[] params;

    public SqlBundle(String sql, Object[] params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
