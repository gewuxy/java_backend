package cn.medcn.transfer.model.readonly;

/**
 * Created by lixuan on 2017/6/16.
 */
public class MeetMenuReadOnly {

    public static final String MEET_MENU_EXAM = "参加考试";

    public static final String MEET_MENU_SURVEY = "问卷调查";

    public static final String MEET_MENU_VIDEO = "观看视频";

    private Integer menuId;		//菜单id
    private Integer meetingId;	//会议id
    private String menuName;	//菜单名称
    private String menuUrl;		//菜单url
    private String menuImgUrl;  //菜单图标url
    private Integer menuState;	//标识在什么状态下 才能进入菜单项 [菜单项在会议进行中可点击，还是在会议结束后可点击（0：进行中；1：已结束）]
    private String menuFlag;	//标识该菜单是否是专门定制的（Y：专门定制；N：默认）


    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuImgUrl() {
        return menuImgUrl;
    }

    public void setMenuImgUrl(String menuImgUrl) {
        this.menuImgUrl = menuImgUrl;
    }

    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
        this.menuState = menuState;
    }

    public String getMenuFlag() {
        return menuFlag;
    }

    public void setMenuFlag(String menuFlag) {
        this.menuFlag = menuFlag;
    }
}
