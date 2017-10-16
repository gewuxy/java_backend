package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.*;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.*;

import cn.medcn.meet.model.SurveyQuestion;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.SurveyService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
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
 * Created by lixuan on 2017/6/6.
 */
@Controller
@RequestMapping(value = "/func/meet/survey")
public class SurveyController extends BaseController {


    @Autowired
    private SurveyService surveyService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${app.file.upload.base}")
    private String appUploadBase;


    @RequestMapping(value = "/batch")
    @ResponseBody
    public String batch(@RequestParam(value = "file", required = false) MultipartFile file, String meetId, Integer moduleId) {
        String dir = FilePath.TEMP.path;
        FileUploadResult result = null;
        try {
            result = fileUploadService.upload(file, dir);
        } catch (SystemException e) {
            e.printStackTrace();
            return APIUtils.error(e.getMessage());
        }
        File excel = new File(appUploadBase + result.getRelativePath());
        try {
            List<Object[]> objectList = ExcelUtils.readExcel(excel);
            Principal principal = SubjectUtils.getCurrentUser();
            Integer paperId = surveyService.insertOrUpdateSurveyPaper(meetId, moduleId, principal.getId());
            surveyService.executeImport(paperId, surveyService.parseSurveyQuestions(objectList));
        } catch (Exception e) {
            e.printStackTrace();
            return APIUtils.error("解析问卷模板文件出错");
        }
        fileUploadService.removeFile(result.getRelativePath());
        return APIUtils.success();
    }


    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(Integer paperId, Integer questionId) {
        surveyService.deleteQuestion(paperId, questionId);
        return APIUtils.success();
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(SurveyQuestion question) {
        boolean isAdd = question.getId() == null || question.getId() == 0;
        question.optionArrToJSON();
        if (isAdd) {
            question.setSort(surveyService.countQuestions(question.getPaperId()) + 1);
            surveyService.addQuestion(question);
        } else {
            surveyService.updateQuestion(question);
        }
        return APIUtils.success();
    }


    @RequestMapping(value = "/edit")
    public String edit(Integer paperId, Integer questionId, Integer qtype, Model model) {
        model.addAttribute("paperId", paperId);
        if (questionId != null) {
            SurveyQuestion question = surveyService.findQuestion(questionId);
            model.addAttribute("question", question);
            model.addAttribute("qtype", question.getQtype());
            return "/meet/survey/edit";
        } else {
            if (qtype == null) {
                qtype = 0;
            }
            model.addAttribute("qtype", qtype);
            return "/meet/survey/add";
        }
    }

    /**
     * 问卷统计
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/statistics")
    public String survey(String id) {
        return "/tongji/surveyStastic";
    }

    /**
     * 问卷统计列表
     *
     * @param pageable
     * @param id
     * @return
     */
    @RequestMapping(value = "/record")
    @ResponseBody
    public String surveyRecord(Pageable pageable, String id) {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), id)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        MyPage<SurveyHistoryRecordDTO> page = null;
        if (!StringUtils.isEmpty(id)) {
            pageable.put("meetId", id);
            page = surveyService.findSurveyRecord(pageable);
        } else {
            return APIUtils.error("参数错误");
        }
        return APIUtils.success(page);
    }

    /**
     * 导出 所有参加问卷调查的用户记录
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportExcel")
    @ResponseBody
    public String exportSurveyExcel(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }

        if (!StringUtils.isEmpty(meetId)) {
            Workbook workbook = null;
            String fileName = "_参与问卷人员情况.xls";
            List<Object> dataList = Lists.newArrayList();

            List<AttendSurveyUserDataDTO> surveyDataList = findSurveyDataList(principal.getId(), meetId);
            if (!CheckUtils.isEmpty(surveyDataList)) {
                fileName = surveyDataList.get(0).getMeetName() + fileName;

                // 查询一共多少道题目
                List<Integer> sortList = surveyService.findQuestionSort(meetId);

                // 根据用户ID 查询用户的答题数据
                for (AttendSurveyUserDataDTO userDataDTO : surveyDataList) {
                    // 填充excel表格数据
                    SurveyHistoryUserExcelData excelData = fillDataToSurveyExcel(userDataDTO);

                    Integer userId = userDataDTO.getUserId();
                    // 用户每道题选择的答案记录
                    List answerList = findUserSelectAnswer(meetId, userId, sortList);
                    excelData.setAnswerList(answerList);

                    dataList.add(excelData);
                }
            } else {
                return APIUtils.error("暂无相关数据");
            }

            workbook = ExcelUtils.writeExcel(fileName, dataList, SurveyHistoryUserExcelData.class);

            try {

                ExcelUtils.outputWorkBook(fileName, workbook, response);

            } catch (Exception e) {
                e.printStackTrace();
                return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
            }
        }

        return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
    }

    /**
     * 查找用户选择的答案
     *
     * @param meetId
     * @param userId
     * @param sortList
     */
    private List findUserSelectAnswer(String meetId, Integer userId, List<Integer> sortList) {
        List<QuestionOptItemDTO> optItemDTOList = surveyService.findOptItemListByUser(meetId, userId);
        List answerList = Lists.newArrayList();
        if (!CheckUtils.isEmpty(sortList)) {
            for (Integer i : sortList) {
                if (!CheckUtils.isEmpty(optItemDTOList)) {
                    for (QuestionOptItemDTO itemDTO : optItemDTOList) {
                        // 比较题号和用户答题的题号一致 设置该题用户选择的答案
                        if (i == itemDTO.getSort()) {
                            answerList.add(itemDTO.getSelAnswer());
                        }
                    }
                } else {
                    answerList.add("空");
                }
            }
        }
        return answerList;
    }

    /**
     * 查询 参加问卷的用户记录
     *
     * @param userId
     * @param meetId
     * @return
     */
    private List<AttendSurveyUserDataDTO> findSurveyDataList(Integer userId, String meetId) {
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("meetId", meetId);
        conditionMap.put("userId", userId);
        return surveyService.findUserSurveyHis(conditionMap);
    }

    /**
     * 问卷 选项统计
     *
     * @param id
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/option/statistics")
    @ResponseBody
    public String selAnswerRecord(String id, Pageable pageable) {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), id)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }

        if (!StringUtils.isEmpty(id)) {
            List surveyList = Lists.newArrayList();

            pageable.setPageSize(3);// 设置每页显示3条数据
            pageable.put("meetId", id);
            // 查询问卷数据列表
            MyPage<SurveyQuestion> questionPage = surveyService.findSurveyDatas(pageable);

            if (!CheckUtils.isEmpty(questionPage.getDataList())) {
                for (SurveyQuestion sq : questionPage.getDataList()) {
                    SurveyRecordDTO recordDTO = new SurveyRecordDTO();
                    recordDTO.setId(sq.getId());
                    recordDTO.setSort(sq.getSort());
                    recordDTO.setTitle(sq.getTitle());
                    recordDTO.setQtype(sq.getQtype());

                    // 每道题目的所有选项
                    List<KeyValuePair> kvList = sq.getOptionList();

                    // 根据题目ID 查询所有用户答题记录
                    Integer questionId = sq.getId();
                    if (sq.getQtype().intValue() < SurveyQuestion.QuestionType.FILL_BLANK.getType().intValue()) {
                        // 选择题 用户每道题目选择的答案和正确答案比较，统计出每个选项被选择的次数
                        assignRecordItemDTOData(questionId, kvList, recordDTO);
                    } else {
                        // 主观题 作答人数
                        List answerList = surveyService.findSubjQuesAnswerByQuestionId(questionId);
                        recordDTO.setAnswerCount(answerList.size());
                    }

                    surveyList.add(recordDTO);
                }
            }

            questionPage.setDataList(surveyList);
            return APIUtils.success(questionPage);

        } else {
            return APIUtils.error("参数错误");
        }
    }

    /**
     * 统计每个选项被选择的次数
     *
     * @param questionId 题目id
     * @param kvList     题目的选项列表
     * @param recordDTO  问卷内容DTO
     */
    private void assignRecordItemDTOData(Integer questionId, List<KeyValuePair> kvList, SurveyRecordDTO recordDTO) {
        // 选项内容及被选择的次数记录
        List<SurveyRecordItemDTO> itemDTO = new ArrayList<>();
        // 题目选项记录
        List<SurveyRecordItemDTO> surveyRecordList = surveyService.findSurveyRecordByQid(questionId);
        // 遍历所有的答题记录和题目的选项列表，比较正确答案和用户选择的答案，累加每个选项被选择的次数
        if (!CheckUtils.isEmpty(surveyRecordList)) {
            for (KeyValuePair kv : kvList) {
                // 选项
                String key = kv.getKey();
                // 选项内容
                String v = kv.getValue();

                // 题目选项内容
                SurveyRecordItemDTO item = new SurveyRecordItemDTO();
                item.setOptkey(key);
                item.setOption(v);

                Integer selCount = 0;
                for (SurveyRecordItemDTO sv : surveyRecordList) {
                    if (!StringUtils.isBlank(sv.getSelAnswer())) { // 用户选择的答案
                        // 计算每个选项被选择的次数
                        selCount = calculateNumber(key, sv.getSelAnswer(), selCount);
                    }
                }
                item.setSelCount(selCount);
                itemDTO.add(item);
                recordDTO.setSurveyRecordItemDTO(itemDTO);
                selCount = 0;
            }
        } else {
            // 遍历题目所有选项
            for (KeyValuePair kv : kvList) {
                // 选项
                String key = kv.getKey();
                // 选项内容
                String v = kv.getValue();
                SurveyRecordItemDTO item = new SurveyRecordItemDTO();
                item.setOptkey(key);
                item.setOption(v);
                item.setSelCount(0);
                itemDTO.add(item);
            }
            recordDTO.setSurveyRecordItemDTO(itemDTO);
        }
    }

    /**
     * 填充用户问卷记录到excel表格
     *
     * @param userDataDTO
     * @return
     */
    private SurveyHistoryUserExcelData fillDataToSurveyExcel(AttendSurveyUserDataDTO userDataDTO) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SurveyHistoryUserExcelData excelData = new SurveyHistoryUserExcelData();
        excelData.setName(userDataDTO.getNickname());
        if (userDataDTO.getAttend()) {
            excelData.setParticipation("是");
        } else {
            excelData.setParticipation("否");
        }
        excelData.setUnitName(userDataDTO.getUnitName());
        excelData.setSubUnitName(userDataDTO.getSubUnitName());
        excelData.setHosLevel(userDataDTO.getLevel());
        excelData.setTitle(userDataDTO.getTitle());
        excelData.setProvince(userDataDTO.getProvince() + userDataDTO.getCity());
        excelData.setGroupName(userDataDTO.getGroupName() == null ? "未分组" : userDataDTO.getGroupName());
        excelData.setSubmitTime(userDataDTO.getSubmitTime() == null ? "" : format.format(userDataDTO.getSubmitTime()));
        excelData.setAnswerList(null);
        return excelData;
    }

    /**
     * 导出问卷数据分析Excel
     *
     * @param meetId
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportSvyData")
    @ResponseBody
    public String exportSurveyDataExcel(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if (!meetService.checkMeetIsMine(principal.getId(), meetId)) {
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }

        if (!StringUtils.isEmpty(meetId)) {
            Workbook workbook = null;
            String fileName = "_问卷数据分析.xls";
            List<Object> dataList = Lists.newArrayList();

            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("meetId", meetId);
            List<SurveyRecordDTO> surveyList = surveyService.findSurveyToExcel(conditionMap);

            if (!CheckUtils.isEmpty(surveyList)) {
                fileName = surveyList.get(0).getMeetName() + fileName;

                // 根据Map的value大小 合并数据
                Map<Integer, List> questionMap = assignDataToMap(surveyList);

                for (SurveyRecordDTO survey : surveyList) {
                    dataList.addAll(assignmentToSurvey(survey));
                }

                workbook = ExcelUtils.writeExcel(fileName, dataList, SurveyExcelData.class);

                try {
                    Integer sortIndex = SurveyExcelData.columnIndex.SORT.getColumnIndex();
                    Integer questionTitleIndex = SurveyExcelData.columnIndex.QUESTION_TITLE.getColumnIndex();
                    // 需要合并的列的下标
                    int[] columnIndexArray = new int[]{sortIndex, questionTitleIndex};
                    ExcelUtils.createMergeExcel(fileName, workbook, questionMap, columnIndexArray, response);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                return APIUtils.error("暂无数据导出");
            }
        }
        return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
    }

    /**
     * 赋值数据到map中
     *
     * @param surveyList
     * @return
     */
    private Map<Integer, List> assignDataToMap(List<SurveyRecordDTO> surveyList) {
        Map<Integer, List> questionMap = Maps.newHashMap();
        for (SurveyRecordDTO sv : surveyList) {
            if (sv.getQtype().intValue() < SurveyQuestion.QuestionType.MULTIPLE_CHOICE.getType().intValue()) {
                // 每道题目的所有选项
                if (questionMap.get(sv.getSort()) == null) {
                    List<KeyValuePair> kvList = sv.getOptionList();
                    questionMap.put(sv.getSort(), kvList);
                } else {
                    questionMap.get(sv.getSort()).add(sv.getOptionList());
                }
            } else { // 主观题
                // 查询主观题用户答题记录
                List<SubjQuesAnswerDTO> list = surveyService.findSubjQuesAnswerByQuestionId(sv.getId());
                questionMap.put(sv.getSort(),list);
            }
        }
        return questionMap;
    }

    /**
     * 填充用户问卷记录 excel表格数据
     *
     * @param surveyDTO
     * @return
     */
    private List assignmentToSurvey(SurveyRecordDTO surveyDTO) {
        List<Object> dataList = Lists.newArrayList();
        // 每道题目的所有选项
        List<KeyValuePair> kvlist = surveyDTO.getOptionList();
        // 根据题目id查询所有用户答题记录
        Integer questionId = surveyDTO.getId();
        // 选择题
        if (surveyDTO.getQtype().intValue() < SurveyQuestion.QuestionType.FILL_BLANK.getType().intValue()){
            List<SurveyRecordItemDTO> userRecordList = surveyService.findSurveyRecordByQid(questionId);
            // 遍历所有的答题记录和题目的选项列表遍历 对比用户选择的选项 累加每个选项的选择次数
            if (!CheckUtils.isEmpty(userRecordList)) {
                DecimalFormat dft = new DecimalFormat("0%");

                for (KeyValuePair kv : kvlist) {
                    SurveyExcelData excelData = new SurveyExcelData();
                    excelData.setSort(surveyDTO.getSort().toString());
                    excelData.setQuestionTitle(surveyDTO.getTitle());
                    // 选项
                    excelData.setQuestionOption(kv.getKey());
                    // 参加问卷的总人数
                    Integer totalCount = surveyDTO.getTotalCount();
                    // 被选择的次数
                    Integer selectCount = 0;

                    for (SurveyRecordItemDTO recordItemDTO : userRecordList) {
                        if (!StringUtils.isBlank(recordItemDTO.getSelAnswer())) {
                            // 计算每个选项 选择的人数
                            selectCount = calculateNumber(kv.getKey(), recordItemDTO.getSelAnswer(), selectCount);
                        }
                        excelData.setSelectionCount(selectCount.toString());
                        excelData.setSelectance(dft.format((float) selectCount / totalCount));
                    }
                    dataList.add(excelData);
                    selectCount = 0;
                }
            } else {
                // 遍历题目所有选项
                for (KeyValuePair kv : kvlist) {
                    String key = kv.getKey();
                    SurveyExcelData excelData = new SurveyExcelData();
                    excelData.setSort(surveyDTO.getSort().toString());
                    excelData.setQuestionTitle(surveyDTO.getTitle());
                    excelData.setQuestionOption(key);
                    excelData.setSelectionCount("0");
                    excelData.setSelectance("0%");
                    dataList.add(excelData);
                }
            }
        } else {
            List<SubjQuesAnswerDTO> answerDTOList = surveyService.findSubjQuesAnswerByQuestionId(questionId);
            // 主观题用户答案
            if (!CheckUtils.isEmpty(answerDTOList)) {
                for (SubjQuesAnswerDTO answerDTO : answerDTOList) {
                    SurveyExcelData excelData = new SurveyExcelData();
                    excelData.setSort(surveyDTO.getSort().toString());
                    excelData.setQuestionTitle(surveyDTO.getTitle());
                    excelData.setQuestionOption(answerDTO.getAnswer());
                    excelData.setSelectionCount(answerDTO.getNickName());
                    dataList.add(excelData);
                }
            }
        }

        return dataList;
    }

    /**
     * 计算选项选择人数
     *
     * @param rightKey    正确答案
     * @param selAnswer   用户选择的答案
     * @param selectCount 选择的次数
     * @return
     */
    private int calculateNumber(String rightKey, String selAnswer, int selectCount) {
        // 遍历题目所有选项
        for (int i = 0; i < selAnswer.length(); i++) {
            char selKey = selAnswer.charAt(i);
            if (rightKey.equals(String.valueOf(selKey))) {
                selectCount++;
                break;
            }
        }
        return selectCount;
    }


    /**
     * 导出主观题用户答题记录
     * @param questionId
     * @param title 题目内容
     * @return
     */
    @RequestMapping(value = "/export/answer")
    @ResponseBody
    public String exportSubjectiveAnswerExcel(Integer questionId, String title, HttpServletResponse response) {
        if (questionId != null && questionId != 0) {
            Workbook workbook;
            // 导出的excel文件名
            String fileName = SubjQuesAnswerExcelData.ExcelName.excel_name.getTitle() + ".xls";

            List<Object> dataList = Lists.newArrayList();
            // 查询主观题 所有用户答题记录
            List<SubjQuesAnswerDTO> answerList = surveyService.findSubjQuesAnswerByQuestionId(questionId);
            if (!CheckUtils.isEmpty(answerList)) {

                for (SubjQuesAnswerDTO answer : answerList) {
                    // 将数据赋值给excel填充表格
                    SubjQuesAnswerExcelData excelData = new SubjQuesAnswerExcelData();
                    excelData.setTitleName(title);
                    excelData.setName(answer.getNickName());
                    excelData.setAnswer(answer.getAnswer());
                    dataList.add(excelData);
                }

                try {
                    workbook = ExcelUtils.writeExcel(fileName, dataList, SubjQuesAnswerExcelData.class);
                    if (dataList.size() > 1) {
                        // 需要合并的列的下标
                        Integer mergeIndex = SubjQuesAnswerExcelData.ColumnIndex.TITLE.getIndex();
                        int[] mergeIndexArray = new int[]{mergeIndex};
                        ExcelUtils.createMergeExcel(fileName, workbook, dataList.size(), mergeIndexArray, response);
                    } else {
                        ExcelUtils.outputWorkBook(fileName, workbook, response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
                }

            } else {
                  return APIUtils.error("暂无数据导出");
            }
        }
        return null;
    }

}
