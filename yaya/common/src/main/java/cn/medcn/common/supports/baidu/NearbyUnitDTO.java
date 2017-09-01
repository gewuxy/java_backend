package cn.medcn.common.supports.baidu;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by lixuan on 2017/7/26.
 */
@Data
@NoArgsConstructor
public class NearbyUnitDTO {
    private String name;

    private String address;

    private Integer detail;

    private NearbyDetail detail_info;
}
