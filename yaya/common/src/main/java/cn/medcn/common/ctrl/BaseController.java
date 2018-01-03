package cn.medcn.common.ctrl;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.supports.BeanValidator;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.common.utils.SpringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by lixuan on 2017/4/17.
 */
public class BaseController {

    protected String messageKey = "message";

    protected void addFlashMessage(RedirectAttributes redirectAttributes, String message){
        redirectAttributes.addFlashAttribute(APIUtils.MESSAGE_KEY, message);
        redirectAttributes.addFlashAttribute(APIUtils.CODE_KEY, APIUtils.SUCCESS_CODE);
    }

    protected void addErrorFlashMessage(RedirectAttributes redirectAttributes, String message){
        redirectAttributes.addFlashAttribute(APIUtils.MESSAGE_KEY, message);
        redirectAttributes.addFlashAttribute(APIUtils.CODE_KEY, APIUtils.ERROR_CODE);
    }

    /**
     * 获取国际化信息
     * @param key
     * @return
     */
    public String local(String key){
        return SpringUtils.getMessage(key);
    }

    /**
     * 获取国际化信息
     * @param key
     * @return
     */
    public String local(String key, Object[] obj){
        return SpringUtils.getMessage(key, obj);
    }


    protected boolean isAbroad(){
        return LocalUtils.isAbroad();
    }


    protected String success(Object object){
        return APIUtils.success(object);
    }

    protected String success(){
        return APIUtils.success();
    }

    protected String error(){
        return APIUtils.error();
    }

    protected String error(String msg){
        return APIUtils.error(msg);
    }

    protected String error(String errorCode , String msg){
        return APIUtils.error(errorCode, msg);
    }

    @InitBinder
    protected void init(WebDataBinder dataBinder){
        dataBinder.setValidator(new BeanValidator());
    }

    /**
     * 处理验证结果
     * @param bindingResult
     * @throws SystemException
     */
    protected void resolveValidateResult(BindingResult bindingResult) throws SystemException{
        if (bindingResult.hasErrors()){
            ObjectError objectError = bindingResult.getAllErrors().get(Constants.NUMBER_ZERO);
            throw new SystemException(objectError.getDefaultMessage());
        }
    }

    /**
     * 适合需要跳转到国际化视图的请求使用
     * @param view
     * @return
     */
    protected String localeView(String view){
        //return LocalUtils.getLocalStr() + view;
        //这里统一做修改 跳转到原视图 国际化在页面处理
        return view;
    }

}
