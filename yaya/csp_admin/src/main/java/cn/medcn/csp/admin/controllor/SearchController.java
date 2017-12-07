package cn.medcn.csp.admin.controllor;

import cn.medcn.article.model.Article;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.search.dto.SearchResult;
import cn.medcn.search.service.SearchService;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuLP on 2017/11/2.
 */
@Controller
@RequestMapping("/search")
public class SearchController extends BaseController {

    @Autowired
    private SearchService testServiceImpl;



    @RequestMapping(value = "/get")
    public String search(String keyword, Pageable pageable) throws SystemException {
        Map<String,String> map = new HashMap<>();
        map.put("history_id",keyword);
        Map<String,String> sortMap = new HashMap<>();
        sortMap.put("id","desc");
        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("id","1230003");
        SearchResult result = testServiceImpl.search(map,null,filterMap,sortMap,pageable);
        return "/login";
    }





}
