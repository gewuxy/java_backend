package cn.medcn.jbms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.data.model.Category;
import cn.medcn.data.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by lixuan on 2017/5/18.
 */
@Controller
@RequestMapping(value="/data/category")
public class DataCategoryContorller extends BaseController {

    @Autowired
    private CategoryService categoryService;


    @RequestMapping(value="/list")
    public String list(Model model){
        Category condition = new Category();
        condition.setPreId("0");
        List<Category> list = categoryService.select(condition);
        model.addAttribute("list", list);
        return "/data/categoryTree";
    }


    @RequestMapping(value="/treeData")
    @ResponseBody
    public String treeData(String id){
        if(StringUtils.isEmpty(id)){
            APIUtils.success();
        }
        Category condition = new Category();
        condition.setPreId(id);
        List<Category> list = categoryService.select(condition);
        return APIUtils.success(list);
    }

    @RequestMapping(value="/edit")
    public String edit(String id, String preId, Model model){
        if(!StringUtils.isEmpty(id)){
            Category category = categoryService.selectByPrimaryKey(id);
            model.addAttribute("category", category);
            if(!StringUtils.isEmpty(category.getPreId())){
                Category parent = categoryService.selectByPrimaryKey(categoryService.selectByPrimaryKey(category.getPreId()));
                model.addAttribute("parent", parent);
            }
        }
        if(!StringUtils.isEmpty(preId)){
            Category parent = categoryService.selectByPrimaryKey(preId);
            model.addAttribute("parent",parent);
        }
        return "/data/categoryForm";
    }


    @RequestMapping(value="/save")
    public String save(Category category, RedirectAttributes redirectAttributes){
        boolean isAdd = StringUtils.isEmpty(category.getId());
        if(isAdd){
            category.setId(UUIDUtil.getNowStringID());
            categoryService.insert(category);
        }else{
            categoryService.updateByPrimaryKeySelective(category);
        }
        addFlashMessage(redirectAttributes, (isAdd?"添加":"修改")+"栏目成功");
        return "redirect:/data/category/edit?id="+category.getId();
    }
}
