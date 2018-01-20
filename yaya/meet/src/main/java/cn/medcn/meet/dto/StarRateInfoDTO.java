package cn.medcn.meet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixuan on 2018/1/19.
 */
@Data
@NoArgsConstructor
public class StarRateInfoDTO implements Serializable {

    protected String title;

    protected String info;

    protected Boolean starRateFlag;

    protected StarRateResultDTO multipleResult;

    protected List<StarRateResultDTO> detailList;


    public StarRateResultDTO getMultipleResult(){
        if (multipleResult != null) {
            return multipleResult;
        } else if (detailList == null || detailList.size() == 0){
            return null;
        } else {
            StarRateResultDTO result = new StarRateResultDTO();
            float avgScore = 0f;
            for(StarRateResultDTO dto : detailList){
                avgScore += dto.getAvgScore();
            }
            result.setAvgScore(avgScore / detailList.size());
            result.setScoreCount(detailList.size());
            return result;
        }
    }
}
