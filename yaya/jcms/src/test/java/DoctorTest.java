import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.meet.dto.MeetHistoryDTO;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.dao.AppUserDAO;
import cn.medcn.user.dto.PublicAccountDTO;
import cn.medcn.user.model.Group;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.DoctorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-*.xml"})
public class DoctorTest {

   @Autowired
    private DoctorService doctorService;

   @Autowired
   private MeetService meetService;



   @Test
    public void testGroup(){
       List<Group> list = doctorService.findGroupList(14);
       System.out.println(list.size());
   }

   @Test
    public void testMeetHistory(){
       Pageable pageable = new Pageable(1,8);
       pageable.getParams().put("doctorId",7);
       pageable.getParams().put("ownerId",14);
       MyPage<MeetHistoryDTO> myPage = meetService.getMeetHistory(pageable);
//       for(MeetHistoryDTO dto:myPage.getDataList()){
//           System.out.println(dto.getModuleList());
//       }
   }


}
