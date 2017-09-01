package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/6/14.
 */
public class PptReadOnly {

    private Long messageId;			//消息id   主键
    private String sendTime;				//发送时间
    //private Blob messageContent;			//消息内容
    private String messageType;				//消息类型
    private Integer messageTime;			//语音消息 时长
    private String fileFileName;   //文件名
    private String msgGroupid;
    private String picDesc; //图片备注说明


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Integer messageTime) {
        this.messageTime = messageTime;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getMsgGroupid() {
        return msgGroupid;
    }

    public void setMsgGroupid(String msgGroupid) {
        this.msgGroupid = msgGroupid;
    }

    public String getPicDesc() {
        return picDesc;
    }

    public void setPicDesc(String picDesc) {
        this.picDesc = picDesc;
    }


    public boolean hasSameGroup(PptReadOnly only){
        if(only == null){
            return false;
        }
        if(only.getMsgGroupid() == null || this.msgGroupid == null){
            return false;
        }
        if(only.getMsgGroupid().equals(this.messageId)){
            return true;
        }
        return false;
    }
}
