package com.huajuan.killer.mapper;

import com.huajuan.killer.domain.ItemStockDO;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Long id);

    ItemStockDO selectByItemId(Long id);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int decreaseStock(@Param("itemId") Object itemId, @Param("amount") Object amount);

    int updateByPrimaryKey(ItemStockDO record);
}