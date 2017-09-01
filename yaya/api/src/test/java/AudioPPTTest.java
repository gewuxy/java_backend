import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.service.AudioService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lixuan on 2017/4/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class AudioPPTTest {

    @Autowired
    private AudioService audioService;

    @Test
    public void testBatchAddAudio(){
        String meetId = "17042512131640894904";
        Integer moduleId = 3;
        AudioCourse course = new AudioCourse();
        course.setCreateTime(new Date());
        course.setCategory("普外科");
        course.setOwner(14);
        course.setTitle("普外科综合培训课程一");
        audioService.insert(course);
        List<AudioCourseDetail> pptList = Lists.newArrayList();
        for(int i = 1; i <=10; i++){
            AudioCourseDetail ppt = new AudioCourseDetail();
            ppt.setDuration(new Random().nextInt(50)+1);
            //ppt.setType(AudioCourseDetailDTO.AudioPptType.DEMAND.getType());
            ppt.setSort(i);
            ppt.setCourseId(course.getId());
            pptList.add(ppt);
        }
        audioService.insertBatch(pptList);
    }

    @Test
    public void testFindCourse(){
        Integer courseId = 1;
        AudioCourse course = audioService.findAudioCourse(courseId);
        System.out.println(course.getTitle());
        for(AudioCourseDetail detail:course.getDetails()){
            System.out.println(detail.getSort());
        }
    }
}
