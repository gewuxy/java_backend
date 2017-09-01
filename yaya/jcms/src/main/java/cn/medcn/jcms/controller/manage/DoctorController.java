package cn.medcn.jcms.controller.manage;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.common.utils.RegexUtils;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.MeetModule;
import cn.medcn.meet.service.ExamService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.SurveyService;
import cn.medcn.user.dto.AppUserDTO;
import cn.medcn.user.dto.DoctorDTO;
import cn.medcn.user.dto.DoctorImportExcel;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.Group;
import cn.medcn.user.model.PubUserDocGroup;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.DoctorService;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuLP on 2017/5/16/016.
 */

@Controller
@RequestMapping("/mng/doc")
public class DoctorController extends BaseController{

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private ExamService examService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private AppUserService appUserService;

    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${app.file.upload.base}")
    private String uploadBase;


    private interface groupNameId{
        Integer NO_GROUP_ID = 0; // 未分组ID
        Integer WX_GROUP_ID = 1; // 绑定微信分组ID
    }

    @RequestMapping("/list")
    public String doctors(Integer groupId, Pageable pageable, Model model,String searchName){
        Integer userId = SubjectUtils.getCurrentUserid();
        pageable.put("userId", userId);

        //查询出所有分组信息
        List<Group> groupList = doctorService.findGroupList(userId);
        model.addAttribute("groupId", groupId);
        model.addAttribute("groupList", groupList);

        //查询未分组的人员数量
        int num = doctorService.findUndifindGroupNum(userId);
        model.addAttribute("num", num);

        // 查询绑定微信的医生数量
        int bindWxCount = doctorService.findBindWxCount(userId);
        model.addAttribute("bindWxCount",bindWxCount);

        //查询关注此公众号的所有医生数量
        Integer count = doctorService.findAllDocCount(userId);
        model.addAttribute("count", count);

        // 查询医生信息
        if (searchName == null) {
            searchName = "";
        }
        MyPage<DoctorDTO> list = null;
        if (groupId == null) {
            //查询所有医生信息 根据医生名字 全局搜索 不对分组搜索
            pageable.put("linkman", searchName);
            list = doctorService.findAllDocInfo(pageable);
        } else if (groupId == groupNameId.NO_GROUP_ID) {
            //未分组的医生信息
            list = doctorService.findUndifindGroupDocInfo(pageable);
        } else if (groupId == groupNameId.WX_GROUP_ID) {
            // 查询绑定微信的医生信息
            list = doctorService.findBindWxDocInfo(pageable);
        } else {
            //查询分组医生信息 模糊搜索医生信息
            pageable.getParams().put("groupId", groupId);
            list = doctorService.findDocInfoByGId(pageable);
        }
        for (DoctorDTO dto : list.getDataList()) {
            if (!StringUtils.isEmpty(dto.getHeadimg())) {
                dto.setHeadimg(appFileBase + dto.getHeadimg());
            }
        }
        model.addAttribute("page", list);
        return "/manage/doctors";


    }

    /**
     * 弹出框信息
     * @param docId
     * @return
     */
    @RequestMapping("/popupInfo")
    @ResponseBody
    public String getPopUpInfo(Integer docId){
        Integer ownerId = SubjectUtils.getCurrentUserid();
        DoctorDTO dto = appUserService.getPopUpInfo(docId,ownerId);
        return APIUtils.success(dto);
    }

    /**
     * 提供分组信息给前台,用于回显分组名称
     * @param groupId
     * @return
     */
    @RequestMapping("/editGroup")
    @ResponseBody
    public String editGroup(Integer groupId){
        Group group = null;
        if(groupId != null && groupId != 0){
            group = doctorService.selectByPrimaryKey(groupId);
        }
        return APIUtils.success(group);
    }

    /**
     * 保存设置
     * @param group
     * @return
     */
    @RequestMapping("/saveGroup")
    @ResponseBody
    public String saveGroup(@Validated(value = {Group.class}) Group group){
        Integer userId = SubjectUtils.getCurrentUserid();
        group.setPubUserId(userId);
        if(group.getId() != null && group.getId() != 0){
            //更新操作
            if(!StringUtils.isEmpty(group.getGroupName())){
                doctorService.updateByPrimaryKeySelective(group);
            }
        }else{
            //新增操作
                doctorService.insertSelective(group);
        }
        return APIUtils.success();
    }

