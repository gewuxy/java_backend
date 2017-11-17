import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.data.dao.RareDiseaseDAO;
import cn.medcn.data.model.RareDisease;
import cn.medcn.user.dao.MaterialDAO;
import cn.medcn.user.model.Material;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class MaterialTest {

    @Autowired
    protected MaterialDAO materialDAO;

    @Autowired
    protected RareDiseaseDAO rareDiseaseDAO;

    @Test
    public void testFindMaterials(){
        Material cond = new Material();
        cond.setUserId(120007);
        List<Material> list = materialDAO.select(cond);
        for (Material material : list) {
            System.out.println(material.getMaterialName());
        }
    }

    @Test
    public void testTransferMaterial(){
        RareDisease cond = new RareDisease();
        List<RareDisease> rareList = rareDiseaseDAO.select(cond);
        for (RareDisease rare : rareList) {
            Material material = new Material();
            material.setId(StringUtils.nowStr());
            material.setMaterialName(rare.getName());
            material.setUserId(1200007);
            material.setCreateTime(new Date());
            material.setMaterialType("html");
            material.setInfinityId("0");
            material.setHtmlUrl("pubmaterial/" + rare.getId() + ".html");

            materialDAO.insert(material);
        }

    }
}
