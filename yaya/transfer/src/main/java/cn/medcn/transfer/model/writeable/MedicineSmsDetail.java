package cn.medcn.transfer.model.writeable;

/**
 * Created by lixuan on 2017/8/21.
 */
public class MedicineSmsDetail {

    protected Long id;

    protected String detailKey;

    protected String detailValue;

    protected String dateFileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetailKey() {
        return detailKey;
    }

    public void setDetailKey(String detailKey) {
        this.detailKey = detailKey;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }

    public String getDateFileId() {
        return dateFileId;
    }

    public void setDateFileId(String dateFileId) {
        this.dateFileId = dateFileId;
    }


}
