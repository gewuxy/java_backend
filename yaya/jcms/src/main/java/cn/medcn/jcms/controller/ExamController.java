package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.*;
import cn.medcn.meet.model.ExamPaper;
import cn.medcn.meet.model.ExamQuestion;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.model.MeetExam;
import cn.medcn.meet.service.ExamService;
import cn.medcn.meet.service.MeetService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lixuan on 2017/6/2.
 * 考试模块
 */
@Controller
@RequestMapping(value = "/func/meet/exam")
public class ExamController extends BaseController {

    private static Log log = LogFactory.getLog(ExamController.class);

    @Autowired
    private ExamService examService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private MeetService meetService;

    @Value("${app.file.upload.base}")
    private String appUploadBase;


    @RequestMapping(value = "/batch")
    @ResponseBody
    public String batch(@RequestParam(value = "file", required = false) MultipartFile file, String meetId, Integer moduleId, Integer examId) {
        String dir = FilePath.TEMP.path;
        FileUploadResult result = null;
        try {
            result = fileUploadService.upload(file, dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        //ExcelUtils.readExcel()
        String realPath = appUploadBase + result.getRelativePath();
        File excel = new File(realPath);
        List<Object[]> datas = null;
        try {
            datas = ExcelUtils.readExcel(excel);
        } catch (Exception e) {
            e.printStackTrace();
            return APIUtils.error("解析试卷出错");
        }
        List<ExamQuestion> questionList = examService.parseExamQuestions(datas);
        examService.executeImportPaper(createPaper(meetId, moduleId), questionList);
        return APIUtils.success();
    }


    private Integer createPaper(String meetId, Integer moduleId) {
        Principal principal = SubjectUtils.getCurrentUser();
        MeetExam exam = examService.findExam(meetId, moduleId);
        Meet meet = meetService.selectByPrimaryKey(meetId);
        ExamPaper paper = new ExamPaper();
        paper.setPaperName(meet.getMeetName());
        paper.setCreateTime(new Date());
        paper.setOwner(principal.getId());
        paper.setShared(false);
        paper.setCategory(meet.getMeetType());
        paper.setTotalPoint(0);
        Integer paperId = examService.addPaper(paper);
        exam.setPaperId(paperId);
        examService.updateMeetExam(exam);
        return paperId;
    }

    /**
     * 添加或者编辑试题
     *
     * @param paperId
     * @param questionId
     * @param qtype
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(Integer paperId, Integer questionId, Integer qtype, Model model) {
        model.addAttribute("paperId", paperId);
        if (questionId != null) {
            ExamQuestion question = examService.findQuestion(paperId, questionId);
            model.addAttribute("question", question);
            model.addAttribute("qtype", question.getQtype());
            return "/meet/question/edit";
        } else {
            if (qtype == null) {
                qtype = 0;
            }
            model.addAttribute("qtype", qtype);
            return "/meet/question/add";
        }
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(Integer paperId, Integer questionId) {
        examService.deleteQuestion(paperId, questionId);
        return APIUtils.success();
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(ExamQuestion question, Integer paperId) {
        boolean isAdd = question.getId() == null || question.getId() == 0;
        question.optionArrToJSON();
        question.rightKeyArrToRightKey();
        if (isAdd) {
            examService.addQuestion(paperId, question);
        } else {
            examService.updateQuestion(paperId, question);
        }
        return APIUtils.success();
    }

    @RequestMapping(value = "/passScore")
    @ResponseBody
    public String passScore(Integer examId, Integer passScore) {
        MeetExam exam = examService.findMeetExamById(examId);
        exam.setPassScore(passScore);
        examService.updateMeetExam(exam);
        return APIUtils.success();
    }


    @RequestMapping(value = "/resitTimes")
    @ResponseBody
    public String resitTimes(Integer examId, Integer resitTimes) {
        MeetExam exam = examService.findMeetExamById(examId);
        exam.setResitTimes(resitTimes);
        examService.updateMeetExam(exam);
        return APIUtils.success();
    }


    @RequestMapping(value = "/usetime")
    @ResponseBody
    public String usetime(Integer examId, Integer usetime) {
        MeetExam exam = examService.findMeetExamById(examId);
        exam.setUsetime(usetime);
        examService.updateMeetExam(exam);
        return APIUtils.success();
    }


    /**
     * 考题统计
     * @param pageable
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/statistics")
    public String examData(Pageable pageable, String id, Model model) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), id)) {
            throw new SystemException("会议不存在");
        }
        if (!StringUtils.isEmpty(id)) {
            // 查询考题ID
            Integer paperId = examService.findPaperIdById(id);
            if (paperId !=null && paperId!=0){
                pageable.put("paperId", paperId);
                // 查询题目信息 及答题总人数
                MyPage<ExamHistoryDataDTO> page = examService.findTotalCountByQuestion(pageable);
                // 查询答对的人数
                Map<String, Object> rightMap = examService.findExamAnswerRecord(pageable.getParams());

                for (ExamHistoryDataDTO dataDTO : page.getDataList()) {
                    assignDataToExam(dataDTO, rightMap);
                }
                model.addAttribute("page", page);

            }else{
               throw new SystemException("没有试卷");
            }
        }
        return "/tongji/examStastic";
    }


    /**
     * 导出考题数据
     *
     * @param meetId
     * @param response
     * @return
     */
    @RequestMapping(value = "/export")
    @ResponseBody
    public String exportExam(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if (!StringUtils.isEmpty(meetId)) {
            Workbook workbook = null;
            String fileName = " 考题数据统计.xls";
            List<Object> dataList = Lists.newArrayList();

            Integer paperId = examService.findPaperIdById(meetId);
            if (paperId != 0) {
                Map<String, Object> conditionMap = new HashMap<String, Object>();
                conditionMap.put("paperId", paperId);
                List<ExamHistoryDataDTO> examList = examService.findExamDataToExcel(conditionMap);
                if (!CheckUtils.isEmpty(examList)) {
                    // 查询答对的人数
                    Map<String, Object> rightMap = examService.findExamAnswerRecord(conditionMap);
                    for (ExamHistoryDataDTO hisDTO : examList) {
                        hisDTO = assignDataToExam(hisDTO, rightMap);
                        // 填充excel表格数据
                        ExamExcelData excelData = fillExamExcelData(hisDTO);
                        dataList.add(excelData);
                    }
                } else {
                    return APIUtils.error("暂无考题数据");
                }
            } else {
                return APIUtils.error("会议没有考题");
            }

            workbook = ExcelUtils.writeExcel(fileName, dataList, ExamExcelData.class);

            try {
                ExcelUtils.outputWorkBook(fileName, workbook, response);
            } catch (IOException e) {
                e.printStackTrace();
                return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
            }
        }
        return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
    }


    /**
     * 将考题相关字段数据赋值给考题对象
     * @param dataDTO
     * @param rightMap
     * @return
     */
    private ExamHistoryDataDTO assignDataToExam(ExamHistoryDataDTO dataDTO, Map<String, Object> rightMap) {
        DecimalFormat df = new DecimalFormat("0%");
        Integer rightCount = 0;
        Integer errorCount = 0;
        String errorPercent = null;
        float error = 0;
        // 答题总人数
        Integer totalCount = dataDTO.getTotalCount();
        // 答对记录
        if (rightMap != null && rightMap.size() != 0) {
            rightCount = (Integer) rightMap.get(dataDTO.getSort().toString());
            log.info("第"+dataDTO.getSort()+"题，答对次数："+rightCount);
            if (rightCount == null){
                rightCount = 0;
            }
            if (totalCount != 0) {
                errorCount = totalCount - rightCount;
            }
            log.info("第"+dataDTO.getSort()+"题，答错次数："+errorCount);
        } else {
            // 没有答对记录 则算答错记录
            errorCount = totalCount;
            log.info("没有答对记录:"+errorCount);
        }
        // 计算答错概率
        if (errorCount > 0) {
            if (errorCount == totalCount) {
                errorPercent = "100%";
            } else {
                error = ((float) errorCount / (float) totalCount);
                errorPercent = df.format(error);
            }
            dataDTO.setErrorPercent(errorPercent);
        } else {
            dataDTO.setErrorPercent("0%");
        }
        dataDTO.setRightCount(rightCount);
        dataDTO.setErrorCount(errorCount);
        return dataDTO;
    }

    /**
     * 填充考题分析excel数据
     * @param examDataDTO
     * @return
     */
    private ExamExcelData fillExamExcelData(ExamHistoryDataDTO examDataDTO){
        ExamExcelData excelData = new ExamExcelData();
        excelData.setSort(examDataDTO.getSort().toString());
        excelData.setQuestionTitle(examDataDTO.getTitle());
        excelData.setRightNumber(examDataDTO.getRightCount().toString());
        excelData.setWrongNumber(examDataDTO.getErrorCount().toString());
        excelData.setErrorRate(examDataDTO.getErrorPercent());
        return excelData;
    }


    /**
     * 查询 用户考试记录
     *
     * @param pageable
     * @param id
     * @return
     */
    @RequestMapping(value = "/history")
    @ResponseBody
    public String examRecordStastic(Pageable pageable, String id) {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), id)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if (!StringUtils.isEmpty(id)) {
            pageable.put("meetId", id);
            MyPage<ExamHistoryRecordDTO> page = examService.findExamHisRecord(pageable);
            return APIUtils.success(page);
        } else {
            return APIUtils.error("会议ID不能为空");
        }
    }


