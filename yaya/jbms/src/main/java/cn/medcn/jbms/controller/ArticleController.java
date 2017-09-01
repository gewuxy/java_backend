package cn.medcn.jbms.controller;

import cn.medcn.article.model.Article;
import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/5/9.
 */
@Controller
@RequestMapping(value="/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value="/list")
    public String list(Model model){
        List<ArticleCategory> list = articleService.findFirstLevelCategories();
        model.addAttribute("list", list);
        return "/article/articleList";
    }

    @RequestMapping(value="/page")
    public String page(Pageable pageable, String categoryId, String keyword, Boolean authed, Model model){
        pageable.getParams().put("categoryId",categoryId);
        model.addAttribute("categoryId", categoryId);
        if(authed!=null){
            pageable.getParams().put("authed", authed);
            model.addAttribute("authed", authed);
        }
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        MyPage page = articleService.findArticleByPage(pageable);
        model.addAttribute("page", page);
        return "/article/articlePage";
    }


    @RequestMapping(value="/save")
    public String save(Article article, RedirectAttributes redirectAttributes){
        boolean isAdd = StringUtils.isEmpty(article.getId());
        article.setCreateTime(new Date());
        article.setAuthed(false);
        if(isAdd){
            article.setId(UUIDUtil.getNowStringID());
            articleService.insert(article);
        }else{
            articleService.updateByPrimaryKeySelective(article);
        }
        addFlashMessage(redirectAttributes, (isAdd?"添加":"修改")+"内容成功");
        return "redirect:/article/page?categoryId="+article.getCategoryId();
    }


    @RequestMapping(value="/view")
    public String view(String id, Model model){
        Article article = articleService.selectByPrimaryKey(id);
        model.addAttribute("article", article);
        return "/article/view";
    }


    @RequestMapping(value="/edit")
    public String edit(String id, String categoryId, Model model) throws SystemException {
        if(StringUtils.isEmpty(id) && StringUtils.isEmpty(categoryId)){
            throw new SystemException("请求参数错误");
        }
        if(!StringUtils.isEmpty(id)){
            Article article = articleService.selectByPrimaryKey(id);
            model.addAttribute("article", article);
            categoryId = article.getCategoryId();
        }
        model.addAttribute("categoryId", categoryId);
        ArticleCategory category = articleService.selectCategory(categoryId);
        model.addAttribute("category", category);
        return "/article/articleForm";
    }
}
