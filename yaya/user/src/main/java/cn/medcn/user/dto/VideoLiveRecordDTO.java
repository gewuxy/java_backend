package cn.medcn.user.dto;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.model.Advert;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**视频直播记录
 * Created by LiuLP on 2017/4/28.
 */

@NoArgsConstructor
@Data
public class VideoLiveRecordDTO {

    //t_csp_user_flux_usage表id
  private String id;

  //使用量
  private Integer expense;

  private String courseId;

  //视频下载地址
  private String downloadUrl;

  //视频过期时间
  private Date expireTime;

  //会议名称
  private String meetName;

  //剩余流量
  private Integer flux;

  //过期天数
  private int expireDay;

  //视频下载次数
  private int downloadCount;


  //计算过期天数
  public static void transExpireDay(List<VideoLiveRecordDTO> list){
      if(!CheckUtils.isEmpty(list)){
          Date nowDate = new Date();
          for(VideoLiveRecordDTO dto:list){
              Date expireDate = dto.getExpireTime();
              if(expireDate != null){
                  Long interval = expireDate.getTime() - nowDate.getTime();
                  if(interval > 0){
                      //过期天数
                      int expireDay = (int) (interval /(1000 * 86400));
                      if(expireDay < 1){
                          //过期天数不足一天设置为1天
                          dto.setExpireDay(1);
                      }else {
                          expireDay = interval %(1000 * 86400) > 0 ? expireDay +1 :expireDay;
                          dto.setExpireDay(expireDay);
                      }
                  }
              }

          }
      }

  }





}
