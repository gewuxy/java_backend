package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/25.
 */
@Data
@NoArgsConstructor
public class ResourceCategoryDTO implements Serializable {

    private String name;

    private Integer count;
}
