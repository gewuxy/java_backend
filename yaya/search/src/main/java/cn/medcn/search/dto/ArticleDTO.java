package cn.medcn.search.dto;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by LiuLP on 2017/12/6/006.
 */

public class ArticleDTO {

    @Field
    private String id;

    @Field
    private String history_id;

    @Field
    private String title;

    @Field
    private String author;
    @Field
    private String keywords;

    @Field
    private String summary;

    @Field
    private String content;

    @Field("id")
    public void setId(String id) {
        this.id = id;
    }

    @Field("history_id")
    public void setHistory_id(String history_id) {
        this.history_id = history_id;
    }

    @Field("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Field("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @Field("keywords")
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Field("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Field("content")
    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getHistory_id() {
        return history_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }
}
