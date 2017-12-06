package cn.medcn.csp.admin.controllor;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.user.model.AppUser;
import cn.medcn.user.model.Banner;
import cn.medcn.user.service.AppUserService;
import cn.medcn.user.service.BannerService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：jianliang
 * @Date: Create in 14:23 2017/11/23
 */
@Controller
@RequestMapping(value = "/yaya/banner")
public class BannerController extends BaseController{

    @Autowired
    private BannerService bannerService;

    @Autowired
    private AppUserService appUserService;

    @Value("${app.file.upload.base}")
    protected String appFileUploadBase;

    @Value("${app.file.base}")
    protected String appFileBase;

    /**
     * Banner列表页
     * @param pageable
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name = "Banner列表页")
    public String bannerList(Pageable pageable, Model model){
        MyPage<Banner> myPage = bannerService.findBannerList(pageable);
        model.addAttribute("page",myPage);
        model.addAttribute("pubName", Constants.DEFAULT_HOS_NAME);
        return "/banner/bannerList";
    }

    /**
     * 跳转编辑页面
     * @return
     */
    @RequestMapping(value = "/edit")
    @Log(name = "跳转编辑页面")
    public String editBannerInfo(HttpServletRequest request){
        return "/banner/bannerInfo";
    }

    /**
     * 图片上传
     * @param file
     * @param model
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    @Log(name = "图片上传")
    public String uploadImg(MultipartFile file,Model model){
        if (file == null) {
            return error("不能上传空文件");
        }
        String filename = file.getOriginalFilename();
        String suffix = file.getOriginalFilename().substring(filename.lastIndexOf("."));
        String saveFileName = StringUtils.nowStr()+suffix;
        if (suffix.substring(1).equals("jpg") || suffix.substring(1).equals("png")){
            String imgPath = appFileUploadBase+"banner/"+saveFileName;
            String urlPath = "banner/"+saveFileName;
            File saveFile = new File(imgPath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
                return APIUtils.error("文件保存出错");
            }
            String absolutelyPath = appFileBase + "banner/"+saveFileName;
            Map<String,String> map = new HashMap();
            map.put("saveFileName",absolutelyPath);
            map.put("urlPath",urlPath);
            map.put("src", appFileBase + "banner/"+saveFileName);
            map.put("title", saveFileName);
            return success(map);
        }else {
            return error("文件格式错误");
        }

    }

    /**
     * 新建Banner
     * @param banner
     * @return
     */
    @RequestMapping(value = "/insert")
    @Log(name = "新建Banner")
    public String insertBanner(Banner banner,RedirectAttributes redirectAttributes){
        if (banner != null){
            banner.setCreateTime(new Date());
            banner.setId(UUIDUtil.getNowStringID());
            bannerService.insert(banner);
            addFlashMessage(redirectAttributes,"添加成功");
        }else {
            addErrorFlashMessage(redirectAttributes,"添加失败");
        }

        return "redirect:/yaya/banner/list";
    }

    /**
     * 查看Banner
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/check")
    @Log(name = "查看Banner")
    public String checkBanner(@RequestParam(value = "id", required = true) String id,Model model){
        Banner banner = bannerService.selectByPrimaryKey(id);
        String imgUrl = banner.getImageUrl();
        String absolutelyPath = appFileBase +imgUrl;
        model.addAttribute("absolutelyPath",absolutelyPath);
        model.addAttribute("banner",banner);
        return "/banner/bannerInfoEdit";
    }


    /**
     * 修改Banner
     * @param banner
     * @return
     */
    @RequestMapping(value = "/update")
    @Log(name = "修改Banner")
    public String updateBanner(Banner banner,RedirectAttributes redirectAttributes){
        if(banner != null){
            banner.setCreateTime(new Date());
            bannerService.updateByPrimaryKeySelective(banner);
            addFlashMessage(redirectAttributes,"修改成功");
        }else {
            addErrorFlashMessage(redirectAttributes,"修改失败");
        }
        return "redirect:/yaya/banner/list";
    }

    /**
     * 关闭，开启Banner
     * @param id
     * @return
     */
    @RequestMapping(value = "/close")
    @Log(name = "关闭，开启Banner")
    public String closeBanner(@RequestParam(value = "id", required = true) String id){
        Banner banner = bannerService.selectByPrimaryKey(id);
        if (banner.getActive()==true){
            banner.setActive(false);
        }else {
            banner.setActive(true);
        }
        bannerService.updateByPrimaryKeySelective(banner);
        return "redirect:/yaya/banner/list";
    }
}
