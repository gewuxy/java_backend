package cn.medcn.common.ctrl;

import cn.medcn.common.Constants;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.supports.BeanValidator;
import cn.medcn.common.utils.APIUtils;
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

}
