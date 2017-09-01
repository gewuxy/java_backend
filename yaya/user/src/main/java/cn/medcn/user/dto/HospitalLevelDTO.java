package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/24.
 */
@Data
@NoArgsConstructor
public class HospitalLevelDTO implements Serializable{

    private String level;

    private Integer count;
}
