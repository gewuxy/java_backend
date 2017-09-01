package cn.medcn.jcms.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.service.CreditsService;
import cn.medcn.jcms.security.Principal;
import cn.medcn.jcms.utils.SubjectUtils;
import cn.medcn.meet.dto.CourseReprintDTO;
import cn.medcn.meet.dto.CourseSharedDTO;
import cn.medcn.meet.dto.ResourceCategoryDTO;
import cn.medcn.meet.model.AudioCourse;
import cn.medcn.meet.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by lixuan on 2017/5/25.
 */
@Controller
@RequestMapping(value = "/func/res")
public class ResourceController extends BaseController {

    @Autowired
    private AudioService audioService;

    @Autowired
    private CreditsService creditsService;


    @Value("${app.file.base}")
    private String appFileBase;

    /**
     * 查询资源列表
     * @param pageable
     * @param category
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Pageable pageable, String category, String keyword, Model model){
        if(!StringUtils.isEmpty(category)){
            pageable.getParams().put("category", category);
            model.addAttribute("category", category);
        }
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        Principal principal = SubjectUtils.getCurrentUser();
        pageable.getParams().put("userId", principal.getId());
        MyPage<CourseReprintDTO> page = audioService.findResource(pageable);
        model.addAttribute("page", page);
        //查询出所有的分类
        List<ResourceCategoryDTO> categoryList = audioService.findResourceCategorys();
        //查询出我的象数

        model.addAttribute("currentUserId", principal.getId());
        Credits credits = creditsService.doFindMyCredits(principal.getId());
        model.addAttribute("credit", credits == null?0:credits.getCredit());
        model.addAttribute("categoryList", categoryList);
        return "/res/list";
    }

    /**
     * 我的转载记录
     * @param pageable
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/reprints")
    public String reprints(Pageable pageable,String keyword, Model model){
        Principal principal = SubjectUtils.getCurrentUser();
        pageable.getParams().put("userId", principal.getId());
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        MyPage<CourseReprintDTO> page = audioService.findMyReprints(pageable);
        model.addAttribute("page", page);
        return "/res/reprints";
    }

    /**
     * 资源被转载记录
     * @param pageable
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/reprinted")
    public String reprinted(Pageable pageable, String keyword, Model model){
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        Principal principal = SubjectUtils.getCurrentUser();
        pageable.getParams().put("userId", principal.getId());
        MyPage<CourseReprintDTO> page = audioService.findMyReprinted(pageable);
        model.addAttribute("page", page);
        return "/res/reprinted";
    }

    /**
     * 我的共享记录
     * @param pageable
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/shared")
    public String shared(Pageable pageable, String keyword, Model model){
        Principal principal = SubjectUtils.getCurrentUser();
        pageable.getParams().put("userId", principal.getId());
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        MyPage<CourseSharedDTO> page = audioService.findMyShared(pageable);
        model.addAttribute("page", page);
        return "/res/shared";
    }


    @RequestMapping(value = "/reprint")
    public String reprint(Integer id, RedirectAttributes redirectAttributes){
        Principal principal = SubjectUtils.getCurrentUser();
        try {
            audioService.doReprint(id, principal.getId());
        } catch (NotEnoughCreditsException e) {
            addFlashMessage(redirectAttributes, e.getMessage());
            return "redirect:/func/res/list";
        } catch (SystemException e) {
            addFlashMessage(redirectAttributes, e.getMessage());
            return "redirect:/func/res/list";
        }
        addFlashMessage(redirectAttributes, "转载资源成功！");
        return "redirect:/func/res/list";
    }

    /**
     * 关闭或者打开共享
     * @param id
     * @param credit 转载所需象数
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/modifyShared")
    public String modifyShared(Integer id, Integer shareType, Integer credit, RedirectAttributes redirectAttributes){
        Principal principal = SubjectUtils.getCurrentUser();
        AudioCourse course = audioService.selectByPrimaryKey(id);
        if(course == null){
            addFlashMessage(redirectAttributes, "资源[ID="+id+"]不存在");
            return "redirect:/func/res/shared";
        }
        if(course.getOwner().intValue() != principal.getId().intValue()){
            addFlashMessage(redirectAttributes, "您无法操作不属于您的资源");
            return "redirect:/func/res/shared";
        }
        boolean isClose = course.getShared();
        course.setShared(course.getShared() == null || !course.getShared()?true:false);
        if(!isClose){
            if (shareType == null)
                shareType = 0;//免费
            if(shareType == 1 || shareType == 2) {//收费
                course.setCredits(credit);
            }else{
                course.setCredits(0);
            }
            course.setShareType(shareType);
        }
        audioService.updateByPrimaryKeySelective(course);
        addFlashMessage(redirectAttributes, (isClose?"关闭":"打开")+"共享成功！");
        return "redirect:/func/res/shared";
    }

    /**
     * 查询我的转载记录 用以引用
     * @param model
     * @return
     */
    @RequestMapping(value = "/forQuote")
    public String forQuote(Pageable pageable,String meetId, Integer moduleId, String keyword, Model model){
        pageable.setPageSize(6);
        Principal principal = SubjectUtils.getCurrentUser();
        model.addAttribute("meetId", meetId);
        model.addAttribute("moduleId", moduleId);
        pageable.getParams().put("userId", principal.getId());
        if(!StringUtils.isEmpty(keyword)){
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        MyPage<CourseReprintDTO> page = audioService.findMyReprints(pageable);
        model.addAttribute("page", page);
        return "/res/forQuote";
    }

    /**
     * 预览ppt+语音
     * 未转载只能查看查看前5页
     * @param courseId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view")
    public String view(Integer courseId, Model model){
        AudioCourse course = audioService.findAudioCourse(courseId);
        model.addAttribute("course", course);
        model.addAttribute("appFileBase", appFileBase);
        Principal principal = SubjectUtils.getCurrentUser();
        //判断用户是否已经转载了此资源
        if(course.getOwner() == principal.getId()){
            model.addAttribute("reprinted",false);
        }else{
            boolean reprinted = audioService.checkReprinted(courseId, principal.getId());
            model.addAttribute("reprinted", reprinted);
        }
        model.addAttribute("pageLimit", Constants.NOT_ATTENTION_PPT_VIEW_PAGES);
        return "/res/view";
    }
}