    /**
     * 删除分组
     * @param groupId
     * @return
     */
    @RequestMapping("/deleteGroup")
    @ResponseBody
    public String deleteGroup(Integer groupId){
        if(groupId != null && groupId != 0){
            doctorService.deleteByPrimaryKey(groupId);
        }
        return APIUtils.success();
    }

    @RequestMapping("/cancelAttention")
    @ResponseBody
    public String cancleAttention(Integer doctorId){
        Integer masterId = SubjectUtils.getCurrentUserid();
        appUserService.executeCancleAttention(doctorId, masterId);
        //将用户从分组表中删除
        PubUserDocGroup group = new PubUserDocGroup();
        group.setPubUserId(masterId);
        group.setDoctorId(doctorId);
        doctorService.deletePubUserDoctorGroup(group);
        return APIUtils.success();
    }

    /**
     * 将用户移动到别的分组，支持一次移动多用户
     * groupId为空时，即没有选择分组时，数据库插入操作失败
     * @param doctorId
     * @param groupId
     * @param doctorId
     * @return
     */
    @RequestMapping(value="/allot",method = RequestMethod.POST)
    @ResponseBody
    public String allot(Integer groupId,Integer[] doctorId){

        Integer userId = SubjectUtils.getCurrentUserid();
        if(groupId == null){
            return APIUtils.error("groupId不能为空");
        }
            for(Integer docId:doctorId){
                if(docId == null){
                    continue;
                }
                doctorService.allotGroup(docId,groupId,userId);
            }

        return APIUtils.success();
    }

    /**
     * 查看详情
     * @param doctorId
     * @return
     */
    @RequestMapping("/info")
    public String docDetail(Integer doctorId, Model model){
        if (doctorId != null) {
            Integer ownerId = SubjectUtils.getCurrentUserid();
            Map<String, Object> map = new HashMap<>();
            map.put("doctorId", doctorId);
            map.put("ownerId", ownerId);
            DoctorDTO docInfo = doctorService.findDoctorInfo(map);
            if (!StringUtils.isEmpty(docInfo.getHeadimg())) {

                docInfo.setHeadimg(appFileBase + docInfo.getHeadimg());
            }
            model.addAttribute("info", docInfo);

            //会议历史
            Pageable pageable = new Pageable(1, 2);
            MyPage<MeetHistoryDTO> meetPage = getMeetHistory(pageable, doctorId);
            model.addAttribute("meetPage", meetPage);


            //答题历史
            MyPage<ExamHistoryDTO> examPage = getExamHistory(pageable, doctorId);
            model.addAttribute("examPage", examPage);

            //问卷历史
            MyPage<SurveyHistoryDTO> surveyPage = getSurveyHistory(pageable, doctorId);
            model.addAttribute("surveyPage", surveyPage);

            //查询出发送会议提醒所需的会议列表
            Pageable pageableMeet = new Pageable();
            pageableMeet.getParams().put("masterId", ownerId);
            MyPage<MeetListInfoDTO> page = meetService.findMeetForSend(pageableMeet);
            model.addAttribute("meets", page.getDataList());
        }
        return "/manage/doctorInfo";
    }

    /**
     * 会议历史(医生详情)
     * @param pageable
     * @param doctorId
     * @return
     */
    @RequestMapping("/meetHistory")
    @ResponseBody
    public MyPage<MeetHistoryDTO> getMeetHistory(Pageable pageable,Integer doctorId){
        Integer ownerId = SubjectUtils.getCurrentUserid();
        pageable.getParams().put("doctorId",doctorId);
        pageable.getParams().put("ownerId",ownerId);
        MyPage<MeetHistoryDTO> myPage = meetService.getMeetHistory(pageable);
        return myPage;
    }

    /**
     * 答题历史(医生详情)
     * @param pageable
     * @param doctorId
     * @return
     */
    @RequestMapping("/examHistory")
    @ResponseBody
    public MyPage getExamHistory(Pageable pageable,Integer doctorId){
        Integer ownerId = SubjectUtils.getCurrentUserid();
        pageable.getParams().put("doctorId",doctorId);
        pageable.getParams().put("ownerId",ownerId);
        MyPage<ExamHistoryDTO> myPage = examService.getExamHistory(pageable);
        return myPage;
    }

