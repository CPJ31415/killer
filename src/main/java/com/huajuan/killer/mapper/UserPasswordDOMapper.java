package com.huajuan.killer.mapper;

import com.huajuan.killer.domain.UserPasswordDO;

public interface UserPasswordDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPasswordDO record);

    int insertSelective(UserPasswordDO record);

    UserPasswordDO selectByPrimaryKey(Long id);

    UserPasswordDO selectByUserId(Long id);

    int updateByPrimaryKeySelective(UserPasswordDO record);

    int updateByPrimaryKey(UserPasswordDO record);
}