package com.daily.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by xuqipei on 17-7-19.
 */
public class OrderVo {

    private Long orderNo;//订单号
    private String tableNo;//座位
    private Integer sitReal;//人数
    private List<OrderItemVo> orderItemVo;//订单详情
    private BigDecimal totalPrice;//总价
    private String payTime;//支付时间
    private String status;//支付状态
    private String  createTime;//订单创建时间


    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public Integer getSitReal() {
        return sitReal;
    }

    public void setSitReal(Integer sitReal) {
        this.sitReal = sitReal;
    }

    public List<OrderItemVo> getOrderItemVo() {
        return orderItemVo;
    }

    public void setOrderItemVo(List<OrderItemVo> orderItemVo) {
        this.orderItemVo = orderItemVo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
