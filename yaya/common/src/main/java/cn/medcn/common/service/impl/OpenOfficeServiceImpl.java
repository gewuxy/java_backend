package cn.medcn.common.service.impl;

import cn.medcn.common.Constants;
import cn.medcn.common.service.OfficeConvertProgress;
import cn.medcn.common.service.OpenOfficeService;
import cn.medcn.common.supports.FileTypeSuffix;
import cn.medcn.common.utils.*;
import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by lixuan on 2017/7/7.
 */
@Service
public class OpenOfficeServiceImpl implements OpenOfficeService {

    private static Log log = LogFactory.getLog(OpenOfficeServiceImpl.class);

    @Value("${app.file.upload.base}")
    private String appFileUploadBase;

    /**
     * 将PPT转换成PDF文件
     *
     * @param sourceFilePath
     * @param targetFilePath
     */
    @Override
    public void convert2PDF(String sourceFilePath, String targetFilePath) {
        String cmd = String.format(pdfConvertCommand, sourceFilePath, targetFilePath);
        LogUtils.debug(log, "convert command = " + cmd);
        CommandUtils.CMD(cmd);
    }

    @Override
    public List<String> convertPPT(String sourceFilePath, String targetDir, int courseId, HttpServletRequest request) {
        String pdfFilePath = appFileUploadBase + "temp/";
        convert2PDF(sourceFilePath, pdfFilePath);
        List<String> imageList = null;
        String pdfFileName = null;
        try {
            pdfFileName = pdfFilePath + sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.lastIndexOf(".")) + "." + FileTypeSuffix.PDF_SUFFIX.suffix;
            imageList = pdf2Images(pdfFileName, targetDir, courseId, request);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //删除临时文件
            if (CheckUtils.isNotEmpty(pdfFileName)) {
                File tempFile = new File(pdfFileName);
                if (tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
        return imageList;
    }

    /**
     * 将PDF转换成图片
     *
     * @param pdfPath
     * @param imgDirPath
     * @param request
     * @return
     * @throws Exception
     */
    public List<String> pdf2Images(String pdfPath, String imgDirPath, int courseId, HttpServletRequest request) {
        LogUtils.debug(log, "convert pdf to image pdfPath = " + pdfPath + " - imgDirPath = " + imgDirPath);

        File file = new File(pdfPath);
        File dir = new File(appFileUploadBase + imgDirPath);
        if (!file.exists()) {
            return null;
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            List<String> imageNameList = Lists.newArrayList();
            String suffix = FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix;
            String imageFilePath;
            BufferedImage image = null;
            for (int i = 0; i < pageCount; i++) {
                image = renderer.renderImage(i, 2f);
                imageFilePath = imgDirPath + UUIDUtil.getNowStringID() + "." + suffix;
                ImageIO.write(image, suffix, new File(appFileUploadBase + imageFilePath));
                if (request != null) {
                    request.getSession().setAttribute(Constants.OFFICE_CONVERT_PROGRESS, new OfficeConvertProgress(pageCount, i + 1, courseId));
                }
                imageNameList.add(imageFilePath);
                image.flush();
                image = null;
            }
            return imageNameList;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.error(log, e.getMessage());
        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (file.exists()) {
                file.delete();
            }
        }
        return null;
    }


    /**
     * 打印PDF 然后转换成图片
     *
     * @param padFilePath
     * @param imageDir
     * @param courseId
     * @param request
     * @return
     */
    @Override
    public List<String> pdfPrintAndToImages(String padFilePath, String imageDir, int courseId, HttpServletRequest request) {
        PDDocument document = null;
        try {
            document = PDDocument.load(new File(padFilePath));
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdf2Images(padFilePath, imageDir, courseId, request);
    }

    @Override
    public void convert2Html(String sourceFilePath, String destFilePath) {
        String cmd = String.format(htmlConvertCommand, sourceFilePath, destFilePath);
        CommandUtils.CMD(cmd);
    }
}
