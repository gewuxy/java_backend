package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
    // 套餐版本
    protected String version;
    // 金额 单位：人民币
    protected String moneyRmb;
    // 金额 单位：美元
    protected String moneyUsd;
    // 时效 单位：月
    protected Integer limitTime;
    // 限制会议  单位：个
    protected Integer limitMeets;

}
