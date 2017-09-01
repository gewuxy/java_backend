package cn.medcn.transfer.model.writeable;

/**
 * Created by lixuan on 2017/6/19.
 */
public class MeetModule {
    private Integer id;
    /**模块名称*/
    private String moduleName;
    /**功能ID*/
    private Integer functionId;
    /**会议ID*/
    private String meetId;
    /**是否是主模块*/
    private Boolean mainFlag;
    /**模块是否可用*/
    private Boolean active;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }

    public Boolean getMainFlag() {
        return mainFlag;
    }

    public void setMainFlag(Boolean mainFlag) {
        this.mainFlag = mainFlag;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public enum ModuleFunction{
        PPT(1,"微课"),
        VIDEO(2, "视频"),
        EXAM(3,"考试"),
        SURVEY(4, "问卷"),
        SIGN(5, "签到");

        private Integer funId;

        private String funName;

        public Integer getFunId() {
            return funId;
        }

        public String getFunName() {
            return funName;
        }

        public static ModuleFunction getFunctionById(Integer funId){
            switch (funId){
                case 1:
                    return PPT;
                case 2:
                    return VIDEO;
                case 3:
                    return EXAM;
                case 4:
                    return SURVEY;
                case 5:
                    return SIGN;
                default:
                    return null;
            }
        }

        ModuleFunction(Integer funId, String funName){
            this.funId = funId;
            this.funName = funName;
        }
    }

}
