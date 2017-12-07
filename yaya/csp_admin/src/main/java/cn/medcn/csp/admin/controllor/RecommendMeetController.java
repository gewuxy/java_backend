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
        List<Meet> meets= meetService.selectAllMeet();
        model.addAttribute("meets",meets);
        return "recommendMeet/recommendMeetInfo";
    }

    /**
     * 弹窗页面
     * @param model
     * @param pageable
     * @param meetName
     * @return
     */
    @RequestMapping(value = "/queryMeet")
    public String queryMeet(Model model,Pageable pageable,String meetName){
        if (!StringUtils.isEmpty(meetName)) {
            pageable.getParams().put("meetName", meetName);
            model.addAttribute("meetName", meetName);
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
        Meet meet = meetService.selectByPrimaryKey(id);
        model.addAttribute("meet",meet);
        String json = JSON.toJSONString(meet);
        model.addAttribute("json",json);
        return json;
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
    public String insertRecommendMeet(Recommend recommend, MeetTuijianDTO meetTuijianDTO,Short state,String meetId,String headimg){
        System.out.println(meetId);
        Meet meet = meetService.selectByPrimaryKey(meetId);
        recommend.setResourceId(meetId);
        Lecturer lecturer =new Lecturer();
        lecturer.setMeetId(meetId);
        lecturer.setHeadimg(headimg);
        lecturer.setDepart(meetTuijianDTO.getLecturerDepart());
        lecturer.setHospital(meetTuijianDTO.getLecturerHos());
        lecturer.setName(meetTuijianDTO.getLecturer());
        lecturer.setTitle(meetTuijianDTO.getLecturerTile());
        meetLecturerService.insert(lecturer);
        meet.setLecturerId(lecturer.getId());
        recommendMeetService.insert(recommend);
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
        String headimg = lecturer.getHeadimg();
        model.addAttribute("imgPath",appFileBase+headimg);
        model.addAttribute("headimg",headimg);
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
    public String updateRecommendMeet(Recommend recommend,Lecturer lecturer,String meetName,Short state,Integer lecturerId){
        String meetId = recommend.getResourceId();
        Meet meet = meetService.selectByPrimaryKey(meetId);
        meet.setMeetName(meetName);
        lecturer.setId(lecturerId);
        lecturer.setMeetId(meetId);
        meetLecturerService.updateByPrimaryKey(lecturer);
        recommendMeetService.updateByPrimaryKey(recommend);
        meet.setLecturerId(lecturer.getId());
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
       recommend.setRecFlag(false);
       recommendMeetService.updateByPrimaryKey(recommend);
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
