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

import java.beans.IntrospectionException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by lixuan on 2017/8/23.
 */
public class GuideServiceImpl extends DataServiceImpl{

    protected WriteAbleMedCategoryService writeAbleMedCategoryService = new WriteAbleMedCategoryServiceImpl();

    protected WriteAbleMedicineSmsService writeAbleMedicineSmsService = new WriteAbleMedicineSmsServiceImpl();

    protected int counter = 0;

    @Override
    protected void parseContent(File file, String categoryId){
        String fileName = file.getName();

        if (fileName.toLowerCase().endsWith(".pdf")){
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            String author = null;
            if (fileName.contains("#")){
                String[] fileNameArr = title.split("#");
                title = fileNameArr[0];
                author = fileNameArr.length > 1 ? fileNameArr[1] : "";
            }

            String id = StringUtils.getNowStringID();
            String filePath = "data/"+getRootCategoryId()+"/"+categoryId+"/";
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
            medicineSms.setFilePath(filePath + id);
            medicineSms.setHistoryId(writeAbleMedCategoryService.getHistoryId(categoryId, getRootCategoryId()));

            try {
                writeAbleMedicineSmsService.insert(medicineSms);
                File destFileDir = new File(fileBase + filePath);
                if (!destFileDir.exists()){
                    destFileDir.mkdirs();
                }
                FileUtils.copyFile(file, new File(fileBase + filePath + id+".pdf"));
                counter ++;
                LogUtils.debug(this.getClass(), "成功转换了 "+counter+"个临床指南文件 !!!");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected WriteAbleMedCategoryService getWriteAbleMedCategoryService() {
        return writeAbleMedCategoryService;
    }

    @Override
    protected String getRootCategoryId() {
        return Constants.CLINICAL_GUIDE_ROOT_CATEGORY_ID;
    }

    public static void main(String[] args) {
        String url = "sem - crebacteria.jpg";
        try {
            System.out.println(URLEncoder.encode(url, "ASCII"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
