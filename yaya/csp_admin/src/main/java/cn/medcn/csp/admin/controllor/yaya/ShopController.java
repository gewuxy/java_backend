package cn.medcn.csp.admin.controllor.yaya;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.goods.model.Goods;
import cn.medcn.goods.model.Order;
import cn.medcn.goods.service.OrderService;
import cn.medcn.goods.service.ShopService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Liuchangling on 2018/1/22.
 * 象城商品、订单管理控制器
 */
@Controller
@RequestMapping("/yaya/shop")
public class ShopController extends BaseController {
    @Autowired
    protected ShopService shopService;

    @Autowired
    protected OrderService orderService;

    /**
     * 商品列表
     * @param pageable
     * @param name
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String findShopList(Pageable pageable, String name, Model model) {
        if (CheckUtils.isNotEmpty(name)) {
            pageable.put("name", name);
            model.addAttribute("name", name);
        }
        MyPage<Goods> page = shopService.findShopGoodsList(pageable);
        model.addAttribute("page", page);
        return "/yaya/shop/list";
    }

    /**
     * 订单列表
     * @param pageable
     * @param searchKey  订单信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/order/list")
    public String findOrderList(Pageable pageable, String searchKey, Integer status, Model model) {
        if (CheckUtils.isNotEmpty(searchKey)) {
            pageable.put("orderNo", searchKey);
            model.addAttribute("searchKey", searchKey);
        }
        if (status != null) {
            pageable.put("status", status);
            model.addAttribute("status", status);
        }
        MyPage<Order> page = orderService.findOrderList(pageable);
        model.addAttribute("page", page);
        return "/yaya/shop/orderList";
    }


    /**
     * 修改订单信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/order/edit")
    @RequiresPermissions("yaya:shopOrder:edit")
    public String updateOrderInfo(Integer id, Model model) {
        if (id != null) {
           Order order = orderService.selectByPrimaryKey(id);
           model.addAttribute("order", order);
        }
        return "/yaya/shop/editOrderForm";
    }

    /**
     * 修改订单
     * @param order
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/order/save")
    @RequiresPermissions("yaya:shopOrder:edit")
    public String saveUpdateOrder(Order order, RedirectAttributes redirectAttributes){
        orderService.updateByPrimaryKey(order);
        addFlashMessage(redirectAttributes, "修改订单信息成功");
        return "redirect:/yaya/shop/order/list";
    }
}
