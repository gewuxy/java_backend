package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/6/7.
 */
@Data
@NoArgsConstructor
public class AppUserSimpleDTO implements Serializable {

    private Integer id;

    private String username;
}
