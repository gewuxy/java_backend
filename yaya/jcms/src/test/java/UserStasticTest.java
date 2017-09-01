import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.user.dto.UserAttendDTO;
import cn.medcn.user.dto.UserDataDetailDTO;
import cn.medcn.user.service.UserStasticService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.NumberFormat;

/**
 * Created by Liuchangling on 2017/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-*.xml"})
public class UserStasticTest {
    @Autowired
    private UserStasticService userStasticService;

    @Test
    public void testFindAttendCount(){
        UserAttendDTO attendDTO = userStasticService.findAttendCount(8);
        if(attendDTO!=null){
            System.out.println("get week count: " + attendDTO.getWeekAttendCount()+
                            " get month count: " + attendDTO.getMonthAttendCount()+
                            " get total count: " + attendDTO.getTotalAttendCount());
        }
    }

    @Test
    public void testFindDatadetail(){
        Pageable pageable = new Pageable(1,5);
        pageable.getParams().put("userId",14);
        pageable.getParams().put("province","全国");

        MyPage<UserDataDetailDTO> page = new MyPage<UserDataDetailDTO>();
        page = userStasticService.findUserDataByCity(pageable);
        MyPage<UserDataDetailDTO> page2 = userStasticService.findUserDataByProvince(pageable);
        Integer totalCount = page2.getDataList().get(0).getUserCount();
        System.out.println("get total count: "+totalCount);

        // 获取格式化对象
        NumberFormat nft = NumberFormat.getPercentInstance();
        // 设置百分数精确度2 即保留小数点后两位
        nft.setMaximumFractionDigits(2);
        float percent = 0.00f;
        Integer userCount = 0;
        String propName = null;
        for (UserDataDetailDTO detailDTO : page.getDataList()){
            propName = detailDTO.getPropName();
            if(StringUtils.isEmpty(propName)){
                propName = "其他";
            }
            System.out.println("propName: "+propName);
            // 用户数
            userCount = detailDTO.getUserCount();
            System.out.println("用户数：" + userCount);
            // 计算百分比
            percent = 1.0F*userCount / totalCount;
            System.out.println("小数：" + nft.format(percent));
            detailDTO.setPercent(percent);
        }
    }

    @Test
    public void testFindattendCountByPro(){
        Integer totalCount = userStasticService.findTotalAttendCount(14);
        Pageable pageable = new Pageable(1,5);
        MyPage<UserDataDetailDTO> page = userStasticService.findUserDataByProvince(pageable);
        for (UserDataDetailDTO detailDTO : page.getDataList()){
            if(StringUtils.isEmpty(detailDTO.getPropName())){
                detailDTO.setPropName("其他");
            }
            System.out.println("propName: "+detailDTO.getPropName());
            // 用户数
            System.out.println("用户数：" + detailDTO.getUserCount());
            // 计算百分比
            float percent = detailDTO.getUserCount()*1.0f / totalCount;
            System.out.println("小数：" + percent);
            detailDTO.setPercent(percent);
        }
    }

    @Test
    public void  test(){
        String str = "";
        String str1 = "  ";
        String str2 = "null";
        String str3 = null;

        System.out.println("--str--"+StringUtils.isBlank(str));
        System.out.println("--str1--"+StringUtils.isBlank(str1));
        System.out.println("--str2--"+StringUtils.isBlank(str2));
        System.out.println("--str3--"+StringUtils.isBlank(str3));

        System.out.println("--empty str--"+StringUtils.isEmpty(str));
        System.out.println("--empty str1--"+StringUtils.isEmpty(str1));
        System.out.println("--empty str2--"+StringUtils.isEmpty(str2));
        System.out.println("--empty str3--"+StringUtils.isEmpty(str3));
    }

}
