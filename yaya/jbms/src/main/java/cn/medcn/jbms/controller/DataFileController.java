package cn.medcn.jbms.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.data.model.Category;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.service.CategoryService;
import cn.medcn.data.service.DataFileService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/5/18.
 */
@Controller
@RequestMapping(value="/data/file")
public class DataFileController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DataFileService dataFileService;

    @Value("${upload.filetype.allowed}")
    private String fileTypeAllowed;

    @Value("${app.file.upload.base}")
    private String appFileUploadBase;

    @Value("${app.file.base}")
    private String appFileBase;


    @RequestMapping(value="/list")
    public String list(Model model){
        Category condition = new Category();
        condition.setPreId("0");
        List<Category> list = categoryService.select(condition);
        model.addAttribute("list", list);

        return "/data/dataTree";
    }


    @RequestMapping(value="/page")
    public String page(Pageable pageable, String categoryId, String keyword, Model model){
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        pageable.getParams().put("categoryId", categoryId);
        MyPage<DataFile> page = dataFileService.findByPage(pageable);
        model.addAttribute("page", page);
        model.addAttribute("categoryId", categoryId);
        return "/data/dataList";
    }


    @RequestMapping(value="/edit")
    public String edit(String id, String categoryId, Model model){
        if(!StringUtils.isEmpty(id)){
            DataFile dataFile = dataFileService.selectByPrimaryKey(id);
            model.addAttribute("dataFile", dataFile);
            model.addAttribute("categoryId", dataFile.getCategoryId());
        }else{
            model.addAttribute("categoryId", categoryId);
        }
        Category condition = new Category();
        condition.setPreId("0");
        List<Category> categoryList = categoryService.select(condition);
        model.addAttribute("categoryList", categoryList);
        return "/data/dataForm";
    }


    @RequestMapping(value = "/save")
    public String save(DataFile dataFile, RedirectAttributes redirectAttributes){
        boolean isAdd = StringUtils.isEmpty(dataFile.getId());
        dataFile.setUpdateDate(new Date());
        dataFile.setAuthed(true);
        if(isAdd){
            dataFile.setId(UUIDUtil.getNowStringID());
            dataFileService.insert(dataFile);
        }else{
            dataFile.setUpdateDate(new Date());
            dataFileService.updateByPrimaryKeySelective(dataFile);
        }
        addFlashMessage(redirectAttributes, (isAdd?"添加":"修改")+"数据信息成功");
        return "redirect:/data/file/page?categoryId="+dataFile.getCategoryId();
    }


    @RequestMapping(value = "/delete")
    public String delete(String id, String categoryId, RedirectAttributes redirectAttributes){
        if(StringUtils.isEmpty(id)){
            addFlashMessage(redirectAttributes,"删除信息失败,不能删除空");
            return "redirect:/data/file/page?categoryId="+categoryId;
        }
        dataFileService.deleteByPrimaryKey(id);
        addFlashMessage(redirectAttributes, "删除数据成功");
        return "redirect:/data/file/page?categoryId="+categoryId;
    }


    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false)MultipartFile file,String rootCategory, String categoryId){
        if (file == null){
            return APIUtils.error("不能上传空文件");
        }
        String originalName = file.getOriginalFilename();
        String suffixName = file.getOriginalFilename().substring(originalName.lastIndexOf("."));
        if(!fileTypeAllowed.contains(suffixName.substring(1).toLowerCase())){
            return APIUtils.error("文件格式["+suffixName+"]不被允许上传");
        }
        String filePath = appFileUploadBase+FilePath.DATA.path+File.separator+rootCategory+File.separator+categoryId ;
        File dir = new File(filePath);
        if(!dir.exists()){
           dir.mkdirs();
        }
        String saveFileName = UUIDUtil.getNowStringID()+suffixName;
        try {
            file.transferTo(new File(filePath+"/"+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        Map<String, String> map = Maps.newHashMap();
        map.put("url1", appFileBase+FilePath.DATA.path+File.separator+rootCategory+File.separator+categoryId+File.separator+saveFileName);
        map.put("url2", FilePath.DATA.path+File.separator+rootCategory+File.separator+categoryId+File.separator+saveFileName);
        map.put("fileSize", String.valueOf(file.getSize()/ Constants.BYTE_UNIT_K));
        return APIUtils.success(map);
    }
}
