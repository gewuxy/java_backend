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

    // 文件对象
    public static File file = null;

    // 输入图路径
    public static String inputDir = "Z:\\news\\images\\";

    // 输出图路径
    public static String outputDir = "Z:\\news\\images\\";

    // 输入图文件名
    public static String inputFileName;

    // 输出图文件名
    public static String outputFileName;

    // 默认输出图片宽
    public static int outputWidth = 120;

    // 默认输出图片高
    public static int outputHeight = 120;

    // 是否等比缩放标记(默认为等比缩放)
    public static boolean proportion = true;


    /**
     * 获得图片大小
     *
     * @param path 图片路径
     * @return
     */
    public static long getImgSize(String path) {
        file = new File(path);
        return file.length();
    }


    /**
     * 等比压缩图片
     * @param inputDir 大图片路径
     * @param outputDir 生成小图片路径
     * @param inputFileName 大图片文件名
     * @param outputFileName 生成小图片文名
     * @param outputWidth 生成小图片宽度
     * @param outputHeight 生成小图片高度
     * @param gp 是否等比缩放 默认为true
     * @return
     */
    public static String pressImg(String inputDir, String outputDir, String inputFileName, String outputFileName,
                                  int outputWidth, int outputHeight, boolean gp) {
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
                if (proportion == true) {
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


    public static void main(String[] arg) {
        String inputDir = CompressImgUtils.inputDir + "20180117\\";
        String outputDir = CompressImgUtils.inputDir + "20180117\\";
        String inputFileName = "18011714534680923159.jpg";
        String outputFileName = "s_"+inputFileName;
        int width = CompressImgUtils.outputWidth;
        int height = CompressImgUtils.outputHeight;

        System.out.println("输入的图片大小：" + getImgSize(inputDir + inputFileName) / 1024 + "KB");

        pressImg(inputDir, outputDir, inputFileName, outputFileName, width, height, CompressImgUtils.proportion);

        System.out.println("输出的图片大小：" + getImgSize(outputDir + outputFileName) / 1024 + "KB");

    }


}
