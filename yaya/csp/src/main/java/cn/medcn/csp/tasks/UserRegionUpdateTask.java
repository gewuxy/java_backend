package cn.medcn.csp.tasks;

import cn.medcn.user.dto.UserRegionDTO;
import cn.medcn.user.service.CspUserService;

/**
 * 修改用户地理位置线程
 * Created by lixuan on 2017/12/19.
 */
public class UserRegionUpdateTask implements Runnable {

    private CspUserService cspUserService;

    public UserRegionUpdateTask(CspUserService cspUserService){
        this.cspUserService = cspUserService;
    }

    @Override
    public void run() {
        while(true) {
            UserRegionDTO region = cspUserService.brPopUserRegion();
            if (region != null) {
                cspUserService.updateUserRegion(region);
            }
        }
    }
}
