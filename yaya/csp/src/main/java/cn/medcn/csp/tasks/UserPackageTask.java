package cn.medcn.csp.tasks;

import cn.medcn.common.utils.CheckUtils;
import cn.medcn.meet.service.AudioService;
import cn.medcn.user.model.CspUserPackage;
import cn.medcn.user.service.CspUserPackageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户套餐版本定时变更
 * Created by Liuchangling on 2017/12/12.
 */

public class UserPackageTask implements Runnable {
    private static final Log log = LogFactory.getLog(UserPackageTask.class);

    @Autowired
    private CspUserPackageService userPackageService;

    @Autowired
    private AudioService audioService;

    public UserPackageTask (CspUserPackageService userPackageService, AudioService audioService) {
        this.userPackageService = userPackageService;
        this.audioService = audioService;
    }

    @Override
    public void run() {
        List<CspUserPackage> userPackageList = userPackageService.findCspUserPackageList();
        if (!CheckUtils.isEmpty(userPackageList)) {
            // 用户套餐过期 降级为标准版
            userPackageService.doModifyUserPackage(userPackageList);
            log.info("UserPackageTask doModifyUserPackage user size: "+ userPackageList.size());

            for (CspUserPackage userPackage : userPackageList) {
                // 给套餐过期用户的会议 加锁（发布最早的3个会议不需要加锁）
                audioService.doModifyAudioCourse(userPackage.getUserId());
                log.info("UserPackageTask doModifyAudioCourse .....");
            }
        }

    }
}
