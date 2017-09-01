package cn.medcn.weixin.dto;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 微信模板消息DTO类
 * Created by lixuan on 2017/8/7.
 */
public class TemplateMessageDTO {

    public static final String DATA_KEY_FIRST = "first";

    public static final String DATA_KEY_REMARK = "remark";

    public static final String DATA_KEY_KEYWORD_SUFFIX = "keyword";

    private String touser;

    private String template_id;

    private String url;

    private Map<String, TemplateMessageDetailDTO> data;

    public String getTouser() {
        return touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, TemplateMessageDetailDTO> getData() {
        return data;
    }


    private TemplateMessageDTO(Builder builder){
        this.url = builder.url;
        this.touser = builder.touser;
        this.template_id = builder.template_id;
        this.data = builder.data;


    }


    public static class Builder{
        private String touser;

        private String template_id;

        private String url;

        private String first;

        private String remark;

        private String[] values;

        private Map<String, TemplateMessageDetailDTO> data = Maps.newHashMap();

        public Builder(String touser){
            this.touser = touser;
        }

        public Builder template_id(String val){
            this.template_id = val;
            return this;
        }

        public Builder url(String val){
            this.url = val;
            return this;
        }

        public Builder first(String val){
            this.first = val;
            data.put(DATA_KEY_FIRST, new TemplateMessageDetailDTO(val));
            return this;
        }

        public Builder remark(String val){
            this.remark = val;
            data.put(DATA_KEY_REMARK, new TemplateMessageDetailDTO(val));
            return this;
        }

        public Builder values(String...values){
            for (int index = 1; index <= values.length; index++){
                data.put(DATA_KEY_KEYWORD_SUFFIX+index, new TemplateMessageDetailDTO(values[index - 1]));
            }
            return this;
        }


        public TemplateMessageDTO build(){
            return new TemplateMessageDTO(this);
        }
    }
}
