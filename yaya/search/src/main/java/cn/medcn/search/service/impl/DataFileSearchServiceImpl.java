package cn.medcn.search.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import cn.medcn.data.service.DataFileService;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.search.dto.DataFileSearchDTO;
import cn.medcn.search.service.BaseSearchService;
import cn.medcn.search.service.DataFileSearchService;
import cn.medcn.search.util.SearchUtil;
//import cn.medcn.search.util.SolrLoadThread;
//import cn.medcn.search.util.SolrReloadThread;
import cn.medcn.search.util.PDFUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by weilong on 2017/8/7.
 */
@Service
public class DataFileSearchServiceImpl implements DataFileSearchService {
    @Autowired
    private SearchUtil searchUtil;
    @Autowired
    private DataFileService dataFileService;

    @Value("${solr.dataFile.SOLR_CORE}")
    private String DATA_FILE_CORE;
    @Value("${app.file.upload.base}")
    private String uploadPath;

    //保存重新更新检索库的进度信息
    private static String reloadSchedule = "";
    //上面的进度信息等于以下值时停止更新，即：STOP_DATA_FILE_RELOAD.equals(reloadSchedule)
    private static final String STOP_DATA_FILE_RELOAD = "STOP_DATA_FILE_RELOAD";

    @Override
    public void updateById(String id) throws SystemException {
        if (id == null) {
            throw new SystemException("要更新到索引库的内容有误(null)");
        }
        DataFile dataFile = dataFileService.selectByPrimaryKey(id);
        if (dataFile == null) {
            throw new SystemException("抱歉，您要找的内容已经不在了。");
        }
        List<DataFileDetail> dataFileDetailList = dataFileService.selectDetailByDataFileId(dataFile.getId());
        DataFileSearchDTO dataFileSearchDTO = createSearchDTO(dataFile, dataFileDetailList, uploadPath);
        try {
            searchUtil.add(dataFileSearchDTO, DATA_FILE_CORE);
        } catch (Exception e) {
            throw new SystemException(DATA_FILE_CORE + "内容更新到solr索引库失败");
        }
    }

    @Override
    public void updateByIdList(List<String> idList) throws SystemException {
        if (idList == null) {
            throw new SystemException("要更新到索引库的" + DATA_FILE_CORE + "内容有误(null)");
        }
        for (String id : idList) {
            updateById(id);
        }
    }

    @Override
    public void deleteById(String id) throws SystemException {
        if (id == null) {
            throw new SystemException("要删除索引库的" + DATA_FILE_CORE + "内容时参数有误(null)");
        }
        try {
            searchUtil.deleteById(id, DATA_FILE_CORE);
        } catch (Exception e) {
            throw new SystemException("删除索引库的" + DATA_FILE_CORE + "内容失败");
        }
    }

    @Override
    public void deleteByIdList(List<String> idList) throws SystemException {
        if (idList == null) {
            throw new SystemException("要删除索引库的" + DATA_FILE_CORE + "内容时参数有误(null)");
        }
        try {
            searchUtil.deleteByIdList(idList, DATA_FILE_CORE);
        } catch (Exception e) {
            throw new SystemException("删除索引库的" + DATA_FILE_CORE + "内容失败");
        }
    }

    @Override
    public void empty() throws SystemException {
        try {
            searchUtil.emptyCore(DATA_FILE_CORE);
        } catch (Exception e) {
            throw new SystemException("清空" + DATA_FILE_CORE + "索引库的内容失败");
        }
    }

    @Override
    public QueryResponse search(SolrQuery solrQuery) throws SystemException {
        try {
            return searchUtil.search(solrQuery, DATA_FILE_CORE);
        } catch (Exception e) {
            throw new SystemException("T_T 很抱歉！没有找到数据！(solr err)");
        }
    }

    @Override
    public void reload() {
        //如果在更新中就不用再开线程更新了。
        if(reloadSchedule.equals(DATA_FILE_CORE+"数据更新中")){
            return;
        }
        new DataFileSolrLoadThread(searchUtil, dataFileService,
                this,
                DATA_FILE_CORE, STOP_DATA_FILE_RELOAD, uploadPath).start();
    }

    @Override
    public String getReloadSchedule() {
        return reloadSchedule;
    }

