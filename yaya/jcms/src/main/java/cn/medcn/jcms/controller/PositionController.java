package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.MeetSignHistoryDTO;
import cn.medcn.meet.dto.SignHistoryUserExcelData;
import cn.medcn.meet.model.MeetPosition;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.SignService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/6/5.
 */
@Controller
@RequestMapping(value = "/func/meet/position")
public class PositionController extends BaseController {

    @Autowired
    private SignService signService;

    @Autowired
    private MeetService meetService;


    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(MeetPosition position){
        Principal principal = SubjectUtils.getCurrentUser();
        boolean isMine = meetService.checkMeetIsMine(principal.getId(),position.getMeetId());
        if(!isMine){
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        boolean isAdd = position.getId() == null || position.getId() == 0;
        if(isAdd){
            signService.insert(position);
        }else{
            signService.updateByPrimaryKeySelective(position);
        }
        return APIUtils.success();
    }

    /**
     * 签到统计
     * @param pageable
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/sign/statistics")
    public String signRecord(Pageable pageable, String id, Model model){
        Principal principal = SubjectUtils.getCurrentUser();
        boolean isMine = meetService.checkMeetIsMine(principal.getId(),id);
        if(!isMine){
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        pageable.put("meetId",id);
        MyPage<MeetSignHistoryDTO> page = signService.findSignRecord(pageable);
        model.addAttribute("page",page);
        return "/tongji/signStastic";
    }

    /**
     * 导出签到数据
     * @param meetId
     * @param response
     * @return
     * @throws SystemException
     */
    @RequestMapping(value = "/exportSign")
    @ResponseBody
    public String exportSignExcel(String meetId, HttpServletResponse response) throws SystemException {
        Principal principal = SubjectUtils.getCurrentUser();
        if(!meetService.checkMeetIsMine(principal.getId(), meetId)){
            return APIUtils.error(SpringUtils.getMessage("meet.notmine"));
        }
        if(!StringUtils.isEmpty(meetId)){
            Workbook workbook = null;
            String fileName = " 签到数据统计.xls";
            List<Object> dataList = Lists.newArrayList();

            Map<String,Object> conditionmMap = new HashMap<>();
            conditionmMap.put("meetId",meetId);
            List<MeetSignHistoryDTO> signList = signService.findSignListExcel(conditionmMap);
            if (!CheckUtils.isEmpty(signList)){

                for (MeetSignHistoryDTO signDTO : signList) {
                    SignHistoryUserExcelData userExcelData = fillDataToExcel(signDTO);
                    dataList.add(userExcelData);
                }

                workbook = ExcelUtils.writeExcel(fileName,dataList,SignHistoryUserExcelData.class);

            }else{
                return APIUtils.error("暂无数据导出");
            }

            try {
                ExcelUtils.outputWorkBook(fileName,workbook,response);
            } catch (IOException e) {
                e.printStackTrace();
                return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
            }
        }
        return APIUtils.error(APIUtils.ERROR_CODE_EXPORT_EXCEL, SpringUtils.getMessage("export.file.error"));
    }

    /**
     * 填充签到 excel表格数据
     * @param signDTO
     * @return
     */
    private SignHistoryUserExcelData fillDataToExcel(MeetSignHistoryDTO signDTO){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SignHistoryUserExcelData userExcelData = new SignHistoryUserExcelData();
        userExcelData.setName(signDTO.getNickname());
        userExcelData.setUnitName(signDTO.getUnitName()==null?"":signDTO.getUnitName());
        userExcelData.setSubUnitName(signDTO.getSubUnitName()==null?"":signDTO.getSubUnitName());
        userExcelData.setSignTime(signDTO.getSignTime()==null?"":format.format(signDTO.getSignTime()));
        if (signDTO.getSignFlag()==null || signDTO.getSignFlag()==false){
            userExcelData.setSignState("失败");
        } else {
            userExcelData.setSignState("成功");
        }
        return userExcelData;
    }
}
