package cn.medcn.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by lixuan on 2017/11/17.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_data_rare_disease")
public class RareDisease implements Serializable {

    protected String id;

    protected String name;

}
