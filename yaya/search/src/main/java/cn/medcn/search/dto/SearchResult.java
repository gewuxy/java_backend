package cn.medcn.search.dto;

import cn.medcn.article.model.Article;
import lombok.Data;

import java.util.List;

/**
 * Created by LiuLP on 2017/12/5/005.
 */
@Data
public class SearchResult<T> {

    private List<T> list;

    //查询出的总条数
    private long count;


}
