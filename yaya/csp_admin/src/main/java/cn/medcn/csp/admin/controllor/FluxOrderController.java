package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.user.model.FluxOrder;
import cn.medcn.user.model.UserFlux;
import cn.medcn.user.service.ChargeService;
import cn.medcn.user.service.UserFluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 获取流量订单表
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/list")
    @Log(name = "获取流量订单表")
    public String OrderList(Pageable pageable,Model model,String tradeId){
        if (!StringUtils.isEmpty(tradeId)){
            pageable.getParams().put("tradeId",tradeId);
            model.addAttribute("tradeId",tradeId);
        }
        MyPage<FluxOrder> page= chargeService.findFluxOrderList(pageable);
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
            List<FluxOrder> fluxOrder=chargeService.selectOrderInfo(id);
            Integer flux = 0;
            for (FluxOrder order: fluxOrder) {
            String userId = order.getUserId();
            UserFlux userFlux = userFluxService.selectByPrimaryKey(userId);
            flux = userFlux.getFlux();
        }
            model.addAttribute("flux",flux);
            model.addAttribute("fluxOrderList",fluxOrder);
            return "/fluxOrder/fluxOrderInfo";
    }

    /**
     * 修改流量
     * @param fluxOrder
     * @return
     */
    @RequestMapping(value = "/update")
    @Log(name = "修改流量")
    public String updateOrder(FluxOrder fluxOrder){
        String id = fluxOrder.getId();
        FluxOrder order = chargeService.selectByPrimaryKey(id);
        order.setFlux(fluxOrder.getFlux());
        chargeService.updateOrderAndUserFlux(order);
        return "redirect:/csp/order/list";
    }

    /**
     * 关闭订单
     * @param id
     * @return
     */
   @RequestMapping(value = "/close")
   @Log(name = "关闭订单")
    public String closeOrder(@RequestParam(value = "id", required = true) String id){
       FluxOrder fluxOrder = chargeService.selectByPrimaryKey(id);
       fluxOrder.setState(2);
       chargeService.updateByPrimaryKey(fluxOrder);
       return "redirect:/csp/order/list";
   }

}
