package cn.medcn.article.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Liuchangling on 2017/7/4.
 */
@Data
@NoArgsConstructor
public class NewsReadOnly {
    private Long nid;
    private String subject; //标题
    private String updatedate; //更新日期
    private String imgurl; // 图片URL
    private String contenturl; // 内容URL
    private String type; // 新闻类型
    private String wzimgurl; // 网站新闻图片URL
    private String wzcontenturl; // 网站新闻内容URL
    private String xwfrom; // 新闻来源
    private String author; // 新闻作者
    private String keywords; // 关键字
    private String xwzy; // 新闻摘要
    private String contents; // 新闻HTML内容
    private String newshtml; // 手机版新闻HTML内容
    private Integer review_tag; // 是否审核 0：审核通过 1：待审核


}
