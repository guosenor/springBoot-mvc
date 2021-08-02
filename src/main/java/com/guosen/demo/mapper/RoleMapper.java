package com.guosen.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guosen.demo.entity.Role;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
}