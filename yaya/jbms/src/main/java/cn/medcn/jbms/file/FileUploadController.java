package cn.medcn.jbms.file;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.jbms.Constants;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    private String appFileUploadBase;

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
        if (file.getSize() > limitSize && limitSize != null && limitSize != 0){
            return APIUtils.error("文件大小超出限制");
        }
        String originalName = file.getOriginalFilename();
        String houzhui = file.getOriginalFilename().substring(originalName.lastIndexOf("."));
        if(!fileTypeAllowed.contains(houzhui.substring(1).toLowerCase())){
            return APIUtils.error("文件格式["+houzhui+"]不被允许上传");
        }
        String saveFilePath = appFileUploadBase+fileType;
        String saveFileName = System.currentTimeMillis()+houzhui;
        File saveFile = new File(saveFilePath+"/"+saveFileName);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return APIUtils.error("保存文件出错");
        }
        Map<String, String> map = Maps.newHashMap();
        map.put("url1",appFileBase+saveFilePath+"/"+saveFileName);//绝对路径
        map.put("url2",fileType+"/"+saveFileName);//相对路径
        String result = APIUtils.success(map);
        return result;
    }


    /**
     * 文件下载
     * @param fileName
     * @param fileType
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(String fileName, String fileType, HttpServletRequest request) throws IOException {
//        String filePath = request.getRealPath(BASE_PAHT+"/"+fileType+"/"+fileName);
//        File downLoadFile = new File(filePath);
//        HttpHeaders headers = new HttpHeaders();
//        //这里需要将编码改为ISO8859-1 下载中文名文件不乱码
//        headers.setContentDispositionFormData("attachment", new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(downLoadFile),
//                headers, HttpStatus.CREATED);
        return null;
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
        return APIUtils.success();
    }

}
