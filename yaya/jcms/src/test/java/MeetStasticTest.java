import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.meet.dto.MeetAttendCountDTO;
import cn.medcn.meet.dto.MeetDataDTO;
import cn.medcn.meet.dto.MeetStasticDTO;
import cn.medcn.meet.service.MeetStatsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Liuchangling on 2017/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-*.xml"})
public class MeetStasticTest {

    @Autowired
    private MeetStatsService meetStatsService;

    @Test
    public void  testFindmeetdata(){
        MeetStasticDTO meetDto = meetStatsService.findMeetStastic(14);
        System.out.println("get all meet count: "+ meetDto.getAllAttendCount() +
                " get current meet count: "+ meetDto.getMonthAttendCount());
    }

    @Test
    public void testFindMymeet(){
        Pageable pageable = new Pageable(1,5);
        pageable.getParams().put("userId",14);
        MyPage<MeetDataDTO> page = meetStatsService.findMyMeetList(pageable);
        for (MeetDataDTO md : page.getDataList()) {
            System.out.println("the meet name: " + md.getMeetName() + " the start time: " + md.getCreateTime() +
                                "the module name: " + md.getModuleName());
        }
    }

    @Test
    public void testFindAttendCount(){
        Map<String,Object> params = new HashMap();
        params.put("userId",14);
        params.put("tagNo",0);
        params.put("startTime","2017-05-21 00:00:00");
        params.put("endTime","2017-05-21 23:00:00");
        List<MeetAttendCountDTO> attendList = meetStatsService.findAttendCountByTag(params);
        APIUtils.success(attendList);
        if (attendList!=null && attendList.size()!=0){
            for (MeetAttendCountDTO attendCountDTO : attendList) {
                System.out.println("统计总数:"+ attendCountDTO.getAttendCount() +"  时间："+attendCountDTO.getAttendDate());
            }

        } else {
            System.out.println("没有相关数据统计。。。。");
        }
    }

    @Test
    public void testFindUserDetail(){
        meetStatsService.findAttendUserDetailExcel("17071915061286766938",1200007);
    }

    @Test
    public void testPercent() throws ParseException {
       /* DecimalFormat df = new DecimalFormat("0%");
        float progress = ((float)958 / (float)950);
        System.out.println("float: "+progress);

        String proPercent = df.format(progress);
        System.out.println(proPercent);*/
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        Date st =  sf.parse("2017-08-23 14:52:00") ;
       if (st.after(new Date())) {
            System.out.println("meet.starttime.error");
        }
    }
}
