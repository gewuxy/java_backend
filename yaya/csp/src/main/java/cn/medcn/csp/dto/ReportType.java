package cn.medcn.csp.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lixuan on 2017/12/12.
 */
public enum ReportType {

    sexy(0, "色情"),
    crime(1, "违法犯罪"),
    tort(2, "侵权");

    public int type;

    public String label;

    ReportType (int type, String label){
        this.type = type;
        this.label = label;
    }

}
