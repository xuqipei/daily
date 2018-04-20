package com.daily.vo;

import java.math.BigDecimal;

/**
 * Created by xuqipei on 17-7-20.
 */
public class OrderItemVo {

    private Integer menuId;//菜品ID
    private String menuName;//菜品名称
    private BigDecimal price;//菜品单价
    private BigDecimal totalPrice;//合计价钱

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
