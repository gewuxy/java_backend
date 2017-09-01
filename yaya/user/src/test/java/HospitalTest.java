import cn.medcn.user.model.Hospital;
import cn.medcn.user.service.HospitalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lixuan on 2017/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class HospitalTest {

    @Autowired
    private HospitalService hospitalService;

//    @Test
//    public void testFindProvince(){
//        List<String> provinces = hospitalService.findProvinces();
//        for(String province:provinces){
//            System.out.println(province);
//        }
//    }
//
//    @Test
//    public void testFindHospital(){
//        Hospital hospital = new Hospital();
//        hospital.setProvince("广东省");
//        hospital.setCity("广州市");
//        List<Hospital> list = hospitalService.select(hospital);
//        for (Hospital hospital1:list){
//            System.out.println(hospital1.getName());
//        }
//    }
}
