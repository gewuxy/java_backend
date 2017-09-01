package cn.medcn.jcms.controller.manage;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.user.dto.MaterialDTO;
import cn.medcn.user.model.Material;
import cn.medcn.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

/**
 * Created by LiuLP on 2017/5/16/016.
 */
@Controller
@RequestMapping("mng/material")
public class MaterialController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${app.file.upload.base}")
    private String appUploadBase;

    @Value("${app.file.base}")
    private String appFileBase;

    /**
     * 资料列表
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping("/list")
    public String materialList(Model model,Pageable pageable){
        Integer userId = SubjectUtils.getCurrentUserid();
        pageable.getParams().put("id",userId);
        MyPage<MaterialDTO> myPage = appUserService.findMaterialList(pageable);
        model.addAttribute("fileBase",appFileBase);
        model.addAttribute("page",myPage);
        return "/manage/material";
    }



    @RequestMapping("/delete")
    public String delete(String materialId,RedirectAttributes redirectAttributes){
        Material material = appUserService.findMaterial(materialId);
        if(material != null){
            String filePath = material.getMaterialUrl();
            filePath = appUploadBase + filePath;
            File file = new File(filePath);
            if(file.exists() && file.isFile()){
                if(!file.delete()){
                    
                }
            }
            appUserService.deleteMaterial(materialId);
            addFlashMessage(redirectAttributes, "成功删除资料");
        }

        return "redirect:/mng/material/list";

    }

    /**
     * 资料上传
     * @param file
     * @param fileType
     * @param limitSize
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String saveUpload(@RequestParam(value = "file", required = false)MultipartFile file,String fileType, Long limitSize, HttpServletRequest request){
        if(file == null){
            return APIUtils.error("不能上传空文件");
        }
        if(limitSize == null || limitSize < 0){
            return APIUtils.error("limitSize错误");
        }
        if (file.getSize() > limitSize ){
            return APIUtils.error("文件大小超出限制");
        }
        FileUploadResult result = null;
        try {
            String dir = fileType+File.separator+ SubjectUtils.getCurrentUserid();
             result = fileUploadService.upload(file,dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        if(result != null){
            Material entity = new Material();
            entity.setMaterialUrl(result.getRelativePath());
            entity.setUserId(SubjectUtils.getCurrentUserid());
            entity.setCreateTime(new Date());

            int start = result.getRelativePath().lastIndexOf(".")+1;
            String materialType = result.getRelativePath().substring(start);
            entity.setMaterialType(materialType);

            String oFilename = file.getOriginalFilename();
            int end = oFilename.lastIndexOf(".");
            entity.setMaterialName(oFilename.substring(0,end));
            entity.setId(UUIDUtil.getNowStringID());
            entity.setInfinityId("0");
            entity.setFileSize(file.getSize());
            appUserService.insertMaterial(entity);
        }
        return APIUtils.success();
    }


}
