package cn.medcn.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/5/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="t_data_file_detail")
public class DataFileDetail implements Serializable {
    @Id
    private Long id;

    private String detailKey;

    private String detailValue;

    private String dataFileId;
}
