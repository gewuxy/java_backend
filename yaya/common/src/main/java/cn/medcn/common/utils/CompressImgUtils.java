package cn.medcn.common.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 压缩图片工具类
 * Created by Liuchangling on 2018/1/17.
 */

public class CompressImgUtils {
    private static Log log = LogFactory.getLog(CompressImgUtils.class);

    /**
     * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法
     * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
     **/
    protected  File file = null; // 文件对象
    protected  String inputDir; // 输入图路径
    protected  String outputDir; // 输出图路径
    protected  String inputFileName; // 输入图文件名
    protected  String outputFileName; // 输出图文件名
    protected  int outputWidth = 120; // 默认输出图片宽
    protected  int outputHeight = 120; // 默认输出图片高
    protected  boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)


    public void setWidthAndHeight(int width, int height) {
        this.outputWidth = width;
        this.outputHeight = height;
    }

    /**
     * 获得图片大小
     *
     * @param path 图片路径
     * @return
     */
    public long getImgSize(String path) {
        file = new File(path);
        return file.length();
    }

    /**
     * 压缩图片处理
     *
     * @return
     */
    public String pressImg() {
        try {
            // 获得源文件
            file = new File(inputDir + inputFileName);
            if (!file.exists()) {
                return "";
            }

            Image img = ImageIO.read(file);

            // 判断图片格式是否正确
            if (img.getWidth(null) == -1) {
                log.error(" can't read,retry!" + "<BR>");
                return "no";
            } else {
                int newWidth;
                int newHeight;

                // 判断是否是等比缩放
                if (this.proportion == true) {
                    // 为等比缩放计算输出的图片宽度及高度
                    double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;
                    double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;
                    // 根据缩放比率大的进行缩放控制
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    newWidth = (int) (((double) img.getWidth(null)) / rate);
                    newHeight = (int) (((double) img.getHeight(null)) / rate);
                } else {
                    newWidth = outputWidth; // 输出的图片宽度
                    newHeight = outputHeight; // 输出的图片高度
                }

                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

                /**
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
                 * 优先级比速度高 生成的图片质量比较好 但速度慢
                 **/
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);
                // JPEGImageEncoder可适用于其他图片类型的转换
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("CompressImage IOException failed,",ex);
        }

        return "ok";
    }


    /**
     * 压缩图片参数初始化
     *
     * @param inputDir
     * @param outputDir
     * @param inputFileName
     * @param outputFileName
     * @return
     */
    public String pressImg(String inputDir, String outputDir, String inputFileName, String outputFileName) {
        // 输入图路径
        this.inputDir = inputDir;
        // 输出图路径
        this.outputDir = outputDir;
        // 输入图文件名
        this.inputFileName = inputFileName;
        // 输出图文件名
        this.outputFileName = outputFileName;
        return pressImg();
    }

    /**
     * 等比压缩图片参数初始化
     *
     * @param inputDir
     * @param outputDir
     * @param inputFileName
     * @param outputFileName
     * @param width
     * @param height
     * @param gp
     * @return
     */
    public String pressImg(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {
        // 输入图路径
        this.inputDir = inputDir;
        // 输出图路径
        this.outputDir = outputDir;
        // 输入图文件名
        this.inputFileName = inputFileName;
        // 输出图文件名
        this.outputFileName = outputFileName;
        // 设置图片长宽
        setWidthAndHeight(width, height);
        // 是否是等比缩放 标记
        this.proportion = gp;
        return pressImg();
    }

    public static void main(String[] arg) {
        String inputDir = "Z:\\news\\images\\20180117\\";
        String outputDir = "Z:\\news\\images\\20180117\\";
        String inputFileName = "18011711080741938028.jpg";
        String outputFileName = "s_"+inputFileName;
        int width = 120;
        int height = 120;

        CompressImgUtils imgUtils = new CompressImgUtils();
        System.out.println("输入的图片大小：" + imgUtils.getImgSize("Z:\\news\\images\\20180117\\18011711080741938028.jpg") / 1024 + "KB");

        imgUtils.pressImg(inputDir, outputDir, inputFileName, outputFileName, width, height, true);

        System.out.println("输出的图片大小：" + imgUtils.getImgSize(outputDir + outputFileName) / 1024 + "KB");

    }


}
