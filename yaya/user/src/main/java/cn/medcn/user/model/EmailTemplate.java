package cn.medcn.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by LiuLP on 2017/4/24.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_email_template")
public class EmailTemplate implements Serializable{

    @Id
    private Integer id;

    //模板内容
    private String content;

    //邮件标题
   private String subject;

   //模板类型。1表示注册，2表示找回密码，3表示绑定
   private Integer tempType;

   //语言类型
   private String langType;

   //使用对象，1表示csp 2表示官网
   private Integer useType;

   //发送者邮箱
   private String sender;
   //发送者名称
   private String senderName;

   public enum Type{

       REGISTER(1,"注册"), // 注册
       FIND_PWD(2,"找回密码"), // 找回密码
       BIND(3,"绑定");// 绑定


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

       Type(Integer labelId, String label){
           this.labelId = labelId;
           this.label = label;
       }
   }

    public enum UseType{
        CSP(1,"CSP"), // csp
        OFFICIAL(2,"官网"); // 官网

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
        UseType(Integer labelId, String label){
            this.labelId = labelId;
            this.label = label;
        }
    }

}
