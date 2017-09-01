package cn.medcn.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/19.
 */
@Data
@NoArgsConstructor
public class DataCategoryDTO implements Serializable {

    private String id;

    private String name;

    private Boolean leaf;
}
