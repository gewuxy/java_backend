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

}