    /**
     * 导出所有用户考试成绩
     *
     * @param meetId
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportExcel")
    @ResponseBody
    public String exportExamExcel(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }

        if (!StringUtils.isEmpty(meetId)) {
            Workbook workbook = null;
            String fileName = " 参与考试人员情况.xls";
            List<Object> dataList = Lists.newArrayList();

            List<AttendExamHistoryDTO> HistoryDTOList = findExamListByMeetId(principal.getId(), meetId);
            if (!CheckUtils.isEmpty(HistoryDTOList)) {
                fileName = HistoryDTOList.get(0).getMeetName() + fileName;
                // 查询一共多少道题目
                List<Integer> sortList = examService.findSortList(meetId);

                for (AttendExamHistoryDTO historyDTO : HistoryDTOList) {
                    ExamHistoryUserExcelData userExcelData = fillDataToExcel(historyDTO);

                    Integer userId = historyDTO.getUserId();
                    // 查询用户试卷答题记录
                    List<QuestionOptItemDTO> optItemDTOList = examService.findOptItemListByUser(meetId, userId);
                    List answerList = Lists.newArrayList();
                    if (!CheckUtils.isEmpty(sortList)){
                        for (Integer i : sortList) {
                            if (!CheckUtils.isEmpty(optItemDTOList)) {
                                for (QuestionOptItemDTO itemDTO : optItemDTOList) {
                                    // 比较题号和用户答题的题号一致 设置该题用户选择的答案
                                    if (i == itemDTO.getSort()){
                                        answerList.add(itemDTO.getSelAnswer());
                                    }
                                }
                            } else {
                                answerList.add("空");
                            }
                        }
                    }
                    userExcelData.setAnswerList(answerList);
                    dataList.add(userExcelData);
                }
            } else {
                return APIUtils.error("暂无相关数据");
            }

            workbook = ExcelUtils.writeExcel(fileName, dataList, ExamHistoryUserExcelData.class);

            try {
                ExcelUtils.outputWorkBook(fileName, workbook, response);
            } catch (IOException e) {
                e.printStackTrace();
                return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
            }
        }
        return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
    }

    /**
     * 查询 用户考试记录
     *
     * @param userId
     * @param meetId
     * @return
     */
    private List<AttendExamHistoryDTO> findExamListByMeetId(Integer userId, String meetId) {
        List<AttendExamHistoryDTO> examHistoryList = Lists.newArrayList();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("meetId", meetId);
        conditionMap.put("userId", userId);
        // 查询会议是否有限制分组
        MeetLimitDTO meetLimitDTO = examService.findGroupIdIsLimit(meetId);
        if (meetLimitDTO != null &&
                (meetLimitDTO.getGroupId()!=null
                        && meetLimitDTO.getGroupId() != 0)) {
            // 如果限制分组，查询该分组下的用户的基本信息
            conditionMap.put("groupId", meetLimitDTO.getGroupId());
            examHistoryList = examService.findExamHisUserData(conditionMap);
            if (!CheckUtils.isEmpty(examHistoryList)){
                AttendExamHistoryDTO examHistoryDTO = examHistoryList.get(0);
                examHistoryDTO.setMeetName(meetLimitDTO.getMeetName());
            }
        } else {
            // 查询考试记录中用户考试记录
            examHistoryList = examService.findExamHis(conditionMap);
        }
        return examHistoryList;
    }

