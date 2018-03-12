package cn.medcn.search.service;

import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.SearchResult;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

/**
 * Created by LiuLP on 2017/8/7.
 */
public interface SearchService {

    /**
     * @param orMap     or查询需要传入的map，key为要查询的字段，value为要查询的值
     * @param andMap    and查询需要传入的map，key为要查询的字段，value为要查询的值
     * @param filterMap 对查询的结果进行过滤，key为需要过滤的条件字段，value为要滤的条件字段的值
     * @param sortMap   对查询的结果进行排序，key为排序条件的字段，value为排序的方法，传入desc表示降序，asc表示升序
     * @param pageable  pageNum为开始查询的位置，pageSize为要查询的条数
     * @return 查询结果对象
     * @throws SystemException 当sortMap不为null,又有多个值时，抛出异常
     */
    SearchResult search(Map<String, String> orMap, Map<String, String> andMap, Map<String, String> filterMap, Map<String, String> sortMap, Pageable pageable) throws SystemException;

    /**
     * solr core地址
     *
     * @return
     */
    String getCore();

    /**
     * 查询结果对象的class
     *
     * @return
     */
    Class getEntityClass();

    /**
     * 添加或者更新索引
     *
     * @param map
     * @throws Exception
     */
    void add(Map<String, String> map) throws Exception;

}
