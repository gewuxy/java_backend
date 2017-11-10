package cn.medcn.api.wexin;

import cn.medcn.api.wexin.security.Principal;
import cn.medcn.api.wexin.security.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CalendarUtils;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.CookieUtils;
import cn.medcn.common.utils.RedisCacheUtils;
import cn.medcn.meet.dto.MeetFolderDTO;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.service.MeetFolderService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.MeetStatsService;
import cn.medcn.user.model.Favorite;
import cn.medcn.weixin.config.WeixinConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lixuan on 2017/8/1.
 */
@Controller
@RequestMapping(value = "/weixin/meet")
public class WXMeetController extends BaseController {

    @Autowired
    protected MeetStatsService meetStatsService;

    @Autowired
    protected MeetService meetService;

    @Autowired
    protected MeetFolderService meetFolderService;

    @Autowired
    protected RedisCacheUtils redisCacheUtils;


    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public String stats(){
        return "/weixin/meet/stats";
    }

    /**
     *
     * @param offset 相对当前时间的偏移量
     * @return
     */
    @RequestMapping(value = "/attendStats", method = RequestMethod.POST)
    @ResponseBody
    public String attendStats(Integer offset){
        if (offset == null){
            offset = 0;
        }
        Principal principal = SecurityUtils.getCurrentUserInfo();
        return success(meetStatsService.findFinalAttendByPersonal(principal.getId(), offset));
    }


    @RequestMapping(value = "/publishStats")
    @ResponseBody
    public String publishStats(Integer offset){
        if (offset == null){
            offset = 0;
        }
        Principal principal = SecurityUtils.getCurrentUserInfo();
        return success(meetStatsService.findFinalPublishByPersonal(principal.getId(), offset));
    }


    @RequestMapping(value = "/favorite")
    public String favorite(){
        return "/weixin/meet/favorite";
    }

    @RequestMapping(value = "/favorite/page")
    @ResponseBody
    public String pageFavorite(Pageable pageable){
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Integer userId = principal.getId();
        //Integer userId = 1200011;
        pageable.put("userId", userId);
        MyPage<MeetInfoDTO> page = meetService.findMeetFavorite(pageable);
        return success(page.getDataList());
    }


    @RequestMapping(value = "/info")
    public String info(String meetId, Model model, HttpServletRequest request){
        Principal principal = SecurityUtils.getCurrentUserInfo();
        MeetInfoDTO meetInfoDTO = meetService.findFinalMeetInfo(meetId, principal == null ? null : principal.getId());
        if (meetInfoDTO.getStartTime() != null && meetInfoDTO.getEndTime() != null){
            model.addAttribute("meetDuration",
                    CalendarUtils.formatTime((meetInfoDTO.getEndTime().getTime() - meetInfoDTO.getStartTime().getTime())/1000));
        }
        model.addAttribute("meet", meetInfoDTO);
        return "/weixin/meet/info";
    }


    /**
     * 收藏或者取消收藏会议
     * @param meetId
     * @return
     */
    @RequestMapping(value = "/favorite/operate")
    @ResponseBody
    public String storeOrNot(String meetId){
        Principal principal = SecurityUtils.getCurrentUserInfo();
        Favorite favorite = new Favorite();
        favorite.setResourceId(meetId);
        favorite.setUserId(principal.getId());
        favorite.setResourceType(Favorite.FavoriteType.meet.ordinal());
        meetService.updateFavoriteStatus(favorite);
        return success();
    }

    /**
     * 热门会议
     * @param model
     * @return
     */
    @RequestMapping(value = "/hot")
    public String hot(HttpServletRequest request, Model model){
        String unionId = CookieUtils.getCookieValue(request, WeixinConfig.COOKIE_NAME_UNION_ID);
        Principal principal = (Principal) redisCacheUtils.getCacheObject(Constants.WX_TOKEN_KEY_SUFFIX+unionId);
        Integer userId = principal == null ? null : principal.getId();
        List<MeetFolderDTO> list = meetService.findRecommendMeetFolder(userId);
        model.addAttribute("list", list);
        return "/weixin/meet/hot";
    }


    @RequestMapping(value = "/sublist")
    public String sublist(String id, Model model, String rootId){
        if (CheckUtils.isEmpty(rootId)){
            rootId = id;
        }
        MeetFolderDTO meetFolderDTO =  meetService.getMeetFolder(rootId);
        Pageable pageable = new Pageable();
        pageable.put("preId", id);
        MyPage<MeetFolderDTO> page = meetFolderService.findLeafMeetFolder(pageable);
        List<MeetFolderDTO> sublist = page.getDataList();
        model.addAttribute("rootId", rootId);
        model.addAttribute("folder", meetFolderDTO);
        model.addAttribute("list", sublist);
        return "/weixin/meet/sublist";
    }
}
