package cn.medcn.meet.dto;

import cn.medcn.meet.model.MeetModule;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuLP on 2017/5/25.
 */
@Data
@NoArgsConstructor
public class MeetModuleDTO implements Serializable{

    private Integer moduleId;

    private String meetId;

    private String moduleName;






}
