package cn.medcn.api.controller;

import cn.medcn.api.utils.SecurityUtils;
import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.data.dto.FileCategoryDTO;
import cn.medcn.data.model.DataFile;
import cn.medcn.data.model.DataFileDetail;
import cn.medcn.data.service.DataFileService;
import cn.medcn.meet.dto.MeetFavoriteDTO;
import cn.medcn.meet.dto.MeetInfoDTO;
import cn.medcn.meet.service.MeetService;
import cn.medcn.user.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by LiuLP on 2017/5/19/019.
 */
@RequestMapping("/api")
@Controller
public class FavoriteController extends BaseController{

    @Autowired
    private MeetService meetService;

    @Autowired
    private DataFileService dataFileService;


    @Value("${app.file.base}")
    private String appFileBase;

    @Value("${app.yaya.base}")
    private String appYaYaBase;


    /**
     * type为0或为空时表示资源为会议,type为1时表示汤森，type为2时表示药品目录，type为3时表示临床指南
     * @param pageable
     * @param type
     * @return
     */
    @RequestMapping("/my_favorite")
    @ResponseBody
    public String myFavorite(Pageable pageable,Integer type){
        if(type == null){
            type = Constants.NUMBER_ZERO;
        }
        if(type != Constants.NUMBER_ZERO && type != Constants.NUMBER_ONE && type != Constants.NUMBER_TWO &&
                type != Constants.NUMBER_THREE){
            return error("type值不正确");
        }

        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        pageable.getParams().put("userId",userId);
        //会议
        if(type == Constants.NUMBER_ZERO){
            pageable.getParams().put("resourceType",Constants.NUMBER_ZERO);
            MyPage<MeetInfoDTO> myPage =  meetService.findMyFavorite(pageable);
            for(MeetInfoDTO dto:myPage.getDataList()){
                dto.setHeadimg(appFileBase+dto.getHeadimg());
            }
            return success(myPage.getDataList());
        }else {
            MyPage<FileCategoryDTO> myPage = null;
            if(type ==  Constants.NUMBER_ONE){ //汤森
                pageable.getParams().put("resourceType",Constants.NUMBER_ONE);
            }else if(type ==  Constants.NUMBER_TWO){ //药品
                pageable.getParams().put("resourceType",Constants.NUMBER_TWO);
            }else{//临床
                pageable.getParams().put("resourceType",Constants.NUMBER_THREE);
            }
            myPage = dataFileService.findFavorite(pageable);
            List<FileCategoryDTO> list = myPage.getDataList();
            list = addIsFileAndOpenType(list,null);
            return success(list);
        }

    }


//    /**
//     * 根据dataFileId获取文章信息
//     * @param dataFileId
//     * @return
//     */
//    @RequestMapping("/favorite_detail")
//    @ResponseBody
//    public String getDetail(String dataFileId){
//        if(StringUtils.isEmpty(dataFileId)){
//            return APIUtils.error("数据ID不能为空");
//        }
//        List<DataFileDetail> detailList = dataFileService.selectDataFileDetail(dataFileId);
//        return success(detailList);
//    }



    /**
     * 收藏或者取消收藏 会议，汤森，药品目录，临床指南
     * @param resourceId
     * @param type type为空或0时，表示会议，type为1时表示汤森，type为2时表示药品目录，type为3时表示临床指南
     * @return
     */
    @RequestMapping("/set_favorite_status")
    @ResponseBody
    public String setFavoriteStatus(String resourceId,Integer type){
        if(resourceId == null){
            return error("resourceId不能为空");
        }
        if(type != null && type != Constants.NUMBER_ZERO && type != Constants.NUMBER_ONE
                && type != Constants.NUMBER_TWO && type != Constants.NUMBER_THREE){
            return error("typeId不正确");
        }
        Integer userId = SecurityUtils.getCurrentUserInfo().getId();
        Favorite dataFavorite = new Favorite();
        dataFavorite.setUserId(userId);
        dataFavorite.setResourceId(resourceId);
        if(type == null || type == Constants.NUMBER_ZERO){  //会议
            dataFavorite.setResourceType(Constants.NUMBER_ZERO);
        }else if(type == Constants.NUMBER_ONE){ //汤森
            dataFavorite.setResourceType(Constants.NUMBER_ONE);
        }else if(type == Constants.NUMBER_TWO){  //药品目录
            dataFavorite.setResourceType(Constants.NUMBER_TWO);
        }else if(type == Constants.NUMBER_THREE){
            dataFavorite.setResourceType(Constants.NUMBER_THREE);
        }
        meetService.updateFavoriteStatus(dataFavorite);
        return success();
    }


    private List<FileCategoryDTO> addIsFileAndOpenType(List<FileCategoryDTO> list,Boolean isFile){
        if(!StringUtils.isEmpty(list)){
            for(FileCategoryDTO dto:list){
                if(isFile != null){
                    dto.setIsFile(isFile);
                }
                if(StringUtils.isEmpty(dto.getHtmlPath())){
                    if(!StringUtils.isEmpty(dto.getFilePath())){
                        dto.setFilePath(appFileBase+dto.getFilePath());
                    }
                    dto.setOpenType(Constants.NUMBER_ONE);
                }else{
                    //有htmlPath,用html的方式打开
                    dto.setOpenType(Constants.NUMBER_THREE);
                    dto.setHtmlPath(appFileBase + dto.getHtmlPath());
                }
            }
        }

        return list;
    }



}
