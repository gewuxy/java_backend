package cn.medcn.transfer.test;

import cn.medcn.transfer.service.GuideServiceImpl;
import cn.medcn.transfer.service.readonly.DoctorSuggestedService;
import cn.medcn.transfer.service.readonly.impl.DoctorSuggestedCategoryImpl;
import cn.medcn.transfer.service.readonly.impl.PharmacistRecommendCategoryImpl;
import cn.medcn.transfer.service.readonly.impl.SymptomServiceImpl;
import cn.medcn.transfer.support.Timer;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by lixuan on 2017/6/14.
 */
public class ServiceTest {

    public static void main(String[] args) throws IOException, SQLException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        //设置开始时间
        Timer.startTime = System.currentTimeMillis();
//
////        TransferPubuserMaterialService transferPubuserMaterialService = new TransferPubuserMaterialServiceImpl();
////        transferPubuserMaterialService.transferMaterial();
//        String date = "2017-06-25";
//
//        TransferService transferService = new TransferServiceImpl();
//        transferService.transferDoctor(date);
//
//        // =======================================================================

//        List<AppUserUserNameBean> list = new ArrayList<>();
//        AppUserUserNameBean aunb = new AppUserUserNameBean();
//        aunb.setUsername("584479243@qq.com");
//
//        list.add(aunb);
//        EmailHelper.send(list);

//        ClinicalGuideCategoryService clinicalGuideCategoryService = new ClinicalGuideCategoryServiceImpl();
//        clinicalGuideCategoryService.transfer();

//        GuideServiceImpl guideService = new GuideServiceImpl();
//        guideService.transfer("D:\\临床指南综合\\临床指南综合");

//        MedicineServiceImpl medicineService = new MedicineServiceImpl();
//        medicineService.transfer("D:\\药品说明书");
//        TransferService transferService = new TransferServiceImpl();
//        transferService.transferDoctor("957237");

//        String categoryId = "17051817321067996399";
//        String rootId = "17051816413005881649";
//        WriteAbleMedCategoryService writeAbleMedCategoryService = new WriteAbleMedCategoryServiceImpl();
//        String historyId = writeAbleMedCategoryService.getHistoryId(categoryId, rootId);
//        System.out.println(historyId);
//        TransferMeetService transferMeetService = new TransferMeetServiceImpl();
//        //transferMeetService.transferMeet(13048L);//1614
//        transferMeetService.transferMeet(14207L);//1614


        // 对症找药数据转移
       /* SymptomServiceImpl symptomService = new SymptomServiceImpl();
        symptomService.transfer();*/

        // 医师建议 数据转移
        /*DoctorSuggestedCategoryImpl doctorSuggestedCategory = new DoctorSuggestedCategoryImpl();
        doctorSuggestedCategory.transfer();*/

        // 药师建议 数据转移
        PharmacistRecommendCategoryImpl pharmacistRecommendCategory = new PharmacistRecommendCategoryImpl();
        pharmacistRecommendCategory.transfer();

    }
}
