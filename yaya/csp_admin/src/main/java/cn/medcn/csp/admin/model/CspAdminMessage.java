
package cn.medcn.csp.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "T_CSP_ADMIN_MESSAGE")
public class CspAdminMessage implements java.io.Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    /**  */
    private Integer userId;

    /**  */
    private String username;

    /**  */
    private String messageTitle;

    /**  */
    private String messageContent;

    /**  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date creatTime;

    /**  */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
}