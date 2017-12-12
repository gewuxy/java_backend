package cn.medcn.user.service;

import cn.medcn.common.service.BaseService;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.model.FluxOrder;
import com.paypal.api.payments.Payment;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;

/**
 * Created by LiuLP on 2017/9/26.
 */
public interface EmailTempService extends BaseService<EmailTemplate> {
    //内容举报邮件模板ID
    int REPORT_TEMPLATE_ID = 1001;

    EmailTemplate getTemplate(String localStr,Integer tempType,Integer useType);
}
