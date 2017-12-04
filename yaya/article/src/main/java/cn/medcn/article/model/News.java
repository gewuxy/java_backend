package cn.medcn.article.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/2/13.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_article")
public class News extends Article implements Serializable {

    /**
     * 替换掉jsp标签
     * @param basePath
     */
    public void replaceJSPTAG(String basePath){
        if(!StringUtils.isBlank(articleImg)){
            articleImg = (basePath+articleImg);
            if (!StringUtils.isBlank(content)){
                String basePath2 = articleImg.substring(0, articleImg.lastIndexOf("/"));
                content = content.replaceAll("<%=strFullImageDir %>",basePath2);
            }
        }
    }

    /**
     * 旧的新闻类型枚举
     */
    public enum NEWS_TYPE{
        YXZH(1, "医学综合"),
        YOZX(2, "药品资讯"),
        HYYW(3, "行业要闻"),
        GSDT(4, "公司动态");

        public Integer type;
        public String label;

        NEWS_TYPE(Integer type, String label){
            this.type = type;
            this.label = label;
        }
    }

    /**
     * 新的新闻类型枚举
     */
    public enum NEWS_SELECT_TYPE {
        ZYZX(1, "专业资讯"),
        AQYY(2, "安全用药"),
        YYDT(3, "医药动态"),
        GSDT(4, "公司动态");

        public Integer type;
        public String label;

        NEWS_SELECT_TYPE(Integer type, String label){
            this.type = type;
            this.label = label;
        }
    }

    public enum NEWS_CATEGORY{
        CATEGORY_YYXW("医药新闻", "170510101223456"),
        CATEGORY_ZYZX("专业资讯","170510121548925"),
        CATEGORY_AQYY("安全用药","170510121523528"),
        CATEGORY_YYDT("医药动态", "170510121548956"),
        CATEGORY_GSDT("公司动态", "17051016434243959379"),
        CATEGORY_ZXGY("在线购药指南", "17112118044649791417"),
        CATEGORY_RMYY("热门医药新闻", "17080110423820252113"),
        CATEGORY_YYCS("用药常识", "17112118044523161352"),
        CATEGORY_YSJY("药师建议", "170515121565482"),
        CATEGORY_YISJY("医师建议", "170515121577458"),
        CATEGORY_DZXY("对症下药", "171117095417088");


        public String label;

        public String categoryId;

        NEWS_CATEGORY(String label, String categoryId){
            this.label = label;
            this.categoryId = categoryId;
        }

    }

    /**
     * 旧类型转换成新类型
     * @param oldtype
     * @return
     */
    public static String oldType2NewType(String oldtype){
        String newtype = null;
        for (NEWS_TYPE nt:NEWS_TYPE.values()){
            if (oldtype.equals(nt.label)){
                newtype = NEWS_SELECT_TYPE.values()[nt.ordinal()].label;
                break;
            }
        }
        return newtype;
    }


    public static void main(String[] args) {
        String content = "http://192.168.1.114:83/mednews/gsdt/20170105/images/gsdt_shouge3_170105.jpg";
        System.out.println(content.substring(content.lastIndexOf("/")));
    }
}
