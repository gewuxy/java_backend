package cn.medcn.search.util;

/**
 * Created by weilong on 2017/7/25.
 * 控制字符过滤方法
 */
public class SolrUtil {

    public static String escapeSolrQueryChars(String s) {
        if (s == null || "".equals(s.trim())) {
            return "*";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&' || c == ';' || c == '/'
                    || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        String result = sb.toString();
        return "".equals(result) ? "*" : result;
    }

}
