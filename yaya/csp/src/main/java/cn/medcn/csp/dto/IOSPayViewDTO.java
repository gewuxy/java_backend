package cn.medcn.csp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/12/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IOSPayViewDTO implements Serializable{

    public static final String SHOW_PAY_KEY = "show_pay";

    protected Boolean show = true;

    protected String version;

    public static String getCacheKey(String version){
        return SHOW_PAY_KEY + "_" + version;
    }

}
