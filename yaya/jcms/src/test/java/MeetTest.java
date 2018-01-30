import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.AudioHistoryDAO;
import cn.medcn.meet.dao.MeetLearningRecordDAO;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.*;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Autowired
    protected MeetLearningRecordDAO meetLearningRecordDAO;

    @Autowired
    protected AudioHistoryDAO audioHistoryDAO;

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
        String cspUserId = "1";
        pageable.put("cspUserId", cspUserId);
        MyPage<CourseDeliveryDTO> page = audioService.findCspMeetingList(pageable);

        if (!CheckUtils.isEmpty(page.getDataList())) {
            for (CourseDeliveryDTO deliveryDTO : page.getDataList()) {
                if (deliveryDTO.getPlayPage() == null) {
                    deliveryDTO.setPlayPage(0);
                }
            }
        }
        System.out.println(APIUtils.success(page.getDataList()));
    }

    @Test
    public void testdelete() {
        AudioCourse course = new AudioCourse();
        course.setId(14423);
        course.setDeleted(true);
        audioService.updateByPrimaryKeySelective(course);
        System.out.println(APIUtils.success());
    }

    @Test
    public void testFindLiveDetails(){
        List<AudioCourseDetail> details = audioService.findLiveDetails(14471);
        System.out.println(JSON.toJSONString(details));
    }

    /**
     * 将之前的t_audio_history表中的数据统计一下 赋值到t_meet_learning_record表的记录当中
     */
    @Test
    public void testUserLearningRecord() {
        // 查询所以用户ppt学习记录
        MeetLearningRecord condition = new MeetLearningRecord();
        condition.setFunctionId(1);
        List<MeetLearningRecord> recordList = meetLearningRecordDAO.select(condition);
        if (recordList != null && recordList.size() != 0) {
            for (MeetLearningRecord record : recordList) {
                String meetId = record.getMeetId();
                int userId = record.getUserId();
                // 根据会议id和用户id 查询用户的ppt学习记录 并更新记录
                AudioHistory userHistory = new AudioHistory();
                userHistory.setMeetId(meetId);
                userHistory.setUserId(userId);
                List<AudioHistory> historyList = audioHistoryDAO.select(userHistory);
                if (historyList != null && historyList.size() != 0) {
                    int totalTime = 0;
                    for (AudioHistory history : historyList) {
                        totalTime = totalTime + history.getUsedtime();
                    }
                    record.setUsedTime(new Long(totalTime));
                    System.out.println("--更新用户id="+userId+"= 学习总时长为="+totalTime);
                    // 更新记录
                    meetLearningRecordDAO.updateByPrimaryKey(record);
                }
            }
        }
    }
}
