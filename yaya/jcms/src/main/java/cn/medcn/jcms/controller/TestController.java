package cn.medcn.jcms.controller;

import cn.medcn.common.Constants;
import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.service.OpenOfficeService;
import cn.medcn.common.utils.APIUtils;
import cn.medcn.jcms.dto.ValidateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixuan on 2017/7/11.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController extends BaseController{

    @Autowired
    private OpenOfficeService openOfficeService;


    @RequestMapping(value = "/ppt2pdf")
    @ResponseBody
    public String ppt2pdf(){
        String pptFilePath = "/medcn/ppts/1.pptx";
        String pdfFilePath = "/medcn/pdfs/";
//        String pptFilePath = "D:/lixuan/test/1.pptx";
//        String pdfFilePath = "D:/lixuan/test/1.pdf";
        openOfficeService.convert2PDF(pptFilePath, pdfFilePath);
        return APIUtils.success();
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public String save(@Validated ValidateBean bean, BindingResult bindingResult){
        try {
            resolveValidateResult(bindingResult);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success();
    }

    @RequestMapping(value = "/tosave")
    public String tosave(){
        return "/test/saveForm";
    }
}
