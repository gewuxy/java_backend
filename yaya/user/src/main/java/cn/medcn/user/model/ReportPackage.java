package cn.medcn.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * by create HuangHuibin 2018/1/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_report_package")
public class ReportPackage {
    @Id
    private Integer id;

    private Integer staCount = 0;

    private Integer preCount = 0;

    private Integer proCount = 0;

    private Integer userCount = 0;

    private Date registerTime;
}
