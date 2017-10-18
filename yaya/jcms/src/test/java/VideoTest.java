import cn.medcn.common.excptions.SystemException;
import cn.medcn.meet.dto.LiveOrderDTO;
import cn.medcn.meet.dto.MeetVideoDTO;
import cn.medcn.meet.dto.VideoCourseDetailDTO;
import cn.medcn.meet.model.*;
import cn.medcn.meet.service.LiveService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class VideoTest {

    @Autowired
    private VideoService videoService;

    @Autowired
    private MeetService meetService;

    @Autowired
    protected LiveService liveService;


    @Test
    public void testAddVideoModule() throws SystemException {
        MeetModule module = new MeetModule();
        module.setMeetId("17042512131640894904");
        module.setFunctionId(MeetModule.ModuleFunction.VIDEO.getFunId());
        module.setModuleName(MeetModule.ModuleFunction.VIDEO.getFunName());
        module.setActive(true);
        meetService.addMeetModule(module);
    }

    @Test
    public void testAddVideo(){
        VideoCourse course = new VideoCourse();
        course.setCategory("骨外科");
        course.setCreateTime(new Date());
        course.setOwner(14);
        course.setPublished(true);
        course.setTitle("测试视频会议1");
        videoService.insert(course);
    }


    @Test
    public void testAddDetail(){
        VideoCourseDetail detail = new VideoCourseDetail();
        detail.setCourseId(1);
        detail.setPreId(1);
        detail.setType(VideoCourseDetail.VideoDetailType.VIDEO.ordinal());
        detail.setName("王主任第一讲");
        detail.setVideoType(VideoCourseDetail.VideoType.OUTER_LINK.ordinal());
        detail.setUrl("http://128.1.226.186/file/PLAY/61213.mp4");
        videoService.insertDetail(detail);
    }


    @Test
    public void testFindVideo(){
        MeetVideo video = videoService.findMeetVideo("17042512131640894904",12);
        MeetVideoDTO dto = MeetVideoDTO.build(video);
        System.out.println(dto.getCourse().getTitle());
        for(VideoCourseDetailDTO detailDTO:dto.getCourse().getDetails()){
            System.out.println(detailDTO.getName()+" - "+detailDTO.getType()+" - "+detailDTO.getUrl());
        }
    }


    @Test
    public void testFindeDetails(){
        List<VideoCourseDetail> list = videoService.findByPreid( 1);
        for(VideoCourseDetail detail:list){
            System.out.println(detail.getName()+" - "+detail.getType()+" - "+detail.getUrl());
        }
    }


    @Test
    public void testRecordVideoHistory(){
        VideoHistory history = new VideoHistory();
        history.setFinished(true);
        history.setUsedtime(1000);
        history.setDetailId(4);
        history.setUserId(7);
        history.setMeetId("17042512131640894904");
        history.setModuleId(12);
        history.setCourseId(1);
        videoService.insertHistory(history);
    }


    @Test
    public void testUploadFile() throws Exception {
        String urlStr  = "http://www.medcn.com/upload/temp/service_certificate.pdf";
        URL url = new URL(urlStr);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }

        //读文件流
        DataInputStream in = new DataInputStream(urlCon.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream("C:/test/test.pdf"));
        byte[] buffer = new byte[2048];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
        in.close();

    }


    @Test
    public void testSync(){
        LiveOrderDTO dto = new LiveOrderDTO();
        dto.setOrder(LiveOrderDTO.ORDER_SYNC);
        dto.setCourseId("14379");
        dto.setPageNum(4);
        liveService.publish(dto);
    }
}
