package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lixuan on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="t_department")
public class Department {

    @Id
    private Integer id;

    private String name;

    private String category;
}
