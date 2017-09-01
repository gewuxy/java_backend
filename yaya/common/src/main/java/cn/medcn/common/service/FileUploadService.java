package cn.medcn.common.service;

import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lixuan on 2017/6/2.
 */
public interface FileUploadService {
    /**
     * 文件上传
     * @param file
     * @param relativePath 相对路径
     * @return
     */
    FileUploadResult upload(MultipartFile file, String relativePath) throws SystemException;

    /**
     * 移除文件
     * @param relativePath
     */
    void removeFile(String relativePath);


    FileUploadResult uploadBinaryWav(MultipartFile file, String relativePath) throws SystemException;
}
