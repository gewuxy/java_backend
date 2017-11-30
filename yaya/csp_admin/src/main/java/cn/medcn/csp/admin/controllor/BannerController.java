package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.supports.Validate;
import cn.medcn.common.utils.APIUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Value("${csp.file.upload.base}")
    protected String cspFileUploadBase;

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
        return "/banner/bannerList";
    }

    /**
     * 跳转编辑页面
     * @return
     */
    @RequestMapping(value = "/edit")
    @Log(name = "跳转编辑页面")
    public String editBannerInfo(HttpServletRequest request){
        Integer pubFlag = 1;
        List<AppUser> appUser = appUserService.selectByPub(pubFlag);
        request.setAttribute("appUser",appUser);
        return "/banner/bannerInfo";
    }

    /**
     * 图片上传
     * @param uploadFile
     * @param model
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    @Log(name = "图片上传")
    public String uploadImg(MultipartFile uploadFile,Model model){
        if (uploadFile == null) {
            return APIUtils.error("不能上传空文件");
        }
        String filename = uploadFile.getOriginalFilename();
        String suffix = uploadFile.getOriginalFilename().substring(filename.lastIndexOf("."));
        String saveFileName = System.currentTimeMillis()+suffix;
        File saveFile = new File("D:"+cspFileUploadBase + saveFileName);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            uploadFile.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return APIUtils.error("文件保存出错");
        }
        return saveFileName;
    }

    /**
     * 新建Banner
     * @param banner
     * @return
     */
    @RequestMapping(value = "/insert")
    @Log(name = "新建Banner")
    public String insertBanner(Banner banner){
        banner.setCreateTime(new Date());
        banner.setId(UUIDUtil.getNowStringID());
        bannerService.insert(banner);
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
        Integer pubFlag = 1;
        List<AppUser> appUsers = appUserService.selectByPub(pubFlag);
        Banner banner = bannerService.selectByPrimaryKey(id);
        String imgName = banner.getImageUrl().substring(14);
        model.addAttribute("appUsers",appUsers);
        model.addAttribute("imgName",imgName);
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
    public String updateBanner(Banner banner){
        banner.setCreateTime(new Date());
        bannerService.updateByPrimaryKeySelective(banner);
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
