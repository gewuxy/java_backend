import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.jcms.security.Principal;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.CourseDeliveryService;
import cn.medcn.meet.service.ExamService;
import cn.medcn.meet.service.MeetService;
import org.apache.shiro.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-*.xml"})
public class MeetTest {

    @Autowired
    private MeetService meetService;

    @Autowired
    private ExamService examService;

    @Autowired
    protected CourseDeliveryService courseDeliveryService;

    @Autowired
    protected AudioService audioService;

    @Test
    public void testUpdateMeet(){
        Meet meet = meetService.selectByPrimaryKey("17042512131640894934");
        Lecturer lecturer = new Lecturer();
        lecturer.setName("刘主任");
        lecturer.setHeadimg("/upload/headimg/1234567.jpg");
        lecturer.setTitle("主任医师");
        lecturer.setHospital("中山大学附属医院");
        lecturer.setDepart("泌尿外科");
        meet.setLecturer(lecturer);

        meetService.updateMeet(meet);
    }


    @Test
    public void testFindTuijian(){
        Pageable pageable = new Pageable(1, 5);
        MyPage<MeetTuijianDTO> page = meetService.findMeetTuijian(pageable, 7);
        for(MeetTuijianDTO tuijian:page.getDataList()){
            System.out.println("tuijian : "+tuijian.getMeetName()+" 主讲者="+tuijian.getLecturer());
        }
    }


    @Test
    public void testFindMeetInfo(){
        MeetInfoDTO info = meetService.findMeetInfo("17042512131640894904");
        System.out.println(info.getMeetName()+" - "+info.getHeadimg()+" - "+info.getStartTime());
    }

    @Test
    public void testFindMyMeets(){
        Pageable pageable = new Pageable();
        pageable.getParams().put("state",2);
        pageable.getParams().put("userId",7);
        MyPage<MeetInfoDTO> page = meetService.findMyMeets(pageable);
        System.out.println(page.getTotal());
    }

    @Test
    public void testDeleteHistory(){
        ExamHistory history = new ExamHistory();
        history.setExamId(1);
        history.setMeetId("1");
        history.setUserId(1);
        history.setPaperId(1);
        history.setModuleId(1);
        examService.deleteHistory(history);
    }

    @Test
    public void testSearch(){
        String keyword = "测试";
        Pageable pageable = new Pageable();
        pageable.getParams().put("keyword", keyword);
        MyPage<MeetInfoDTO> page = meetService.searchMeetInfo(pageable);
        for(MeetInfoDTO dto:page.getDataList()){
            System.out.println(dto.getMeetName());
        }
    }


    @Test
    public void testFindMeetFavorite(){
        Pageable pageable = new Pageable(1, 3);
        pageable.put("userId", 1200011);
        MyPage<MeetInfoDTO> page = meetService.findMeetFavorite(pageable);
        for (MeetInfoDTO meetInfoDTO : page.getDataList()){
            System.out.println(meetInfoDTO.getMeetName());
        }
    }


    @Test
    public void testFindRecommendMeets(){
        Integer userId = 1200011;
        List<MeetFolderDTO> recommends = meetService.findRecommendMeetFolder(userId);
        for (MeetFolderDTO meetFolderDTO : recommends){
            System.out.println(meetFolderDTO.getMeetName() + " - 会议个数= "+meetFolderDTO.getMeetCount()+" - "+meetFolderDTO.getType()+" - "+ meetFolderDTO.getMeetType() + " - "+meetFolderDTO.getProvince() +" - "+meetFolderDTO.getCity());
        }
    }

    @Test
    public void testAcceptors(){
        MyPage<DeliveryAccepterDTO> page = courseDeliveryService.findAcceptors(new Pageable());
        System.out.println(page.getTotal());
    }

    @Test
    public void testFindCspMeet() {
        Pageable pageable = new Pageable();
        String cspUserId = "17101210221415956422";
        pageable.put("cspUserId", cspUserId);
        MyPage<CourseDeliveryDTO> page = audioService.findCspMeetingList(pageable);

        if (!CheckUtils.isEmpty(page.getDataList())) {
            for (CourseDeliveryDTO deliveryDTO : page.getDataList()) {
                // 录播会议
                if (deliveryDTO.getPlayType().intValue() == AudioCourse.PlayType.normal.getType().intValue()) {
                    // 录播 当前播放第几页
                    if (deliveryDTO.getPlayPage() == null) {
                        deliveryDTO.setPlayPage(0);
                    }
                    // 转换录播音频时长格式
                    long secondTime = Long.parseLong(deliveryDTO.getPlayTime());
                    deliveryDTO.setPlayTime(CalendarUtils.formatTimesDiff(null,null, secondTime));
                } else {
                    // 当前播放第几页
                    if (deliveryDTO.getLivePage() == null) {
                        deliveryDTO.setLivePage(0);
                    }

                    // 如果是直播会议 需计算直播时长
                    int liveState = deliveryDTO.getLiveState().intValue();
                    if (liveState == Live.LiveState.init.ordinal()) {
                        // 直播未开始 设置直播开始时间
                        deliveryDTO.setPlayTime(CalendarUtils.transferLongToDate(deliveryDTO.getStartTime().getTime(),"MM月dd日 HH:mm"));
                    } else if (liveState == Live.LiveState.usable.ordinal()) {
                        // 直播中 计算直播开始时间和当前时间的时间差
                        deliveryDTO.setPlayTime(CalendarUtils.formatTimesDiff(deliveryDTO.getStartTime(), new Date(),0));
                    } else {
                        // 直播结束 计算直播开始和结束的时间差
                        deliveryDTO.setPlayTime(CalendarUtils.formatTimesDiff(deliveryDTO.getStartTime(), deliveryDTO.getEndTime(),0));
                    }
                }

            }
        }
        System.out.println(APIUtils.success(page.getDataList()));
    }
}
