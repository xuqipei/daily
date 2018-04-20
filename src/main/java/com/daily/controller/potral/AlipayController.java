package com.daily.controller.potral;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.daily.common.AlipayConfig;
import com.daily.common.Const;
import com.daily.entity.DailyOrder;
import com.daily.repository.OrderRepository;
import com.daily.service.IMerchantTableService;
import com.daily.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by xuqipei on 17-7-25.
 */
@RequestMapping("/order")
@RestController
public class AlipayController {
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private IMerchantTableService iMerchantTableService;

    @Autowired
    private HttpSession session;

    @GetMapping("/pay/{order_no}")
    public String pay(@PathVariable("order_no") long order_no) throws AlipayApiException {
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

        DailyOrder order = orderRepository.getByOrderNo(order_no);
        if (order.getStatus() != Const.WAIT_BUYER_PAY) {
            return "fail";
        }
        String out_trade_no = order.getOrderNo().toString();
        String subject = "A225餐饮平台";
        String total_amount = order.getTotalPrice().toString();
        String product_code = "QUICK_WAP_PAY";
        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(AlipayConfig.return_url);
        String form = client.pageExecute(alipay_request).getBody();
        return form;
    }


    /**
     * 异步回调方法
     */
    @RequestMapping("/notify_url")
    @ResponseBody
    public String notify_url(HttpServletRequest request) throws AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map params = getParams(request.getParameterMap());
        //商户订单号
        String out_trade_no = request.getParameter("out_trade_no");
        //支付宝交易号
        String trade_no = request.getParameter("trade_no");
        //交易状态
        String trade_status = request.getParameter("trade_status");
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
        if (verify_result) {//验证成功
            updateOrder(out_trade_no);
            return "success";

        } else {
            return "failed";
        }
    }

    /**
     * 同步回调方法
     */
    @RequestMapping("/return_url")
    public String return_url(HttpServletRequest request) throws AlipayApiException {
        //获取支付宝GET过来反馈信息
        Map params = getParams(request.getParameterMap());

        String out_trade_no = request.getParameter("out_trade_no");

        String trade_no = request.getParameter("trade_no");

        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

        String msg, orderPage;
        String forward = "window.location.href=\"../index.html\";";
        String setItem = "localStorage.setItem(\"page\", 'showpay');";
        if (verify_result) {//验证成功
            updateOrder(out_trade_no);
            msg = String.format("alert('%s');", "支付成功");
            orderPage = "<script>" + msg + setItem + forward + "</script>";
        } else {//验证失败
            msg = String.format("alert('%s')", "支付失败");
            orderPage = "<script>" + msg + setItem + forward + "</script>";
        }
        return orderPage;
    }


    /**
     * 更新订单操作
     */
    public void updateOrder(String out_trade_no) {

        DailyOrder order = orderRepository.getByOrderNo(Long.parseLong(out_trade_no));
        if (order.getStatus() == Const.WAIT_BUYER_PAY) {
            Timestamp paytime = new Timestamp(System.currentTimeMillis());
            order.setPayTime(paytime);
            order.setStatus(Const.TRADE_SUCCESS);
            iMerchantTableService.freeTable(order.getTableId());
            orderRepository.saveAndFlush(order);
        }
    }

    /**
     * 获取支付宝反馈过来的信息
     */
    public Map<String, String> getParams(Map requestParams) {
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }
}
