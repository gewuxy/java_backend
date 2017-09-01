package cn.medcn.weixin.util;

import cn.medcn.common.utils.JsonUtils;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.dto.Result;
import org.springframework.beans.factory.annotation.Value;

/**检查微信接口返回结果
 * Created by LiuLP on 2017/7/21/021.
 */
public class ResultCheckUtil {



    private static final String ERR_CODE = "errcode";



    public static Result checkResult(String resultStr){
        Integer errCode = (Integer)JsonUtils.getValue(resultStr,ERR_CODE);
        if(errCode != null){
            return new Result(errCode);
        }else{
            return new Result();
        }
    }
}
