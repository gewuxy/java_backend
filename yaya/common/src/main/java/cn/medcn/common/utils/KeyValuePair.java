package cn.medcn.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/2/9.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValuePair implements Serializable{

    private String key;

    private String value;
}
