package cn.medcn.csp.admin.controllor;

import cn.medcn.common.ctrl.BaseController;
import cn.medcn.common.excptions.SystemException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.utils.ExcelUtils;
import cn.medcn.common.utils.StringUtils;
import cn.medcn.user.dto.CspPackageOrderDTO;
import cn.medcn.user.dto.PackageOrderExcel;
import cn.medcn.user.model.CspPackage;
import cn.medcn.user.model.CspPackageOrder;
import cn.medcn.user.service.CspPackageOrderService;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * csp套餐订单
 * Created by LiuLP on 2017/12/21/021.
 */
@Controller
@RequestMapping("/sys/package/stats")
public class CspPackageStatsController extends BaseController {

    @Autowired
    private CspPackageOrderService cspPackageOrderService;


    /**
     * 海内订单
     * @param model
     * @param type 货币类型，0表示人民币，1表示美元
     * @return
     */
    @RequestMapping("/home")
    public String homeStats(Model model, Pageable pageable,Integer type,String startTime,String endTime){
        pageable.setPageSize(10);
        List<CspPackageOrder> moneyList = cspPackageOrderService.selectAbroadAndHomeMoney();
        float rmbTotal = 0;
        float usdTotal = 0;
        //获取不同货币资金总额
        for(CspPackageOrder order:moneyList){
            if(order.getCurrencyType() == CspPackageOrder.CurrencyType.RMB.ordinal()){
                rmbTotal = order.getTotalMoney();
            }else{
                usdTotal = order.getTotalMoney();
            }
        }

        if(type == null){
            type = CspPackageOrder.CurrencyType.RMB.ordinal();
        }
        //查找订单信息
        pageable.put("type",type);
        //获取对应时间段的交易成功总额
        if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
            Integer successSum = cspPackageOrderService.findOrderSuccessSum(type,startTime,endTime);
            model.addAttribute("successSum",successSum == null ? 0:successSum);
            model.addAttribute("startTime",startTime);
            model.addAttribute("endTime",endTime);
            pageable.put("startTime",startTime);
            pageable.put("endTime",endTime);
        }
        MyPage<CspPackageOrderDTO> myPage = cspPackageOrderService.findOrderListByCurrencyType(pageable);

        model.addAttribute("rmb",rmbTotal);
        model.addAttribute("usd",usdTotal);
        model.addAttribute("page",myPage);
        model.addAttribute("type",type);
        return "/statistics/packageOrderStats";
    }

    /**
     * 修改订单备注
     * @return
     */
    @RequestMapping("/remark")
    @ResponseBody
    public String remark(String tradeId,String remark){
        if(StringUtils.isEmpty(tradeId)){
            return error("请提供订单id");
        }
        CspPackageOrder order = new CspPackageOrder();
        order.setTradeId(tradeId);
        order = cspPackageOrderService.selectOne(order);
        if(order == null){
            return error("没有找到该订单");
        }
        order.setRemark(remark);
        int count = cspPackageOrderService.updateByPrimaryKey(order);
        if(count != 1){
            return error("备注失败");
        }
        return success();
    }

    /**
     * 查找订单
     * @param tradeId
     * @param rmb
     * @param usd
     * @param model
     * @return
     * @throws SystemException
     */
    @RequestMapping("/search")
    public String search(String tradeId,Float rmb,Float usd,Integer type,Model model) throws SystemException {
        if(StringUtils.isEmpty(tradeId)){
            throw new SystemException("请输入订单号");
        }
        Pageable pageable = new Pageable();
        pageable.put("tradeId",tradeId);
        MyPage<CspPackageOrderDTO> myPage = cspPackageOrderService.findOrderListByCurrencyType(pageable);
        if(myPage.getDataList().size() > 1){
            throw new SystemException("订单号不正确");
        }
        model.addAttribute("rmb",rmb);
        model.addAttribute("usd",usd);
        model.addAttribute("page",myPage);
        model.addAttribute("search",true);
        model.addAttribute("type",type);
        return  "/statistics/packageOrderStats";
    }


    @RequestMapping("/export")
    public void export(Integer type,String startTime,String endTime,HttpServletResponse response) throws SystemException {
        if(type == null){
            throw new SystemException("请提供货币类型");
        }
        if(StringUtils.isEmpty(startTime)){
            throw new SystemException("请选择开始时间");
        }
        if(StringUtils.isEmpty(endTime)){
            throw new SystemException("请选择结束时间");
        }
        Pageable pageable = new Pageable();
        pageable.put("type",type);
        //导出所有符合的数据,不分页
        pageable.setPageSize(10000);
        //获取对应时间段的交易成功总额
        Integer successSum = cspPackageOrderService.findOrderSuccessSum(type,startTime,endTime);

        if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
            pageable.put("startTime",startTime);
            pageable.put("endTime",endTime);
        }
        MyPage<CspPackageOrderDTO> myPage = cspPackageOrderService.findOrderListByCurrencyType(pageable);
        List<Object> dataList = Lists.newArrayList();
        String fileName = "套餐订单.xls";
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        for(CspPackageOrderDTO dto :myPage.getDataList()){
            PackageOrderExcel data = new PackageOrderExcel();
            data.setTradeId(dto.getId());
            data.setMoney(dto.getMoney() + "");
            data.setNickname(dto.getNickname());
            data.setPackageId(dto.getPackageId() == CspPackage.TypeId.PREMIUM.getId() ? "高级版" : "专业版");
            data.setPackageType(dto.getPackageType() == 0 ? "1个月" : "1年");
            data.setRemark(dto.getRemark());
            data.setStatus(dto.getStatus() == 0?"待付款" : "交易成功");
            date = dto.getCreateTime();
            data.setCreateTime(format.format(date));
            data.setPlatForm(CspPackageOrder.getPlatFormName(dto.getPlatForm()));
            dataList.add(data);
        }
        //添加统计交易成功总额记录
        PackageOrderExcel data = new PackageOrderExcel();
        data.setTradeId("交易成功金额");
        data.setNickname(successSum + "");
        dataList.add(data);
        Workbook workbook = ExcelUtils.writeExcel(fileName,dataList,PackageOrderExcel.class);
        try {

            ExcelUtils.outputWorkBook(fileName,workbook,response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException("文件导出失败");
        }

    }


}
