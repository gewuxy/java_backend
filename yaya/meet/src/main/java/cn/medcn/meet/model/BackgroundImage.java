package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by Liuchangling on 2018/1/19.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_background_image")
public class BackgroundImage {
    @Id
    protected Integer id;
    // 图片名称
    protected String imgName;
    // 图片大小
    protected Float imgSize;
    // 图片地址
    protected String imgUrl;

    public static void HandelImgUrl(List<BackgroundImage> list, String fileBase){
        if(list != null){
            for(BackgroundImage image : list){
                image.setImgUrl(fileBase + image.getImgUrl());
            }
        }
    }

}
