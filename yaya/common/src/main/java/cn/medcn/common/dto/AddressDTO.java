package cn.medcn.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地理位置
 * Created by lixuan on 2017/9/29.
 */
@Data
@NoArgsConstructor
public class AddressDTO {
    //国家代号
    protected String country_id;
    //国家名称
    protected String country;
    //区域ID
    protected String area_id;
    //区域
    protected String area;
    //省份ID
    protected String region_id;
    //省份
    protected String region;
    //城市ID
    protected String city_id;
    //城市名称
    protected String city;
    //isp 编号
    protected String isp_id;
    //isp名称
    protected String isp;
    //ip地址
    protected String ip;

    public boolean isAbroad(){
        return !this.country_id.endsWith("CN") && !this.country_id.equals("-1");
    }


}
