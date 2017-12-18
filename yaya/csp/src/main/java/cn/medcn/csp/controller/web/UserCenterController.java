package cn.medcn.csp.controller.web;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.*;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.dto.VideoDownloadDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.model.Live;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.service.AudioService;
import cn.medcn.meet.service.LiveService;
import cn.medcn.oauth.service.OauthService;
import cn.medcn.user.dto.CspUserInfoDTO;
import cn.medcn.user.dto.VideoLiveRecordDTO;
import cn.medcn.user.model.*;
import cn.medcn.user.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuLP on 2017/10/11/011.
 */
@Controller
@RequestMapping(value = "/mgr/user")
public class UserCenterController extends CspBaseController {

    private static Log log = LogFactory.getLog(UserCenterController.class);

    @Autowired
    protected CspUserService cspUserService;

    @Autowired
    protected RedisCacheUtils<String> redisCacheUtils;

    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected OauthService oauthService;

    @Autowired
    protected EmailTempService tempService;

    @Autowired
    protected UserFluxService userFluxService;

    @Autowired
    protected CspPackageService packageService;

    @Autowired
    private LiveService liveService;

    @Autowired
    protected CspPackageInfoService cspPackageInfoService;

    @Value("${app.file.upload.base}")
    protected String uploadBase;

    @Value("${app.file.base}")
    protected String fileBase;


    /**
     * 个人中心的用户信息
     *
     * @return
     */
    @RequestMapping("/info")
    public String userInfo(Model model) {
        addBaseUserInfo(model);
        return localeView("/userCenter/userInfo");
    }

    /**
     * 左侧的基本用户信息
     *
     * @param model
     */
    private CspUserInfoDTO addBaseUserInfo(Model model) {
        String userId = getWebPrincipal().getId();
        CspUserInfoDTO dto = cspUserService.findCSPUserInfo(userId);
        if (needAvatarPrefix(dto.getAvatar())) {
            dto.setAvatar(fileBase + dto.getAvatar());
        }
        List<BindInfo> bindInfoList = cspUserService.findBindListByUserId(userId);
        dto.setBindInfoList(bindInfoList);
        model.addAttribute("dto", dto);
        return dto;
    }


    /**
     * 跳转到头像设置页面
     *
     * @return
     */
    @RequestMapping("/toAvatar")
    public String toAvatar(Model model) {
        addBaseUserInfo(model);
        return localeView("/userCenter/setAvatar");
    }

    /**
     * 修改用户头像
     *
     * @return
     */

