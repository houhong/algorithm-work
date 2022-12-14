package com.houhong.thirdpay.bean;

/**
 * @program: algorithm-work
 * @description: 交易状态机
 * @author: houhong
 * @create: 2022-10-06 16:55
 **/
public class TradeStatus {


    /**
     * 未知，订单生成后的默认状态
     **/
    public static final int UNKNOWN = 0;

    /**
     * 支付中
     **/
    public static final int PAY_ING = 6;
    /**
     * 支付成功
     **/
    public static final int PAY_SUCCESS = 1;
    /**
     * 支付失败，包括：支付异常、超时未支付
     **/
    public static final int PAY_FAIL = 2;
    /**
     * 退款中
     **/
    public static final int REFUND_PROGRESS = 3;
    /**
     * 退款成功
     **/
    public static final int REFUND_SUCCESS = 4;
    /**
     * 退款失败
     **/
    public static final int REFUND_FAIL = 5;

    /**
     * 内部状态
     */
    private final int status;

    /**
     * 订单号
     */
    private final String tradeNo;

    /**
     * 防止外部实例化
     *
     * @param status 内部状态
     */
    private TradeStatus(String tradeNo, int status) {
        this.tradeNo = tradeNo;
        this.status = status;
    }

    /**
     * 获取内部状态
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     * 获取订单号
     *
     * @return
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * 退款中状态断言
     *
     * @return
     */
    public boolean isRefundProgress() {
        return this.status == REFUND_PROGRESS;
    }

    /**
     * 退款成功状态断言
     *
     * @return
     */
    public boolean isRefundSuccess() {
        return this.status == REFUND_SUCCESS;
    }

    /**
     * 退款失败状态断言
     *
     * @return
     */
    public boolean isRefundFail() {
        return this.status == REFUND_FAIL;
    }

    /**
     * 支付成功状态断言
     *
     * @return
     */
    public boolean isPaySuccess() {
        return this.status == PAY_SUCCESS;
    }

    /**
     * 支付失败状态断言
     *
     * @return
     */
    public boolean isPayFail() {
        return this.status == PAY_FAIL;
    }

    /**
     * 未知状态断言
     *
     * @return
     */
    public boolean isUnknown() {
        return this.status == UNKNOWN;
    }

    /**
     * 支付中状态断言
     *
     * @return
     */
    public boolean isPaying() {

        return this.status == PAY_ING;
    }

    /**
     * 退款成功状态
     *
     * @return
     */
    public static TradeStatus refundSuccess(String tradeNo) {
        return new TradeStatus(tradeNo, REFUND_SUCCESS);
    }

    /**
     * 退款中状态
     *
     * @return
     */
    public static TradeStatus refundProgress(String tradeNo) {
        return new TradeStatus(tradeNo, REFUND_PROGRESS);
    }

    /**
     * 退款失败状态
     *
     * @return
     */
    public static TradeStatus refundFail(String tradeNo) {
        return new TradeStatus(tradeNo, REFUND_FAIL);
    }

    /**
     * 支付成功状态
     *
     * @return
     */
    public static TradeStatus paySuccess(String tradeNo) {
        return new TradeStatus(tradeNo, PAY_SUCCESS);
    }

    /**
     * 支付失败状态
     *
     * @return
     */
    public static TradeStatus payFail(String tradeNo) {
        return new TradeStatus(tradeNo, PAY_FAIL);
    }

    /**
     * 未知状态
     *
     * @return
     */
    public static TradeStatus unknown(String tradeNo) {
        return new TradeStatus(tradeNo, UNKNOWN);
    }


    /**
     * 支付中
     *
     * @return
     */
    public static TradeStatus payIng(String tradeNo) {
        return new TradeStatus(tradeNo, PAY_ING);
    }
}