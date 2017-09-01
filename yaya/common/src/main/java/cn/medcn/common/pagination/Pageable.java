package cn.medcn.common.pagination;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by lixuan on 2017/1/4.
 */
public class Pageable {
    //统计总数
    public static final boolean countPage = true;
    //不统计总数
    public static final boolean NOT_COUNT_PAGE = false;

    public Pageable(){}

    public Pageable(int pageNum, int pageSize){
        this.pageNum = pageNum == 0?1:pageNum;
        this.pageSize = pageSize;
    }

    public Pageable(int pageNum, int pageSize, Map<String,Object> params){
        this.pageNum = pageNum == 0?1:pageNum;
        this.pageSize = pageSize;
        this.params = params;
    }

    private int pageNum = 1;

    private int pageSize = 15;

    private Map<String, Object> params = Maps.newHashMap();

    private Object entity;

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }


    public void put(String key, Object value){
        params.put(key, value);
    }

    public Object get(String key){
        return params.get(key);
    }
}
