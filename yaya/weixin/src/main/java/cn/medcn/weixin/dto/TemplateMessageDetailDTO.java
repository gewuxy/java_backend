package cn.medcn.weixin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/8/7.
 */
@Data
@NoArgsConstructor
public class TemplateMessageDetailDTO {

    private String value;

    private String color = "#173177";


    public TemplateMessageDetailDTO(String value){
        this.value = value;
    }
}
