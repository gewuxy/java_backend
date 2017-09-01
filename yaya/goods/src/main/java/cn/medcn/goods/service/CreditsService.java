package cn.medcn.goods.service;

import cn.medcn.common.excptions.NotEnoughCreditsException;
import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.goods.dto.CreditPayDTO;
import cn.medcn.goods.dto.RechargeDTO;
import cn.medcn.goods.dto.TradeDetailDTO;
import cn.medcn.goods.model.Credits;
import cn.medcn.goods.model.Order;

/**
 * Created by lixuan on 2017/4/24.
 */
public interface CreditsService extends BaseService<Credits>{


    /**
     * 象数支付
     * @param payDTO
     * @throws Exception
     */
    void executePlayCredits(CreditPayDTO payDTO) throws NotEnoughCreditsException;

    /**
     * 象数奖励
     * @param payDTO
     * @throws Exception
     */
    void executeAwardCredits(CreditPayDTO payDTO)throws NotEnoughCreditsException;

    /**
     * 象数充值
     * @param rechargeDTO
     */
    void executeRecharge(RechargeDTO rechargeDTO)throws Exception;


    /**
     * 象数交易明细
     * @param pageable
     * @return
     */
    MyPage<TradeDetailDTO> findTradeInfo(Pageable pageable);

    /**
     * 查询个人象数
     * @param userId
     * @return
     */
    Credits doFindMyCredits(Integer userId);
}
