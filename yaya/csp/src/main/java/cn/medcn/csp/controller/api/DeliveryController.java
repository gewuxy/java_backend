package cn.medcn.csp.controller.api;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.csp.security.Principal;
import cn.medcn.csp.security.SecurityUtils;
import cn.medcn.meet.dto.CourseDeliveryDTO;
import cn.medcn.meet.dto.DeliveryAccepterDTO;
import cn.medcn.meet.dto.DeliveryHistoryDTO;
import cn.medcn.meet.model.AudioCourseDetail;
import cn.medcn.meet.model.CourseDelivery;
import cn.medcn.meet.service.CourseDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lixuan on 2017/9/26.
 */
@Controller
@RequestMapping(value = "/api/delivery")
public class DeliveryController extends BaseController {

    @Autowired
    protected CourseDeliveryService courseDeliveryService;

    @Value("${app.file.base}")
    protected String fileBase;

    @RequestMapping(value = "/paginate")
    @ResponseBody
    public String paginate(Pageable pageable, Integer acceptId) {
//        if (acceptId != null) {
//            pageable.put("acceptId", acceptId);
//        }
        pageable.put("authorId", SecurityUtils.get().getId());
        MyPage<DeliveryHistoryDTO> page = courseDeliveryService.findDeliveryHistory(pageable);
        DeliveryHistoryDTO.splitAvatarUrl(page.getDataList(),fileBase);
        return success(page.getDataList());
    }

    @RequestMapping(value = "/user/detail")
    @ResponseBody
    public String detail(Integer acceptId) {
        Principal principal = SecurityUtils.get();
        List<CourseDeliveryDTO> list = courseDeliveryService.findByAcceptId(acceptId, principal.getId());
        CourseDeliveryDTO.splitCoverUrl(list,fileBase);
        return success(list);
    }


    @RequestMapping(value = "/push")
    @ResponseBody
    public String push(Integer courseId, Integer[] acceptIds) {
        Principal principal = SecurityUtils.get();
        try {
            courseDeliveryService.executeDelivery(courseId, acceptIds, principal.getId());
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }


    @RequestMapping(value = "/acceptors")
    @ResponseBody
    public String acceptors(Pageable pageable) {
        MyPage<DeliveryAccepterDTO> page = courseDeliveryService.findAcceptors(pageable);
        for (DeliveryAccepterDTO dto : page.getDataList()) {
            if (CheckUtils.isNotEmpty(dto.getAvatar())) {
                dto.setAvatar(fileBase + dto.getAvatar());
            }
        }
        return success(page.getDataList());
    }
}
