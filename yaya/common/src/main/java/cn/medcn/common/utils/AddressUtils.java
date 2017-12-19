package cn.medcn.common.utils;

import cn.medcn.common.dto.AddressDTO;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据IP判断地理位置工具类
 * Created by lixuan on 2017/9/29.
 */
public class AddressUtils {

    protected static final String IP_CHECK_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static final String DEFAULT_PROVINCE = "广东省";

    public static final String DEFAULT_CITY = "广州市";

    /**
     * 根据IP返回地理位置
     * @param ip
     * @return
     */
    protected static String getAddress(String ip){
        Map<String, Object> params = new HashMap<>();
        params.put("ip", ip);
        String response = HttpUtils.get(IP_CHECK_URL, params);

        if (CheckUtils.isEmpty(response)) {
            return null;
        }
        return response;
    }


    public static AddressDTO parseAddress(String ip){
        String response = getAddress(ip);
        if (CheckUtils.isEmpty(response)) {
            return null;
        }
        String data = JsonUtils.getValue(response, "data").toString();
        return JSON.parseObject(data, AddressDTO.class);
    }

    /**
     * 判断IP是否是局域网IP
     * @param ip
     * @return
     */
    public static boolean isLan(String ip){
        if (ip == null) {
            ip = "";
        }
        return ip.startsWith("192.168") || ip.startsWith("10.0") || ip.startsWith("172");
    }



    public static void main(String[] args) {
        AddressDTO address = parseAddress("59.111.90.245");
        System.out.println(address.getIsp());
    }
}
