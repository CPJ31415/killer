package com.huajuan.killer.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

public class ItemModel implements Serializable{

    private Long id;     //商品id

    @NotBlank(message = "商品名称不能为空")
    private String title;     //商品名称

    @NotNull(message = "必须填写商品价格")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;     //商品价格，注意为BigDecimal，若为double会存在精度问题

    @NotNull(message = "必须填写库存")
    private Integer stock;     //商品库存

    @NotBlank(message = "商品描述信息不能为空")
    private String description;     //商品描述

    private Integer sales;     //商品销量

    @NotBlank(message = "商品图片不能为空")
    private String imgUrl;     //商品图片的url

    //使用聚合模型，若PromoModel不为空，则表示其拥有还未结束的活动
    private PromoModel promoModel;

    public PromoModel getPromoModel() {
        return promoModel;
    }

    public void setPromoModel(PromoModel promoModel) {
        this.promoModel = promoModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
