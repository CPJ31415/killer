package com.huajuan.killer.service;

import com.huajuan.killer.domain.OrderDO;
import com.huajuan.killer.domain.SequenceDO;
import com.huajuan.killer.domain.StockLogDO;
import com.huajuan.killer.error.BusinessException;
import com.huajuan.killer.error.EmBusinessError;
import com.huajuan.killer.mapper.OrderDOMapper;
import com.huajuan.killer.mapper.SequenceDOMapper;
import com.huajuan.killer.mapper.StockLogDOMapper;
import com.huajuan.killer.mq.MqConsumer;
import com.huajuan.killer.mq.MqProduct;
import com.huajuan.killer.service.model.ItemModel;
import com.huajuan.killer.service.model.OrderModel;
import com.huajuan.killer.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private StockLogDOMapper stockLogDOMapper;

    @Transactional
    public OrderModel createOrder(Long userId, Long itemId, Integer amount, Long promoId, String stockLogId) throws BusinessException {

        //校验下单状态
        ItemModel itemModel = itemService.getItemByIdInCache(itemId);

        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }

        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不正确");
        }

        //落单减库存 支付减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if (promoId != null) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
        }

        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

//        //加上商品销量，这里也应该异步操作
//        itemService.increaseSales(itemId, amount);

        //设置库存流水状态为成功
        StockLogDO stockLogDO = stockLogDOMapper.selectByPrimaryKey(stockLogId);
        if(stockLogDO == null){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
        }
        stockLogDO.setStatus(2);
        stockLogDOMapper.updateByPrimaryKeySelective(stockLogDO);
        //返回前端
        return orderModel;
    }

}
