package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by LiuLP on 2017/7/13.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_active_store")
public class ActiveStore implements Serializable{
    @Id
    private Integer id;

    private Integer store;

}
