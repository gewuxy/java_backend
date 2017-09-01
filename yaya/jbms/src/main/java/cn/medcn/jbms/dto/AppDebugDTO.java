package cn.medcn.jbms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/4.
 */
@Data
@NoArgsConstructor
public class AppDebugDTO implements Serializable {

    private String type;

    private String token;

    private String params;

    private String url;

}
