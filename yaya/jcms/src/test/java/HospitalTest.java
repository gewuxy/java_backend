import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.model.AppDoctor;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.AppUserDetail;
import cn.medcn.user.model.Hospital;
import cn.medcn.user.service.HospitalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.List;

/**
 * Created by lixuan on 2017/5/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class HospitalTest {

    @Autowired
    private HospitalService hospitalService;

    @Test
    public void testCheckHospital() throws Exception {
        File file = new File("D:\\lixuan\\工作记录\\YAYA医师重构文档\\2017最新最全全国医院名录.xls");
        List<Object[]> objects = ExcelUtils.readExcel(file);
        String hosName = "";
        int addCounter = 0;
        int updateCounter = 0;
        //StringBuilder builder = new StringBuilder();

        for(Object[] objs:objects){
            hosName = (String) objs[2];
            Hospital hospital = hospitalService.findByName(hosName);

            if(hospital == null){
                //如果没有匹配到 新增医院
                hospital = new Hospital();
                hospital.setName(hosName);
                hospital.setProvince((String) objs[0]);
                hospital.setCity((String) objs[1]);
                hospital.setAddress((String) objs[3]);
                hospital.setTel((String) objs[5]);
                hospital.setLevel((String) objs[8]);
                hospitalService.insert(hospital);
                addCounter++;
            }else{
                hospital.setAddress((String) objs[3]);
                hospital.setTel((String) objs[5]);
                hospital.setLevel((String) objs[8]);
                hospitalService.updateByPrimaryKeySelective(hospital);
                updateCounter++;
            }
        }
        System.out.println("新添加医院数="+addCounter);
        System.out.println("修改医院数="+updateCounter);
    }

    @Test
    public void testCheckHospital2() throws Exception {
        File file = new File("D:\\lixuan\\工作记录\\YAYA医师重构文档\\2017最新最全医院名录2.xlsx");
        List<Object[]> objects = ExcelUtils.readExcel(file);
        String hosName = "";
        int addCounter = 0;
        int updateCounter = 0;
        //StringBuilder builder = new StringBuilder();

        for(Object[] objs:objects){
            hosName = (String) objs[2];
            Hospital hospital = hospitalService.findByName(hosName);

            if(hospital == null){
                //如果没有匹配到 新增医院
                hospital = new Hospital();
                hospital.setName(hosName);
                hospital.setProvince((String) objs[0]);
                hospital.setCity((String) objs[1]);
                hospital.setAddress((String) objs[3]);
                hospital.setLevel((String) objs[4]);
                hospitalService.insert(hospital);
                addCounter++;
            }else{
                hospital.setAddress((String) objs[3]);
                hospital.setLevel((String) objs[4]);
                hospitalService.updateByPrimaryKeySelective(hospital);
                updateCounter++;
            }
        }
        System.out.println("新添加医院数="+addCounter);
        System.out.println("修改医院数="+updateCounter);
    }


    @Test
    public void testConvert(){
        AppUserDTO dto = new AppUserDTO();
        dto.setHospital("测试医院");
        dto.setDepartment("测试科室");
        AppUser user = AppUserDTO.rebuildToDoctor(dto);
        AppDoctor doctor = (AppDoctor) user.getUserDetail();
        System.out.println(doctor.getUnitName());
        System.out.println(doctor.getSubUnitName());
    }

}
