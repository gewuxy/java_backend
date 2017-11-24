package cn.medcn.jcms.controller.file;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.supports.upload.FileUploadProgress;
import cn.medcn.common.utils.APIUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件下载上传控制器
 * Created by lixuan on 2017/1/12.
 */
@Controller
@RequestMapping(value="/file")
public class FileUploadController extends BaseController {


    @Value("${upload.filetype.allowed}")
    private String fileTypeAllowed;

    @Value("${app.file.upload.base}")
    private String appUploadBase;

    @Value("${app.file.base}")
    private String appFileBase;

    /**
     * 上传文件 并返回文件路径
     * @param file
     * @param fileType FilePath的4个值
     * @param request
     * @return
     */
    @RequestMapping(value="/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file", required = false)MultipartFile file,String fileType, Long limitSize, HttpServletRequest request){
        if (file == null){
            return APIUtils.error("不能上传空文件");
        }
        if(limitSize == null || limitSize < 0){
            return APIUtils.error("limitSize错误");
        }
        if (file.getSize() > limitSize ){
            return APIUtils.error("文件大小超出限制");
        }
        String originalName = file.getOriginalFilename();
        String suffix = file.getOriginalFilename().substring(originalName.lastIndexOf("."));
        if(!fileTypeAllowed.contains(suffix.substring(1).toLowerCase())){
            return APIUtils.error("文件格式["+suffix+"]不被允许上传");
        }
        String saveFilePath = appUploadBase+File.separator+fileType+File.separator;
        String saveFileName = System.currentTimeMillis()+suffix;
        File saveFile = new File(saveFilePath+saveFileName);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return APIUtils.error("保存文件出错");
        }
        Map<String,String> map = new HashMap<>();
        //绝对路径
        map.put("absolutePath", appFileBase+File.separator+fileType+File.separator+saveFileName);
        //相对路径
        map.put("relativePath",fileType+"/"+saveFileName);
        return APIUtils.success(map);
    }

    @RequestMapping(value="/uploadStatus")
    @ResponseBody
    public String uploadStatus(HttpServletRequest request){
        FileUploadProgress progress = (FileUploadProgress) request.getSession().getAttribute(Constants.UPLOAD_PROGRESS_KEY);
        if(progress == null){
            progress = new FileUploadProgress();
        }
        return APIUtils.success(progress);
    }

    @RequestMapping(value="/cleanProgress")
    @ResponseBody
    public String cleanProgress(HttpServletRequest request){
        request.getSession().removeAttribute(Constants.UPLOAD_PROGRESS_KEY);
        request.getSession().removeAttribute(Constants.OFFICE_CONVERT_PROGRESS);
        return APIUtils.success();
    }

}
