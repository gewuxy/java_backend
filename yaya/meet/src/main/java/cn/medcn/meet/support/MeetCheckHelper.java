package cn.medcn.meet.support;

import cn.medcn.common.Constants;
import cn.medcn.meet.dto.MeetFolderDTO;
import cn.medcn.meet.dto.MeetInfoDTO;
import org.springframework.util.StringUtils;

/**
 * Created by lixuan on 2017/8/4.
 */
public class MeetCheckHelper {


    public static void checkMeetFolder(MeetFolderDTO meetFolderDTO, String appFileBase){
        if (!StringUtils.isEmpty(meetFolderDTO.getLecturerHead())) {
            meetFolderDTO.setLecturerHead(appFileBase + meetFolderDTO.getLecturerHead());
        } else {
            meetFolderDTO.setLecturerHead(appFileBase + Constants.USER_DEFAULT_AVATAR_MAN);
        }
        if (meetFolderDTO.getLecturerHead() == null) {
            meetFolderDTO.setLecturerHead("");
        }
        if (meetFolderDTO.getLecturerDepart() == null) {
            meetFolderDTO.setLecturerDepart("");
        }
        if (meetFolderDTO.getLecturerHos() == null) {
            meetFolderDTO.setLecturerHos("");
        }
        if (meetFolderDTO.getLecturerTitle() == null) {
            meetFolderDTO.setLecturerTitle("");
        }
        if (meetFolderDTO.getXsCredits() == null){
            meetFolderDTO.setXsCredits(0);
        }
        if (meetFolderDTO.getEduCredits() == null){
            meetFolderDTO.setEduCredits(0);
        }
        if (!StringUtils.isEmpty(meetFolderDTO.getHeadimg())) {
            meetFolderDTO.setHeadimg(appFileBase + meetFolderDTO.getHeadimg());
        }
    }

    public static void checkMeetInfo(MeetInfoDTO meetInfoDTO, String appFileBase) {
        if (meetInfoDTO.getXsCredits() == null) {
            meetInfoDTO.setXsCredits(0);
        }
        if (meetInfoDTO.getEduCredits() == null) {
            meetInfoDTO.setEduCredits(0);
        }
        if (meetInfoDTO.getRequiredXs() == null) {
            meetInfoDTO.setRequiredXs(false);
        }
        if (meetInfoDTO.getRewardCredit() == null) {
            meetInfoDTO.setRewardCredit(false);
        }
        if (!StringUtils.isEmpty(meetInfoDTO.getLecturerHead())) {
            meetInfoDTO.setLecturerHead(appFileBase + meetInfoDTO.getLecturerHead());
        } else {
            meetInfoDTO.setLecturerHead(appFileBase + Constants.USER_DEFAULT_AVATAR_MAN);
        }
        if (meetInfoDTO.getLecturerHead() == null) {
            meetInfoDTO.setLecturerHead("");
        }
        if (meetInfoDTO.getLecturerDepart() == null) {
            meetInfoDTO.setLecturerDepart("");
        }
        if (meetInfoDTO.getLecturerHos() == null) {
            meetInfoDTO.setLecturerHos("");
        }
        if (meetInfoDTO.getLecturerTitle() == null) {
            meetInfoDTO.setLecturerTitle("");
        }
        if (meetInfoDTO.getCompleteProgress() == null){
            meetInfoDTO.setCompleteProgress(0);
        }
        if (!StringUtils.isEmpty(meetInfoDTO.getHeadimg())) {
            meetInfoDTO.setHeadimg(appFileBase + meetInfoDTO.getHeadimg());
        }
        if (StringUtils.isEmpty(meetInfoDTO.getCoverUrl())) {//未设置封面的时候 设置默认图片
            meetInfoDTO.setCoverUrl(appFileBase + Constants.DEFAULT_MEET_COVER_URL);
        } else {
            meetInfoDTO.setCoverUrl(appFileBase + meetInfoDTO.getCoverUrl());
        }

    }
}
