package com.huajuan.killer.mapper;

import com.huajuan.killer.domain.PromoDO;

public interface PromoDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    PromoDO selectByPrimaryKey(Long id);

    PromoDO selectByItemId(Long id);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);
}