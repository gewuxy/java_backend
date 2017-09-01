package cn.medcn.weixin.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.common.utils.HttpUtils;
import cn.medcn.weixin.config.WeixinConfig;
import cn.medcn.weixin.dao.WXMenuDAO;
import cn.medcn.weixin.model.WXMenu;
import cn.medcn.weixin.service.WXMenuService;
import cn.medcn.weixin.service.WXTokenService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.mapper.Mapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/7/28.
 */
@Service
public class WXMenuServiceImpl extends WXBaseServiceImpl<WXMenu> implements WXMenuService {

    @Autowired
    protected WXMenuDAO wxMenuDAO;

    @Autowired
    protected WXTokenService wxTokenService;

    @Override
    protected WXTokenService getWxTokenService() {
        return wxTokenService;
    }

    @Value("${app.yaya.base}")
    protected String appBase;

    @Override
    public Mapper<WXMenu> getBaseMapper() {
        return wxMenuDAO;
    }

    @Override
    public void deleteMenu() {
        String accessToken = wxTokenService.getGlobalAccessToken();
        Map<String, Object> params = Maps.newHashMap();
        wxGet(WeixinConfig.MENU_DELETE_URL, accessToken, params);
    }

    @Override
    public void createMenu() {
        String accessToken = wxTokenService.getGlobalAccessToken();
        List<WXMenu> list = findSortedMenus();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("button", JSONArray.toJSON(list));
        wxPostJSON(WeixinConfig.MENU_CREATE_URL, accessToken, jsonObject);
    }

    @Override
    public WXMenu findMenu() {
        String accessToken = wxTokenService.getGlobalAccessToken();
        Map<String, Object> params = Maps.newHashMap();
        String response = wxGet(WeixinConfig.MENU_SELECT_URL, accessToken, params);
        WXMenu menu = JSON.parseObject(response, WXMenu.class);
        return menu;
    }


    @Override
    public List<WXMenu> findSortedMenus() {
        List<WXMenu> list = wxMenuDAO.findAll();
        List<WXMenu> soredList = Lists.newArrayList();
        String urlPrefix = "https://app.medyaya.cn/";
        for (WXMenu menu:list){
            if (menu.getParentId() == null || menu.getParentId() == 0){
                if(!CheckUtils.isEmpty(menu.getUrl())  && !menu.getUrl().startsWith("http")){
                    menu.setUrl(urlPrefix+menu.getUrl());
                }
                soredList.add(menu);
            }
        }
        for(WXMenu menu:list){
            for (WXMenu parentMenu : soredList){
                if (menu.getParentId().intValue() == parentMenu.getId().intValue()){
                    if(!CheckUtils.isEmpty(menu.getUrl()) && !menu.getUrl().startsWith("http")){
                        menu.setUrl(urlPrefix+menu.getUrl());
                    }
                    parentMenu.getSub_button().add(menu);
                }
            }
        }
        return soredList;
    }



}
