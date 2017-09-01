package cn.medcn.transfer.utils;

import cn.medcn.transfer.support.Page;
import cn.medcn.transfer.support.Pageable;
import cn.medcn.transfer.support.SqlBundle;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * Created by lixuan on 2017/6/14.
 * 统一数据库操作工具类
 */
public class DAOUtils {


    /**
     * 获取当前表中最大ID值
     * @param connection
     * @param table
     * @param idKey
     * @return
     */
    public static Object getMaxId(Connection connection, String table, String idKey){
        String sql = "select max(`"+idKey+"`) from `"+table+"` ";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = connection.prepareStatement(sql);
            rs = pst.executeQuery();
            Object idVal = null;
            if (rs.next()){
                idVal = rs.getObject(1);
            }
            return idVal;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonConnctionUtils.closeResources(connection, pst, rs);
        }
        return null;
    }

    public static Object selectOne(Connection conn, String sql, Object[] params, Class clazz) throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Map<String, Object> data = selectOneByParams(conn, sql, params);
        if(data != null){
            return MappingUtils.transferToPOJO(data, clazz);
        }
        return null;
    }


    public static Object selectOne(Connection connection, Object condition, String table) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        SqlBundle sqlBundle = buildSelectSql(condition, table);
        return selectOne(connection, sqlBundle.getSql(), sqlBundle.getParams(), condition.getClass());
    }


    public static List<?> selectList(Connection conn, Object condition, String table) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        SqlBundle sqlBundle = buildSelectSql(condition, table);
        return selectList(conn, sqlBundle.getSql(), sqlBundle.getParams(), condition.getClass());
    }


    public static List<?> selectList(Connection conn, String sql, Object[] params, Class clazz) throws InvocationTargetException, IntrospectionException, InstantiationException, IllegalAccessException {
        List<Map<String, Object>> list = selectByParams(conn, sql , params);
        if(list != null && list.size() > 0){
            return MappingUtils.transferToPOJOList(list, clazz);
        }
        return new ArrayList<>();
    }


    public static Blob getBlobData(Connection conn, String sql, Object[] params){
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            if(params != null){
                for(int i = 0; i < params.length; i ++){
                    pst.setObject(i+1, params[i]);
                }
            }
            rs = pst.executeQuery();
            Blob blob = null;
            if (rs.next()){
                blob = rs.getBlob(1);
            }
            return blob;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonConnctionUtils.closeResources(conn,pst, rs);
        }
        return null;
    }


    private static SqlBundle buildSelectSql(Object condition, String table) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from `"+table+"` where 1=1 ");
        List<Object> objects = new ArrayList<>();
        buildParamsFromCondition(sqlBuilder,condition, objects);
        return new SqlBundle(sqlBuilder.toString(), objects.toArray());
    }


    private static void buildParamsFromCondition(StringBuilder sqlBuilder, Object condition, List<Object> objects) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(condition.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if(!"class".equals(key)){
                String underLineKey = MappingUtils.humpToUnderline(key);
                Method getter = property.getReadMethod();
                Object value = getter.invoke(condition);
                if(value != null){
                    sqlBuilder.append(" and `"+underLineKey+"`=?");
                    objects.add(value);
                }
            }
        }
    }

    /**
     * 根据实体类和表明 构建sql
     * @param entity
     * @param table
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     */
    private static SqlBundle buildInsertSql(Object entity, String table) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        sqlBuilder.append("insert into "+table +" (");
        valueBuilder.append(" values (");
        List<Object> objects = new ArrayList<>();
        int counter = 0;
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            Class properType = property.getPropertyType();
            if(!"class".equals(key) && !Collection.class.isAssignableFrom(properType)){
                String underLineKey = MappingUtils.humpToUnderline(key);
                sqlBuilder.append((counter>0?",":"")+" `"+underLineKey+"`");
                Method getter = property.getReadMethod();
                Object value = getter.invoke(entity);
                valueBuilder.append((counter>0?",":"")+"?");
                objects.add(counter, value);
                counter++;
            }
        }
        sqlBuilder.append(")");
        valueBuilder.append(")");
        String sql = sqlBuilder.toString() + valueBuilder.toString();
        return new SqlBundle(sql, objects.toArray());
    }

    public static void insert(Connection connection, Object entity, String table) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        SqlBundle sqlBundle = buildInsertSql(entity, table);
        execute(connection, sqlBundle.getSql(), sqlBundle.getParams());
    }

    /**
     * 插入数据并返回自增ID
     */
    public static Object insertReturnId(Connection connection, Object entity, String table) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        SqlBundle sqlBundle = buildInsertSql(entity, table);
        return executeReturnId(connection, sqlBundle.getSql(), sqlBundle.getParams());
    }

    public static Object executeReturnId(Connection conn, String sql, Object[] params){
        PreparedStatement pst = null;
        Object id = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            if(params != null){
                for(int i = 0; i < params.length; i ++){
                    pst.setObject(i+1, params[i]);
                }
            }
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if(rs.next()){
                id =  rs.getObject(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonConnctionUtils.closeResources(conn,pst, rs);
        }

        return id;
    }

    public static void execute(Connection conn, String sql, Object[] params){
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(sql);
            if(params != null){
                for(int i = 0; i < params.length; i ++){
                    pst.setObject(i+1, params[i]);
                }
            }
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonConnctionUtils.closeResources(conn, pst, null);
        }
    }


    private static Map<String, Object> selectOneByParams(Connection conn, String sql, Object[] params){
        sql += " limit 1";
        List<Map<String, Object>> list = selectByParams(conn, sql , params);
        return list.size()>0?list.get(0):null;
    }

    private static List<Map<String, Object>> selectByParams(Connection conn, String sql, Object[] params){
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            if(params != null){
                for(int i = 0; i < params.length; i ++){
                    pst.setObject(i+1, params[i]);
                }
            }
            rs = pst.executeQuery();
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> data ;
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()){
                data = new HashMap<>();
                for(int i = 1; i <= metaData.getColumnCount(); i++){
                    data.put(metaData.getColumnName(i).toLowerCase(), rs.getObject(i));
                }
                list.add(data);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CommonConnctionUtils.closeResources(conn, pst, rs);
        }
        return null;
    }


    private static SqlBundle buildCountSql(Object condition, String table, String idKey) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        StringBuilder sqlBuilder = new StringBuilder();
        idKey = MappingUtils.humpToUnderline(idKey);
        sqlBuilder.append("select count(`"+idKey+"`) from `"+table+"` where 1=1 ");
        List<Object> objects = new ArrayList<>();
        buildParamsFromCondition(sqlBuilder,condition, objects);
        return new SqlBundle(sqlBuilder.toString(), objects.toArray());
    }

    public  static Page findByPage(Pageable pageable, Connection connection, String table, String idKey) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        SqlBundle sqlBundle = buildPageSql(pageable, table);
        Long count = count(connection, pageable.getCondition(), table, idKey);
        Page page = initPage(pageable, count);
        if(count > 0){
            List<?> list = selectList(connection, sqlBundle.getSql(), sqlBundle.getParams(), pageable.getCondition().getClass());
            page.setDatas(list);
        }
        return page;
    }

    public  static Page findByPage(Pageable pageable, Connection connection,String sql, String table, String idKey) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException {
        SqlBundle sqlBundle = buildPageSqls(pageable, sql);
        Long count = count(connection, pageable.getCondition(),table, idKey);
        Page page = initPage(pageable, count);
        if(count > 0){
            List<?> list = selectList(connection, sqlBundle.getSql(), sqlBundle.getParams(), pageable.getCondition().getClass());
            page.setDatas(list);
        }
        return page;
    }


    private static Page initPage(Pageable pageable, Long count){
        Page page = new Page();
        page.setPageNum(pageable.getPageNum());
        page.setPageSize(pageable.getPageSize());
        page.setStartRow(pageable.getStartRow());
        page.setPages(Long.valueOf(count%pageable.getPageSize() == 0?count/pageable.getPageSize():count/pageable.getPageSize()+1).intValue());
        page.setTotal(count);
        return page;
    }


    public static Long count(Connection connection, Object condition, String table, String idKey) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        SqlBundle sqlBundle = buildCountSql(condition, table, idKey);
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = connection.prepareStatement(sqlBundle.getSql());
            Object[] params = sqlBundle.getParams();
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            rs = pst.executeQuery();
            Long count = 0L;
            if(rs.next()){
                count = rs.getLong(1);
            }
            return count;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            CommonConnctionUtils.closeResources(connection,pst, rs);
        }
        return 0L;
    }

    private static SqlBundle buildPageSql(Pageable page, String table) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from `"+table+"` where 1=1 ");
        List<Object> objects = new ArrayList<>();
        buildParamsFromCondition(sqlBuilder,page.getCondition(), objects);
        sqlBuilder.append(" limit "+page.getStartRow()+", "+page.getPageSize());
        return new SqlBundle(sqlBuilder.toString(), objects.toArray());
    }

    private static SqlBundle buildPageSqls(Pageable page, String sql) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        StringBuilder sqlBuilder = new StringBuilder();
        //sqlBuilder.append(sql +" where 1=1 ");
        sqlBuilder.append(sql);
        List<Object> objects = new ArrayList<>();
        buildParamsFromCondition(sqlBuilder,page.getCondition(), objects);
        sqlBuilder.append(" limit "+page.getStartRow()+", "+page.getPageSize());
        return new SqlBundle(sqlBuilder.toString(), objects.toArray());
    }

    public static void main(String[] args) {
        String a = new String();
        Integer b = new Integer(1);
        System.out.println(b.getClass().isPrimitive());
    }
}
