package cn.medcn.meet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2017/10/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetGroup implements Serializable {

    protected Integer id;

    protected String meetId;

    protected Integer groupId;
}
