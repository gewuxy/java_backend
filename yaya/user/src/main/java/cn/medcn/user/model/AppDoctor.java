package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lixuan on 2017/4/21.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_app_user_detail")
public class AppDoctor extends AppUserDetail {
    /**许可证号*/
    private String licence;
    /**许可证图片路径*/
    private String licenceImg;
    /**专长*/
    private String major;
    /**职称*/
    private String title;
    /**职位*/
    private String place;
    /**医院ID*/
    private Integer hosId;

    private String cmeId;

    private String hosLevel;

    /** 专科名称 */
    private String specialtyName;
}
