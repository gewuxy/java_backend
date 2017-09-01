package cn.medcn.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixuan on 2017/4/20.
 */
public class RegexUtils {

    /**
     * 检测手机格式是否合法
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
//        String reg = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
        String reg = "^1[3|4|5|7|8][0-9]{9}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }


    public static boolean checkEmail(String email){
        if(StringUtils.isEmpty(email)){
            return false;
        }
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static void main(String[] args) {
        String mobile = "15645666786";
        System.out.println(checkMobile(mobile));

        String email = "12312312312@medcn.cn";
        System.out.println(checkEmail(email));
    }
}