    /**
     * 填充用户考题数据 Excel表格
     *
     * @param examHistoryDTO
     * @return
     */
    private ExamHistoryUserExcelData fillDataToExcel(AttendExamHistoryDTO examHistoryDTO) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ExamHistoryUserExcelData excelData = new ExamHistoryUserExcelData();
        excelData.setName(examHistoryDTO.getNickname());
        if (examHistoryDTO.getAttend()) {
            excelData.setParticipation("是");
        } else {
            excelData.setParticipation("否");
        }
        excelData.setUnitName(examHistoryDTO.getUnitName() == null ? "" : examHistoryDTO.getUnitName());
        excelData.setSubUnitName(examHistoryDTO.getSubUnitName() == null ? "" : examHistoryDTO.getSubUnitName());
        excelData.setHosLevel(examHistoryDTO.getLevel());
        excelData.setTitle(examHistoryDTO.getTitle());
        excelData.setProvince(examHistoryDTO.getProvince() + examHistoryDTO.getCity());
        excelData.setExamTimes(examHistoryDTO.getFinishTimes() == null ? "0" :examHistoryDTO.getFinishTimes().toString());
        excelData.setSubmitTime(examHistoryDTO.getSubmitTime() == null ? "" : format.format(examHistoryDTO.getSubmitTime()));
        excelData.setScore(examHistoryDTO.getScore() == null ? "0" : examHistoryDTO.getScore().toString());
        excelData.setAnswerList(null);
        return excelData;
    }

}
