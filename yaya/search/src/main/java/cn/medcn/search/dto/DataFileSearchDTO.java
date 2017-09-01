package cn.medcn.search.dto;

import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import cn.medcn.search.util.PDFUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by weilong on 2017/7/24.
 */
@Data
@NoArgsConstructor
public class DataFileSearchDTO {

    @Field
    private String authed;
    @Field
    private String author;
    @Field
    private String categoryId;
    @Field
    private String content;
    @Field
    private String dataFrom;
    @Field
    private String downLoadCost;
    @Field
    private String filePath;
    @Field
    private String fileSize;
    @Field
    private String id;
    @Field
    private String rootCategory;
    @Field
    private String summary;
    @Field
    private String title;
    @Field
    private String updateDate;
    @Field
    protected String historyId;
    @Field
    protected String img;
    @Field
    protected String keywords;
    @Field
    protected String detail;
    @Field
    protected String fileContent;

}
