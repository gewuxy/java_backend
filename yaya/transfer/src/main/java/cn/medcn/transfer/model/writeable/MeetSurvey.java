package cn.medcn.transfer.model.writeable;

/**
 * Created by lixuan on 2017/6/19.
 */
public class MeetSurvey {
    private Integer id;

    private String meetId;

    private Integer moduleId;

    private Integer paperId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }


    public static MeetSurvey build(String meetId, Integer moduleId, Integer paperId){
        MeetSurvey survey = new MeetSurvey();
        survey.setMeetId(meetId);
        survey.setModuleId(moduleId);
        survey.setPaperId(paperId);
        return survey;
    }
}
