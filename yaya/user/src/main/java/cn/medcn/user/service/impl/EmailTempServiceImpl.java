package cn.medcn.user.service.impl;

import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.user.dao.AdvertDAO;
import cn.medcn.user.dao.BannerDAO;
import cn.medcn.user.dao.EmailTemplateDAO;
import cn.medcn.user.model.Advert;
import cn.medcn.user.model.Banner;
import cn.medcn.user.model.EmailTemplate;
import cn.medcn.user.service.AdvertService;
import cn.medcn.user.service.EmailTempService;
import com.github.abel533.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuLP on 2017/4/20.
 */
@Service
public class EmailTempServiceImpl extends BaseServiceImpl<EmailTemplate> implements EmailTempService{

    @Autowired
    protected EmailTemplateDAO templateDAO;

    @Override
    public Mapper<EmailTemplate> getBaseMapper() {
        return templateDAO;
    }

    @Override
    public EmailTemplate getTemplate(String localStr, Integer tempType) {
        EmailTemplate template = new EmailTemplate();
        template.setLangType(localStr);
        template.setTempType(tempType);
        return selectOne(template);
    }
}