    @RequestMapping("/surveyHistory")
    @ResponseBody
    public MyPage getSurveyHistory(Pageable pageable,Integer doctorId){
        Integer ownerId = SubjectUtils.getCurrentUserid();
        pageable.getParams().put("doctorId",doctorId);
        pageable.getParams().put("ownerId",ownerId);
        MyPage<SurveyHistoryDTO> myPage = surveyService.getSurveyHistory(pageable);
        return myPage;
    }

    /**
     * 导入医生界面
     * @param model
     * @return
     */
    @RequestMapping("/import")
    public String importDoctor(Model model){
        model.addAttribute("fileBase",appFileBase);
        return "/manage/importDoctor";
    }

    /**
     * 根据用户上传的excel文件导入医生
     * @param file
     * @param fileType
     * @param limitSize
     * @param request
     * @return
     */
    @RequestMapping("/uploadTemp")
    @ResponseBody
    public String uploadExcel(@RequestParam(value = "file", required = false)MultipartFile file, String fileType, Long limitSize, HttpServletRequest request) throws Exception {
       //上传文件
        if(file == null){
            return APIUtils.error("不能上传空文件");
        }
        if(limitSize == null || limitSize <= 0){
            return APIUtils.error("limitSize错误");
        }
        if (file.getSize() > limitSize){
            return APIUtils.error("文件大小超出限制");
        }
        FileUploadResult result = null;
        try {
            result = fileUploadService.upload(file,fileType);
        } catch (SystemException e) {
            e.printStackTrace();
            throw new SystemException(e.getMessage());
        }
        //读取上传的excel文件
        String filePath = uploadBase + result.getRelativePath();
        File excelFile = new File(filePath);
        List<DoctorImportExcel> docList = (List<DoctorImportExcel>)ExcelUtils.readFirstSheetToExcelDataList(excelFile,true,DoctorImportExcel.class);
                int hasUserCount = 0;
                int finishCount = 0;
             for(DoctorImportExcel docExcel:docList){
                      //判断数据合法性
                    try {
                        checkData(docExcel);
                    } catch (SystemException e) {
                        excelFile.delete();
                        return APIUtils.error(e.getMessage());
                    }
                    //判断用户是否存在
                    if(hasUser(docExcel.getUsername())){
                        hasUserCount ++;
                          continue;
                    }
                     AppUser user = AppUserDTO.rebuildToDoctor(getUserDTO(docExcel));
                    appUserService.executeBatchRegister(user,SubjectUtils.getCurrentUserid());
                    finishCount ++;
            }
                if(hasUserCount > 0){
                    return APIUtils.success("成功导入"+finishCount+"个用户，有"+hasUserCount+"个用户已存在");
                }else {
                    return APIUtils.success("成功导入"+finishCount+"个用户");
                }
    }


    private void checkData(DoctorImportExcel excel) throws SystemException{

                if(StringUtils.isEmpty(excel.getLinkman())){
                    throw new SystemException("医生姓名不能为空");
                }
                if(StringUtils.isEmpty(excel.getHospital())){
                    throw new SystemException("医生:"+excel.getLinkman()+"的单位不能为空");
                }
                if(StringUtils.isEmpty(excel.getHosLevel())){
                    throw new SystemException("医生:"+excel.getLinkman()+"的医院级别不能为空");
                }
                if(StringUtils.isEmpty(excel.getDepartment())){
                    throw new SystemException("医生:"+excel.getLinkman()+"的科室不能为空");
                }
                if(StringUtils.isEmpty(excel.getProvince())){
                    throw new SystemException("医生:"+excel.getLinkman()+"的省份不能为空");
                }
                if(StringUtils.isEmpty(excel.getCity())){
                    throw new SystemException("医生:"+excel.getLinkman()+"的城市不能为空");
                }
                if(!RegexUtils.checkMobile(excel.getMobile())){
                    throw new SystemException("医生:"+excel.getLinkman()+"的手机格式不正确,请仔细检查");
                }
                if(!RegexUtils.checkEmail(excel.getUsername())){
                    throw new SystemException("医生:"+excel.getLinkman()+"的邮箱格式不正确,请仔细检查");
                }
                if(!"123456".equals(excel.getPassword())){
                    throw new SystemException("请设置初始密码为123456");
                }

    }

