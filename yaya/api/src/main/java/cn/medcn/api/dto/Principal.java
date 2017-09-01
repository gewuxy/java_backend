package cn.medcn.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/20.
 */
@Data
@NoArgsConstructor
public class Principal implements Serializable{

    private Integer id;

    private String username;

    private String mobile;

    private String nickname;

    private String headimg;

    private String token;
}
