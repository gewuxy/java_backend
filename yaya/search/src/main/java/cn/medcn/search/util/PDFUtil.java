package cn.medcn.search.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * Created by weilong on 2017/7/26.
 */
public class PDFUtil {
    public static String getText(String filePath) throws IOException {
        //检验是否为文件路径
        File file = new File(filePath);
        if (!file.isFile()) {
            return null;
        }
        //获取后缀
        int index = filePath.lastIndexOf(".");
        String postfix = "";
        if (index > 0) {
            postfix = filePath.substring(index + 1, filePath.length());
        }
        //读取PDF格式的文件内容
        if ("pdf".equals(postfix)) {
            PDDocument doc = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(doc);
            doc.close();
            return text;
        }
        return null;
    }

}
