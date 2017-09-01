package cn.medcn.api.controller;

import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.data.dto.DataCategoryDTO;
import cn.medcn.data.dto.DataFileDTO;
import cn.medcn.data.dto.FileCategoryDTO;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import cn.medcn.data.service.CategoryService;
import cn.medcn.data.service.DataFavoriteService;
import cn.medcn.data.service.DataFileService;
import cn.medcn.user.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuan on 2017/5/19.
 */
@Controller
@RequestMapping(value="/api/data")
public class DataController extends BaseController{

    @Autowired
    private DataFileService dataFileService;

    @Autowired
    private DataFavoriteService dataFavoriteService;

    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${app.yaya.base}")
    private String appYaYaBase;

    private static final String THOMSON_CATEGORY_ID = "17051816403418566342";
    private static final String MEDICINE_CATEGORY_ID = "17051816413005881649";
    private static final String CLINICAL_CATEGORY_ID = "17051816422178368282";


    /**
     * 获取文件夹或文件
     * 第一级目录不用传preId,根据type区分。type=1 代表汤森,type=2代表药品目录，type=3代表临床
     * @param preId 父级id
     * @param type
     * @param leaf  下级是否为文件
     * @return
     */
    @RequestMapping("/data_category")
    @ResponseBody
    public String getDataCategory(String preId,Integer type,Boolean leaf,Pageable pageable){

        if( type != Constants.NUMBER_ONE  && type != Constants.NUMBER_TWO && type != Constants.NUMBER_THREE ){
            return error("数据类型不正确");
        }

        MyPage<FileCategoryDTO> myPage = null;

        if(type == DataFile.DataType.THOMSON.getType()){//汤森
            preId = THOMSON_CATEGORY_ID;
        }
        if(type == DataFile.DataType.MEDICINE.getType() && StringUtils.isEmpty(preId)) {  //药品一级
            preId = MEDICINE_CATEGORY_ID;
        }
        if(type == DataFile.DataType.CLINICAL.getType() && StringUtils.isEmpty(preId)){ //临床一级
            preId = CLINICAL_CATEGORY_ID;
        }

        pageable.put("preId",preId);
        if(leaf == true || type == DataFile.DataType.THOMSON.getType()){ //下一级为文件
            myPage = dataFileService.findFile(pageable);
            List<FileCategoryDTO> list = myPage.getDataList();
            //添加打开方式和判断是否为文件
            list = addIsFileAndOpenType(list,true);

            return success(list);
        }else if(leaf == false){  //下一级为文件夹
            myPage = dataFileService.findCategory(pageable);
            return success(myPage.getDataList());
        }else {
            return error("参数错误");
        }
    }



//    /**
//     * 根据父级id查找文件夹列表
//     * @param preId
//     * @param type 第一级目录不用传preId,根据type区分。type=1代表药品目录，type=2代表临床
//     * @return
//     */
//    @RequestMapping(value="/category")
//    @ResponseBody
//    public String getCategoryList(String preId,Integer type){
//        if(StringUtils.isEmpty(preId)){
//            if(type == Constants.NUMBER_ZERO){
//
//            }
//
//            if(type == Constants.NUMBER_ONE){
//                preId = MEDICINE_CATEGORY_ID;
//            }else if(type == Constants.NUMBER_TWO){
//                preId = CLINICAL_CATEGORY_ID;
//            }else {
//                return error("type不正确");
//            }
//        }
//        List<DataCategoryDTO> list = categoryService.findCategories(preId);
//        return APIUtils.success(list);
//    }

//
//    /**
//     * 根据categoryId获取该文件夹下的所有文件
//     * @param categoryId
//     * @return
//     */
//    @RequestMapping(value = "/all_file")
//    @ResponseBody
//    public String all(String categoryId,Pageable pageable){
//        if(StringUtils.isEmpty(categoryId)){
//            categoryId = THOMSON_CATEGORY_ID;
//        }
//        pageable.getParams().put("categoryId", categoryId);
//        MyPage<DataFile> myPage = dataFileService.findByPage(pageable);
//        initFilePath(myPage.getDataList());
//        return APIUtils.success(myPage.getDataList());
//    }


    /**
     *根据文章id查找文章内容,是否收藏
     * @param dataFileId
     * @return
     */
    @RequestMapping(value = "/data_detail")
    @ResponseBody
    public String getDataDetail(String dataFileId){
        if(StringUtils.isEmpty(dataFileId)){
            return error("文章ID不能为空");
        }


        //查询是否收藏
        DataFileDTO dto = new DataFileDTO();
        Favorite favorite = new Favorite();
        favorite.setResourceId(dataFileId);
        favorite.setUserId(SecurityUtils.getCurrentUserInfo().getId());
        int count = dataFavoriteService.selectCount(favorite);
        if (count == Constants.NUMBER_ZERO) {
            dto.setFavorite(false);
        }else{
            dto.setFavorite(true);
        }

    return success(dto);
    }





    /**
     * 根据关键字和type搜索药品或临床指南，type为2时表示药品，type为3时表示临床
     * @param keyword
     * @param type
     * @return
     */
    @RequestMapping(value ="/data_search",method = RequestMethod.POST)
    @ResponseBody
    public String search(String keyword,Integer type,Pageable pageable){
        if(StringUtils.isEmpty(keyword)){
            return error("请输入关键字");
        }
        MyPage<FileCategoryDTO> myPage = null;
        pageable.put("keyword",keyword);
        if(type == DataFile.DataType.MEDICINE.getType()){  //查询药品
            pageable.put("rootCategory",MEDICINE_CATEGORY_ID);
        }else if(type == DataFile.DataType.CLINICAL.getType()){ //查询临床指南
            pageable.put("rootCategory",CLINICAL_CATEGORY_ID);
        }else{
            return error("查找类型不正确");
        }
        myPage  = dataFileService.selectMedicineListByKeyword(pageable);
        List<FileCategoryDTO> list = myPage.getDataList();
        list = addIsFileAndOpenType(list,true);

        return success(list);


    }





    private List<FileCategoryDTO> addIsFileAndOpenType(List<FileCategoryDTO> list,Boolean isFile){
        if(!StringUtils.isEmpty(list)){
            for(FileCategoryDTO dto:list){
                if(isFile != null){
                    dto.setIsFile(isFile);
                }
                //首选html打开方式
                if(StringUtils.isEmpty(dto.getHtmlPath())){
                    if(!StringUtils.isEmpty(dto.getFilePath())){
                        dto.setFilePath(appFileBase+dto.getFilePath());
                    }
                    dto.setOpenType(DataFile.OpenType.BY_PDF.getType());
                }else{
                    //有htmlPath,用html的方式打开
                    dto.setOpenType(DataFile.OpenType.BY_HTML.getType());
                    dto.setHtmlPath(appFileBase + dto.getHtmlPath());
                }
            }
        }
            return list;

    }

//    @RequestMapping(value="/view")
//    public String thomsonView(String id, Model model) throws SystemException{
//        if(StringUtils.isEmpty(id)){
//            throw new SystemException("请求参数错误");
//        }
//        DataFile file = dataFileService.selectByPrimaryKey(id);
//        if(file == null){
//            throw new SystemException("数据不存在");
//        }
//        if(StringUtils.isEmpty(file.getFilePath())){
//            throw new SystemException("数据内容错误");
//        }
//        file.setFilePath(appFileBase+file.getFilePath());
//        model.addAttribute("dataFile", file);
//        return "/dataList/";
//    }
}
