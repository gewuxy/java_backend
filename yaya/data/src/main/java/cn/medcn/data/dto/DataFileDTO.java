package cn.medcn.data.dto;

import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lixuan on 2017/5/19.
 */
@Data
@NoArgsConstructor
public class DataFileDTO implements Serializable{

    private String id;

    private String title;

    private String author;

    private String dataFrom;

    private Float fileSize;

    private Date updateDate;

    private List<DataFileDetail> detailList;

    //是否收藏
    private boolean favorite;


    public static List<DataFileDTO> build(List<DataFile> list){
        List<DataFileDTO> dtoList = Lists.newArrayList();
        if(list != null){
            for(DataFile dataFile:list){
                DataFileDTO dto = new DataFileDTO();
                dto.setId(dataFile.getId());
                dto.setAuthor(dataFile.getAuthor());
                dto.setDataFrom(dataFile.getDataFrom());
                dto.setTitle(dataFile.getTitle());
                dto.setFileSize(dataFile.getFileSize());
                dto.setUpdateDate(dataFile.getUpdateDate());
                //dto.setFilePath(appFileBase+dataFile.getFilePath());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
}
