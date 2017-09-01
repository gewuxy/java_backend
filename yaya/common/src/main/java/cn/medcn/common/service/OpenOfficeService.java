package cn.medcn.common.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lixuan on 2017/7/7.
 */
public interface OpenOfficeService {

    String pdfConvertCommand = "soffice --headless --invisible --convert-to pdf %s --outdir %s";

    String htmlConvertCommand = "soffice --headless --invisible --convert-to html %s --outdir %s";

    /**
     * 将PDF文件转换成图片
     * @param pptSourcePath
     * @param imageDir
     * @return
     */
    List<String> convertPPT(String pptSourcePath, String imageDir, int courseId, HttpServletRequest request);

    /**
     * 将PPT转换成PDF文件
     * @param sourceFilePath
     * @param targetFilePath
     */
    void convert2PDF(String sourceFilePath, String targetFilePath);

    List<String> pdf2Images(String padFilePath, String imageDir,int courseId, HttpServletRequest request);

    void convert2Html(String sourceFilePath, String destFilePath);
}