    @Override
    public void setReloadSchedule(String schedule) {
        this.reloadSchedule = schedule;
    }

    @Override
    public void stopReload() {
        reloadSchedule = STOP_DATA_FILE_RELOAD;
    }

    @Override
    public void update(DataFile dataFile, List<DataFileDetail> dataFileDetailList) throws SystemException {
        if (dataFile == null) {
            throw new SystemException("抱歉，您要找的内容已经不在了。");
        }
        DataFileSearchDTO dataFileSearchDTO = createSearchDTO(dataFile, dataFileDetailList, uploadPath);
        try {
            searchUtil.add(dataFileSearchDTO, DATA_FILE_CORE);
        } catch (Exception e) {
            throw new SystemException(DATA_FILE_CORE + "内容更新到solr索引库失败");
        }
    }

    @Override
    public Object getEntity() {
        return new DataFile();
    }
    //    @Override
//    public String getCore() {
//        return core;
//    }

//    @Override
//    public EntitySearchDTO getEntitySearchDto() {
//        return null;
//    }

    public DataFileSearchDTO createSearchDTO(DataFile dataFile, List<DataFileDetail> list, String appFileBase) {
        DataFileSearchDTO dataFileSearchDTO = new DataFileSearchDTO();
        dataFileSearchDTO.setAuthed(dataFile.getAuthed() == null ? null : dataFile.getAuthed() ? "true" : "false");
        dataFileSearchDTO.setAuthor(dataFile.getAuthor());
        dataFileSearchDTO.setCategoryId(dataFile.getCategoryId());
        dataFileSearchDTO.setContent(dataFile.getContent());
        dataFileSearchDTO.setDataFrom(dataFile.getDataFrom());
        dataFileSearchDTO.setDownLoadCost(dataFile.getDownLoadCost() == null ? null : "" + dataFile.getDownLoadCost());
        dataFileSearchDTO.setFilePath(dataFile.getFilePath());
        dataFileSearchDTO.setFileSize(dataFile.getFileSize() == null ? null : "" + dataFile.getFileSize());
        dataFileSearchDTO.setId(dataFile.getId());
        dataFileSearchDTO.setRootCategory(dataFile.getRootCategory());
        dataFileSearchDTO.setSummary(dataFile.getSummary());
        dataFileSearchDTO.setTitle(dataFile.getTitle());
        dataFileSearchDTO.setHistoryId(dataFile.getHistoryId());
        dataFileSearchDTO.setImg(dataFile.getImg());
        dataFileSearchDTO.setKeywords(dataFile.getKeywords());
        if (dataFile.getUpdateDate() == null) {
            dataFileSearchDTO.setUpdateDate(null);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            //dateString最终要的是这种格式："yyyy-MM-ddTHH:mm:ssZ"，而formatter不认识"T,Z"
            String dateString = formatter.format(dataFile.getUpdateDate());
            StringBuffer strbuf = new StringBuffer(dateString);
            strbuf.replace(10, 11, "T");
            strbuf.replace(19, 20, "Z");
            dateString = strbuf.toString();
            dataFileSearchDTO.setUpdateDate(dateString);
        }

        //详情表的内容
        String detail = "";
        if (list != null && list.size() > 0) {
            detail = list.get(0).getDetailValue();
            for (int i = 1; i < list.size(); i++) {
                detail += "，" + list.get(i).getDetailValue();
            }
        }
        dataFileSearchDTO.setDetail(detail);

        //文件的内容
        //(need to do)url转本地路径
        String path = appFileBase + dataFile.getFilePath();
        try {
            String fileContent = PDFUtil.getText(path);
            dataFileSearchDTO.setFileContent(fileContent);
        } catch (IOException e) {
        }

        return dataFileSearchDTO;
    }

