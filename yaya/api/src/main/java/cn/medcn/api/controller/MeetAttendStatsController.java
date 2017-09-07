package cn.medcn.api.controller;

import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.meet.dto.MeetAttendDTO;
import cn.medcn.meet.service.MeetStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**参会统计
 * Created by LiuLP on 2017/8/2/002.
 */
@Controller
@RequestMapping("/api")
public class MeetAttendStatsController extends BaseController{


    @Autowired
    protected MeetStatsService meetStatsService;


    /**
     *获取个人参会记录
     * @param offset 相对当前时间的偏移量,上一周offset为1，下一周offset为-1
     * @return
     */
    @RequestMapping(value = "/meet/attend_stats", method = RequestMethod.POST)
    @ResponseBody
    public String attendStats(Integer offset){
        if (offset == null){
            offset = 0;
        }
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        MeetAttendDTO meetAttendDTO = meetStatsService.findFinalAttendByPersonal(userId, offset);
        long[] array = CalendarUtils.getWeekTimeStartToEndByOffset(offset);
        meetAttendDTO.setTimeArray(array);
        meetAttendDTO.setDetailList(meetAttendDTO.build());
        meetAttendDTO.setCountArray(null);
        meetAttendDTO.setDateArray(null);
        meetAttendDTO.setTimeArray(null);
        return  success(meetAttendDTO);
    }


    /**
     * 获取关注的单位号的发布会议记录
     * @param offset 相对当前时间的偏移量，相对当前时间的偏移量,上一周offset为1，下一周offset为-1
     * @return
     */
    @RequestMapping(value = "/meet/publish_stats")
    @ResponseBody
    public String publishStats(Integer offset){
        if (offset == null){
            offset = 0;
        }
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        MeetAttendDTO meetAttendDTO = meetStatsService.findFinalPublishByPersonal(userId, offset);
        long[] array = CalendarUtils.getWeekTimeStartToEndByOffset(offset);
        meetAttendDTO.setTimeArray(array);
        meetAttendDTO.setDetailList(meetAttendDTO.build());
        meetAttendDTO.setCountArray(null);
        meetAttendDTO.setDateArray(null);
        meetAttendDTO.setTimeArray(null);
        return success(meetAttendDTO);
    }


}
