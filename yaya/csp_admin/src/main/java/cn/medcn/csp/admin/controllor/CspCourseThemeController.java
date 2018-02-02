package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.supports.MediaInfo;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.FileUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.meet.dao.BackgroundImageDAO;
import cn.medcn.meet.dao.BackgroundMusicDAO;
import cn.medcn.meet.model.AudioCourseTheme;
import cn.medcn.meet.model.BackgroundImage;
import cn.medcn.meet.model.BackgroundMusic;
import cn.medcn.meet.service.CourseThemeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * 讲本主题 背景图片、背景音乐 控制器
 * Created by Liuchangling on 2018/2/1.
 */
@Controller
@RequestMapping(value = "/csp/course/theme")
public class CspCourseThemeController extends BaseController{
    @Value("${app.file.base}")
    protected String fileBase;
    @Value("${app.file.upload.base}")
    protected String fileUploadBase;

    @Autowired
    protected CourseThemeService courseThemeService;
    @Autowired
    protected FileUploadService fileUploadService;

    @Autowired
    protected BackgroundImageDAO backgroundImageDAO;

    @Autowired
    protected BackgroundMusicDAO backgroundMusicDAO;

    /**
     * 背景图片列表
     * @param pageable
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/image/list")
    public String backgroundImageList(Pageable pageable, String keyword, Model model){
        if (CheckUtils.isNotEmpty(keyword)) {
            pageable.put("keyword", keyword);
        }
        MyPage<BackgroundImage> page = courseThemeService.findImagePageList(pageable);
        BackgroundImage.HandelImgUrl(page.getDataList(), fileBase);
        model.addAttribute("page", page);
        return "/theme/imageList";
    }


    /**
     * 背景音乐列表
     * @param pageable
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping(value = "/music/list")
    public String backgroundMusicList(Pageable pageable, String keyword, Model model){
        if (CheckUtils.isNotEmpty(keyword)) {
            pageable.put("name", keyword);
        }
        MyPage<BackgroundMusic> page = courseThemeService.findMusicPageList(pageable);
        BackgroundMusic.HandelMusicUrl(page.getDataList(), fileBase);
        model.addAttribute("page", page);
        return "/theme/musicList";
    }

    /**
     * 编辑图片
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/image/edit")
    @RequiresPermissions("theme:image:edit")
    @Log(name = "编辑图片")
    public String editImage(Integer id, Model model){
        if (id != null) {
            BackgroundImage image = backgroundImageDAO.selectByPrimaryKey(id);
            model.addAttribute("image", image);
        }
        model.addAttribute("fileBase", fileBase);
        return "/theme/editImage";
    }

    /**
     * 编辑音乐
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/music/edit")
    @RequiresPermissions("theme:music:edit")
    @Log(name = "编辑音乐")
    public String editMusic(Integer id, Model model){
        if (id != null) {
            BackgroundMusic music = backgroundMusicDAO.selectByPrimaryKey(id);
            model.addAttribute("music", music);
        }
        model.addAttribute("fileBase", fileBase);
        return "/theme/editMusic";
    }


    /**
     * 保存/修改背景图片或音乐
     * @param type
     * @param image
     * @param music
     * @return
     */
    @RequestMapping(value = "/save")
    @Log(name = "保存/修改背景图片或音乐")
    public String saveOrUpdateTheme(Integer type,BackgroundImage image, BackgroundMusic music,
                                    Model model, RedirectAttributes redirectAttributes){

        String imageUrl = "/csp/course/theme/image/list";
        String musicUrl = "/csp/course/theme/music/list";
        if (type == AudioCourseTheme.ImageMusic.IMAGE.ordinal()){
            boolean isAdd = image.getId() == null;
            if (isAdd && image != null) {
                // 检查图片是否已经添加过
                BackgroundImage condition = new BackgroundImage();
                condition.setImgName(image.getImgName());
                BackgroundImage img = backgroundImageDAO.selectOne(condition);
                if (img != null) {
                    model.addAttribute("error", "图片[" + image.getImgName() + "] 已经添加过.");
                    model.addAttribute("image", image);
                    return "/theme/editImage";
                } else {
                    backgroundImageDAO.insert(image);
                    addFlashMessage(redirectAttributes, "添加背景图片成功");
                    return "redirect:" + imageUrl;
                }
            } else {
                backgroundImageDAO.updateByPrimaryKeySelective(image);
                addFlashMessage(redirectAttributes,"修改背景图片成功");
                return  "redirect:" + imageUrl;
            }

        } else if (type == AudioCourseTheme.ImageMusic.MUSIC.ordinal()) {
            boolean isAdd = music.getId() == null;
            if (isAdd && music != null) {
                // 检查音乐是否已经添加过
                BackgroundMusic condition = new BackgroundMusic();
                condition.setName(music.getName());
                BackgroundMusic msc = backgroundMusicDAO.selectOne(condition);
                if (msc != null) {
                    model.addAttribute("error", "音乐[" + msc.getName() + "] 已经添加过.");
                    model.addAttribute("music", music);
                    return "/theme/editMusic";
                } else {
                    backgroundMusicDAO.insert(music);
                    addFlashMessage(redirectAttributes,"添加背景音乐成功");
                    return  "redirect:" + musicUrl;
                }
            } else {
                backgroundMusicDAO.updateByPrimaryKeySelective(music);
                addFlashMessage(redirectAttributes,"修改背景音乐成功");
                return  "redirect:" + musicUrl;
            }
        }
        return null;
    }


    /**
     * 上传背景图片或音乐
     * @param file
     * type {@link AudioCourseTheme.ImageMusic}
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    @Log(name = "上传背景图片或音乐")
    public String uploadImageOrMusic(@RequestParam MultipartFile file, Integer type) {
        FileUploadResult result = null;
        String fileName = file.getOriginalFilename();
        int duration = 0;
        String size = null;
        try {
            if (type == null) {
                type = AudioCourseTheme.ImageMusic.IMAGE.ordinal();
            }
            if (type == AudioCourseTheme.ImageMusic.IMAGE.ordinal()) {
                result = fileUploadService.upload(file, FilePath.BACKGROUND_IMAGE.path);
            } else if (type == AudioCourseTheme.ImageMusic.MUSIC.ordinal()) {
                result = fileUploadService.upload(file, FilePath.BACKGROUND_MUSIC.path);
                MediaInfo info = FileUtils.parseAudioMediaInfo(fileUploadBase + result.getRelativePath());
                duration = info.getDuration();
            }
            fileName = fileName.substring(0, fileName.lastIndexOf("."));

            long fileSize = file.getSize();
            size = FileUtils.fileSize2String(fileSize);
            if (size.endsWith("MB") || size.endsWith("KB") || size.endsWith("B")) {
                size = size.replace("MB","").replace("KB","").replace("B","");
            }
        } catch (SystemException e) {
            return error(e.getMessage());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("fileName", fileName);
        map.put("size", size);
        map.put("duration", duration);
        return success(map);
    }
}
