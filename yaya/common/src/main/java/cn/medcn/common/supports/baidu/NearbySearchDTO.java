package cn.medcn.common.supports.baidu;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/2/3.
 */
@Data
@NoArgsConstructor
public class NearbySearchDTO implements Serializable{

    public final static String QUERY_HOSPITAL = "医院,医疗";

    public final static String DEFAULT_RADIUS = "2000";

    private Integer pageSize = 10;

    private Integer pageNo = 0;
    //关键字
    private String query;

    private String output = "json";

    private String scope = "2";
    //范围
    private String radius = "10000";

    private double x;//维度

    private double y;//经度

    private String location;//维度+","+经度

    private String filter = "sort_name:distance";

    public String getLocation(){
        return x+","+y;
    }

    public NearbySearchDTO(String query, String radius) {
        this.query = query;
        this.radius = radius;
    }
}
