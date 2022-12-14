package com.houhong.thirdpay.core.common;


import com.houhong.thirdpay.bean.TradeStatus;
import com.houhong.thirdpay.bean.common.NoticeInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
*  通知统一支付回调管理
*
**/
public interface NoticeManager {

    /**
     * 接收支付宝回调参数
     * @param request
     * @return
     */
    Map<String, String> receiveAliParams(HttpServletRequest request);

    /**
     * 接收微信回调参数
     * @param request
     * @return
     */
    Map<String, String> receiveWxParams(HttpServletRequest request);

    /**
     * 统一支付回调
     * @param params 回调参数
     * @param info 回调参数封装，传入一个空白封装，此方法会填充数据
     * @return 订单状态
     */
    TradeStatus execute(Map<String, String> params, NoticeInfo info);

    /**
     * 发送支付宝响应
     * @param response
     */
    void sendAliResponse(HttpServletResponse response);

    /**
     * 发送微信响应
     * @param response
     */
    void sendWxResponse(HttpServletResponse response);

}
