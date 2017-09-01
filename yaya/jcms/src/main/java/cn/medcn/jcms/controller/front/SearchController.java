package cn.medcn.jcms.controller.front;

import cn.medcn.article.model.Article;
import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.data.dto.DataCategoryDTO;
import cn.medcn.data.model.Category;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import cn.medcn.data.service.CategoryService;
import cn.medcn.data.service.DataFileService;
import cn.medcn.search.dto.ArticleSearchDTO;
import cn.medcn.search.dto.DataFileSearchDTO;
import cn.medcn.search.service.ArticleSearchService;
import cn.medcn.search.service.DataFileSearchService;
import cn.medcn.search.util.SolrUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by weilong on 2017/7/17.
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private DataFileService dataFileService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleSearchService articleSearchService;
    @Autowired
    private DataFileSearchService dataFileSearchService;


    @Value("${editor_media_path}")
    private String editorMediaPath;

    //限制获取数据的最大行数
    private final int maxRows = 30;

    /**
     * 跳转到公司安全用药列表页
     *
     * @param pageable 分页信息
     * @return
     */
    @RequestMapping(value = "/safe/medication/list")
    public String safeMedicationList(Pageable pageable, Integer pageSize, Model model) throws SystemException {
        if (pageSize == null) {//pageSize为空表示没有设定每页行数。
            pageable.setPageSize(0);
        }

        int start = 0;//默认从首条记录开始读取
        int rows = 5;//默认只获取5条记录
        if (pageable.getPageSize() > 0) {
            rows = pageable.getPageSize();
        }
        if (pageable.getPageNum() > 0) {
            start = (pageable.getPageNum() - 1) * rows;
        }

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart(start);
        rows=rows>maxRows?maxRows:rows;
        solrQuery.setRows(rows);
        solrQuery.set("q", "authed:true");
        //ArticleCategoryId.SAFE_MEDICATION_ID是安全用药对应的目录id
        solrQuery.set("fq", "historyId:*" + ArticleCategoryId.SAFE_MEDICATION_ID + "*");
        QueryResponse response = null;
        MyPage<ArticleSearchDTO> myPage = new MyPage<ArticleSearchDTO>();
        myPage.setPageSize(rows);
        myPage.setPageNum(pageable.getPageNum() >= 1 ? pageable.getPageNum() : 1);
        try {
            //response = entitySearchService.search(solrQuery, ARTICLE_CORE);
            response=articleSearchService.search(solrQuery);
            SolrDocumentList results = response.getResults();
            myPage.setDataList(response.getBeans(ArticleSearchDTO.class));
            myPage.setTotal(results.getNumFound());//符合查询条件的总数。不是单页记录数
            model.addAttribute("myPage", myPage);
        } catch (Exception e) {
            throw new SystemException("T_T 很抱歉！没有找到数据！(solr err)");
        }
        model.addAttribute("editorMediaPath",editorMediaPath);
        return "/search/safeMedicationList";
    }

    /**
     * 跳转到公司安全用药详细页
     *
     * @return
     */
    @RequestMapping(value = "/safe/medication/detail")
    public String safeMedicationDetail(String id, Model model) throws SystemException {

        if (id == null) {
            throw new SystemException("您的请求缺少参数，没指定目标内容（ID）");
        }
        Article article = articleService.selectByPrimaryKey(id);
        if (article == null) {
            throw new SystemException("抱歉，您要找的内容已经不在了。");
        }
        model.addAttribute("article", article);
        model.addAttribute("editorMediaPath",editorMediaPath);
        return "/search/safeMedicationDetail";
    }

    /**
     * 跳转到医师建议-用药知识列表页
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/news/list")
    public String newsList(Pageable pageable, String searchingNews, Integer pageSize, Model model) throws SystemException {
        if (searchingNews != null) {
            model.addAttribute("searchingText", searchingNews);
        }

        if (pageSize == null) {//pageSize为空表示没有设定每页行数。
            pageable.setPageSize(0);
        }
        int start = 0;//默认从首条记录开始读取
        int rows = 5;//默认只获取5条记录
        if (pageable.getPageSize() > 0) {
            rows = pageable.getPageSize();
        }
        if (pageable.getPageNum() > 0) {
            start = (pageable.getPageNum() - 1) * rows;
        }

        //过滤solr的控制字符，避免恶意请求
        searchingNews = SolrUtil.escapeSolrQueryChars(searchingNews);

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart(start);
        rows=rows>maxRows?maxRows:rows;
        solrQuery.setRows(rows);
        solrQuery.set("q", "authed:true AND ( title:" + searchingNews + " content:" + searchingNews + " summary:" + searchingNews + " keywords:" + searchingNews + " )");
        solrQuery.set("fq", "historyId:*" + ArticleCategoryId.NEWS_ID + "*");//类似于MySQL的like %categoryId%
        QueryResponse response = null;
        MyPage<ArticleSearchDTO> myPage = new MyPage<ArticleSearchDTO>();
        myPage.setPageSize(rows);
        myPage.setPageNum(pageable.getPageNum() >= 1 ? pageable.getPageNum() : 1);
        try {
            response=articleSearchService.search(solrQuery);
            myPage.setTotal(response.getResults().getNumFound());//符合查询条件的总数。不是单页记录数
            myPage.setDataList(response.getBeans(ArticleSearchDTO.class));
            model.addAttribute("myPage", myPage);
        } catch (Exception e) {
            throw new SystemException("T_T 很抱歉！没有找到数据！(solr err)");
        }

        return "/search/newsList";
    }


    /**
     * 跳转到公司药师建议搜索页
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/pharmacist/advice/home")
    public String pharmacistAdviceHome(Model model) throws SystemException {
        //在这里要获取医师建议的搜索热门词并设置到model里(need to do) 例：model.addAttribute("hotWords",String[])
        //......
        //如果不设置热门词则默认采用默认原有的内容

        //获取二级目录
        Category category = new Category();
        category.setPreId(DataFileCategoryId.PHARMACIST_ADVICE_ID);
        List<Category> categoryList = categoryService.select(category);
        if (categoryList != null && categoryList.size() > 0) {
            model.addAttribute("childCategoryList", categoryList);
        }
        return "/search/pharmacistAdviceHome";
    }

    /**
     * 跳转到药师建议搜索结果页
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/pharmacist/advice/list")
    public String pharmacistAdviceList(String searchingText, String categoryId, Pageable pageable, Model model,HttpSession session) throws SystemException {
        if(searchingText==null){
            searchingText=(String)session.getAttribute("searchingText");
        }else{
            //记录上次的搜索内容，翻页时要用回原来的搜索内容。
            session.setAttribute("searchingText",searchingText);
        }

        if (searchingText != null) {
            model.addAttribute("searchingText", searchingText);
        }

        //获取二级目录
        List<DataCategoryDTO> dataCategoryDTOList = categoryService.findCategories(DataFileCategoryId.PHARMACIST_ADVICE_ID);
        if (dataCategoryDTOList != null || dataCategoryDTOList.size() > 0) {
            model.addAttribute("childCategoryList", dataCategoryDTOList);
        }

        //是否搜索子分类
        if (categoryId == null || "0".equals(categoryId)) {
            //如果没有带子分类id 则表示搜索父分类。
            categoryId = DataFileCategoryId.PHARMACIST_ADVICE_ID;
        } else {
            //带了之分类ID则设置默认分类信息显示子分类
            for (DataCategoryDTO dataCategoryDTO : dataCategoryDTOList) {
                if (categoryId.equals(dataCategoryDTO.getId())) {
                    model.addAttribute("childCategoryId", categoryId);
                }
            }
        }

        //处理分页
        int rows = pageable.getPageSize();//用户没传入pageSize数据时，已经默认初始化为15，所以默认rows=15
        int start = (pageable.getPageNum() - 1) * rows;//用户没传入pageNum数据时，已经默认初始化为1，所以默认start=0；
        //不允许获取太多数据。
        if (rows > 100) {
            rows = 100;
        }
        if (rows <= 0) {
            rows = 15;
        }
        if (start < 0) {
            start = 0;
        }

        //过滤solr的控制字符，避免恶意请求
        searchingText = SolrUtil.escapeSolrQueryChars(searchingText);

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart(start);
        rows=rows>maxRows?maxRows:rows;
        solrQuery.setRows(rows);
        solrQuery.set("q", "authed:true AND ( title:" + searchingText + " content:" + searchingText + " summary:" + searchingText + " )");
        solrQuery.set("fq", "historyId:*" + categoryId + "*");//类似于MySQL的like %categoryId%
        QueryResponse response = null;
        MyPage<DataFileSearchDTO> myPage = new MyPage<DataFileSearchDTO>();
        myPage.setPageSize(rows);
        myPage.setPageNum(pageable.getPageNum() >= 1 ? pageable.getPageNum() : 1);
        try {
            //response = entitySearchService.search(solrQuery, DATA_FILE_CORE);
            response=dataFileSearchService.search(solrQuery);
            myPage.setTotal(response.getResults().getNumFound());//符合查询条件的总数。不是单页记录数
            myPage.setDataList(response.getBeans(DataFileSearchDTO.class));
            model.addAttribute("myPage", myPage);
        } catch (Exception e) {
            throw new SystemException("T_T 很抱歉！ 没有找到数据！(solr err)");
        }
        return "/search/pharmacistAdviceList";
    }

    /**
     * 跳转到药师建议搜索结果详情页
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/pharmacist/advice/detail")
    public String pharmacistAdviceDetail(String id, Model model) throws SystemException {

        if (id == null) {
            throw new SystemException("您请求的缺少参数，没指定目标内容（ID）");
        }
        DataFile dataFile = dataFileService.selectByPrimaryKey(id);
        if (dataFile == null) {
            throw new SystemException("抱歉，您要找的内容已经不在了。");
        }
        model.addAttribute("dataFile", dataFile);
        return "/search/pharmacistAdviceDetail";
    }

    /**
     * 跳转到医师建议搜索页
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/doctor/advice/home")
    public String doctorAdviceHome(Model model) {
        //获取二级目录
        List<DataCategoryDTO> dataCategoryDTOList = categoryService.findCategories(DataFileCategoryId.DOCTOR_ADVICE_ID);
        if (dataCategoryDTOList != null || dataCategoryDTOList.size() > 0) {
            model.addAttribute("childCategoryList", dataCategoryDTOList);
        }
        return "/search/doctorAdviceHome";
    }

    /**
     * 跳转到医师建议搜索结果页
     * searchingText 用户搜索的内容
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/doctor/advice/list")
    public String doctorAdviceList(String searchingText, String categoryId, Pageable pageable, Integer pageSize, Model model, HttpSession session) throws SystemException {
        if(searchingText==null){
            searchingText=(String)session.getAttribute("searchingText");
        }else{
            //记录上次的搜索内容，翻页时要用回原来的搜索内容。
            session.setAttribute("searchingText",searchingText);
        }


        if(searchingText!=null){
            model.addAttribute("searchingText",searchingText);
        }

        //获取二级目录
        List<DataCategoryDTO> dataCategoryDTOList = categoryService.findCategories(DataFileCategoryId.DOCTOR_ADVICE_ID);
        if (dataCategoryDTOList != null || dataCategoryDTOList.size() > 0) {
            model.addAttribute("childCategoryList", dataCategoryDTOList);
        }

        //是否搜索子分类
        if (categoryId == null || "0".equals(categoryId)) {
            //如果没有带子分类id 则表示搜索父分类。
            categoryId = DataFileCategoryId.DOCTOR_ADVICE_ID;
        } else {
            //带了之分类ID则设置默认分类信息显示子分类
            for (DataCategoryDTO dataCategoryDTO : dataCategoryDTOList) {
                if (categoryId.equals(dataCategoryDTO.getId())) {
                    model.addAttribute("childCategoryId", categoryId);
                }
            }
        }

        //处理分页
        if (pageSize == null) {//pageable默认初始化值是15会影响本页面。
            pageable.setPageSize(0);
        }
        //System.out.println("-----pageable"+ JSON.toJSONString(pageable));
        int start = 0;//默认从首条记录开始读取
        int rows = 5;//默认只获取5条记录
        if (pageable.getPageSize() > 0) {
            rows = pageable.getPageSize();
        }
        if (pageable.getPageNum() > 0) {
            start = (pageable.getPageNum() - 1) * rows;
        }

        //过滤solr的控制字符，避免恶意请求
        searchingText = SolrUtil.escapeSolrQueryChars(searchingText);

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart(start);
        rows=rows>maxRows?maxRows:rows;
        solrQuery.setRows(rows);
        solrQuery.set("q", "authed:true AND ( title:" + searchingText + " content:" + searchingText + " summary:" + searchingText + " )");
        solrQuery.set("fq", "historyId:*" + categoryId + "*");//类似于MySQL的like %categoryId%
        QueryResponse response = null;
        MyPage<DataFileSearchDTO> myPage = new MyPage<DataFileSearchDTO>();
        myPage.setPageSize(rows);
        myPage.setPageNum(pageable.getPageNum() >= 1 ? pageable.getPageNum() : 1);
        try {
            //response = entitySearchService.search(solrQuery, DATA_FILE_CORE);
            response=dataFileSearchService.search(solrQuery);
            SolrDocumentList results = response.getResults();
            myPage.setDataList(response.getBeans(DataFileSearchDTO.class));
            myPage.setTotal(results.getNumFound());//符合查询条件的总数。不是单页记录数
            model.addAttribute("myPage", myPage);
        } catch (Exception e) {
            throw new SystemException("T_T 很抱歉！ 没有找到数据！(solr err)");
        }
        model.addAttribute("editorMediaPath",editorMediaPath);
        return "/search/doctorAdviceList";
    }

    /**
     * 跳转到医师建议搜索结果详情页
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/doctor/advice/detail")
    public String doctorAdviceDetail(String id, Model model) throws SystemException {
        if (id == null) {
            throw new SystemException("您请求的缺少参数，没指定目标内容（ID）");
        }
        DataFile dataFile = dataFileService.selectByPrimaryKey(id);
        if (dataFile == null) {
            throw new SystemException("抱歉，您要找的内容已经不在了。");
        }
        model.addAttribute("dataFile", dataFile);
        if (dataFile.getKeywords() != null) {
            String keywords[] = dataFile.getKeywords().split("，");
            if (keywords.length > 0) {
                model.addAttribute("keywords", keywords);
            }
        }
        model.addAttribute("editorMediaPath",editorMediaPath);
        return "/search/doctorAdviceDetail";
    }


    /**
     * 跳转到对症找药搜索页
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/find/medication/home")
    public String findMedicationHome(Model model) {
        //如果需要，在这里要获取医师建议的搜索热门词并设置到model里(need to do) 例：model.addAttribute("hotWords",String[])
        //......
        //如果不设置热门词则默认采用默认原有的内容

        //获取二级目录
        List<DataCategoryDTO> dataCategoryDTOList = categoryService.findCategories(DataFileCategoryId.FIND_MEDICATION_ID);
        if (dataCategoryDTOList != null || dataCategoryDTOList.size() > 0) {
            model.addAttribute("childCategoryList", dataCategoryDTOList);
        }
        return "/search/findMedicationHome";
    }

    /**
     * 跳转到对症找药搜索结果页
     *
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/find/medication/list")
    public String findMedicationList(String searchingText, String categoryId, Pageable pageable, Model model,HttpSession session) throws SystemException {
        //获取二级目录
        List<DataCategoryDTO> dataCategoryDTOList = categoryService.findCategories(DataFileCategoryId.FIND_MEDICATION_ID);
        if (dataCategoryDTOList != null || dataCategoryDTOList.size() > 0) {
            model.addAttribute("childCategoryList", dataCategoryDTOList);
        }

        //是否搜索子分类
        if (categoryId == null || "0".equals(categoryId)) {
            //如果没有带子分类id 则表示搜索父分类。
            categoryId = DataFileCategoryId.FIND_MEDICATION_ID;
        } else {
            //带了子分类ID则设置默认分类信息显示子分类
            for (DataCategoryDTO dataCategoryDTO : dataCategoryDTOList) {
                if (categoryId.equals(dataCategoryDTO.getId())) {
                    model.addAttribute("childCategoryId", categoryId);
                }
            }
        }

        if(searchingText==null){
            searchingText=(String)session.getAttribute("searchingText");
        }else{
            //记录上次的搜索内容，翻页时要用回原来的搜索内容。
            session.setAttribute("searchingText",searchingText);
        }

        if (searchingText != null) {
            model.addAttribute("searchingText", searchingText);
        }


        //处理分页
        int rows = pageable.getPageSize();//用户没传入pageSize数据时，已经默认初始化为15，所以默认rows=15
        int start = (pageable.getPageNum() - 1) * rows;//用户没传入pageNum数据时，已经默认初始化为1，所以默认start=0；
        //不允许获取太多数据。
        if (rows > 100) {
            rows = 100;
        }
        if (rows <= 0) {
            rows = 15;
        }
        if (start < 0) {
            start = 0;
        }

        //过滤solr的控制字符，避免恶意请求
        searchingText = SolrUtil.escapeSolrQueryChars(searchingText);

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart(start);
        rows=rows>maxRows?maxRows:rows;
        solrQuery.setRows(rows);
        solrQuery.set("q", "authed:true AND ( title:" + searchingText + " content:" + searchingText + " summary:" + searchingText + " detail:" + searchingText + " fileContent:" + searchingText + " )");
        solrQuery.set("fq", "historyId:*" + categoryId + "*");//类似于MySQL的like %categoryId%
        QueryResponse response = null;
        MyPage<DataFileSearchDTO> myPage = new MyPage<DataFileSearchDTO>();
        myPage.setPageSize(rows);
        myPage.setPageNum(pageable.getPageNum() >= 1 ? pageable.getPageNum() : 1);
        try {
            //response = entitySearchService.search(solrQuery, DATA_FILE_CORE);
            response=dataFileSearchService.search(solrQuery);
            myPage.setDataList(response.getBeans(DataFileSearchDTO.class));
            myPage.setTotal(response.getResults().getNumFound());//符合查询条件的总数。不是单页记录数
            model.addAttribute("myPage", myPage);
        } catch (Exception e) {
            throw new SystemException("T_T 很抱歉！ 没有找到数据！(solr err)");
        }
        //获取的数据结果结构说明
        //detailKeys 用集合记录表头的字段
        //dataFileList 记录包含多少行，以DataFile元素的id关联每一行的内容，k1是DataFile的id
        //dataFileDetailMap的结构{k1:{k2:value,k2:value....},...} k1是DataFile的id,k2值与表头字段值相等，value是内容
        //获取药品数据详情信息
        List<DataFileSearchDTO> dataFileSearchDTOList = myPage.getDataList();
        if (dataFileSearchDTOList != null || dataFileSearchDTOList.size() > 0) {
            HashMap<String, HashMap<String, String>> dataFileDetailMapMap = new HashMap<String, HashMap<String, String>>();
            //HashSet<String> detailKeys = new HashSet<String>();
            for (DataFileSearchDTO dataFileSearchDTO : myPage.getDataList()) {
                List<DataFileDetail> dataFileDetailList = dataFileService.selectDetailByDataFileId(dataFileSearchDTO.getId());
                if (dataFileDetailList == null || dataFileDetailList.size() == 0) {
                    dataFileDetailMapMap.put(dataFileSearchDTO.getId(), null);
                } else {
                    HashMap<String, String> detailMap = new HashMap<String, String>();
                    for (DataFileDetail dfd : dataFileDetailList) {
                        //detailKeys.add(dfd.getDetailKey());
                        detailMap.put(dfd.getDetailKey(), dfd.getDetailValue());
                    }
                    dataFileDetailMapMap.put(dataFileSearchDTO.getId(), detailMap);
                }
            }
            model.addAttribute("dataFileList", dataFileSearchDTOList);
            //model.addAttribute("detailKeys", detailKeys);
            model.addAttribute("dataFileDetailMapMap", dataFileDetailMapMap);
        }

        return "/search/findMedicationDetail";

    }


    /**
     * 获取侧栏内容
     * 如果传来了参数则按参数指定的目录查找内容。没有就按默认的数据：ASIDE_CATEGORY_IDS
     *
     * @return
     */
    @RequestMapping(value = "/aside/category")
    @ResponseBody
    public ArrayList<HashMap<String, Object>> asideCategory() {
        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        //处理article 的数据
        Pageable pageable = new Pageable();
        pageable.setPageNum(1);
        pageable.setPageSize(5);
        //在线购药指南 和 用药常识
        String[] dataFileCategoryIds = new String[2];
        dataFileCategoryIds[0] = ASIDE_CATEGORY_IDS.CATEGORY1;
        dataFileCategoryIds[1] = ASIDE_CATEGORY_IDS.CATEGORY2;
        for (String categoryId : dataFileCategoryIds) {
            Category category = categoryService.selectByPrimaryKey(categoryId);
            if (category != null) {
                HashMap<String, Object> dataFileData = new HashMap<String, Object>();
                dataFileData.put("categoryName", category.getName());
                dataFileData.put("categoryId", categoryId);
                dataFileData.put("mapping", "/search/pharmacist/advice");
                pageable.getParams().put("categoryId", categoryId);
                MyPage<DataFile> myPage = dataFileService.page(pageable);
                List<DataFile> dataFileList = myPage.getDataList();
                if (dataFileList != null && dataFileList.size() > 0) {
                    dataFileData.put("entityList", dataFileList);
                }
                result.add(dataFileData);
            }
        }

        //热门医药新闻
        ArticleCategory articleCategory = articleService.selectCategory(ASIDE_CATEGORY_IDS.CATEGORY3);
        if (articleCategory != null) {
            MyPage<Article> myPage = articleService.findAppBanners(pageable, ASIDE_CATEGORY_IDS.CATEGORY3);
            HashMap<String, Object> articleData = new HashMap<String, Object>();
            articleData.put("categoryName", articleCategory.getName());
            articleData.put("categoryId", ASIDE_CATEGORY_IDS.CATEGORY3);
            articleData.put("mapping", "/search/safe/medication");
            List<Article> articleList = myPage.getDataList();
            if (articleList != null && articleList.size() > 0) {
                articleData.put("entityList", articleList);
            }
            result.add(articleData);
        }
        return result;
    }

    //侧栏显示的dataFile内容的默认目录ID。
    interface ASIDE_CATEGORY_IDS {
        public static final String CATEGORY1 = "17051817321067996395";//在线购药指南  目录id （dataFile）
        public static final String CATEGORY2 = "17051817321067996394";//用药常识  目录id（dataFile）
        public static final String CATEGORY3 = "17080110423820252113";//热门医药新闻  目录id（article）
    }


    public interface ArticleCategoryId {
        public static final String SAFE_MEDICATION_ID = "170510121523528";
        public static final String NEWS_ID = "17080110423820252114";//页头搜索项，搜这个新闻目录
    }

    public interface DataFileCategoryId {
        public static final String DOCTOR_ADVICE_ID = "17051817223925018055";
        public static final String PHARMACIST_ADVICE_ID = "17051817231168693629";
        public static final String FIND_MEDICATION_ID = "17051817321067996391";
    }



    //======================以下内容是更新检索库的一些操作=================================================

    /**
     * 更新索引库里面的所有记录
     * @param user 系统管理员账号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/reload/{core}")
    @ResponseBody
    public String reload(@PathVariable String core, String user, String password, HttpServletRequest request) throws Exception {
        //在这里校验更新权限，不允许其他人乱动
        if(!"admin".equals(user)||!"123456".equals(password)){
            return "没有访问权限";
        }

        ServletContext servletContext = request.getServletContext();
        if (servletContext == null) {
            return "系统错误！";
        }

        //这里的article常量是跟url传来的末尾词相对应的。
        if ("article".equals(core)) {
            articleSearchService.empty();
            articleSearchService.reload();
        } else if ("dataFile".equals(core)) {
            dataFileSearchService.empty();
            dataFileSearchService.reload();
        } else {
            return "您要更新的内容不存在";
        }
        return core + "数据更新中...";
    }

    /**
     * 获取更新进度
     * @param user 系统管理员账号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/get/schedule/{core}")
    @ResponseBody
    public String getSchedule(@PathVariable String core, String user, String password, HttpServletRequest request) throws Exception {
        //在这里校验更新权限，不允许其他人乱动
        if(!"admin".equals(user)||!"123456".equals(password)){
            return "没有访问权限";
        }

        ServletContext servletContext = request.getServletContext();
        if (servletContext == null) {
            return "系统错误！";
        }

        //这里的article常量是跟url传来的末尾词相对应的。
        if ("article".equals(core)) {
            return articleSearchService.getReloadSchedule();
        } else if ("dataFile".equals(core)) {
            return dataFileSearchService.getReloadSchedule();
        } else {
            return "您要获取的内容不存在";
        }
    }

    /**
     * 暂停更新索引库里面的内容
     * @param user 系统管理员账号
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/stop/reload/{core}")
    @ResponseBody
    public String stopReload(@PathVariable String core, String user, String password, HttpServletRequest request) throws Exception {
        //在这里校验更新权限，不允许其他人乱动
        if(!"admin".equals(user)||!"123456".equals(password)){
            return "没有访问权限";
        }

        ServletContext servletContext = request.getServletContext();
        if (servletContext == null) {
            return "系统错误！";
        }

        //这里的article常量是跟url传来的末尾词相对应的。
        if ("article".equals(core)) {
            articleSearchService.stopReload();
            return "已发出article停止更新命令";
        } else if ("dataFile".equals(core)) {
            dataFileSearchService.stopReload();
            return "已发出dataFile停止更新命令";
        } else {
            return "您要操作的内容不存在";
        }
    }

    @RequestMapping(value = "/reload/core")
    public String reloadCore(HttpServletRequest request) throws Exception {
        return "/search/reloadCore";
    }
}
