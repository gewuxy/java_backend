package cn.medcn.search.dto;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by LiuLP on 2017/12/6/006.
 */
@Data
public class ArticleDTO {

    @Field("id")
    private String id;

    @Field("title")
    private String title;

    @Field("category_id")
    private String category_id;

    @Field("create_time")
    private Date create_time;

    @Field("xfrom")
    private String xfrom;

    @Field("author")
    private String author;

    @Field("content")
    private String content;

    @Field("summary")
    private String summary;

    @Field("file_path")
    private String file_path;

    @Field("article_img")
    private String article_img;

    @Field("creator")
    private Integer creator;

    @Field("keywords")
    private String keywords;

    @Field("authed")
    private Integer authed;

    @Field("hits")
    private Integer hits;

    @Field("out_link")
    private String out_link;

    @Field("article_img_s")
    private String article_img_s;

    @Field("weight")
    private Integer weight;

    @Field("old_id")
    private Integer old_id;

    @Field("history_id")
    private String history_id;

    public static void replaceJSPTAG(String basePath,ArticleDTO dto){
        String articleImg = dto.getArticle_img();
        if (!StringUtils.isBlank(articleImg)) {
            String newArticleImg = basePath + articleImg;
            dto.setArticle_img(newArticleImg);
            if (!StringUtils.isBlank(dto.getContent())){
                String basePath2 = newArticleImg.substring(0, newArticleImg.lastIndexOf("/"));
                dto.setContent(dto.getContent().replaceAll("<%=strFullImageDir %>", basePath2));
            }
        }
    }
}
