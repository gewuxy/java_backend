package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Liuchangling on 2018/1/19.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_background_image")
public class BackgroundImage {
    @Id
    protected Integer id;
    // 图片名称
    protected String imgName;
    // 图片大小
    protected Integer imgSize;
    // 图片地址
    protected String imgUrl;

}
