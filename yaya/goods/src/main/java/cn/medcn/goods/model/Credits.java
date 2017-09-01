package cn.medcn.goods.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_xs_credits")
public class Credits implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer userId;

    private Integer credit;
}
