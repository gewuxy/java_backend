package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.meet.dto.MeetTuijianDTO;
import cn.medcn.meet.model.Lecturer;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.model.Recommend;
import cn.medcn.meet.service.MeetLecturerService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.RecommendMeetService;
import cn.medcn.user.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author：jianliang
 * @Date: Create in 15:04 2017/11/27
 */
@Controller
@RequestMapping(value = "/yaya/recommendMeet")
public class RecommendMeetController extends BaseController {

    @Autowired
    private RecommendMeetService recommendMeetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MeetService meetService;

    @Autowired
    private MeetLecturerService meetLecturerService;

    /**
     * 推荐会议列表 搜索会议 排序
     * @param pageable
     * @param model
     * @param meetName 会议名称
     * @param recType 会议类型
     * @param recFlag 是否推荐
     * @param sort 排序序号
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name = "推荐会议列表 搜索会议")
    public String recommendMeetList(Pageable pageable, Model model, String meetName, Integer recType, Boolean recFlag,Integer sort) {
        if (!StringUtils.isEmpty(meetName)) {
            pageable.getParams().put("meetName", meetName);
            model.addAttribute("meetName", meetName);
        }
        if (StringUtils.isNotEmpty(String.valueOf(recType))) {
            pageable.getParams().put("recType", recType);
            model.addAttribute("recType", recType);
        }
        if (StringUtils.isNotEmpty(String.valueOf(recFlag))) {
            pageable.getParams().put("recFlag", recFlag);
            model.addAttribute("recFlag", recFlag);
        }
        if (StringUtils.isNotEmpty(String.valueOf(sort))) {
            pageable.getParams().put("sort", sort);
            model.addAttribute("sort", sort);
        }

            MyPage<Recommend> myPage=recommendMeetService.recommendMeetList(pageable);
            model.addAttribute("page", myPage);
             return "/recommendMeet/recommendMeetList";
    }

    /**
     * 跳转添加页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    @Log(name = "跳转添加页面")
    public String editRecommendMeet(Model model) {
        List<String> departments= departmentService.findAlldepartment();
        model.addAttribute("departments",departments);
        return "recommendMeet/recommendMeetInfo";
    }

    /**
     * 新建推荐会议
     * @param recommend
     * @param meetTuijianDTO
     * @param state 会议状态
     * @return
     */
    @RequestMapping(value = "/insert")
    @Log(name = "新建推荐会议")
    public String insertRecommendMeet(Recommend recommend, MeetTuijianDTO meetTuijianDTO,Short state){
        String meetName = recommend.getMeetName();
        Meet meet = meetService.selectByMeetName(meetName);
        recommend.setResourceId(meet.getId());
        recommendMeetService.insert(recommend);
        Lecturer lecturer =new Lecturer();
        lecturer.setMeetId(meet.getId());
        lecturer.setDepart(meetTuijianDTO.getLecturerDepart());
        lecturer.setHospital(meetTuijianDTO.getLecturerHos());
        lecturer.setName(meetTuijianDTO.getLecturer());
        lecturer.setTitle(meetTuijianDTO.getLecturerTile());
        meetLecturerService.insert(lecturer);
        meet.setLecturerId(lecturer.getId());
        meet.setState(state);
        meetService.updateByPrimaryKeySelective(meet);
        return "redirect:/yaya/recommendMeet/list";
    }

    /**
     * 查看推荐会议详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/check")
    @Log(name = "查看推荐会议详情")
    public String checkRecommendMeet(@RequestParam(value = "id", required = true) Integer id,Model model){
        Recommend recommend = recommendMeetService.selectByPrimaryKey(id);
        Date recDate = recommend.getRecDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(recDate);
        model.addAttribute("format",format);
        model.addAttribute("recommend",recommend);
        String meetId = recommend.getResourceId();
        Lecturer lecturer = meetLecturerService.selectByMeetId(meetId);
        model.addAttribute("lecturer",lecturer);
        Meet meet = meetService.selectByPrimaryKey(meetId);
        model.addAttribute("meet",meet);
        List<String> departments= departmentService.findAlldepartment();
        model.addAttribute("departments",departments);
        return "recommendMeet/recommendMeetInfoEdit";
    }

    /**
     * 修改
     * @param recommend 推荐
     * @param lecturer 主讲
     * @param meetName 会议名称
     * @param state 会议状态
     * @return
     */
    @RequestMapping(value = "/update")
    @Log(name = "修改")
    public String updateRecommendMeet(Recommend recommend,Lecturer lecturer,String meetName,Short state){
        Meet meet = meetService.selectByMeetName(meetName);
        String meetId= meet.getId();
        Lecturer lecturerObj = meetLecturerService.selectByMeetId(meetId);
        Lecturer lecturerNew = new Lecturer();
        if (lecturerObj == null){
            lecturerNew.setMeetId(meetId);
            lecturerNew.setDepart(lecturer.getDepart());
            lecturerNew.setHospital(lecturer.getHospital());
            lecturerNew.setName(lecturer.getName());
            lecturerNew.setTitle(lecturer.getTitle());
            meetLecturerService.insert(lecturerNew);
        }else {
            meetLecturerService.updateByPrimaryKey(lecturer);
        }
        recommend.setResourceId(meetId);
        recommendMeetService.updateByPrimaryKey(recommend);
        meet.setLecturerId(lecturerNew.getId());
        meet.setTuijian(recommend.getRecFlag());
        meet.setState(state);
        meetService.updateByPrimaryKeySelective(meet);
        return "redirect:/yaya/recommendMeet/list";
    }

    /**
     * 关闭会议
     * @param id
     * @return
     */
    @RequestMapping(value = "/close")
    @Log(name = "关闭会议")
    public String closeRecommendMeet(@RequestParam(value = "id", required = true) Integer id){
        Recommend recommend = recommendMeetService.selectByPrimaryKey(id);
        String meetId = recommend.getResourceId();
        Meet meet = meetService.selectByPrimaryKey(meetId);
        meet.setState(Meet.MeetType.CLOSED.getState());
        meetService.updateByPrimaryKeySelective(meet);
        return "redirect:/yaya/recommendMeet/list";
    }
}
