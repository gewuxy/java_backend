package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lixuan on 2017/5/25.
 */
@Data
@NoArgsConstructor
public class CourseReprintDTO implements Serializable {
    // 会议id
    private Integer id;
    // 会议标题
    private String title;
    // 会议分类
    private String category;
    // 单位号名称
    private String pubUserName;
    // 奖励或支付象数
    private Integer credits;
    // 创建时间
    private Date createTime;
    // 发布者id
    private Integer owner;
    // 是否转载（获取）
    private Boolean reprinted;
    // 奖励或支付
    private Integer shareType;
    // 会议封面
    private String coverUrl;
    // 单位号账号
    private String username;
    // 单位号头像
    private String headimg;

    public enum AcquiredStatus {
        no_get_acquired, // 未获取（未转载）
        acquired;  // 已获取（已转载）
    }
}
