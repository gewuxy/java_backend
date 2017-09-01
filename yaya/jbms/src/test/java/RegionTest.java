import cn.medcn.common.utils.PinyinUtils;
import cn.medcn.sys.model.SystemRegion;
import cn.medcn.sys.service.SystemRegionService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.List;

/**
 * Created by lixuan on 2017/5/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-common.xml"})
public class RegionTest {

    @Autowired
    private SystemRegionService systemRegionService;

    @Test
    public void testAddProvince(){
        System.out.println("hah");
        String filePath = "D:\\lixuan\\test\\region\\province.txt";
        FileInputStream fis = null;
        InputStreamReader bis = null;
        BufferedReader reader = null;
        List<SystemRegion> datas = Lists.newArrayList();
        try {
             fis = new FileInputStream(filePath);
             bis = new InputStreamReader(fis);
             reader = new BufferedReader(bis);
             String readStr = reader.readLine();

             while(readStr!=null){
                 String[] arr = readStr.split(",");
                 SystemRegion region = new SystemRegion();
                 region.setId(Integer.parseInt(arr[0]));
                 region.setName(arr[1].trim());
                 region.setPreId(0);
                 region.setAlpha(PinyinUtils.getAlpha(region.getName()));
                 region.setSpell(PinyinUtils.getPingYin(region.getName()));
                 datas.add(region);
                 readStr = reader.readLine();
             }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie){
            ie.printStackTrace();
        }finally {
                try {
                    if(fis != null){
                        fis.close();
                    }
                    if(bis!=null){
                        bis.close();
                    }
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        systemRegionService.executeBatchAdd(datas);
    }

    @Test
    public void testAddCity(){
        System.out.println("hah");
        String filePath = "D:\\lixuan\\test\\region\\city.txt";
        FileInputStream fis = null;
        InputStreamReader bis = null;
        BufferedReader reader = null;
        List<SystemRegion> datas = Lists.newArrayList();
        try {
            fis = new FileInputStream(filePath);
            bis = new InputStreamReader(fis);
            reader = new BufferedReader(bis);
            String readStr = reader.readLine();

            while(readStr!=null){
                String[] arr = readStr.split(",");
                SystemRegion region = new SystemRegion();
                region.setId(Integer.parseInt(arr[0]));
                region.setName(arr[1].trim());
                region.setPreId(Integer.parseInt(arr[2].trim()));
                region.setAlpha(PinyinUtils.getAlpha(region.getName()));
                region.setSpell(PinyinUtils.getPingYin(region.getName()));
                datas.add(region);
                readStr = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie){
            ie.printStackTrace();
        }finally {
            try {
                if(fis != null){
                    fis.close();
                }
                if(bis!=null){
                    bis.close();
                }
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        systemRegionService.executeBatchAdd(datas);
    }

    @Test
    public void testAddQu(){
        System.out.println("hah");
        String filePath = "D:\\lixuan\\test\\region\\qu.txt";
        FileInputStream fis = null;
        InputStreamReader bis = null;
        BufferedReader reader = null;
        List<SystemRegion> datas = Lists.newArrayList();
        try {
            fis = new FileInputStream(filePath);
            bis = new InputStreamReader(fis);
            reader = new BufferedReader(bis);
            String readStr = reader.readLine();

            while(readStr!=null){
                String[] arr = readStr.split(",");
                SystemRegion region = new SystemRegion();
                region.setId(Integer.parseInt(arr[0]));
                region.setName(arr[1].trim());
                region.setPreId(Integer.parseInt(arr[2].trim()));
                region.setAlpha(PinyinUtils.getAlpha(region.getName()));
                region.setSpell(PinyinUtils.getPingYin(region.getName()));
                datas.add(region);
                readStr = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie){
            ie.printStackTrace();
        }finally {
            try {
                if(fis != null){
                    fis.close();
                }
                if(bis!=null){
                    bis.close();
                }
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        systemRegionService.executeBatchAdd(datas);
    }


    @Test
    public void testFindProvince(){
        List<SystemRegion> list = systemRegionService.findRegionByPreid(0);
        for(SystemRegion region:list){
            System.out.println(region.getName());
        }
    }
}
