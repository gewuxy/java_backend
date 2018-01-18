package cn.medcn.csp.admin.controllor.website;

import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.model.News;
import cn.medcn.article.service.NewsService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import cn.medcn.csp.admin.log.Log;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Liuchangling on 2018/1/16.
 */
@Controller
@RequestMapping("/website/news")
public class NewsController extends BaseController {
    @Autowired
    private NewsService newsService;

    @Value("${app.file.upload.base}")
    protected String appFileUploadBase;

    @Value("${app.file.base}")
    protected String appFileBase;

    /**
     * 查询新闻列表
     *
     * @param pageable
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String findNewsList(Pageable pageable, String keyword, Model model) {
        if (StringUtils.isNotEmpty(keyword)) {
            model.addAttribute("keyword", keyword);

            if (keyword.equals(News.NEWS_CATEGORY.CATEGORY_AQYY.label)) { // 安全用药
                keyword = News.NEWS_CATEGORY.CATEGORY_AQYY.categoryId;
            } else if (keyword.equals(News.NEWS_CATEGORY.CATEGORY_ZYZX.label)) { // 专业资讯
                keyword = News.NEWS_CATEGORY.CATEGORY_ZYZX.categoryId;
            } else if (keyword.equals(News.NEWS_CATEGORY.CATEGORY_YYDT.label)) { // 医药动态
                keyword = News.NEWS_CATEGORY.CATEGORY_YYDT.categoryId;
            } else if (keyword.equals(News.NEWS_CATEGORY.CATEGORY_GSDT.label)) { // 公司动态
                keyword = News.NEWS_CATEGORY.CATEGORY_GSDT.categoryId;
            } else if (keyword.equals(News.NEWS_CATEGORY.CATEGORY_YXZH.label)) { // 医学综合
                keyword = News.NEWS_CATEGORY.CATEGORY_YXZH.categoryId;
            }
            pageable.put("keyword", keyword);
        }
        MyPage<News> page = newsService.findNewsList(pageable);
        model.addAttribute("page", page);
        return "/news/newsList";
    }


    @RequestMapping(value = "/view")
    @RequiresPermissions("website:news:view")
    @Log(name = "查看新闻详情内容")
    public String viewNewsContent(String id, Model model) {
        if (StringUtils.isNotEmpty(id)) {
            News news = newsService.selectByPrimaryKey(id);

            // 查询新闻类别
            List<ArticleCategory> categoryList = newsService.findCategoryList();
            model.addAttribute("categoryList", categoryList);

            String imgUrl = news.getArticleImgS();
            if (StringUtils.isNotEmpty(imgUrl)) {
                model.addAttribute("smallImgUrl", appFileBase + imgUrl);
            }
            model.addAttribute("news", news);
        }
        return "/news/viewEdit";
    }

    @RequestMapping(value = "/to/add")
    @RequiresPermissions("website:news:auth")
    @Log(name = "跳转到发布新闻页面")
    public String toAddNews(Model model) {
        // 查询新闻类别
        List<ArticleCategory> categoryList = newsService.findCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "/news/addNews";
    }

    @RequestMapping(value = "/add")
    @RequiresPermissions("website:news:add")
    @Log(name = "发布新闻")
    public String addNews(News news, RedirectAttributes redirectAttributes) {
        if (news != null) {
            news.setId(StringUtils.nowStr());
            news.setCreateTime(new Date());
            newsService.insert(news);
            addFlashMessage(redirectAttributes, "发布成功");
        } else {
            addErrorFlashMessage(redirectAttributes, "发布失败");

        }
        return "redirect:/website/news/list";
    }

    @RequestMapping(value = "/update")
    @RequiresPermissions("website:news:auth")
    @Log(name = "修改新闻内容")
    public String updateNewsContent(News news, RedirectAttributes redirectAttributes) {
        if (news != null) {
            newsService.updateByPrimaryKeySelective(news);
            addFlashMessage(redirectAttributes, "修改成功");
        } else {
            addErrorFlashMessage(redirectAttributes, "修改失败");
        }
        return "redirect:/website/news/list";
    }


    @RequestMapping(value = "/delete")
    @RequiresPermissions("website:news:del")
    @Log(name = "删除新闻")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        if (id != null) {
            int delSta = newsService.deleteByPrimaryKey(id);
            if (delSta != 1) {
                addErrorFlashMessage(redirectAttributes, "删除失败");
            }
        } else {
            addErrorFlashMessage(redirectAttributes, "删除失败");
        }
        return "redirect:/website/news/list";
    }

    /**
     * 上传新闻图片
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload/img")
    @ResponseBody
    @Log(name = "上传新闻小图片")
    public String uploadImg(MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String suffix = FileUtils.getSuffix(fileName, false);

            // 图片名称
            String saveFileName = StringUtils.nowStr() + "." + suffix;

            String imgFolder = "news/images/";
            // 当天新闻图片日期文件夹
            String dateFolderPath = CalendarUtils.getCurrentDate();
            // 新闻图片地址前缀
            String imgPrefixPath = appFileUploadBase + imgFolder;

            suffix = suffix.toLowerCase();
            if (suffix.endsWith(FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix)
                    || suffix.endsWith(FileTypeSuffix.IMAGE_SUFFIX_JPEG.suffix)
                    || suffix.endsWith(FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix)
                    || suffix.endsWith(FileTypeSuffix.IMAGE_SUFFIX_GIF.suffix)) {

                // 上传保存到服务器的文件
                String imgFilePath = imgPrefixPath + dateFolderPath + "/" + saveFileName;
                File saveFile = new File(imgFilePath);
                if (!saveFile.exists()) {
                    saveFile.mkdirs();
                }

                // 前端显示的图片地址
                String imgUrl = imgFolder + dateFolderPath + "/" + saveFileName;
                // 小图片地址
                String smallImgPath = dateFolderPath + "/s_" + saveFileName;
                String smallImgUrl = null;

                try {
                    file.transferTo(saveFile);
                    // 生成缩略图
                    smallImgUrl = FileUtils.thumbnailUploadImage(saveFile, FileUtils.thumbWidth,
                            FileUtils.thumbWidth, smallImgPath, imgPrefixPath );

                } catch (IOException e) {
                    e.printStackTrace();
                    return APIUtils.error("文件保存出错");
                }

                Map<String, String> map = new HashMap();
                map.put("imgURL", imgUrl);
                map.put("src", appFileBase + imgUrl);
                map.put("title", saveFileName);
                map.put("smallImgUrl", smallImgUrl);
                return success(map);
            } else {
                return error("文件格式错误");
            }
        }
        return null;
    }


    /**
     * 上传新闻图片
     *
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @Log(name = "上传新闻图片")
    public String uploadNewsImg(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("imgFile") MultipartFile[] imgFile) {
        String imgFolder = "news/images/";

        // 当天新闻图片日期文件夹
        String dateFolderPath = CalendarUtils.getCurrentDate();

        // 上传保存的新闻图片地址
        String savePath = appFileUploadBase + imgFolder + dateFolderPath + "/";

        // 前端显示的图片URL
        String saveUrl = appFileBase + imgFolder + dateFolderPath + "/";

        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        String imgSuffix = "gif,jpg,jpeg,png,bmp";
        extMap.put("image", imgSuffix);

        response.setContentType("text/html; charset=UTF-8");

        if (!ServletFileUpload.isMultipartContent(request)) {
            return error("请选择文件");
        }
        // 检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            return error("上传目录不存在");
        }
        // 检查目录写权限
        if (!uploadDir.canWrite()) {
            return error("上传目录没有写权限");
        }

        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "image";
        }

        // 创建文件夹
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }

        // 保存文件
        for (MultipartFile iFile : imgFile) {
            String fileName = iFile.getOriginalFilename();
            // 检查扩展名
            String fileExt = FileUtils.getSuffix(fileName, false).toLowerCase();
            if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
                return error("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
            }

            String newFileName = StringUtils.nowStr() + "." + fileExt;
            try {
                File uploadedFile = new File(savePath, newFileName);

                // 写入文件
                iFile.transferTo(uploadedFile);

            } catch (Exception e) {
                return error("上传文件失败");
            }

            Map<String, String> map = new HashMap();
            map.put("url", saveUrl + newFileName);
            return success(map);
        }

        return null;

    }


}