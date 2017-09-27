package cn.medcn.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by lixuan on 2017/9/27.
 */
public class ResponseUtils {

    public static void writeJson(HttpServletResponse httpServletResponse, String jsonStr){
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Writer writer = null;
        try {
            writer = httpServletResponse.getWriter();
            writer.write(jsonStr);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
