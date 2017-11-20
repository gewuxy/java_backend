package cn.medcn.official.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * by create HuangHuibin 2017/11/15
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_off_search")
public class OffSearch {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String search;
    private String searchUser;
    private Integer searchType;
    private Date searchTime;
    private Integer times;
}
