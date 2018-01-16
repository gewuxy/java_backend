package cn.medcn.csp.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by lixuan on 2018/1/16.
 */
@Data
@NoArgsConstructor
public class UnitAccountSearchDTO implements Serializable {

    public static final String FIELD_KEYWORD = "keyword";

    public static final String FIELD_RCD = "rcd";

    public static final String FIELD_TEST_FLAG = "testFlag";

    protected String keyword;

    protected Boolean rcd;

    protected Boolean testFlag;
}
