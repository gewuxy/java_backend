package cn.medcn.csp.admin.controllor.yaya;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.ctrl.FilePath;
import cn.medcn.common.dto.FileUploadResult;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.FileUploadService;
import cn.medcn.common.utils.CheckUtils;
import cn.medcn.csp.admin.log.Log;
import cn.medcn.goods.model.Goods;
import cn.medcn.goods.model.Order;
import cn.medcn.goods.service.OrderService;
import cn.medcn.goods.service.ShopService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

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

    @Autowired
    protected FileUploadService fileUploadService;

    @Value("${app.file.base}")
    protected String appFileBase;

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


    @RequestMapping(value = "/edit")
    @RequiresPermissions("yaya:shop:edit")
    @Log(name = "编辑商品信息")
    public String editShopInfo(Integer id, Model model) {
        if (id != null) {
            Goods goods = shopService.selectByPrimaryKey(id);
            model.addAttribute("goods", goods);
        }
        model.addAttribute("appFileBase", appFileBase);
        return "/yaya/shop/editForm";
    }

    @RequestMapping(value = "/save")
    @RequiresPermissions("yaya:shop:add")
    @Log(name = "保存商品信息")
    public String saveEditInfo(Goods goods, Model model,RedirectAttributes redirectAttributes) {
        boolean isAdd = goods.getId() == null;
        if (isAdd) {
            // 需要检查商品是否已经存在
            Goods existedGoods = shopService.selectByPrimaryKey(goods.getId());
            if (existedGoods != null) {
                model.addAttribute("goods", goods);
                model.addAttribute("error", "商品[" + goods.getName() + "] 已经存在.");
                return "/yaya/shop/editForm";
            } else {
                goods.setCreateTime(new Date());
                shopService.insert(goods);
                addFlashMessage(redirectAttributes, "添加商品信息成功");
                return "redirect:/yaya/shop/list";
            }
        } else {
            shopService.updateByPrimaryKey(goods);
            addFlashMessage(redirectAttributes, "修改商品信息成功");
            return "redirect:/yaya/shop/list";
        }

    }

    @RequestMapping(value = "/upload/picture")
    @Log(name = "上传商品图片")
    public String uploadPicture(@RequestParam MultipartFile file) {
        FileUploadResult result;
        try {
            result = fileUploadService.upload(file, FilePath.GOODS.path);
        } catch (SystemException e) {
            return error(e.getMessage());
        }
        return success(result);
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
    @Log(name = "编辑订单信息")
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
    @Log(name = "修改订单信息")
    public String saveUpdateOrder(Order order, RedirectAttributes redirectAttributes){
        orderService.updateByPrimaryKey(order);
        addFlashMessage(redirectAttributes, "修改订单信息成功");
        return "redirect:/yaya/shop/order/list";
    }
}
