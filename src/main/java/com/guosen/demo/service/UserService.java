package com.guosen.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guosen.demo.entity.User;
import com.guosen.demo.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper mapper;

    public Integer selectCountAll(){
        return mapper.selectCountAll();
    }

    public User findOneByName(String name){
        return mapper.findOneByName(name);
    }
}