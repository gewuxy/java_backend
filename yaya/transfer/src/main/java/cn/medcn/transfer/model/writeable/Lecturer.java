package cn.medcn.transfer.model.writeable;

import cn.medcn.transfer.model.readonly.MeetRecomdReadOnly;
import cn.medcn.transfer.model.readonly.MeetSourceReadOnly;

/**
 * Created by lixuan on 2017/6/19.
 */
public class Lecturer {

    private Integer id;

    private String name;

    private String headimg;

    private String title;

    private String hospital;

    private String depart;

    private String meetId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }


    public static Lecturer build(MeetSourceReadOnly source , MeetRecomdReadOnly recomd){
        Lecturer lecturer = new Lecturer();
        if(source != null){
            lecturer.setName(source.getLecturer());
        }
        if(recomd != null){
            lecturer.setTitle(recomd.getLecturerTitle());
        }

        return lecturer;
    }
}
