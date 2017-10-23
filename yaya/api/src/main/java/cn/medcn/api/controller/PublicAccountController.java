package cn.medcn.api.controller;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.dto.MeetFolderDTO;
import cn.medcn.meet.service.MeetFolderService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.user.service.AppUserService;

/**
 * 公众号接口
 * Created by LiuLP on 2017/4/20.
 */
@Controller
@RequestMapping("/api/publicAccount")
public class PublicAccountController {

    private static final int YAYA_UNIT_ATTENTION_BASE = 400226;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MeetFolderService meetFolderService;

    @Autowired
    private MeetService meetService;

    @Value("${app.file.base}")
    private String appFileBase;

    /**
     * 查询推荐公众号,可能根据用户所在地区等条件筛选
     *
     * @return 推荐公众号集合
     */
    @RequestMapping("/recommend")
    @ResponseBody
    public String recommend(Pageable pageable) {
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        pageable.getParams().put("userId", userId);
        MyPage<RecommendDTO> myPage = appUserService.selectRecommend(pageable);
        for (RecommendDTO dto : myPage.getDataList()) {
            if (!StringUtils.isEmpty(dto.getHeadimg())) {
                dto.setHeadimg(appFileBase + dto.getHeadimg());
            }
        }
        return APIUtils.success(myPage.getDataList());
    }


    /**
     * 公众号搜索
     *
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public String search(String keyword, Pageable pageable) {
        if (StringUtils.isEmpty(keyword)) {
            return APIUtils.error(SpringUtils.getMessage("user.keyword.notnull"));
        }
        pageable.getParams().put(Constants.KEY_WORD, keyword);
        MyPage<PublicAccountDTO> myPage = appUserService.searchAccount(pageable);
        for (PublicAccountDTO dto : myPage.getDataList()) {
            if (!StringUtils.isEmpty(dto.getHeadimg())) {
                dto.setHeadimg(appFileBase + dto.getHeadimg());
            }
        }
        return APIUtils.success(myPage.getDataList());
    }

    /**
     * 查询我已关注的公众号
     *
     * @param
     * @return
     */
    @RequestMapping("/mySubscribe")
    @ResponseBody
    public String mySubscribe(Pageable pageable) {
        Integer slaverId = SecurityUtils.getCurrentUserInfo().getId();
        pageable.setPageSize(1000);
        pageable.getParams().put("slaverId", slaverId);
        MyPage<PublicAccountDTO> myPage = appUserService.mySubscribe(pageable);
        for (PublicAccountDTO dto : myPage.getDataList()) {
            if (!StringUtils.isEmpty(dto.getHeadimg())) {
                dto.setHeadimg(appFileBase + dto.getHeadimg());
            }
        }
        Collections.sort(myPage.getDataList());
        return APIUtils.success(myPage.getDataList());

    }

    /**
     * 关注或取消关注公众号，status=0 不关注 status=1 关注
     *
     * @param masterId 被关注者id
     * @param status   关注状态 status=0 不关注 status=1 关注
     * @return
     */
    @RequestMapping("/subscribe")
    @ResponseBody
    public String subscribeOrNot(Integer masterId, Integer status) {
        Integer slaverId = SecurityUtils.getCurrentUserInfo().getId();
        if (masterId == null || status != Constants.NUMBER_ZERO && status != Constants.NUMBER_ONE) {
            return APIUtils.error(SpringUtils.getMessage("user.subscribe.paramError"));
        } else {
            //取消关注
            if (status == Constants.NUMBER_ZERO) {
                appUserService.executeCancleAttention(slaverId, masterId);
            } else {  //关注
                appUserService.executeAttention(slaverId, masterId);
            }
            return APIUtils.success();
        }
    }


    /**
     * 公众号资料列表
     *
     * @param pageable
     * @return
     */
    @RequestMapping("/materialList")
    @ResponseBody
    public String findMaterialList(Integer id, Pageable pageable) {
        pageable.getParams().put("id", id);
        MyPage<MaterialDTO> myPage = appUserService.findMaterialList(pageable);
        for (MaterialDTO dto : myPage.getDataList()) {
            dto.setMaterialUrl(appFileBase + dto.getMaterialUrl());
        }
        return APIUtils.success(myPage.getDataList());
    }

    /**
     * 单位号详情
     * pageNum为1时，数据库中查询所有，pageNum大于1时，后台只获取会议列表
     *
     * @param id 单位号id
     * @return
     */
    @RequestMapping("/unitInfo")
    @ResponseBody
    public String unitInfo(Integer id, Pageable pageable) {
        if (StringUtils.isEmpty(id)) {
            return APIUtils.error(SpringUtils.getMessage("user.param.empty"));
        }
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        pageable.getParams().put("masterId", id);
        pageable.getParams().put("slaverId", userId);
        UnitInfoDTO unitInfoDTO = appUserService.findUnitInfo(pageable);
        // 查询会议文件夹及会议列表
        MyPage<MeetFolderDTO> page = meetFolderService.findUnitMeetFolder(pageable);
        for (MeetFolderDTO folderDTO : page.getDataList()) {
            meetService.setUserLearningRecord(folderDTO,userId);
        }
        // 第一页时 查询单位号的详情信息,往下翻页只需要查询会议文件夹及会议数据
        if (pageable.getPageNum() == 1) {
            if (!StringUtils.isEmpty(unitInfoDTO.getHeadimg())) {
                unitInfoDTO.setHeadimg(appFileBase + unitInfoDTO.getHeadimg());
            }
            if (!CheckUtils.isEmpty(unitInfoDTO.getMaterialList())) {
                for (MaterialDTO dto : unitInfoDTO.getMaterialList()) {
                    dto.setMaterialUrl(appFileBase + dto.getMaterialUrl());
                    // 增加返回html文件路径
                    //  dto.setHtmlUrl(appFileBase + dto.getHtmlUrl());
                    // TODO 测试数据 发布正式线需删除此代码，释放上面注释代码
                    String testHtml = "data/17051816413005881649/17082511344000160770/17082511344008521117.html";
                    dto.setHtmlUrl(appFileBase + testHtml);

                }
            }
            if (Constants.DEFAULT_ATTENTION_PUBLIC_ACCOUNT.intValue() == unitInfoDTO.getId().intValue()) {
                unitInfoDTO.setAttentionNum(YAYA_UNIT_ATTENTION_BASE + unitInfoDTO.getAttentionNum());
            }
            unitInfoDTO.setMeetFolderList(page.getDataList());
            return APIUtils.success(unitInfoDTO);
        } else { //下拉列表操作，只返回会议文件夹及会议列表
            List<MeetFolderDTO> folderList = page.getDataList();
            return APIUtils.success(folderList);
        }
    }


}
