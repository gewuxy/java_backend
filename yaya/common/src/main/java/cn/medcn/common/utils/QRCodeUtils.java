package cn.medcn.common.utils;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lixuan on 2017/8/18.
 */
public class QRCodeUtils {
    public static final int QRCODE_DEFAULT_WIDTH = 300;

    public static final int QRCODE_DEFAULT_HEIGHT = 300;

    public static final String PICTURE_FORMAT = "png";

    public static void createQRCode(String text, String outPath){
        Map hints= Maps.newHashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, QRCODE_DEFAULT_WIDTH, QRCODE_DEFAULT_HEIGHT,hints);
            File outputFile = new File(outPath);
            if (!outputFile.exists()) {
                outputFile.mkdirs();
            }
            MatrixToImageWriter.writeToFile(bitMatrix, PICTURE_FORMAT, outputFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
