import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.dto.MeetMessageDTO;
import cn.medcn.meet.dto.MeetTuijianDTO;
import cn.medcn.meet.dto.MeetTypeState;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.MeetMessageService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.SignService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lixuan on 2017/4/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-*.xml"})
public class MeetTest {

    private String meetId = "17042112132942370437";

    private Integer userId = 7;

    @Autowired
    private MeetService meetService;

    @Autowired
    private MeetMessageService meetMessageService;

    @Autowired
    private SignService signService;

    @Test
    public void testFindTuijian(){
        Pageable pageable = new Pageable(1, 5);
        MyPage<MeetTuijianDTO> page = meetService.findMeetTuijian(pageable, 7);
        for(MeetTuijianDTO tuijian:page.getDataList()){
            System.out.println("tuijian : "+tuijian.getMeetName()+" 主讲者="+tuijian.getLecturer());
        }
    }


    @Test
    public void testFindMeetTypeState(){
        List<MeetTypeState> list = meetService.findMeetTypes(7);
        for (MeetTypeState state:list){
            System.out.println("name="+state.getName()+" - count="+state.getCount());
        }
    }


    @Test
    public void testAddMeet(){
        Meet meet = new Meet();
        meet.setId(UUIDUtil.getNowStringID());
        meet.setMeetName("测试会议");
        meet.setCreateTime(new Date());
        //meet.setLecturer("李教授");
        meet.setMeetType("儿科");
        meet.setOrganizer("yaya大讲堂");
        meet.setOwner("test@qq.com");
        meet.setOwnerId(14);
        meet.setState((short)1);
        meet.setPublishTime(new Date());
        meetService.insert(meet);
    }

    @Test
    public void testAddTuijian() throws Exception {
        MeetTuijian tuijian = new MeetTuijian();
        tuijian.setMeetId("17042112132942370437");
        tuijian.setLecturer("王主任");
        tuijian.setLecturerTitle("主任医师");
        meetService.addMeetTuijian(tuijian);
    }

    @Test
    public void testFindMeetInfo(){
        MeetInfoDTO info = meetService.findMeetInfo("17042512131640894904");
        Assert.assertNotNull(info);
        System.out.println("info = "+info.getMeetName());
    }

    @Test
    public void testAddModule() throws Exception {
        MeetModule module = new MeetModule();
        module.setMeetId("17042512131640894904");
//        module.setFunctionId(MeetModule.ModuleFunction.PPT.getFunId());
//        module.setModuleName(MeetModule.ModuleFunction.PPT.getFunName());
        module.setFunctionId(MeetModule.ModuleFunction.SURVEY.getFunId());
        module.setModuleName(MeetModule.ModuleFunction.SURVEY.getFunName());
        //module.setMainFlag(true);
        meetService.addMeetModule(module);
    }


    @Test
    public void testAddPosition(){
        MeetPosition position = new MeetPosition();
        position.setMeetId("17042512131640894904");
        position.setModuleId(4);
        position.setPositionName("珠江新城花城湾B3栋2501室");
        position.setPositionLat(23.119303);
        position.setPositionLng(113.326393);
        signService.insert(position);
    }


    @Test
    public void testSearchMeet(){
        String keyword = "yaya";
        Pageable pageable = new Pageable(1,5);
        pageable.getParams().put("keyword", keyword);
        pageable.getParams().put("userId", userId);
        MyPage<MeetInfoDTO> page = meetService.searchMeetInfo(pageable);
        System.out.println("pages = "+page.getPages());
        for(MeetInfoDTO info:page.getDataList()){
            System.out.println("meet_name = "+info.getMeetName());
        }
        //System.out.println(list.size());
    }


    @Test
    public void testAddMeetProperty(){
        MeetProperty property = new MeetProperty();
        property.setMeetId("17042512131640894904");
        property.setEduCredits(0);
        property.setXsCredits(100);
        property.setSpecifyProvince("广东省");
        property.setLinkman("李轩");
        //property.setMemberLimit(true);
        meetService.insertMeetProp(property);
    }


    @Test
    public void testAddMeetMessage() throws Exception {
        for(int i = 0; i < 1; i++){
            MeetMessageDTO message = new MeetMessageDTO();
            message.setMeetId("17042512131640894904");
            message.setMessage("用户["+i+"]说:你好世界["+new Random().nextInt(1000)+"]");
            message.setSenderId(7);
            message.setSender("李轩");
            message.setHeadimg("http://img1.imgtn.bdimg.com/it/u=2593538730,3924807743&fm=23&gp=0.jpg");
            message.setMsgType(0);
            message.setSendTime(new Date());
            meetMessageService.publish(message);
        }
    }

    @Test
    public void testPopMeetMessage() throws Exception {
        MeetMessage message = meetMessageService.bpopFromQueue();
        if(message != null){
            System.out.println(message.getMeetId()+" - "+message.getMessage());
        }
    }


    @Test
    public void testPop() throws Exception {
        MeetMessage meetMessage = meetMessageService.popFromQueue();
        if (meetMessage!=null){
            meetMessageService.insert(meetMessage);
        }
    }


    @Test
    public void testUpdateMeet(){
        Meet meet = meetService.selectByPrimaryKey("17042512131640894904");
        Lecturer lecturer = new Lecturer();
        lecturer.setName("李叫兽");
        lecturer.setHeadimg("/upload/headimg/123456.jpg");
        lecturer.setTitle("主任医师");
        lecturer.setHospital("南方医院");
        lecturer.setDepart("骨外科");
        meet.setLecturer(lecturer);

        meetService.updateMeet(meet);
    }
}
