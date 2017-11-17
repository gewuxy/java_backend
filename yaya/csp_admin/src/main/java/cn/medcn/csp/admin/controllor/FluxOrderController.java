package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.admin.dto.FluxOrderDTO;
import cn.medcn.csp.admin.service.FluxOrderService;
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

import java.util.List;

/**
 * @Author：jianliang
 * @Date: Creat in 9:31 2017/11/15
 */
@Controller
@RequestMapping(value = "/csp/order")
public class FluxOrderController extends BaseController{

    @Autowired
    private FluxOrderService fluxOrderService;

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
    public String OrderList(Pageable pageable,Model model,String tradeId){
        if (!StringUtils.isEmpty(tradeId)){
            pageable.getParams().put("tradeId",tradeId);
            model.addAttribute("tradeId",tradeId);
        }
        MyPage<FluxOrderDTO> page= fluxOrderService.findFluxOrderList(pageable);
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
    public String checkOrder(@RequestParam(value = "id", required = true) String id,Model model){
            List<FluxOrderDTO> fluxOrderDTO=fluxOrderService.selectOrderInfo(id);
            Integer flux = 0;
            for (FluxOrderDTO order: fluxOrderDTO) {
            String userId = order.getUserId();
            System.out.println(userId);
            UserFlux userFlux = userFluxService.selectByPrimaryKey(userId);
            flux = userFlux.getFlux();
            System.out.println(flux);
        }
            model.addAttribute("flux",flux);
            model.addAttribute("fluxOrderList",fluxOrderDTO);
            return "/fluxOrder/fluxOrderInfo";
    }

    /**
     * 修改流量
     * @param fluxOrderDTO
     * @return
     */
    @RequestMapping(value = "/update")
    public String updateOrder(FluxOrderDTO fluxOrderDTO){
        //TODO 数据库中的id存在问题
        String id = fluxOrderDTO.getId();
        FluxOrder fluxOrder = chargeService.selectByPrimaryKey(id);
        fluxOrder.setFlux(fluxOrderDTO.getFlux());
        chargeService.updateOrderAndUserFlux(fluxOrder);
        return "redirect:/csp/order/list";
    }

   @RequestMapping(value = "/close")
    public String closeOrder(@RequestParam(value = "id", required = true) String id){
       FluxOrder fluxOrder = chargeService.selectByPrimaryKey(id);
       fluxOrder.setState(2);
       chargeService.updateByPrimaryKey(fluxOrder);
       return "redirect:/csp/order/list";
   }

}
