package cn.medcn.common.service.impl;

import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by lixuan on 2017/6/2.
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${app.file.upload.base}")
    private String appUploadBase;

    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${upload.filetype.allowed}")
    private String uploadAllowed;


    /**
     * 文件上传
     *
     * @param file
     * @param dir 相对路径目录 例如：headimg
     * @return
     */
    @Override
    public FileUploadResult upload(MultipartFile file, String dir) throws SystemException {
        if(file == null){
            throw new SystemException("不能上传空文件");
        }
        String originalName = file.getOriginalFilename();
        String suffix = file.getOriginalFilename().substring(originalName.lastIndexOf("."));
        if(!uploadAllowed.contains(suffix.substring(1).toLowerCase())){
            throw new SystemException("文件格式["+suffix+"]不被允许上传");
        }
        String relativePath = dir+File.separator+UUIDUtil.getNowStringID()+suffix;
        File saveFile = new File(appUploadBase+File.separator+relativePath);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException("保存文件出错");
        }
        FileUploadResult result = new FileUploadResult(relativePath, appFileBase+relativePath);
        return result;
    }

    /**
     * 移除文件
     *
     * @param relativePath
     */
    @Override
    public void removeFile(String relativePath) {
        if(!StringUtils.isEmpty(relativePath)){
            File file = new File(appUploadBase+relativePath);
            if(file.exists()){
                file.delete();
            }
        }
    }

    @Override
    public FileUploadResult uploadBinaryWav(MultipartFile file, String dir) throws SystemException {
        if(file == null){
            throw new SystemException("不能上传空文件");
        }
        String relativePath = dir+File.separator+UUIDUtil.getNowStringID()+"."+ FileTypeSuffix.AUDIO_SUFFIX_WAV.suffix;
        File saveFile = new File(appUploadBase+File.separator+relativePath);
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException("保存文件出错");
        }
        FileUploadResult result = new FileUploadResult(relativePath, appFileBase+relativePath);
        return result;
    }
}
