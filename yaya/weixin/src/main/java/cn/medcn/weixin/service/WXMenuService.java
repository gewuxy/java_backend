package cn.medcn.weixin.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.weixin.model.WXMenu;

import java.util.List;

/**
 * Created by lixuan on 2017/7/18.
 */
public interface WXMenuService extends BaseService<WXMenu>{

    void deleteMenu();

    void createMenu();

    WXMenu findMenu();

    List<WXMenu> findSortedMenus();
}
