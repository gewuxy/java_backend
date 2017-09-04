import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dao.MeetFolderDAO;
import cn.medcn.meet.dao.MeetRewardHistoryDAO;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.MeetFolderService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.dto.MaterialDTO;
import cn.medcn.user.dto.MeetingDTO;
import cn.medcn.user.dto.UnitInfoDTO;
import cn.medcn.user.service.AppUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;

/**
 * Created by Liuchangling on 2017/7/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class MeetFolderAPITest {
    @Autowired
    private MeetFolderService meetFolderService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private AudioService audioService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MeetRewardHistoryDAO meetRewardHistoryDAO;

    @Test
    public void testFindRecommend(){
        Pageable pageable = new Pageable();
        pageable.setPageNum(1);
        if (pageable.getPageNum() ==1){
            List<MeetFolderDTO> list = meetService.findRecommendMeetFolder(7345);
            System.out.println(APIUtils.success(list));
        }else{
            List<MeetFolderDTO> list = new ArrayList<MeetFolderDTO>();
            System.out.println(APIUtils.success(list));
        }
    }


    @Test
    public void testFindLeaf(){
        Pageable pageable = new Pageable();
        String preId = "2017072617081230";
        pageable.put("preId", preId);
        pageable.put("userId", 7345);
        MyPage<MeetFolderDTO> page = meetFolderService.findLeafMeetFolder(pageable);
        System.out.println("json: "+APIUtils.success(page.getDataList()));
    }

    @Test
    public void testFindLeafResource(){
        String preId = "20170726170812301";
        Pageable pageable = new Pageable();
        pageable.put("preId", preId);
        pageable.put("userId", 7345);
        MyPage<MeetFolderDTO> page = meetFolderService.findLeafMeetFolder(pageable);
        System.out.println("json: "+APIUtils.success(page.getDataList()));
    }

    @Test
    public void testFindLearnRecord(){
       // Integer s = meetService.findUserLearningRecord("17062316273862869316",7342);
        /*float a = 50;
        float b = 4;
        Integer c = Math.round(a / b);*/
        Integer c = Math.round((float) 0 / 200 * 100);
        System.out.println("----"+c);
    }

    @Test
    public void testFindRewardHistory(){
       List<MeetRewardHistory> rewardHistoryList = meetService.findUserGetRewardHistory("17062316384929986180",7345);
       for (MeetRewardHistory history : rewardHistoryList){
           System.out.println("奖励类型："+history.getRewardLable()+" 奖励数值："+history.getRewardPoint());
       }
        System.out.println("json: "+APIUtils.success(rewardHistoryList));
    }

    @Test
    public void testSaveRewardHistory(){
        String meetId = "17062316273832654738";
        Integer userId = 7345;
        Map<String,Object> conditionMap = new HashMap<String,Object>();
        conditionMap.put("meetId",meetId);
        List<MeetSetting> meetSettingList = meetService.findMeetSetting(conditionMap);
        if (!CheckUtils.isEmpty(meetSettingList)) {
            Integer propMode = 0;
            for (MeetSetting setting : meetSettingList) {
                propMode = setting.getPropMode();

                MeetRewardHistory rewardHistory = new MeetRewardHistory();
                rewardHistory.setUserId(userId);
                rewardHistory.setMeetId(meetId);
                rewardHistory.setRewardType(propMode);
                MeetRewardHistory history = meetRewardHistoryDAO.selectOne(rewardHistory);

                rewardHistory.setRewardPoint(setting.getPropValue());
                rewardHistory.setRewardTime(new Date());

                if (history == null) {
                    meetRewardHistoryDAO.insert(rewardHistory);
                }
            }
        }
    }

    @Test
    public void testAddRecord(){
        /*AudioHistory audioHistory = new AudioHistory();
        audioHistory.setUserId(7342);
        audioHistory.setMeetId("17062316273862869316");
        audioHistory.setDetailId(358888);
        audioHistory.setCourseId(13758);
        audioHistory.setStartTime(new Date());
        audioHistory.setUsedtime(2);
        audioHistory.setFinished(true);
        audioService.insertHistory(audioHistory);

        List<AudioCourseDetail> audioCourseList = audioService.findPPtTotalCount(audioHistory.getMeetId());
        Integer pptTotalCount = audioCourseList.size();
        // 查询用户观看的记录数
        Integer userViewCount = audioService.findUserViewPPTCount(audioHistory.getMeetId(),audioHistory.getUserId());
        Integer completeCount = 0;
        if (pptTotalCount>0){
            completeCount = Math.round((float)userViewCount / (float)pptTotalCount * 100) ;
        }
        MeetLearningRecord learningRecord = new MeetLearningRecord();
        learningRecord.setUserId(audioHistory.getUserId());
        learningRecord.setMeetId(audioHistory.getMeetId());
        learningRecord.setFunctionId(1);
        learningRecord.setCompleteProgress(completeCount);
        meetService.saveOrUpdateLearnRecord(learningRecord);*/

       /* ExamHistory history = new ExamHistory();
        history.setUserId(7342);
        history.setMeetId("17062316273862869316");
        MeetLearningRecord learningRecord = new MeetLearningRecord();
        learningRecord.setUserId(history.getUserId());
        learningRecord.setMeetId(history.getMeetId());
        learningRecord.setFunctionId(3);
        meetService.saveOrUpdateLearnRecord(learningRecord);*/

        Integer completeProgress = meetService.findUserLearningRecord("17062316273862869316",7342);
        System.out.println("progress: "+completeProgress);
    }

    @Test
    public void testFindDepartment(){
        List<MeetTypeState> typeStates = meetService.findMeetTypes(7345);
        System.out.println(APIUtils.success(typeStates));
    }

    @Test
    public void testMeetInfo(){
        //MeetInfoDTO info = meetService.findMeetInfo("17062316273862869316");
        MeetInfoDTO info = meetService.findFinalMeetInfo("17082215121483692842",7342);
        System.out.println(APIUtils.success(info));
    }

    @Test
    public void testMeetList(){
        Integer userId =7345;
        Integer state = 2;
        Pageable pageable = new Pageable();
        pageable.put("state", state);
        //pageable.put("meetType", depart);
        pageable.put("userId", userId);
        MyPage<MeetFolderDTO> page = meetFolderService.findMyMeetFolderList(pageable);
        if (!CheckUtils.isEmpty(page.getDataList())) {
            for (MeetFolderDTO folderDTO : page.getDataList()) {
                // 设置用户学习进度
                meetService.setUserLearningRecord(folderDTO, userId);
                if (folderDTO.getType() == MeetFolderDTO.FolderType.folder.ordinal()) { // 文件夹 设置相应的状态
                    folderDTO.setState(state);
                }
            }
        }
        System.out.println(APIUtils.success(page.getDataList()));
    }

    @Test
    public void testUnitInfo(){
        Pageable pageable = new Pageable();
        pageable.put("masterId",1200007);
        pageable.put("slaverId",7345);
        pageable.setPageNum(2);
        MyPage<MeetFolderDTO> page = meetFolderService.findUnitMeetFolder(pageable);
        UnitInfoDTO unitInfoDTO = appUserService.findUnitInfo(pageable);
        if (pageable.getPageNum() == 1) {
            if(!StringUtils.isEmpty(unitInfoDTO.getHeadimg())){
                unitInfoDTO.setHeadimg(unitInfoDTO.getHeadimg());
            }
            if (!CheckUtils.isEmpty(unitInfoDTO.getMaterialList())){
                for(MaterialDTO dto:unitInfoDTO.getMaterialList()){
                    dto.setMaterialUrl(dto.getMaterialUrl());
                }
            }
            if(Constants.DEFAULT_ATTENTION_PUBLIC_ACCOUNT.intValue() == unitInfoDTO.getId().intValue()){
                unitInfoDTO.setAttentionNum(unitInfoDTO.getAttentionNum());
            }
            unitInfoDTO.setMeetFolderList(page.getDataList());
            System.out.println(APIUtils.success(unitInfoDTO));
        } else { //下拉列表操作，只返回会议文件夹及会议列表
            List folderDTOList = page.getDataList();
            System.out.println(APIUtils.success(folderDTOList));
        }
    }

    @Test
    public void testFavorite(){
        Pageable pageable = new Pageable();
        pageable.put("userId",7356);
        pageable.put("resourceType",0);
        MyPage<MeetInfoDTO> myPage =  meetService.findMyFavorite(pageable);
        System.out.println(APIUtils.success(myPage.getDataList()));
    }

    @Test
    public void testSearch(){
        Pageable pageable = new Pageable();
        pageable.put(Constants.KEY_WORD, "Ya");
        MyPage<MeetFolderDTO> page = meetService.searchMeetFolderInfo(pageable);
        System.out.println(APIUtils.success(page.getDataList()));
    }
}
