package cn.medcn.search.supports;

import java.util.HashMap;
import java.util.Map;

/**
 * Solr搜索支持类
 * Created by lixuan on 2017/12/7.
 */
public class Searchable {

    private static final String AND = " && ";

    private static final String OR = " || ";

    private static final String MATCH = ":";

    private static final String ALL = "*";

    private static final String EMPTY = "";

    private int pageNum = 0;

    private int pageSize = 15;

    private Map<String, String> andMap = new HashMap<>();

    private Map<String, String> orMap = new HashMap<>();

    public Searchable(){}

    public Searchable(int pageNum, int pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }


    public void and(String key, String value){
        this.andMap.put(key, value);
    }


    public void or(String key, String value){
        this.orMap.put(key, value);
    }

    /**
     * 根据andMap和orMap生成Solr的query语句
     * 先处理andMap 然后将orMap当成一个and条件的一个子项
     * @return
     */
    public String getQuery(){
        StringBuffer buffer = new StringBuffer();
        int index = 0;

        if (andMap.size() == 0 && orMap.size() == 0) {
            buffer.append(ALL).append(MATCH).append(ALL);
        } else {
            for (String key : andMap.keySet()) {
                buffer.append(key).append(MATCH).append(andMap.get(key));
                buffer.append(index == andMap.size() - 1 ? EMPTY : AND);
                index ++ ;
            }

            if (orMap.size() > 0) {
                buffer.append(AND).append("(");
                index = 0;
                for (String key : orMap.keySet()) {
                    buffer.append(key).append(MATCH).append(orMap.get(key));
                    buffer.append(index == andMap.size() - 1 ? EMPTY : OR);
                    index ++;
                }
                buffer.append(")");
            }
        }
        return buffer.toString();
    }


    public int getStart(){
        return pageNum * pageSize;
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
}
