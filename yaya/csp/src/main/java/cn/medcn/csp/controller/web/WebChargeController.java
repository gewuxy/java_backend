package cn.medcn.csp.controller.web;

import cn.medcn.common.utils.StringUtils;
import cn.medcn.csp.controller.CspBaseController;
import cn.medcn.user.service.ChargeService;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixuan on 2017/9/12.
 */
@Controller
@RequestMapping("/mgr/charge/")
public class WebChargeController extends CspBaseController {


    @Autowired
    protected ChargeService chargeService;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${appId}")
    private String appId;


    /**
     * 购买流量，需要传递flux(流量值),channel(支付渠道)
     */
    @RequestMapping("/toCharge")
    public String toCharge(Integer flux, String channel, HttpServletRequest request,Model model)  {
        String path = this.getClass().getClassLoader().getResource("privateKey.pem").getPath();
        Pingpp.apiKey = apiKey;
        String orderNo = StringUtils.nowStr();
        String userId = getWebPrincipal().getId();
        String ip = request.getRemoteAddr();
        Pingpp.privateKeyPath = path;
        Charge charge = null;


        try {
            //生成Charge对象
            charge = chargeService.createCharge(orderNo, appId, flux, channel, ip);
        } catch (RateLimitException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (APIException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (ChannelException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (APIConnectionException e) {
            e.printStackTrace();
            return error(e.getMessage());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
        //创建订单
        chargeService.createOrder(userId, orderNo, flux, channel);
        model.addAttribute("charge",charge.toString());
        return localeView("/userCenter/newPage");

    }



    /**
     * paypal支付创建订单
     * @param flux 流量值
     * @return
     */
    @RequestMapping("/createOrder")
    @ResponseBody
    public String createOrder(Integer flux){
        String userId = getWebPrincipal().getId();
        String orderId = chargeService.createPaypalOrder(userId,flux);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderId);
        return success(map);
    }


}







