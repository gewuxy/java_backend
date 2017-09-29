package cn.medcn.csp;

import cn.medcn.common.utils.LocalUtils;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by lixuan on 2017/9/29.
 */
public class LocaleResourceViewResolver extends InternalResourceViewResolver {

    public LocaleResourceViewResolver(String prefix, String suffix){
        super();
        this.setPrefix(prefix + LocalUtils.getLocalStr());
        this.setSuffix(suffix);
    }

    public LocaleResourceViewResolver() {
    }
}
