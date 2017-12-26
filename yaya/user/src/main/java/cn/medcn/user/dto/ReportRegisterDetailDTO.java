package cn.medcn.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/12/26.
 */
@Data
@NoArgsConstructor
public class ReportRegisterDetailDTO {

    /**
     * {@link cn.medcn.user.model.BindInfo.Type}
     */
    protected Integer registerFrom;

    protected Integer reportCount;

    protected Boolean abroad;
}
