package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.user.model.CspUserInfo;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.service.ChargeService;
import cn.medcn.user.service.CspUserService;
import cn.medcn.user.service.UserFluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 9:31 2017/11/15
 */
@Controller
@RequestMapping(value = "/csp/order")
public class FluxOrderController extends BaseController{

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private UserFluxService userFluxService;

    @Autowired
    private CspUserService cspUserService;

    /**
     * 获取流量订单表
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name = "获取流量订单表")
    public String OrderList(Pageable pageable,Model model,String tradeId,Integer type,String startTime,String endTime){
         float allMoney= chargeService.selectAllMoney();
         //美元
        float UsMoney= chargeService.selectAllMoneyByPapal();
        model.addAttribute("rmb",allMoney-UsMoney);
        model.addAttribute("usd",UsMoney);
        if (type == null){
            type = 0;
        }
        model.addAttribute("type",type);
        if (!StringUtils.isEmpty(tradeId)){
            pageable.getParams().put("tradeId",tradeId);
            model.addAttribute("tradeId",tradeId);
        }
        pageable.put("type",type);
        MyPage<FluxOrder> page = null;
        Float queryMoney = null;
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
            pageable.getParams().put("startTime",startTime);
            pageable.getParams().put("endTime",endTime);
            if (type == 0){
                queryMoney = chargeService.findOrderListByTime(startTime,endTime);
            }else {
                queryMoney = chargeService.findOrderListByTimeUs(startTime,endTime);
            }
            model.addAttribute("startTime",startTime);
            model.addAttribute("endTime",endTime);

        }

        if (type == 0){
            page= chargeService.findFluxOrderList(pageable);
        }else {
            page= chargeService.findFluxOrderListByUs(pageable);
        }
        model.addAttribute("queryMoney",queryMoney == null ? 0:queryMoney);
        model.addAttribute("search",true);
        model.addAttribute("page",page);
        return "/fluxOrder/fluxOrderList";
    }

    /**
     * 查看个人订单列表
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/check")
    @Log(name = "查看个人订单列表")
    public String checkOrder(@RequestParam(value = "id", required = true) String id,Model model){
            FluxOrder fluxOrder=chargeService.selectByPrimaryKey(id);
            String userId = fluxOrder.getUserId();

            UserFlux userFlux = userFluxService.selectByPrimaryKey(userId);
            CspUserInfo cspUserInfo = cspUserService.selectByPrimaryKey(userId);
            Integer flux = userFlux.getFlux();
            model.addAttribute("nickName",cspUserInfo.getNickName());
            model.addAttribute("flux",flux);
            model.addAttribute("fluxOrder",fluxOrder);
            return "/fluxOrder/fluxOrderInfo";
    }

    /**
     * 修改流量
     * @param fluxOrder
     * @return
     */
    @RequestMapping(value = "/update")
    @Log(name = "修改流量")
    public String updateOrder(FluxOrder fluxOrder,RedirectAttributes redirectAttributes){
        if (fluxOrder != null){
            String id = fluxOrder.getId();
            FluxOrder order = chargeService.selectByPrimaryKey(id);
            order.setFlux(fluxOrder.getFlux());
            addFlashMessage(redirectAttributes,"修改成功");
            chargeService.updateOrderAndUserFlux(order);
        }else {
            addErrorFlashMessage(redirectAttributes,"修改失败");
        }

        return "redirect:/csp/order/list";
    }

    /**
     * 关闭订单
     * @param id
     * @return
     */
   @RequestMapping(value = "/close")
   @Log(name = "关闭订单")
    public String closeOrder(@RequestParam(value = "id", required = true) String id,RedirectAttributes redirectAttributes){
       FluxOrder fluxOrder = chargeService.selectByPrimaryKey(id);
       if (fluxOrder != null){
           fluxOrder.setState(2);
           addFlashMessage(redirectAttributes,"删除成功");
           chargeService.updateByPrimaryKey(fluxOrder);
       }else {
           addErrorFlashMessage(redirectAttributes,"删除失败");
       }
       return "redirect:/csp/order/list";
   }

}
