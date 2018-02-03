package cn.medcn.csp.controller.api;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.meet.dto.ActivityGuideDTO;
import cn.medcn.meet.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lixuan
 * @Date: 2018/2/3
 * @Description: 活动接口
 */
@RequestMapping(value = "/api/activity")
public class ActivityController extends CspBaseController {

    @Autowired
    protected AudioService audioService;

    @Value("${app.file.base}")
    protected String appFileBase;

    /**
     * 暂时只是查询出信后引导会议
     * 以后需要改成分页
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public String list(){
        List<ActivityGuideDTO> list = new ArrayList<>();
        ActivityGuideDTO dto = audioService.findActivityCourse(LocalUtils.isAbroad()
                ? AudioService.ABROAD_GUIDE_SOURCE_ID : AudioService.GUIDE_SOURCE_ID);
        if (dto != null && CheckUtils.isNotEmpty(dto.getCoverUrl())) {
            dto.setCoverUrl(appFileBase + dto.getCoverUrl());
        }
        list.add(dto);
        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        return success(list);
    }
}
