package cn.medcn.goods.service.impl;

import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.JPushService;
import cn.medcn.common.service.impl.BaseServiceImpl;
import cn.medcn.common.utils.SpringUtils;
import cn.medcn.common.utils.UUIDUtil;
import cn.medcn.goods.dao.CreditsDAO;
import cn.medcn.goods.dao.OrderDAO;
import cn.medcn.goods.dao.TradeDetailDAO;
import cn.medcn.goods.dto.CreditPayDTO;
import cn.medcn.goods.dto.RechargeDTO;
import cn.medcn.goods.dto.TradeDetailDTO;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.model.TradeDetail;
import cn.medcn.goods.service.CreditsService;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lixuan on 2017/4/24.
 */
@Service
public class CreditsServiceImpl extends BaseServiceImpl<Credits> implements CreditsService {

    @Autowired
    private CreditsDAO creditsDAO;

    @Autowired
    private TradeDetailDAO tradeDetailDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public Mapper<Credits> getBaseMapper() {
        return creditsDAO;
    }

    @Autowired
    protected JPushService jPushService;


    /**
     * 支付象数
     *
     * @param payDTO
     * @throws Exception
     */
    @Override
    public void executePlayCredits(CreditPayDTO payDTO) throws NotEnoughCreditsException {
        //扣除支付方象数
        Credits credits = doFindMyCredits(payDTO.getPayer());
        //象数不足的情况下抛出异常
        if(credits == null||credits.getCredit()<=0 || payDTO.getCredits() > credits.getCredit()){
            throw new NotEnoughCreditsException(SpringUtils.getMessage("credits.notenough")+payDTO.getCredits());
        }
        credits.setCredit(credits.getCredit() - payDTO.getCredits());
        creditsDAO.updateByPrimaryKeySelective(credits);
        //增加接收方象数
        if(payDTO.getAccepter()!=null && payDTO.getAccepter()!=0){
            Credits acceptCredits = doFindMyCredits(payDTO.getAccepter());
            acceptCredits.setCredit(acceptCredits.getCredit()+payDTO.getCredits());
            creditsDAO.updateByPrimaryKeySelective(acceptCredits);

            //发送象数更改消息
            jPushService.sendChangeMessage(acceptCredits.getUserId(), "2",acceptCredits.getCredit());
        }

        executeRecordDetail(payDTO);
    }

    /**
     * 记录支付或者奖励象数的明细
     * @param payDTO
     */
    protected void executeRecordDetail(CreditPayDTO payDTO){
        TradeDetail payTradeDetail = new TradeDetail();
        payTradeDetail.setCostTime(new Date());
        payTradeDetail.setType(TradeDetail.PayType.PAY.getType());
        payTradeDetail.setCost(payDTO.getCredits());
        payTradeDetail.setCode(UUIDUtil.getNowStringID());
        payTradeDetail.setDescription(payDTO.getPayerDescrib());
        payTradeDetail.setUserId(payDTO.getPayer());
        tradeDetailDAO.insert(payTradeDetail);

        if(payDTO.getAccepter()!=null && payDTO.getAccepter()!=0){
            TradeDetail acceptTradeDetail = new TradeDetail();
            acceptTradeDetail.setUserId(payDTO.getAccepter());
            acceptTradeDetail.setDescription(payDTO.getAccepterDescrib());
            acceptTradeDetail.setCode(UUIDUtil.getNowStringID());
            acceptTradeDetail.setCost(payDTO.getCredits());
            acceptTradeDetail.setType(TradeDetail.PayType.ACCEPT.getType());
            acceptTradeDetail.setCostTime(new Date());
            tradeDetailDAO.insert(acceptTradeDetail);
        }
    }


    /**
     * 奖励象数
     *
     * @param payDTO
     */
    @Override
    public void executeAwardCredits(CreditPayDTO payDTO)throws NotEnoughCreditsException {
        //查询发送方的象数是否够用
        Credits creditsCondition = new Credits();
        creditsCondition.setUserId(payDTO.getPayer());
        Credits trueCredit = selectOne(creditsCondition);
        if(trueCredit != null && trueCredit.getCredit()>payDTO.getCredits()){
            //扣除奖励方象数
            trueCredit.setCredit(trueCredit.getCredit() - payDTO.getCredits());
            creditsDAO.updateByPrimaryKeySelective(trueCredit);
            //增加被奖励放象数
            Credits acceptCredits = doFindMyCredits(payDTO.getAccepter());
            acceptCredits.setCredit(acceptCredits.getCredit()+payDTO.getCredits());
            creditsDAO.updateByPrimaryKeySelective(acceptCredits);
            executeRecordDetail(payDTO);

            //发送象数更改消息
            jPushService.sendChangeMessage(acceptCredits.getUserId(), "2",acceptCredits.getCredit());
        }

    }

    /**
     * @param rechargeDTO
     */
    @Override
    public void executeRecharge(RechargeDTO rechargeDTO) throws Exception {
        Credits credits = doFindMyCredits(rechargeDTO.getUserId());
        credits.setCredit(credits.getCredit()+rechargeDTO.getCredits());
        creditsDAO.updateByPrimaryKeySelective(credits);
        //发送象数更新消息
        jPushService.sendChangeMessage(credits.getUserId(), "2",credits.getCredit());


        TradeDetail detail = new TradeDetail();
        detail.setUserId(rechargeDTO.getUserId());
        detail.setDescription(rechargeDTO.getDescribe());
        detail.setCode(UUIDUtil.getNowStringID());
        detail.setCost(rechargeDTO.getCredits());
        detail.setType(TradeDetail.PayType.RECHARGE.getType());
        detail.setCostTime(new Date());
        tradeDetailDAO.insert(detail);
    }

    /**
     * 象数交易明细
     * @param pageable
     * @return
     */
    @Override
    public MyPage<TradeDetailDTO> findTradeInfo(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),Pageable.countPage);
        Page<TradeDetailDTO> page = (Page<TradeDetailDTO>) tradeDetailDAO.findTradeInfo(pageable.getParams());
        return MyPage.page2Mypage(page);
    }


    /**
     * 查询个人象数
     *
     * @param userId
     * @return
     */
    @Override
    public Credits doFindMyCredits(Integer userId) {
        Credits condition = new Credits();
        condition.setUserId(userId);
        Credits credits = creditsDAO.selectOne(condition);
        if(credits == null){
            credits = new Credits();
            credits.setUserId(userId);
            credits.setCredit(0);
            creditsDAO.insert(credits);
        }
        return credits;
    }
}
