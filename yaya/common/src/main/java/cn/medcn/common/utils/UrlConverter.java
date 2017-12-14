package cn.medcn.common.utils;

import cn.medcn.common.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 将Map转换成url
 * Created by lixuan on 2017/12/14.
 */
public class UrlConverter {

    private static final String FIRST_SEPARATOR = "?";

    private static final String DEFAULT_SEPARATOR = "&";

    private static final String DEFAULT_CONNECTOR = "=";

    private Map<String, Object> params;

    private String url;

    private UrlConverter() {
    }

    private void createNewParams() {
        this.params = new HashMap<>();
    }


    /**
     * 转换成URL字符串
     *
     * @return
     */
    public String convert() {
        StringBuffer buffer = new StringBuffer(url);

        boolean isFirst = url.indexOf(FIRST_SEPARATOR) < 0;

        for (String key : this.params.keySet()) {
            if (isFirst) {
                buffer.append(FIRST_SEPARATOR).append(key).append(DEFAULT_CONNECTOR).append(params.get(key));
                isFirst = false;
            } else {
                buffer.append(DEFAULT_SEPARATOR).append(key).append(DEFAULT_CONNECTOR).append(params.get(key));
            }
        }

        return buffer.toString();
    }


    public static UrlConverter newInstance(String url) {
        if (CheckUtils.isEmpty(url)) {
            throw new RuntimeException("Invalid url ");
        }
        UrlConverter urlUtils = new UrlConverter();
        urlUtils.createNewParams();
        urlUtils.url = url;

        return urlUtils;
    }

    public UrlConverter put(String key, Object value) {
        try {
            this.params.put(key, URLEncoder.encode(String.valueOf(value), Constants.CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        UrlConverter converter = UrlConverter.newInstance("http://10.0.0.250:8081/csp").
                put("user_id", "123").
                put("abroad", true).
                put("sex", 1).put("username", "李轩").
                put("callback", "https://cspmeeting.com/csp/login?id=123");

        System.out.println(converter.convert());

        System.out.println(URLEncoder.encode("https://cspmeeting.com/csp/login?id=123", Constants.CHARSET));
    }
}
