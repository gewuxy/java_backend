package cn.medcn.user.dto;

import cn.medcn.common.utils.CalendarUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 输出到页面的用户新增数据
 * Created by LiuLP on 2017/12/21/021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CspNewlyEChartsDTO {

    private String[] dateCount;

    private Integer[] weiBoCount;

    private Integer[] weiXinCount;

    private Integer[] facebookCount;

    private Integer[] twitterCount;

    private Integer[] emailCount;

    private Integer[] mobileCount;

    private Integer[] yaYaCount;

    public static CspNewlyEChartsDTO build(List<CspNewlyStaticDTO> staticList,Integer grain,Date startTime,Date endTime) throws ParseException {

        CspNewlyEChartsDTO chartsDTO = new CspNewlyEChartsDTO();
        List<Date> list = null;
        if(grain == CspNewlyStaticDTO.Grain.DAY.ordinal()){
            list = CalendarUtils.getAllDateList(startTime,endTime);
        }else if(grain == CspNewlyStaticDTO.Grain.WEEK.ordinal()){
            list = CalendarUtils.getWeekFirstDateList(startTime,endTime);
        }else if(grain == CspNewlyStaticDTO.Grain.MONTH.ordinal()){
            list = CalendarUtils.getMonthFirstDateList(startTime,endTime);
        }else if(grain == CspNewlyStaticDTO.Grain.QUARTER.ordinal()){
            list = CalendarUtils.getQuarterFirstDateList(startTime,endTime);
        }else if(grain == CspNewlyStaticDTO.Grain.YEAR.ordinal()){
            list = CalendarUtils.getYearFirstDateList(startTime,endTime);
        }else {
            return null;
        }

        initEChartsDTO(chartsDTO,list);
        buildUserRegisterCount(staticList, grain, list,chartsDTO);
        return chartsDTO;
    }

    private static void initEChartsDTO(CspNewlyEChartsDTO dto,List<Date> list) throws ParseException {
        String[] dateCount = dto.getDateCount(list);
        dto.setDateCount(dateCount);
        dto.setEmailCount(new Integer[list.size()]);
        dto.setFacebookCount(new Integer[list.size()]);
        dto.setMobileCount(new Integer[list.size()]);
        dto.setTwitterCount(new Integer[list.size()]);
        dto.setWeiBoCount(new Integer[list.size()]);
        dto.setWeiXinCount(new Integer[list.size()]);
        dto.setYaYaCount(new Integer[list.size()]);
    }


    private static void buildUserRegisterCount(List<CspNewlyStaticDTO> staticList, Integer grain, List<Date> list,CspNewlyEChartsDTO chartsDTO) {
        for(CspNewlyStaticDTO dto : staticList){
            for(int i=0;i<list.size();i++){
                if(grain == CspNewlyStaticDTO.Grain.DAY.ordinal()){
                    if(dto.getRegisterDate().getTime() == list.get(i).getTime()){
                        setCount(chartsDTO, dto, i);
                    }
                }else{
                    if(i == list.size()-1){
                        if(dto.getRegisterDate().getTime() >= list.get(i).getTime()){
                            setCount(chartsDTO, dto, i);
                        }
                    }else if(dto.getRegisterDate().getTime() >= list.get(i).getTime() && dto.getRegisterDate().getTime() < list.get(i+1).getTime()){
                        setCount(chartsDTO, dto, i);
                    }

                }
            }
        }
    }

    private static void setCount(CspNewlyEChartsDTO chartsDTO, CspNewlyStaticDTO dto, int i) {
        chartsDTO.getWeiBoCount()[i] = dto.getWeiBoCount();
        chartsDTO.getWeiXinCount()[i] = dto.getWeiXinCount();
        chartsDTO.getFacebookCount()[i] = dto.getFacebookCount();
        chartsDTO.getTwitterCount()[i] = dto.getTwitterCount();
        chartsDTO.getEmailCount()[i] = dto.getEmailCount();
        chartsDTO.getMobileCount()[i] = dto.getMobileCount();
        chartsDTO.getYaYaCount()[i] = dto.getYaYaCount();
    }


    /**
     * 格式化日期
     * @param list
     * @return
     * @throws ParseException
     */
    public  String[] getDateCount(List<Date> list) throws ParseException {

        if(list != null){
            String[] dateCount = new String[list.size()];
            Calendar cal = Calendar.getInstance();
            for(int i=0;i<list.size();i++){
                cal.setTime(list.get(i));
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                dateCount[i] = month + "-" + day;
            }
            return dateCount;
        }
        return null;

    }
}
