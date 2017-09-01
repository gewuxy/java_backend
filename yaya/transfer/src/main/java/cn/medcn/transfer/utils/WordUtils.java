package cn.medcn.transfer.utils;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * jacob方式实现转换  只能在windows下执行
 * 需要将jacob-1.18-x64.dll放到System32下
 * Created by lixuan on 2017/8/24.
 */
public class WordUtils {

    protected static final String CHARSET_UTF8 = "utf-8";

    protected static final String CHARSET_DOC_DEFAULT = "x-cp20936";

    public static final String CHARSET_GBK = "gbk";

    public static final int DOC_FORMAT_PDF = 17;

    public static final int DOC_FORMAT_HTML = 8;

    public static final int DOC_NOT_SAVE_CHANGE = 0;

    public static void wordToHtml(String docFilePath, String htmlFilePath) {
        ActiveXComponent app = new ActiveXComponent("Word.Application");
        try
        {
            // 设置word应用程序不可见
            app.setProperty("Visible", new Variant(false));
            // documents表示word程序的所有文档窗口，（word是多文档应用程序）
            Dispatch docs = app.getProperty("Documents").toDispatch();
            // 打开要转换的word文件
            Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] { docFilePath, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
            // 作为html格式保存到临时文件
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { htmlFilePath, new Variant(DOC_FORMAT_HTML) }, new int[1]);
            // 关闭word文件
            Dispatch.call(doc, "Close", new Variant(false));

            encodeUTF8(htmlFilePath, CHARSET_GBK, CHARSET_UTF8);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            //关闭word应用程序
            app.invoke("Quit", new Variant[] {});
        }
    }


    public static void wordToHTML(Dispatch docs, String docFilePath, String htmlFilePath){
        // 打开要转换的word文件
        try {
            Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] { docFilePath, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
            Dispatch.call(doc, "SaveAs",htmlFilePath , DOC_FORMAT_HTML);
            Dispatch.call(doc, "Close", false);
            encodeUTF8(htmlFilePath, CHARSET_GBK, CHARSET_UTF8);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public static void wordToPDF(Dispatch docs, String docFilePath, String pdfFilePath){
        try{
            Dispatch doc = Dispatch.call(docs, "Open", docFilePath, false, true).toDispatch();
            Dispatch.call(doc, "SaveAs", pdfFilePath, DOC_FORMAT_PDF);
            Dispatch.call(doc, "Close", false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void wordToPDF(String docFilePath, String pdfFilePath){
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();

            Dispatch doc = Dispatch.call(docs, "Open", docFilePath, false, true).toDispatch();
            File destFile = new File(pdfFilePath);
            if (destFile.exists()) {
                destFile.delete();
            }
            Dispatch.call(doc, "SaveAs", pdfFilePath, DOC_FORMAT_PDF);
            Dispatch.call(doc, "Close", false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (app != null) {
                app.invoke("Quit", DOC_NOT_SAVE_CHANGE);
            }
        }
    }


    public static void encodeUTF8(String filePath, String sourceCharset, String destCharset){
        FileInputStream fileinputstream = null;
        FileOutputStream fileOutputStream = null;
        try{
            fileinputstream = new FileInputStream(filePath);
            int length = fileinputstream.available();
            byte bytes[] = new byte[length];
            fileinputstream.read(bytes);
            fileinputstream.close();
            String replaceContent = "charset="+CHARSET_UTF8;

            String templateContent = new String(bytes, sourceCharset);
            templateContent = templateContent.replaceFirst("charset="+CHARSET_DOC_DEFAULT, replaceContent);
            byte destBytes[] = templateContent.getBytes(destCharset);
            fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(destBytes);
            fileOutputStream.flush();
        } catch (Exception e){

        } finally {
            try {
                if(fileinputstream != null){
                    fileinputstream.close();
                }
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args)  {
        String docFilePath = "D:\\临床指南综合\\临床指南综合\\产科\\其他疾病\\2011美国妊娠期可疑肺栓塞评估指南解读.doc";
        String htmlFilePath = "D:\\converts\\17051915590566189016.html";

        String pdfFilePath = "D:\\converts\\17051915590566189016.pdf";


        wordToHtml(docFilePath, htmlFilePath);

        wordToPDF(docFilePath, pdfFilePath);
    }
}