    public Object searchDTO2entity(DataFileSearchDTO dataFileSearchDTO) {
        DataFile dataFile = new DataFile();
        dataFile.setAuthed(dataFileSearchDTO.getAuthed() == null ? null : "true".equals(dataFileSearchDTO.getAuthed()));
        dataFile.setAuthor(dataFileSearchDTO.getAuthor());
        dataFile.setCategoryId(dataFileSearchDTO.getCategoryId());
        dataFile.setContent(dataFileSearchDTO.getContent());
        dataFile.setDataFrom(dataFileSearchDTO.getDataFrom());
        dataFile.setDownLoadCost(dataFileSearchDTO.getAuthed() == null ? null : new Integer(dataFileSearchDTO.getDownLoadCost()));
        dataFile.setFilePath(dataFileSearchDTO.getFilePath());
        dataFile.setFileSize(dataFileSearchDTO.getAuthed() == null ? null : new Float(dataFileSearchDTO.getFileSize()));
        dataFile.setId(dataFileSearchDTO.getId());
        dataFile.setRootCategory(dataFileSearchDTO.getRootCategory());
        dataFile.setSummary(dataFileSearchDTO.getSummary());
        dataFile.setTitle(dataFileSearchDTO.getTitle());
        dataFile.setHistoryId(dataFileSearchDTO.getHistoryId());
        if (dataFileSearchDTO.getUpdateDate() == null) {
            dataFile.setUpdateDate(null);
        } else {
            String dateString = dataFileSearchDTO.getUpdateDate();
            //拿出来的dateString格式是这样的："yyyy-MM-ddTHH:mm:ssZ"，而formatter不认识"T,Z"
            StringBuffer strbuf = new StringBuffer(dateString);
            strbuf.replace(10, 11, " ");
            strbuf.replace(19, 20, " ");
            dateString = strbuf.toString();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            ParsePosition pos = new ParsePosition(8);
            Date date = formatter.parse(dateString, pos);
            dataFile.setUpdateDate(date);
        }
        return dataFileSearchDTO;
    }

    @Override
    public ArrayList<Object> getEntitySearchDtoList(List<Object> list) {
        return null;
    }

    /**
     * 创建一个线程独立运行，根据传入的core加载整个数据库中的索引字段到solr服务器
     */
    private class DataFileSolrLoadThread extends Thread {

        //当BaseSearchService.getReloadSchedule()值为stopReloadSign的值时更新将终止
        public final String stopReloadSign;
        private SearchUtil searchUtil;
        private DataFileService dataFileService;
        private BaseSearchService baseSearchService;
        private String core;
        private String uploadPath;

        public DataFileSolrLoadThread(SearchUtil searchUtil, DataFileService dataFileService,
                                      BaseSearchService baseSearchService,
                                      String core, String stopReloadSign, String uploadPath) {
            this.searchUtil = searchUtil;
            this.dataFileService = dataFileService;
            this.baseSearchService = baseSearchService;
            this.core = core;
            this.stopReloadSign = stopReloadSign;
            this.uploadPath = uploadPath;
        }

        // @Override
        public void run() {
            //super.run();
            int counting = 0;
            long total = 0;
            int pageNum = 1;
            int pageSize = 5;//设定每次传递5条记录到solr检索工具
            boolean exit = false;
            Pageable pageable = new Pageable();
            pageable.setPageSize(pageSize);
            Object entity = baseSearchService.getEntity();
            pageable.setEntity(entity);
            //Class clazz=entity.getClass();
            MyPage<DataFile> myPage;
            try {
                //初始化更新前的状态
                baseSearchService.setReloadSchedule(core + "数据更新中，总数：未知;已加载：0;");
                while (!exit) {
                    pageable.setPageNum(pageNum);
                    myPage = dataFileService.page(pageable);
                    List<DataFile> dataFileList = myPage.getDataList();
                    ArrayList<Object> entitySearchDTOList = new ArrayList<Object>();
                    for (DataFile dataFile : dataFileList) {
                        List<DataFileDetail> dataFileDetailList = dataFileService.selectDetailByDataFileId(dataFile.getId());
                        DataFileSearchDTO dataFileSearchDTO = createSearchDTO(dataFile, dataFileDetailList, uploadPath);
                        entitySearchDTOList.add(dataFileSearchDTO);
                    }
                    searchUtil.addList(entitySearchDTOList, core);

                    pageNum++;
                    counting += myPage.getDataList().size();
                    total = myPage.getTotal();
                    if (total <= counting) {
                        exit = true;
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        baseSearchService.setReloadSchedule(core + "更新完成，时间：" + formatter.format(new Date()) + "。总数：" + total + ";已加载：" + counting + ";");
                        //System.out.println("------------" + core + "更新到solr已完成。");
                    }
                    {
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


}
