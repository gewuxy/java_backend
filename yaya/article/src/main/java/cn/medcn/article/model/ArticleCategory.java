package cn.medcn.article.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/9.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_article_category")
public class ArticleCategory implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;
    /**栏目名称*/
    private String name;
    /**父ID*/
    private String preId;
    /**序号*/
    private Integer sort;
    /**是否是叶子节点*/
    private Boolean leaf;

    @Transient
    private ArticleCategory parent;


}
