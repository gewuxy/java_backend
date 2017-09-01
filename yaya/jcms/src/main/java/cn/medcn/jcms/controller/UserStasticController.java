package cn.medcn.jcms.controller;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.dto.UserAttendDTO;
import cn.medcn.user.dto.UserDataDetailDTO;
import cn.medcn.user.service.UserStasticService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Liuchangling on 2017/5/16.
 * 医生统计模块
 */
@Controller
@RequestMapping(value = "/data/state")
public class UserStasticController extends BaseController {
    @Autowired
    private UserStasticService userStasticService;

    /**
     * 医生统计 本周、本月、所有 关注数
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/doc")
    public String attendCount(Pageable pageable, Model model) {
        Integer userId = SubjectUtils.getCurrentUserid();
        // 公众号的关注数统计
        UserAttendDTO attendDTO = userStasticService.findAttendCount(userId);
        model.addAttribute("userAttendDTO", attendDTO);

        // 地图数据
        pageable.put("userId", userId);
        pageable.put("province", "全国");
        List<UserDataDetailDTO> list = userStasticService.findUserDataByPro(pageable.getParams());
        if (!CheckUtils.isEmpty(list)) {
            calculatePercent(list, pageable.getParams());
        }
        model.addAttribute("list", list);
        // 前端根据pages 分页 固定页数
        Integer pages = (list.size() - 1) / 15 + 1;
        model.addAttribute("pages", pages);
        return "/tongji/doctorStastic";
    }


    /**
     * 用户属性占比分析
     *
     * @param pageable
     * @param propNum
     * @return
     */
    @RequestMapping(value = "/userDataProp")
    @ResponseBody
    public String userDataProp(Pageable pageable, Integer propNum, String province) {
        Integer userId = SubjectUtils.getCurrentUserid();

        pageable.put("userId", userId);
        pageable.put("province", province);
        // 每页显示的条数
        pageable.setPageSize(6);

        MyPage<UserDataDetailDTO> page = userStasticService.findUserData(pageable, propNum);
        if (page != null && !CheckUtils.isEmpty(page.getDataList())) {
            calculatePercent(page.getDataList(), pageable.getParams());
        }
        return APIUtils.success(page);
    }


    /**
     * 饼图用户数据分布
     *
     * @param propNum
     * @param province
     * @return
     */
    @RequestMapping(value = "/chartData")
    @ResponseBody
    public String chartData(Pageable pageable, Integer propNum, String province) {
        Integer userId = SubjectUtils.getCurrentUserid();
        pageable.put("userId", userId);
        pageable.put("province", province);
        pageable.setPageSize(Integer.MAX_VALUE);

        MyPage<UserDataDetailDTO> page = userStasticService.findUserData(pageable, propNum);
        List<UserDataDetailDTO> list = Lists.newArrayList();
        if (page != null) {
            list = page.getDataList();
            if (!CheckUtils.isEmpty(list)) {
                calculatePercent(list, pageable.getParams());
            }
        }
        return APIUtils.success(list);
    }

    /**
     * 计算百分比
     *
     * @param userDataDetailDTOS
     * @param params
     */
    private void calculatePercent(List<UserDataDetailDTO> userDataDetailDTOS, Map<String, Object> params) {
        Integer userCount;
        // 省份为空的情况下 查询公众号的 所有用户关注数
        int totalCount = 0;
        Integer userId = (Integer) params.get("userId");
        String province = (String) params.get("province");
        if (province == null || province.equals("全国")) {
            totalCount = userStasticService.findTotalAttendCount(userId);
        } else {
            // 查询指定省份 公众号的 用户关注数
            List<UserDataDetailDTO> ulist = userStasticService.findDataByProvince(params);
            totalCount = CheckUtils.isEmpty(ulist) ? 0 : ulist.get(0).getUserCount();
        }
        float percent;
        for (UserDataDetailDTO detailDTO : userDataDetailDTOS) {
            if (StringUtils.isBlank(detailDTO.getPropName()) || detailDTO.getPropName().equals("null")) {
                detailDTO.setPropName("未设置");
            }
            // 用户数
            userCount = detailDTO.getUserCount();
            // 计算百分比
            percent = userCount * 1.0F / totalCount;
            detailDTO.setPercent(percent);
        }
    }


}
