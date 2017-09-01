package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/6/16.
 */
public class ReportSurveyItemReadOnly {
    private Long r_id;
    private Long q_id;
    private Long p_id;
    private Long o_id;
    private Long userId;


    private String answer;

    public Long getR_id() {
        return r_id;
    }

    public void setR_id(Long r_id) {
        this.r_id = r_id;
    }

    public Long getQ_id() {
        return q_id;
    }

    public void setQ_id(Long q_id) {
        this.q_id = q_id;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public Long getO_id() {
        return o_id;
    }

    public void setO_id(Long o_id) {
        this.o_id = o_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
