package com.guosen.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guosen.demo.entity.User;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public Integer selectCountAll();
    public User findOneByName(String name);
    public User findOneByNameWithRoles(String name);
}