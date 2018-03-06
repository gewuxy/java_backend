package cn.medcn.transfer.service;

import cn.medcn.transfer.Constants;
import cn.medcn.transfer.model.writeable.MedicineSms;
import cn.medcn.transfer.service.writeable.WriteAbleMedCategoryService;
import cn.medcn.transfer.service.writeable.WriteAbleMedicineSmsService;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMedCategoryServiceImpl;
import cn.medcn.transfer.service.writeable.impl.WriteAbleMedicineSmsServiceImpl;
import cn.medcn.transfer.utils.FileUtils;
import cn.medcn.transfer.utils.LogUtils;
import cn.medcn.transfer.utils.StringUtils;
import cn.medcn.transfer.utils.WordUtils;
import com.jacob.activeX.ActiveXComponent;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixuan on 2017/8/24.
 */
public class MedicineServiceImpl extends DataServiceImpl {

    protected WriteAbleMedCategoryService writeAbleMedCategoryService = new WriteAbleMedCategoryServiceImpl();

    protected WriteAbleMedicineSmsService writeAbleMedicineSmsService = new WriteAbleMedicineSmsServiceImpl();



    protected int counter;

    protected String defaultAuthor = "敬信药草园";

    @Override
    protected WriteAbleMedCategoryService getWriteAbleMedCategoryService() {
        return writeAbleMedCategoryService;
    }

    @Override
    protected void parseContent(File file, String categoryId) {
        String fileName = file.getName();

        if (fileName.toLowerCase().endsWith(".doc") || fileName.toLowerCase().endsWith(".docx")) {
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            title = title.replaceAll(" ?\\([0-9]+\\)","");
            String[] infoArray = title.split("_");
            String author = null;
            if (infoArray != null && infoArray.length > 0){
                title = infoArray[0];
                author = infoArray.length > 1 ? infoArray[1] : null;
            }

            MedicineSms condition = new MedicineSms();
            condition.setTitle(title);
            condition.setCategoryId(categoryId);
            condition.setAuthor(author);
            try {
                MedicineSms existed = writeAbleMedicineSmsService.findOne(condition);
                if (existed != null) {
                    //LogUtils.debug(this.getClass(), "文件 ："+title+"["+author+"]"+"已经存在，跳过....");
                    return ;
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            String id = StringUtils.getNowStringID();
            String filePath = "data/" + getRootCategoryId() + "/" + categoryId + "/";
            MedicineSms medicineSms = new MedicineSms();
            medicineSms.setTitle(title);
            medicineSms.setFileSize(Float.valueOf(file.length()));
            medicineSms.setAuthed(true);
            medicineSms.setAuthor(author);
            medicineSms.setRootCategory(getRootCategoryId());
            medicineSms.setUpdateDate(new Date());
            medicineSms.setDataFrom("敬信药草园");
            medicineSms.setId(id);
            medicineSms.setCategoryId(categoryId);
            medicineSms.setFilePath(filePath + id + ".pdf");
            medicineSms.setHtmlPath(filePath + id + ".html");
            medicineSms.setHistoryId(writeAbleMedCategoryService.getHistoryId(categoryId, getRootCategoryId()));

            try {

                File destFileDir = new File(fileBase + filePath);
                if (!destFileDir.exists()) {
                    destFileDir.mkdirs();
                }

                String pdfFilePath = fileBase + filePath + id + ".pdf";
                String htmlFilePath = fileBase + filePath + id + ".html";

                WordUtils.wordToHTML(docs, file.getAbsolutePath(), htmlFilePath);
                WordUtils.wordToPDF(docs, file.getAbsolutePath(), pdfFilePath);

                medicineSms.setFileSize(FileUtils.getFileKSize(pdfFilePath));
                writeAbleMedicineSmsService.transfer(medicineSms);

                counter++;
                LogUtils.debug(this.getClass(), "成功转换了 " + counter + "个药品说明书文件 !!!");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getRootCategoryId() {
        return Constants.MEDICINE_MANUAL_ROOT_CATEGORY_ID;
    }

    protected String getApprovalNumber(String title){
        String approval = null;
        if (!StringUtils.isEmpty(title)){
            Matcher matcher = Pattern.compile("_?[A-Z|0-9][0-9]{5}[0-9]+").matcher(title);
            if (matcher.find()){
                approval = matcher.group();
            }
        }
        return approval;
    }



    public static void main(String[] args) {
        String text = "羟乙基淀粉1300.4氯化钠注射液H20103503";
        MedicineServiceImpl medicineService = new MedicineServiceImpl();
        System.out.println(medicineService.getApprovalNumber(text));
    }
}
