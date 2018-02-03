package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Lixuan
 * @Date: 2018/2/3
 * @Description:
 */
@Data
@NoArgsConstructor
public class ActivityGuideDTO implements Serializable {

    protected Integer id;
    //标题
    protected String title;
    //封面
    protected String coverUrl;
    //时长
    protected Integer duration;
    //0表示新手引导
    protected Integer type = 0;
    //ppt页数
    protected Integer pptSize;
}