    @RequestMapping(value = "/updateAvatar", method = RequestMethod.POST)
    @ResponseBody
    public String updateAvatar(@RequestParam(value = "file", required = false) MultipartFile file, Integer limitSize) {
        if (file == null) {
            return error(local("upload.error.null"));
        }

        if (file.getSize() > limitSize) {
            return error(local("upload.fileSize.err"));
        }

        String userId = getWebPrincipal().getId();
        String url = null;
        try {
            url = cspUserService.updateAvatar(file, userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        Principal principal = getWebPrincipal();
        principal.setAvatar(url);
        SecurityUtils.set(principal);
        return success(url);
    }


    /**
     * 跳转到修改密码页面
     *
     * @param model
     * @return
     */
    @RequestMapping("toReset")
    public String toResetPwd(Model model) {
        CspUserInfoDTO dto = addBaseUserInfo(model);
        model.addAttribute("email", dto.getEmail());
        return localeView("/userCenter/pwdReset");

    }

    /**
     * 重置密码
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping("/resetPwd")
    @ResponseBody
    public String resetPwd(String oldPwd, String newPwd) {

        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
            return error(local("user.empty.password"));
        }
        String userId = getWebPrincipal().getId();
        try {
            cspUserService.resetPwd(userId, oldPwd, newPwd);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 更新个人信息中的姓名和简介
     */
    @RequestMapping("/updateInfo")
    @ResponseBody
    public String updateInfo(CspUserInfo info) {
        info.setId(getWebPrincipal().getId());
        cspUserService.updateByPrimaryKeySelective(info);
        return success();
    }


    /**
     * 跳转到账号管理页面
     *
     * @return
     */
    @RequestMapping("/toAccount")
    public String toAccount(Model model) {
        CspUserInfoDTO dto = addBaseUserInfo(model);
        List<BindInfo> list = dto.getBindInfoList();
        String localStr = LocalUtils.getLocalStr();

        for (BindInfo info : list) {
            //英文版
            if (LocalUtils.Local.en_US.name().equals(localStr)) {
                if (info.getThirdPartyId() == BindInfo.Type.FACEBOOK.getTypeId()) {
                    model.addAttribute("facebook", info.getNickName());
                }
                if (info.getThirdPartyId() == BindInfo.Type.TWITTER.getTypeId()) {
                    model.addAttribute("twitter", info.getNickName());
                }
            } else { //中文版
                if (info.getThirdPartyId() == BindInfo.Type.WE_CHAT.getTypeId()) {
                    model.addAttribute("weChat", info.getNickName());
                }
                if (info.getThirdPartyId() == BindInfo.Type.WEI_BO.getTypeId()) {
                    model.addAttribute("weiBo", info.getNickName());
                }
            }

            if (info.getThirdPartyId() == BindInfo.Type.YaYa.getTypeId()) {
                model.addAttribute("YaYa", info.getNickName());
            }
        }
        return localeView("/userCenter/accountBind");
    }


    /**
     * 跳转到第三方授权页面
     *
     * @param type
     * @return
     * @throws SystemException
     */
    @RequestMapping("/jumpOauth")
    @ResponseBody
    public String jumpOauthUrl(Integer type) throws SystemException {
        if (type == null) {
            return error(local("bind.type.empty"));
        }
        String url = oauthService.jumpThirdPartyAuthorizePage(type);
        return success(url);
    }

    /**
     * 绑定手机号
     *
     * @param mobile
     * @param captcha
     * @return
     */
    @RequestMapping("/bindMobile")
    @ResponseBody
    public String bindMobile(String mobile, String captcha) {
        if (!StringUtils.isMobile(mobile) || StringUtils.isEmpty(captcha)) {
            return error(local("error.param"));
        }
        try {
            //检查验证码合法性
            checkCaptchaIsOrNotValid(mobile, captcha);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        String userId = getWebPrincipal().getId();
        try {
            cspUserService.doBindMobile(mobile, captcha, userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 发送绑定邮件
     */
    @RequestMapping("/bindEmail")
    @ResponseBody
    public String toBind(String email, String password) {

        String userId = getWebPrincipal().getId();
        String localStr = LocalUtils.getLocalStr();
        try {
            cspUserService.sendBindMail(email, password, userId, localStr);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();

    }


    /**
     * 解绑账号
     *
     * @param type
     * @return
     */
    @RequestMapping("/unbind")
    @ResponseBody
    public String unbind(Integer type) {
        String userId = getWebPrincipal().getId();
        //解绑手机或邮箱
        if (type == BindInfo.Type.MOBILE.getTypeId() || type == BindInfo.Type.EMAIL.getTypeId()) {
            try {
                cspUserService.doUnbindEmailOrMobile(type, userId);
            } catch (SystemException e) {
                return error(e.getMessage());
            }
            return success();
        }

        //解绑第三方账号
        try {
            cspUserService.doUnbindThirdAccount(type, userId);
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        return success();
    }

    /**
     * 跳转到流量管理页面
     *
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping("/toFlux")
    public String toFlux(Pageable pageable, Model model) {
        pageable.setPageSize(3);
        addBaseUserInfo(model);
        String userId = getWebPrincipal().getId();
        pageable.put("userId", userId);
        int flux = cspUserService.findFlux(userId);
        model.addAttribute("flux", flux);
        //直播视频记录
        MyPage<VideoLiveRecordDTO> myPage = cspUserService.findVideoLiveRecord(pageable);
        VideoLiveRecordDTO.transExpireDay(myPage.getDataList());
        model.addAttribute("page", myPage);
        return localeView("/userCenter/toFlux");
    }

    /**
     * 下载视频
     *
     * @param courseId
     * @return
     */
    @RequestMapping("/download")
    public void downLoad(String courseId, String meetName, HttpServletResponse response) throws SystemException {

        String userId = getWebPrincipal().getId();
        if (StringUtils.isEmpty(userId)) {
            throw new SystemException(local("meeting.error.not_mine"));
        }
        if (StringUtils.isEmpty(courseId)) {
            throw new SystemException(local("courseId.empty"));
        }
        UserFluxUsage usage = userFluxService.findUsage(userId, courseId);
        if (usage == null) {
            throw new SystemException(local("source.not.exists"));
        }
        Live live = liveService.findByCourseId(Integer.valueOf(courseId));
        if (live == null || CheckUtils.isEmpty(live.getReplayUrl())) {
            throw new SystemException(local("source.not.exists"));
        }

        Integer downloadCount = usage.getDownloadCount();
        //下载次数大于5，不允许下载
        if (downloadCount != null && downloadCount > 5) {
            throw new SystemException(local("download.count.err"));
        }
        //更新视频下载次数
        usage.setDownloadCount(downloadCount == null ? 1 : downloadCount + 1);
        int count = userFluxService.updateVideoDownloadCount(usage);
        //更新异常
        if (count != 1) {
            throw new SystemException(local("download.fail"));
        }
        //打开下载框
        DownloadUtils.openDownloadBox(meetName, response, live.getReplayUrl());

    }


    /**
     * 下载视频
     *
     * @param key
     */
    @RequestMapping("/cache/download")
    public void downloadVideo(String key, HttpServletResponse response) throws Exception {
        if (org.springframework.util.StringUtils.isEmpty(key)) {
            throw new SystemException(local("user.param.empty"));
        }
        VideoDownloadDTO dto = redisCacheUtils.getCacheObject(Constants.VIDEO_DOWNLOAD_URL + key);

        //下载地址不存在
        if (dto == null) {
            throw new SystemException(local("download.address.expired"));
        }

        String url = fileBase + dto.getDownloadUrl();
        String fileName = dto.getFileName();
        String courseId = dto.getCourseId();
        String userId = dto.getUserId();

        try {
            //更新下载次数，并且将缓存中的数据删除
            userFluxService.updateDownloadCountAndDeleteRedisKey(key, courseId, userId);
            DownloadUtils.openDownloadBox(fileName, response, url);
        } catch (Exception e) {
            throw new SystemException(e.getMessage());
        }

    }


    /**
     * 进入会员权限界面
     *
     * @return
     */
    @RequestMapping(value = "/memberManage")
    public String memberManage(Model model) {
        Principal principal = getWebPrincipal();
        String userId = principal.getId();
        addBaseUserInfo(model);
        CspPackage cspPackage = packageService.findUserPackageById(userId);
        List<CspPackageInfo> cspPackageInfos = cspPackageInfoService.selectByPackageId(cspPackage.getId());
        model.addAttribute("cspPackageInfos", cspPackageInfos);
        model.addAttribute("cspPackage", cspPackage);
        return localeView("/userCenter/memberManage");
    }
}
