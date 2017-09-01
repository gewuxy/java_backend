package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.PptReadOnly;

import java.util.List;

/**
 * Created by lixuan on 2017/6/19.
 */
public class AudioCourseDetailSupport{

    private String msgGroupId;

    private List<PptReadOnly> pptList;

    public List<PptReadOnly> getPptList() {
        return pptList;
    }

    public void setPptList(List<PptReadOnly> pptList) {
        this.pptList = pptList;
    }

    public String getMsgGroupId() {
        return msgGroupId;
    }

    public void setMsgGroupId(String msgGroupId) {
        this.msgGroupId = msgGroupId;
    }


    public PptReadOnly findPPTByType(String typeKey){
        PptReadOnly pptReadOnly = null;
        if(this.getPptList() != null){
            for(PptReadOnly ppt:this.getPptList()){
                if(ppt.getMessageType().equals(typeKey)){
                    pptReadOnly = ppt;
                    break;
                }
            }
        }
        return pptReadOnly;
    }
}
