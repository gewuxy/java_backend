package cn.medcn.jbms.controller;

import cn.medcn.article.model.ArticleCategory;
import cn.medcn.article.service.ArticleService;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by lixuan on 2017/5/9.
 */
@Controller
@RequestMapping(value="/article/category")
public class ArticleCategoryController extends BaseController {


    @Autowired
    private ArticleService articleService;

    @RequestMapping(value="/list")
    public String list(Model model){
        List<ArticleCategory> list = articleService.findFirstLevelCategories();
        //Collections.sort(list);
        model.addAttribute("list", list);
        return "/article/categoryList";
    }


    @RequestMapping(value="/edit")
    public String edit(String id, String preId, Model model){
        if(id != null){
            ArticleCategory category = articleService.selectCategory(id);
            model.addAttribute("category", category);
            if(category != null && !StringUtils.isEmpty(category.getPreId())){
                ArticleCategory parent = articleService.selectCategory(category.getPreId());
                model.addAttribute("parent", parent);
            }
        }
        if(preId != null){
            ArticleCategory parent = articleService.selectCategory(preId);
            model.addAttribute("parent", parent);
        }
        return "/article/categoryForm";
    }

    @RequestMapping(value="/save")
    public String save(ArticleCategory category, RedirectAttributes redirectAttributes){
        Boolean add = StringUtils.isEmpty(category.getId());
        if(StringUtils.isEmpty(category.getPreId())){
            category.setPreId("0");
        }
        if(category.getLeaf() == null){
            category.setLeaf(false);
        }
        if(add){
            category.setId(UUIDUtil.getNowStringID());
            articleService.addCategory(category);
        }else{
            articleService.updateCategory(category);
        }
        addFlashMessage(redirectAttributes, (add?"添加":"修改")+"栏目成功");
        return "redirect:/article/category/edit?id="+category.getId();
    }


    @RequestMapping(value="/treeData")
    @ResponseBody
    public String treeData(String id, Model model){
        //model.addAttribute("preId", pId);
        List<ArticleCategory> list = articleService.findCategoryByPreid(id);
        return APIUtils.success(list);
    }


    @RequestMapping(value="/delete")
    public String delete(String id , String preId, RedirectAttributes redirectAttributes){
        ArticleCategory category = new ArticleCategory();
        category.setId(id);
        category.setPreId(preId);
        articleService.deleteCategory(category);
        addFlashMessage(redirectAttributes, "删除栏目成功");
        return "redirect:/article/category/edit?id="+preId;
    }
}
