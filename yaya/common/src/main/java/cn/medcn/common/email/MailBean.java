package cn.medcn.common.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lixuan on 2017/1/10.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailBean {
    private String from;
    private String fromName;
    private String[] toEmails;
    private String subject;
    private String context;

    /**
     * 邮箱模板内容
     */
    public enum MailTemplate{
        REGISTER(0, "register"), // 注册
        FIND_PWD(1, "pwdRest"), // 找回密码
        BIND(2, "bindEmail"); // 绑定

        private Integer labelId;
        private String label;

        public Integer getLabelId() {
            return labelId;
        }

        public void setLabelId(Integer labelId) {
            this.labelId = labelId;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        MailTemplate(Integer labelId, String label){
            this.labelId = labelId;
            this.label = label;
        }
    }
}
