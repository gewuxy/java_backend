package cn.medcn.weixin.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**过滤掉微信昵称中的图片
 * Created by LiuLP on 2017/9/5/005.
 */
public class EmojiFilterUtil {


    /**
     * 过滤昵称特殊表情
     */
    public static String filterName(String name) {
        if(name==null){
            return null;

        }
        if("".equals(name.trim())){
            return "";
        }

        Pattern patter = Pattern.compile("[a-zA-Z0-9\u4e00-\u9fa5]");
        Matcher match = patter.matcher(name);

        StringBuffer buffer = new StringBuffer();

        while (match.find()) {
            buffer.append(match.group());
        }

        String nickname = buffer.toString();
        if(StringUtils.isEmpty(nickname)){
            nickname = "未知昵称";
        }
        return nickname;
    }

}
