package cn.medcn.user.model;

import cn.medcn.common.Constants;
import cn.medcn.common.utils.LocalUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Liuchangling on 2017/12/8.
 * csp套餐表
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_package")
public class CspPackage implements Serializable {
    @Id
    protected Integer id;
    // 套餐版本 中文
    protected String packageCn;
    // 套餐版本 繁体
    protected String packageTw;
    // 套餐版本 英文
    protected String packageUs;
    // 月费 单位：人民币
    protected float monthRmb;
    // 月费 单位：美元
    protected float monthUsd;
    // 年费 单位：人民币
    protected float yearRmb;
    // 年费 单位：美元
    protected float yearUsd;
    // 时效 单位：月
    protected Integer limitTime;
    // 限制会议  单位：个
    protected Integer limitMeets;
    //是否是无期限
    @Transient
    protected Boolean unlimited;

    // 用户id
    @Transient
    protected String userId;

    // 已经使用的会议数
    @Transient
    protected Integer usedMeetCount;

    // 套餐开始时间
    @Transient
    protected Date packageStart;

    // 套餐结束时间
    @Transient
    protected Date packageEnd;

    @Transient
    // 套餐即将过期天数
    protected Integer expireDays;

    @Transient
    // 套餐过期 隐藏的会议数
    protected Integer hiddenMeetCount;

    // 过期提醒
    @Transient
    protected String expireRemind;

    @Transient
    protected Integer meetTotalCount;

    public enum TypeId {
        STANDARD(1, "标准版","標準版","standard"),
        PREMIUM(2, "高级版","高級版","premium"),
        PROFESSIONAL(3, "专业版","專業版","professional");

        private Integer id;
        private String label;
        private String labelTw;
        private String labelUs;

        public Integer getId () {
            return id;
        }

        public String getLabel() {
            return label;
        }
        public String getLabelTw() {
            return labelTw;
        }
        public String getLabelUs() {
            return labelUs;
        }

        TypeId(Integer id, String label,String labelTw,String labelUs) {
            this.id = id;
            this.label = label;
            this.labelTw = labelTw;
            this.labelUs = labelUs;
        }
    }

}
