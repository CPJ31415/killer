package com.huajuan.killer.service.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderModel implements Serializable{

    private String id;              //用String表id，一般企业订单标有一串日期等信息

    private Long userId;

    private Long itemId;

    private Integer amount;         //购买数量

    private BigDecimal orderPrice; //购买总金额

    private BigDecimal itemPrice;   //购买时商品价格

    private Long promoId;    //若为空，表示为原价下单

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }
}
