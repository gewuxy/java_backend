import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.supports.ExportData;
import cn.medcn.common.utils.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.List;

/**
 * Created by LiuLP on 2017/6/15/015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-*.xml"})
public class ExcelTest {


    //@Test
    /*public void testExcel() throws Exception {
        List<Object[]> list = ExcelUtils.readExcel(new File("C:\\Users\\Administrator\\Desktop\\yaya医师-批量注册.xls"));
        for(Object[] objs:list){
            for (Object obj:objs){
                System.out.println(obj);
            }
        }
    }*/

    @Test
    public void testRegionExcel() throws Exception {
        //ReadMergeRegionExcel.readExcelToObj("C:\\360安全浏览器下载\\问卷规范填写及FOBT检测技巧 PPT明细 (17).xls");

    }

    @Test
    public void testMerge() throws IOException, InvalidFormatException {
        String filePath = "D:\\11.xls";
        InputStream is = new FileInputStream(filePath);
        Workbook wb = WorkbookFactory.create(is);
        int[] colIndexArray = new int[]{0,1,2,3,4,5,6,10,11};
        ExcelUtils.mergeRows(wb, 26, colIndexArray, true);
    }


    @Test
    public void testWriteExcel() throws IOException {
        String excelFileName = "test1.xls";
        String filePath = "D:\\";
        List<ExportData> list = (List<ExportData>) ExcelUtils.readToExcelDataList(new File("D:\\11.xls"), true, ExportData.class);
        try {
            Workbook workbook = ExcelUtils.writeExcel(excelFileName, list, ExportData.class);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath+excelFileName);
            workbook.write(fileOutputStream);
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadExcel(){
        String filePath = "D:\\11.xls";
        File file = new File(filePath);
        List<ExportData> list = (List<ExportData>) ExcelUtils.readToExcelDataList(file, true, ExportData.class);
        for(ExportData exportData : list){
            System.out.println(exportData.getUsername()+" - "+exportData.getDepart()+" - "+exportData.getFinishRate());
        }
    }
}
