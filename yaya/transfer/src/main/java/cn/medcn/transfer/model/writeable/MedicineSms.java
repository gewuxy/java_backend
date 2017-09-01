package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.ClinicalGuide;
import cn.medcn.transfer.model.readonly.Medsms;
import cn.medcn.transfer.support.Medicine;
import cn.medcn.transfer.utils.ReflectUtils;
import cn.medcn.transfer.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.medcn.transfer.Constants.*;

/**
 * Created by lixuan on 2017/8/21.
 */
public class MedicineSms {

    private String id;

    private String categoryId;
    /**文件标题*/
    private String title;
    /**源文件路径*/
    private String filePath;
    /**文件大小*/
    private Float fileSize;
    /**作者*/
    private String author;
    /**文件摘要*/
    private String summary;
    /**html格式内容*/
    private String content;
    /**下载所需象数*/
    private Integer downLoadCost;
    /**修改日期*/
    private Date updateDate;
    /**根栏目ID 用来区分数据类型*/
    private String rootCategory;
    /**资料来源*/
    private String dataFrom;
    /**是否审核*/
    private Boolean authed;
    /**关键字（医师建议）*/
    private String keywords;
    /**图片（医师建议）*/
    private String img;
    /** 目录结构链，从跟目录到自身目录ID本身,用下划线分隔*/
    protected String historyId;

    protected String htmlPath;

    protected List<MedicineSmsDetail> details;

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public List<MedicineSmsDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MedicineSmsDetail> details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Float getFileSize() {
        return fileSize;
    }

    public void setFileSize(Float fileSize) {
        this.fileSize = fileSize;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDownLoadCost() {
        return downLoadCost;
    }

    public void setDownLoadCost(Integer downLoadCost) {
        this.downLoadCost = downLoadCost;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(String rootCategory) {
        this.rootCategory = rootCategory;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }

    public Boolean getAuthed() {
        return authed;
    }

    public void setAuthed(Boolean authed) {
        this.authed = authed;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }


    public static MedicineSms build(Medsms medsms, String categoryId){
        MedicineSms medicineSms = new MedicineSms();
        if (medsms != null){
            medicineSms.setRootCategory(MEDICINE_MANUAL_ROOT_CATEGORY_ID);
            medicineSms.setAuthed(true);
            medicineSms.setAuthor(medsms.getM_producer());
            medicineSms.setDataFrom("敬信药草园");
            medicineSms.setId(StringUtils.getNowStringID());
            medicineSms.setTitle(medsms.getM_name());
            medicineSms.setUpdateDate(new Date());
            medicineSms.setCategoryId(categoryId);

            medicineSms.setDetails(buildDetails(medicineSms, medsms));
        }
        return medicineSms;
    }

    public static MedicineSms build(ClinicalGuide clinicalGuide, String categoryId){

        if (clinicalGuide != null){
            MedicineSms medicineSms = new MedicineSms();
            medicineSms.setRootCategory(CLINICAL_GUIDE_ROOT_CATEGORY_ID);
            medicineSms.setAuthed(true);
            medicineSms.setAuthor(clinicalGuide.getAuthor());
            medicineSms.setDataFrom("敬信药草园");
            medicineSms.setId(StringUtils.getNowStringID());
            medicineSms.setTitle(clinicalGuide.getTitle());
            medicineSms.setUpdateDate(new Date());
            medicineSms.setCategoryId(categoryId);
            String fileSizeStr = clinicalGuide.getSize();
            if (!StringUtils.isEmpty(fileSizeStr)){
                if (fileSizeStr.endsWith("K")){
                    fileSizeStr = fileSizeStr.replace("K", "");
                    medicineSms.setFileSize(Float.parseFloat(fileSizeStr) * 1024);
                } else if(fileSizeStr.endsWith("M")){
                    fileSizeStr = fileSizeStr.replace("M", "");
                    medicineSms.setFileSize(Float.parseFloat(fileSizeStr) * 1024 * 1024);
                }
            }
            return medicineSms;
        }
        return null;
    }

    protected static List<MedicineSmsDetail> buildDetails(MedicineSms medicineSms, Medsms medsms){
        List<MedicineSmsDetail> details = new ArrayList<>();
        Field[] fields = medsms.getClass().getFields();
        Medicine medicine;

        for (Field field : fields){
            medicine = field.getAnnotation(Medicine.class);
            if (medicine != null){
                MedicineSmsDetail detail = new MedicineSmsDetail();
                detail.setDateFileId(medicineSms.getId());
                detail.setDetailKey(medicine.propertyName());
                detail.setDetailValue((String) ReflectUtils.getFieldValue(medsms, field));
            }
        }

        return details;
    }
}
