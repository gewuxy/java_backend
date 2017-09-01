package cn.medcn.common.utils;

import cn.medcn.common.supports.FileTypeSuffix;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * PPT文件转换工具类
 * Created by lixuan on 2017/2/15.
 */
public class PPTUtils {

    private static final Log log = LogFactory.getLog(PPTUtils.class);

    private static final String TYPE_2003 = "2003";

    private static final String TYPE_2007 = "2007";


    public static int convert2003(File file, String outPath) throws Exception{
        try {
            InputStream is = new FileInputStream(file);
            HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(is));
            is.close();
            Dimension pgsize = ppt.getPageSize();
            for (int i = 0; i < ppt.getSlides().size(); i++) {
                //防止中文乱码
                for(HSLFShape shape : ppt.getSlides().get(i).getShapes()){
                    if(shape instanceof HSLFTextShape) {
                        HSLFTextShape tsh = (HSLFTextShape)shape;
                        for(HSLFTextParagraph p : tsh){
                            for(HSLFTextRun r : p){
                                r.setFontFamily("宋体");
                            }
                        }
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                ppt.getSlides().get(i).draw(graphics);
                String filename = outPath+File.separator+ (i+1) + "."+FileTypeSuffix.IMAGE_SUFFIX_JPG.suffix;
                FileOutputStream out = new FileOutputStream(filename);
                javax.imageio.ImageIO.write(img, FileTypeSuffix.IMAGE_SUFFIX_PNG.suffix, out);
                out.close();
            }
            return ppt.getSlides().size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int convert2007(File file, String outPath) throws IOException {
        FileInputStream is = new FileInputStream(file);
        XMLSlideShow ppt = new XMLSlideShow(is);
        is.close();
        Dimension pgsize = ppt.getPageSize();
        for (int i = 0; i < ppt.getSlides().size(); i++) {
            try {
                //防止中文乱码
                for(XSLFShape shape : ppt.getSlides().get(i).getShapes()){
                    if(shape instanceof XSLFTextShape) {
                        XSLFTextShape tsh = (XSLFTextShape)shape;
                        for(XSLFTextParagraph p : tsh){
                            for(XSLFTextRun r : p){
                                r.setFontFamily("宋体");
                            }
                        }
                    }
                }
                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                ppt.getSlides().get(i).draw(graphics);
                String filename = outPath +"/"+ (i+1) + "."+ FileTypeSuffix.IMAGE_SUFFIX_JPG;
                FileOutputStream out = new FileOutputStream(filename);
                javax.imageio.ImageIO.write(img, FileTypeSuffix.IMAGE_SUFFIX_JPEG.suffix, out);
                out.close();
            } catch (Exception e) {
                LogUtils.error(log, "第"+i+"张ppt转换出错");
            }
        }
        return ppt.getSlides().size();
    }

    /**
     * 将ppt文件转换成图片并保存
     * 保存格式为jpg
     * @param file
     * @param outPath
     * @throws Exception
     */
    public static int ppt2Image(File file, String outPath) throws Exception{
        String type = checkFile(file);
        if (type == null) {
            throw new Exception("您输入的文件不是PPT文件");
        }
        int pageSize = 0;
        switch (type){
            case TYPE_2003:
                pageSize = convert2003(file, outPath);
                break;
            case TYPE_2007:
                pageSize = convert2007(file, outPath);
                break;
            default:
                break;
        }
        return pageSize;
    }

    /**
     * 检测文件是否为ppt
     * @param file
     * @return
     */
    public static String checkFile(File file) {
        String type = null;
        String filename = file.getName();
        String suffix;
        if (filename != null && filename.indexOf(".") != -1) {
            suffix = filename.substring(filename.indexOf("."));
            if(suffix.equalsIgnoreCase(FileTypeSuffix.PPT_SUFFIX_PPT.suffix)){
                type = TYPE_2003;
            }else if(suffix.equals(FileTypeSuffix.PPT_SUFFIX_PPTX.suffix)){
                type = TYPE_2007;
            }
        }
        return type;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(ppt2Image(new File("D:\\lixuan\\test\\test.ppt"), "D:\\lixuan\\test"));
    }

}
