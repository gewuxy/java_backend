package cn.medcn.article.model;

import cn.medcn.common.utils.UUIDUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lixuan on 2017/5/9.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_article")
public class Article implements Serializable {

    @Id
    protected String id;
    /**文章标题*/
    protected String title;
    /**栏目ID*/
    protected String categoryId;
    /**创建时间*/
    protected Date createTime;
    /**来源*/
    protected String xfrom;
    /**作者*/
    protected String author;
    /**文章内容 html格式*/
    protected String content;
    /**文章摘要*/
    protected String summary;
    /**pdf或者word格式源文档地址*/
    protected String filePath;
    /**文章显示的图片*/
    protected String articleImg;
    /**文章关键字*/
    protected String keywords;
    /**文章是否审核*/
    protected Boolean authed;
    /**文章点击量*/
    protected Integer hits;
    /**外链文章的地址*/
    protected String outLink;
    /**权重 影响列表中的排序 权重越大排在越前面*/
    protected Integer weight;
    /**旧数据ID*/
    protected Integer oldId;
    /** 小图片地址*/
    protected String articleImgS;
    /** 目录结构链，从跟目录到自身目录ID本身,用下划线分隔*/
    protected String historyId;

}
