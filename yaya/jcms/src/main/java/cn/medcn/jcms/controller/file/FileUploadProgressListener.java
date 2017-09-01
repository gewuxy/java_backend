package cn.medcn.jcms.controller.file;

import cn.medcn.common.Constants;
import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpSession;

/**
 * Created by lixuan on 2017/3/13.
 */
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;

    public FileUploadProgressListener(HttpSession session) {
        this.session=session;
        FileUploadProgress status = new FileUploadProgress();
        session.setAttribute(Constants.UPLOAD_PROGRESS_KEY, status);
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
        FileUploadProgress status = (FileUploadProgress) session.getAttribute(Constants.UPLOAD_PROGRESS_KEY);
        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        session.setAttribute(Constants.UPLOAD_PROGRESS_KEY, status);
    }
}
