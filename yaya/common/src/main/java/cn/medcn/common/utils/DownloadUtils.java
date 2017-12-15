package cn.medcn.common.utils;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by LiuLP on 2017/12/15/015.
 */
public class DownloadUtils {

    public static final String CONTENT_TYPE = "application/octet-stream";

    public static final String CONTENT_DISPOSITION = "Content-Disposition";

    public static final String FILE_NAME_PREFIX = "attachment;filename=\"";

    public static final String FILE_NAME_SUFFIX = ".mp4\"";





    /**
     * 打开视频下载框
     * @param fileName
     * @param response
     * @param downloadUrl
     * @throws SystemException
     */
    public static void openDownloadBox(String fileName, HttpServletResponse response, String downloadUrl) throws SystemException {

        try {
            response.reset();
            response.setContentType(CONTENT_TYPE);
            fileName = URLEncoder.encode(fileName, Constants.CHARSET);
            response.setHeader(CONTENT_DISPOSITION, FILE_NAME_PREFIX + fileName + FILE_NAME_SUFFIX);
            URL url=new URL(downloadUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            Integer fileLength = conn.getContentLength();
            response.setContentLength(fileLength);
            BufferedInputStream ins=new BufferedInputStream(conn.getInputStream());
            int i = ins.read();
            while(i!=-1){
                response.getOutputStream().write(i);
            }
            ins.close();
            response.getOutputStream().close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(e.getMessage());
        }
    }
}