    private Boolean hasUser(String username){
        AppUser user  = new AppUser();
        user.setUsername(username);
        return appUserService.selectOne(user) != null;
    }

    private AppUserDTO getUserDTO(DoctorImportExcel docExcel){
       AppUserDTO dto = new AppUserDTO();
        dto.setLinkman(docExcel.getLinkman());
        dto.setNickname(docExcel.getLinkman());
        dto.setHospital(docExcel.getHospital());
        dto.setHosLevel(docExcel.getHosLevel());
        dto.setDepartment(docExcel.getDepartment());
        dto.setProvince(docExcel.getProvince());
        dto.setCity(docExcel.getCity());
       dto.setZone(docExcel.getZone());
       dto.setAddress(docExcel.getProvince()+docExcel.getCity()+docExcel.getZone());
       dto.setMobile(docExcel.getMobile());
       dto.setUsername(docExcel.getUsername());
       dto.setPassword("123456");
        return dto;
    }


    /**
     * 导出会议，考试，问卷历史
     * @param docId
     * @param total
     * @param type
     * @param response
     * @throws SystemException
     */
    @RequestMapping("/export")
    public void exportExcel(Integer docId,Integer total, String type,HttpServletResponse response) throws SystemException {
        if(docId == null || docId == 0){
            throw new SystemException("医生id不存在");
        }
        List<Object> dataList = Lists.newArrayList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Pageable pageable = new Pageable(1,total);
        pageable.getParams().put("doctorId",docId);
        pageable.getParams().put("ownerId",SubjectUtils.getCurrentUserid());
        String fileName = null;
        Workbook workbook = null;
        if(ExportType.meet.name().equals(type)) {
            MyPage<MeetHistoryDTO> myPage = getMeetHistory(pageable,docId);
             fileName = "会议历史.xls";
            for(MeetHistoryDTO dto:myPage.getDataList()){
                MeetHistoryExcelData data = new MeetHistoryExcelData();
                data.setMeetType(MeetModule.ModuleFunction.PPT.getFunName());
                data.setMeetName(dto.getMeetName());
                data.setPublishTime(dto.getPublishTime() == null?"":format.format(dto.getPublishTime()));
                data.setLearnTime(dto.getTime());
                dataList.add(data);
            }
            workbook = ExcelUtils.writeExcel(fileName,dataList,MeetHistoryExcelData.class);
        }else if(ExportType.exam.name().equals(type)){
            MyPage<ExamHistoryDTO> myPage = examService.getExamHistory(pageable);
            fileName = "答题历史.xls";
            for(ExamHistoryDTO dto:myPage.getDataList()){
                ExamHistoryExcelData data = new ExamHistoryExcelData();
                data.setMeetType(MeetModule.ModuleFunction.EXAM.getFunName());
                data.setExamName(dto.getPaperName());
                data.setTime(dto.getTime());
                data.setScore(dto.getScore()+"");
                dataList.add(data);
            }
            workbook = ExcelUtils.writeExcel(fileName,dataList,ExamHistoryExcelData.class);
        }else if(ExportType.survey.name().equals(type)){
            MyPage<SurveyHistoryDTO> myPage = surveyService.getSurveyHistory(pageable);
            fileName = "问卷历史.xls";
            for(SurveyHistoryDTO dto:myPage.getDataList()){
                SurveyHistoryExcelData data = new SurveyHistoryExcelData();
                data.setMeetType( MeetModule.ModuleFunction.SURVEY.getFunName());
                data.setSurveyName(dto.getPaperName());
                data.setTime( dto.getTime());
                dataList.add(data);
            }
            workbook = ExcelUtils.writeExcel(fileName,dataList,SurveyHistoryExcelData.class);
        }else{
            throw new SystemException("请选择正确导出类型");
        }
        try {

            ExcelUtils.createExcel(fileName,workbook,response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException("文件导出失败");
        }

    }


    public enum ExportType{
        meet,
        exam,
        survey
    }




}

