package cn.medcn.article.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Liuchangling on 2017/10/25.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_article")
public class CspArticle implements Serializable {
    @Id
    protected String id;
    // 中文标题
    protected String titleCn;
    // 台湾标题
    protected String titleTw;
    // 英文标题
    protected String titleUs;
    // 中文内容
    protected String contentCn;
    // 台湾内容
    protected String contentTw;
    // 英文内容
    protected String contentUs;
    // 图片
    protected String imgUrl;
    // 文章是否审核
    protected Integer authed;

}
