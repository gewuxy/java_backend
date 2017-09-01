package cn.medcn.common.supports.baidu;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 查找附近药房信息映射类
 * Created by lixuan on 2017/2/3.
 */
@Data
@NoArgsConstructor
public class SearchResultDTO implements Serializable {

    private Integer status;

    private String message;

    private Integer total;

    private List<NearbyUnitDTO> results;
}
