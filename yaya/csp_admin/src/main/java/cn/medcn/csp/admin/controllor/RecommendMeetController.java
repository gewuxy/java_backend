package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.meet.dto.MeetTuijianDTO;
import cn.medcn.meet.model.Lecturer;
import cn.medcn.meet.model.Meet;
import cn.medcn.meet.model.MeetTuijian;
import cn.medcn.meet.model.Recommend;
import cn.medcn.meet.service.MeetLecturerService;
import cn.medcn.meet.service.MeetService;
import cn.medcn.meet.service.RecommendMeetService;
import cn.medcn.user.service.DepartmentService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Value("${app.file.upload.base}")
    protected String appFileUploadBase;

    @Value("${app.file.base}")
    protected String appFileBase;

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
        if (StringUtils.isNotEmpty(meetName)) {
            pageable.getParams().put("meetName", meetName);
            model.addAttribute("meetName", meetName);
        }
        if (recType != null) {
            pageable.getParams().put("recType", recType);
            model.addAttribute("recType", recType);
        }
        if (recFlag != null) {
            pageable.getParams().put("recFlag", recFlag);
            model.addAttribute("recFlag", recFlag);
        }
        if (sort != null) {
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
        List<Meet> meets= meetService.selectAllMeet();
        model.addAttribute("meets",meets);
        return "recommendMeet/recommendMeetInfo";
    }

    /**
     * 弹窗页面
     * @param model
     * @param pageable
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/queryMeet")
    public String queryMeet(Model model,Pageable pageable,String keyword){
        if (!StringUtils.isEmpty(keyword)) {
            pageable.getParams().put("keyword", keyword);
            model.addAttribute("keyword", keyword);
        }
        MyPage<Meet> myPage = meetService.selectMeetList(pageable);
        model.addAttribute("page", myPage);
        return "/recommendMeet/queryMeetList";
    }

    /**
     * 弹窗页面根据id获得会议内容
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectOne")
    @ResponseBody
    public String selectOne(@RequestParam(value = "id", required = true) String id,Model model){
        Meet meet = meetService.selectById(id);
        meet.setImgPath(appFileBase + meet.getHeadimg());
        model.addAttribute("meet",meet);
        String json = JSON.toJSONString(meet);
        model.addAttribute("json",json);
        return json;
    }

    /**
     * 新建推荐会议
     * @param recommend
     * @param lecturer
     * @param state
     * @param meetId
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/insert")
    @Log(name = "新建推荐会议")
    public String insertRecommendMeet(Recommend recommend, Lecturer lecturer,Short state,String meetId,RedirectAttributes redirectAttributes){
        Recommend recommend1 = recommendMeetService.selectByMeetId(meetId);
        Lecturer lecturer1 = meetLecturerService.selectByMeetId(meetId);
        Meet meet = meetService.selectByPrimaryKey(meetId);
        if (recommend1==null) {
            int lecturerCount = 0;
            if (lecturer1 == null){
                lecturerCount = meetLecturerService.insert(lecturer);
            }else {
                lecturer1.setHeadimg(lecturer.getHeadimg());
                lecturer1.setDepart(lecturer.getDepart());
                lecturer1.setTitle(lecturer.getTitle());
                lecturer1.setName(lecturer.getName());
                lecturer1.setHospital(lecturer.getHospital());
                lecturer1.setMeetId(lecturer.getMeetId());
                lecturerCount = meetLecturerService.updateByPrimaryKey(lecturer1);
            }
            if (lecturerCount != 1){
                addErrorFlashMessage(redirectAttributes,"添加讲师失败");
            }
            recommend.setResourceId(meetId);
            int recommendMeetCount = recommendMeetService.insert(recommend);
            if (recommendMeetCount != 1){
                addErrorFlashMessage(redirectAttributes,"添加推荐会议失败");
            }
            meet.setLecturerId(lecturer.getId());
            meet.setState(state);
            meet.setTuijian(recommend.getRecFlag());
            int meetCount = meetService.updateByPrimaryKeySelective(meet);
            if (meetCount != 1){
                addErrorFlashMessage(redirectAttributes,"会议更改失败");
            }
            addFlashMessage(redirectAttributes,"添加成功");
        }else {
          addErrorFlashMessage(redirectAttributes,"会议已推荐");
        }
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
        if (lecturer != null){
            String headimg = lecturer.getHeadimg();
            if (headimg != null){
                model.addAttribute("imgPath",appFileBase+headimg);
                model.addAttribute("headimg",headimg);
            }
        }
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
    public String updateRecommendMeet(Recommend recommend,Lecturer lecturer,String meetName,Short state,Integer lecturerId,RedirectAttributes redirectAttributes){
        String meetId = recommend.getResourceId();
        Meet meet = meetService.selectByPrimaryKey(meetId);
        meet.setMeetName(meetName);
        lecturer.setId(lecturerId);
        lecturer.setMeetId(meetId);
        int lecturerCount = meetLecturerService.updateByPrimaryKey(lecturer);
        if (lecturerCount != 1){
            addErrorFlashMessage(redirectAttributes,"修改主讲者失败");
        }
        int recommendMeetCount = recommendMeetService.updateByPrimaryKey(recommend);
        if (recommendMeetCount != 1){
            addErrorFlashMessage(redirectAttributes,"修改推荐会议失败");
        }
        meet.setLecturerId(lecturer.getId());
        meet.setTuijian(recommend.getRecFlag());
        meet.setState(state);
        int meetCount = meetService.updateByPrimaryKeySelective(meet);
        if (meetCount != 1){
            addErrorFlashMessage(redirectAttributes,"修改会议失败");
        }
        return "redirect:/yaya/recommendMeet/list";
    }

    /**
     * 关闭推荐
     * @param id
     * @return
     */
    @RequestMapping(value = "/close")
    @Log(name = "关闭推荐")
    public String closeRecommendMeet(@RequestParam(value = "id", required = true) Integer id,RedirectAttributes redirectAttributes){
        Recommend recommend = recommendMeetService.selectByPrimaryKey(id);
        if (recommend != null){
            recommend.setRecFlag(false);
            int recommendMeet = recommendMeetService.updateByPrimaryKey(recommend);
            if (recommendMeet != 1){
                addErrorFlashMessage(redirectAttributes,"关闭推荐失败");
            }
        }else {
            addErrorFlashMessage(redirectAttributes,"推荐会不存在");
        }
        return "redirect:/yaya/recommendMeet/list";
    }


    /**
     * 图片上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    @Log(name = "图片上传")
    public String uploadImg(MultipartFile file){
        if (file == null) {
            return error("不能上传空文件");
        }
        String filename = file.getOriginalFilename();
        String suffix = file.getOriginalFilename().substring(filename.lastIndexOf("."));
        String saveFileName = StringUtils.nowStr()+suffix;
        if (suffix.substring(1).equals("jpg") || suffix.substring(1).equals("png")||suffix.substring(1).equals("JPG") || suffix.substring(1).equals("PNG")){
            String imgPath = appFileUploadBase+"headimg/"+saveFileName;
            String imgURL = "headimg/"+saveFileName;
            File saveFile = new File(imgPath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
                return error("文件保存出错");
            }
            Map<String,String> map = new HashMap();
            map.put("imgURL",imgURL);
            map.put("imgPath", appFileBase+imgURL);
            return success(map);
        }else {
            return error("文件格式错误");
        }

    }

}
