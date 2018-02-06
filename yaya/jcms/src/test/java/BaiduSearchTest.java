import cn.medcn.common.service.BaiduApiService;
import cn.medcn.common.supports.baidu.NearbySearchDTO;
import cn.medcn.common.supports.baidu.NearbyUnitDTO;
import cn.medcn.common.supports.baidu.SearchResultDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Lixuan
 * @Date: 2018/2/6
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class BaiduSearchTest {

    @Autowired
    protected BaiduApiService baiduApiService;

    @Test
    public void testSearch(){
        NearbySearchDTO dto = new NearbySearchDTO();
        SearchResultDTO result = baiduApiService.search(dto);
        dto.setQuery("医院");
        for (NearbyUnitDTO unit : result.getResults()) {
            System.out.println(unit.getName());
        }
    }
}
