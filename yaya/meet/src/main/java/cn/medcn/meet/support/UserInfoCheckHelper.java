package cn.medcn.meet.support;

import cn.medcn.meet.dto.AttendMeetUserDetailDTO;
import cn.medcn.meet.dto.AudioRecordDTO;
import cn.medcn.meet.dto.MeetAttendUserDTO;

import java.text.SimpleDateFormat;

/**
 * Created by Liuchangling on 2017/8/9.
 */

public class UserInfoCheckHelper {

    public static void checkAttendUserDTO(MeetAttendUserDTO attendUserDTO) {
        if (attendUserDTO.getUnitName() == null) {
            attendUserDTO.setUnitName("");
        }
        if (attendUserDTO.getSubUnitName() == null) {
            attendUserDTO.setSubUnitName("");
        }
        if (attendUserDTO.getTitle() == null) {
            attendUserDTO.setTitle("");
        }
        if (attendUserDTO.getLevel() == null) {
            attendUserDTO.setLevel("其他");
        }
        if (attendUserDTO.getProvince() == null
                || attendUserDTO.getCity() != null) {
            attendUserDTO.setProvince("" + attendUserDTO.getCity());
        }
        if (attendUserDTO.getProvince() == null
                && attendUserDTO.getCity() == null) {
            attendUserDTO.setProvince("");
        }
        if (attendUserDTO.getProvince() != null
                && attendUserDTO.getCity() == null) {
            attendUserDTO.setProvince(attendUserDTO.getProvince() + "");
        }
        if (attendUserDTO.getGroupName() == null) {
            attendUserDTO.setGroupName("未分组");
        }
        if (attendUserDTO.getCmeId() == null) {
            attendUserDTO.setCmeId("");
        }

    }

    public static void checkPPTDetailUserDTO(AudioRecordDTO audioRecordDTO) {
        if (audioRecordDTO.getUnitName() == null) {
            audioRecordDTO.setUnitName("");
        }
        if (audioRecordDTO.getSubUnitName() == null) {
            audioRecordDTO.setSubUnitName("");
        }
        if (audioRecordDTO.getTitle() == null) {
            audioRecordDTO.setTitle("");
        }
        if (audioRecordDTO.getLevel() == null) {
            audioRecordDTO.setLevel("其他");
        }
        if (audioRecordDTO.getProvince() == null
                || audioRecordDTO.getCity() != null) {
            audioRecordDTO.setProvince("" + audioRecordDTO.getCity());
        }
        if (audioRecordDTO.getProvince() == null
                && audioRecordDTO.getCity() == null) {
            audioRecordDTO.setProvince("");
        }
        if (audioRecordDTO.getProvince() != null
                && audioRecordDTO.getCity() == null) {
            audioRecordDTO.setProvince(audioRecordDTO.getProvince() + "");
        }
        if (audioRecordDTO.getGroupName() == null) {
            audioRecordDTO.setGroupName("未分组");
        }

    }

    public static void checkAttendMeetUserDetailDTO(AttendMeetUserDetailDTO detailDTO) {
        if (detailDTO.getUnitName() == null) {
            detailDTO.setUnitName("");
        }
        if (detailDTO.getSubUnitName() == null) {
            detailDTO.setSubUnitName("");
        }
        if (detailDTO.getMobile() == null) {
            detailDTO.setMobile("");
        }
        if (detailDTO.getUsername() == null) {
            detailDTO.setUsername("");
        }
        if (detailDTO.getCmeId() == null) {
            detailDTO.setCmeId("");
        }
        if (detailDTO.getGroupName() == null) {
            detailDTO.setGroupName("未分组");
        }
        if (detailDTO.getFunctionId() == null) {
            detailDTO.setFunctionId(0);
        }
        if (detailDTO.getCompleteProgress() == null) {
            detailDTO.setCompleteProgress(0);
        }
        if (detailDTO.getUsedTime() == null) {
            detailDTO.setUsedTime(0L);
        }
        if (detailDTO.getStartTime() == null) {
            detailDTO.setStartTime(null);
        }
    }
}
