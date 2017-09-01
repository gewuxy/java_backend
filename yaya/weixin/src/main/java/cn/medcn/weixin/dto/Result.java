package cn.medcn.weixin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LiuLP on 2017/7/21/021.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private  Integer errcode;

    private String errmsg;

    public Result(Integer errcode){
        this.errcode = errcode;
    }



}
