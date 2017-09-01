package cn.medcn.transfer.model.readonly;

import java.util.Date;

/**
 * Created by Liuchangling on 2017/6/21.
 */
public class PubUserMaterialReadOnly {
    private Long id;
    private Integer pub_user_id; // 公众号ID
    private String file_name; //文件名称
    private String file_type; //文件类型
    private String file_size; //文件大小
    private Date upload_time; //上传时间
    private Integer pay_credits; //支付象数
    private String file_url; //文件地址
    private Integer pay_type; //支付类型  0:免费  1:奖励  2:支付

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPub_user_id() {
        return pub_user_id;
    }

    public void setPub_user_id(Integer pub_user_id) {
        this.pub_user_id = pub_user_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public Integer getPay_credits() {
        return pay_credits;
    }

    public void setPay_credits(Integer pay_credits) {
        this.pay_credits = pay_credits;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }
}
