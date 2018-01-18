package cn.medcn.csp.admin.controllor.website;

import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.model.News;
import cn.medcn.article.service.NewsService;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import cn.medcn.csp.admin.log.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param pageable
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String findNewsList(Pageable pageable, String keyword,Model model) {
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
            addFlashMessage(redirectAttributes,"发布成功");
        } else {
            addErrorFlashMessage(redirectAttributes,"发布失败");

        }
        return "redirect:/website/news/list";
    }

    @RequestMapping(value = "/update")
    @RequiresPermissions("website:news:auth")
    @Log(name = "修改新闻内容")
    public String updateNewsContent(News news, RedirectAttributes redirectAttributes) {
        if (news != null) {
            newsService.updateByPrimaryKeySelective(news);
            addFlashMessage(redirectAttributes,"修改成功");
        } else {
            addErrorFlashMessage(redirectAttributes,"修改失败");
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
                addErrorFlashMessage(redirectAttributes,"删除失败");
            }
        } else {
            addErrorFlashMessage(redirectAttributes,"删除失败");
        }
        return "redirect:/website/news/list";
    }

    /**
     * 上传新闻图片
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    @Log(name = "上传新闻图片")
    public String uploadImg(MultipartFile file){
        if (file != null) {
            String fileName = file.getOriginalFilename();
            String suffix = FileUtils.getSuffix(fileName, false);
            // 图片名称
            String saveFileName = StringUtils.nowStr() + suffix;

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

                // 前端显示的图片地址
                String imgUrl = imgFolder + dateFolderPath + "/" + saveFileName;

                // 小图片地址
                String smallImgUrl = imgFolder + dateFolderPath + "/s_" + saveFileName;

                File saveFile = new File(imgFilePath);
                if (!saveFile.exists()) {
                    saveFile.mkdirs();
                }
                try {
                    file.transferTo(saveFile);

                    // 压缩小图片
                    String filePath = CompressImgUtils.inputDir + dateFolderPath + "\\";
                    String smallFileName = "s_" + saveFileName;
                    int width = CompressImgUtils.outputWidth;
                    int height = CompressImgUtils.outputHeight;

                    CompressImgUtils.pressImg(filePath, filePath, saveFileName, smallFileName,
                            width, height, CompressImgUtils.proportion);

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
}
