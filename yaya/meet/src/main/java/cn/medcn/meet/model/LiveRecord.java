package cn.medcn.meet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lixuan on 2017/9/26.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_csp_live_record")
public class LiveRecord {

    @Id
    protected String id;

    protected Integer courseId;//对应的课件ID

    protected String replayUrl;//视频重播地址

    protected String liveId;//对应的直播信息ID

    protected Integer onlineCount;//在线人数

    protected Integer playCount;//播放总人数
}
