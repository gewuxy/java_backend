package cn.medcn.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用来处理用户地理位置信息的
 * Created by lixuan on 2017/12/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegionDTO implements Serializable {

    protected String userId;

    protected String ip;

}
