package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/6/16.
 */
public class QuestionOptionReadOnly {
    private Long o_id;//选项ID
    private Long q_id;
    private String q_option;

    public Long getO_id() {
        return o_id;
    }

    public void setO_id(Long o_id) {
        this.o_id = o_id;
    }

    public Long getQ_id() {
        return q_id;
    }

    public void setQ_id(Long q_id) {
        this.q_id = q_id;
    }

    public String getQ_option() {
        return q_option;
    }

    public void setQ_option(String q_option) {
        this.q_option = q_option;
    }
}
