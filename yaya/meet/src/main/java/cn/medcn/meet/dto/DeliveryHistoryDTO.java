package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/9/27.
 */
@Data
@NoArgsConstructor
public class DeliveryHistoryDTO implements Serializable {

    protected String headimg;//接收方头像

    protected Integer acceptId;//接收方ID

    protected String acceptName;//接收方名称

    protected int acceptCount;//已投稿数

    @Transient
    protected String sign; //单位号简介
}
