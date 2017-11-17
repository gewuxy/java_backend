package cn.medcn.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by lixuan on 2017/1/3.
 */
public class HttpUtils {
    private static Log log = LogFactory.getLog(HttpUtils.class);

    /**
     * 定义编码格式 UTF-8
     */
    public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";

    private static final String URL_PARAM_CONNECT_FLAG = "&";

    private static final String EMPTY = "";

    private static MultiThreadedHttpConnectionManager connectionManager = null;

    private static int connectionTimeOut = 25000;

    private static int socketTimeOut = 25000;

    private static int maxConnectionPerHost = 50;

    private static int maxTotalConnections = 50;

    private static HttpClient client;

    static{
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }


    public static String get(String url, Map<String, Object> params, Map<String, Object> headers){
        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);
        if(strtTotalURL.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, URL_PARAM_DECODECHARSET_UTF8));
        } else {
            strtTotalURL.append(url).append("&").append(getUrl(params, URL_PARAM_DECODECHARSET_UTF8));
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());
        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
            getMethod.setRequestHeader("Access-Control-Allow-Origin","*");
            if(headers!=null){
                for(String key:headers.keySet()){
                    getMethod.setRequestHeader(key, (String) headers.get(key));
                }
            }
            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if(statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
            }else{
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        }catch(HttpException e){
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        }catch(IOException e){
            log.error("发生网络异常", e);
            e.printStackTrace();
        }finally{
            if(getMethod != null){
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return response;
    }


    public static String post(String url, Map<String,Object> params, Map<String,Object> headers){
        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
            postMethod.setRequestHeader("Access-Control-Allow-Origin","*");
            if(headers!=null){
                for(String key:headers.keySet()){
                    postMethod.setRequestHeader(key, (String) headers.get(key));
                }
            }
            //将表单的值放入postMethod中

            Set<String> keySet = params.keySet();
            for(String key : keySet){
                String value = (String) params.get(key);
                postMethod.addParameter(key, value);
            }
            //执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if(statusCode == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            }else{
                log.error("响应状态码 = " + postMethod.getStatusCode());
            }
        }catch(HttpException e){
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        }catch(IOException e){
            log.error("发生网络异常", e);
            e.printStackTrace();
        }finally{
            if(postMethod != null){
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        return response;
    }


    public static boolean accessAble(String url){
        GetMethod getMethod = null;
        try {
            getMethod = new GetMethod(url);
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
            getMethod.setRequestHeader("Access-Control-Allow-Origin", "*");

            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                return true;
            } else {
                return false;
            }
        }catch (ConnectException e){
            return false;
        }catch(HttpException e){
            return false;
        }catch(IOException e){
            return false;
        }finally{
            if(getMethod != null){
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
    }

    /**
     * POST方式提交数据
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String,String> params){
        String response = EMPTY;
        PostMethod postMethod = null;
        try {
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
            //将表单的值放入postMethod中
            Set<String> keySet = params.keySet();
            for(String key : keySet){
                String value = params.get(key);
                postMethod.addParameter(key, value);
            }
            //执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if(statusCode == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            }else{
                log.error("响应状态码 = " + postMethod.getStatusCode());
            }
        }catch(HttpException e){
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        }catch(IOException e){
            log.error("发生网络异常", e);
            e.printStackTrace();
        }finally{
            if(postMethod != null){
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        return response;
    }


    public static String postJson(String url, JSONObject jsonParam){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost(url);
        method.setHeader("Content-Type","application/json");
        method.setHeader("Content-Encoding","utf-8");
        StringEntity entity = new StringEntity(jsonParam.toJSONString(),"utf-8");//解决中文乱码问题
        entity.setContentEncoding("utf-8");
        entity.setContentType("application/json");
        method.setEntity(entity);
        HttpResponse result = null;
        try {
            result = httpClient.execute(method);
            // 请求结束，返回结果
            String resData = EntityUtils.toString(result.getEntity());
            return resData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GET方式获取数据
     * @param url
     * @return
     */
    public static String get(String url,Map<String,Object> params){
        String response = EMPTY;
        GetMethod getMethod = null;
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);
        if(strtTotalURL.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(getUrl(params, URL_PARAM_DECODECHARSET_UTF8));
        } else {
            strtTotalURL.append(url).append("&").append(getUrl(params, URL_PARAM_DECODECHARSET_UTF8));
        }
        log.debug("GET请求URL = \n" + strtTotalURL.toString());
        try {
            getMethod = new GetMethod(strtTotalURL.toString());
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + URL_PARAM_DECODECHARSET_UTF8);
            //执行getMethod
            int statusCode = client.executeMethod(getMethod);
            if(statusCode == HttpStatus.SC_OK) {
                response = getMethod.getResponseBodyAsString();
            }else{
                log.debug("响应状态码 = " + getMethod.getStatusCode());
            }
        }catch(HttpException e){
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
            e.printStackTrace();
        }catch(IOException e){
            log.error("发生网络异常", e);
            e.printStackTrace();
        }finally{
            if(getMethod != null){
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return response;
    }

    /**
     * 根据Map生成URL字符串
     * @param map
     * @param valueEnc
     * @return
     */
    private static String getUrl(Map<String, Object> map, String valueEnc) {
        if (null == map || map.keySet().size() == 0) {
            return (EMPTY);
        }
        StringBuffer url = new StringBuffer();
        Set<String> keys = map.keySet();
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            if (map.containsKey(key)) {
                String val = (String) map.get(key);
                String str = val != null ? val : EMPTY;
                try {
                    str = URLEncoder.encode(str, valueEnc);
                    if(str.startsWith("[")){
                        JSONArray array = JSONArray.parseArray(str);
                        for(Object obj:array){
                            System.out.println(obj);
                        }
                    }else{
                        url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        String strURL = EMPTY;
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return (strURL);
    }


    public static void copyFromNetwork(String url, String savePath){
        FileOutputStream outputStream = null;
        try {
            URL netWorkURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) netWorkURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);
            File imageFile = new File(savePath);
            outputStream = new FileOutputStream(imageFile);


            outputStream.write(data);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (outputStream != null){
                //关闭输出流
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static byte[] readInputStream(InputStream inStream){
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try{
            while( (len=inStream.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
        }catch (Exception e){
            if(inStream != null){
                try {
                    inStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return outStream.toByteArray();
    }
}
