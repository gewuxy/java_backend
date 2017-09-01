package cn.medcn.search.util;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.search.service.BaseSearchService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by weilong on 2017/7/21.
 * 创建一个线程独立运行，根据传入的core加载整个数据库中的索引字段到solr服务器
 */
public class SolrReloadThread extends Thread {

    //当BaseSearchService.getReloadSchedule()值为stopReloadSign的值时更新将终止
    public final String stopReloadSign;
    private SearchUtil searchUtil;
    private BaseService baseService;
    private BaseSearchService baseSearchService;
    private final String core;

    public SolrReloadThread(SearchUtil searchUtil, BaseService baseService,
                            BaseSearchService baseSearchService,
                            String core, String stopReloadSign) {
        this.searchUtil = searchUtil;
        this.baseService = baseService;
        this.baseSearchService = baseSearchService;
        this.core = core;
        this.stopReloadSign = stopReloadSign;
    }

    // @Override
    public void run() {
        //super.run();
        int counting = 0;
        long total = 0;
        int pageNum = 1;
        int pageSize = 10;//设定每次传递10条记录到solr检索工具
        boolean exit = false;
        Pageable pageable = new Pageable();
        pageable.setPageSize(pageSize);
        Object entity = baseSearchService.getEntity();
        pageable.setEntity(entity);
        //Class clazz=entity.getClass();
        MyPage<Object> myPage;
        try {
            //初始化更新前的状态
            baseSearchService.setReloadSchedule(core + "数据更新中，总数：未知;已加载：0;");
            while (!exit) {
                pageable.setPageNum(pageNum);
                myPage = baseService.page(pageable);
                List<Object> entityList = myPage.getDataList();
                ArrayList<Object> entitySearchDTOList = baseSearchService.getEntitySearchDtoList(entityList);
                searchUtil.addList(entitySearchDTOList, core);

                pageNum++;
                counting += myPage.getDataList().size();
                total = myPage.getTotal();
                if (total <= counting) {
                    exit = true;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    baseSearchService.setReloadSchedule(core + "更新完成，时间：" + formatter.format(new Date()) + "。总数：" + total + ";已加载：" + counting + ";");
                    //System.out.println("------------" + core + "更新到solr已完成。");
                } else {
                    //提供关闭更新的方法。
                    if (stopReloadSign.equals(baseSearchService.getReloadSchedule())) {
                        baseSearchService.setReloadSchedule(core + "加载已被终止!总数：" + total + ";已加载：" + counting + ";");
                        exit = true;
                    } else {
                        baseSearchService.setReloadSchedule(core + "数据更新中，总数：" + total + ";已加载：" + counting + ";");
                    }
                }
            }
        } catch (Exception e) {
            baseSearchService.setReloadSchedule(core + "更新出现了状况已终止;总数：" + (total == 0 ? "未知" : total) + ";已加载数：" + counting + ";");
        }

    }
}
