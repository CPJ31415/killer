package com.huajuan.killer.domain;

public class StockLogDO {
    private String stockLogId;

    private Long itemId;

    private Integer amount;

    private Integer status;

    public String getStockLogId() {
        return stockLogId;
    }

    public void setStockLogId(String stockLogId) {
        this.stockLogId = stockLogId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stockLogId=").append(stockLogId);
        sb.append(", itemId=").append(itemId);
        sb.append(", amount=").append(amount);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}