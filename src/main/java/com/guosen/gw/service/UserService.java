package com.guosen.gw.service;
import java.util.List;
import java.util.Optional;

import com.guosen.gw.dao.UserDao;
import com.guosen.gw.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findName(String name) {
        return userDao.findByName(name);
    }

    public Optional<User> findById(Integer id) {
        return userDao.findById(id);
    }

    public List<User> list(){
        List<User> list = (List<User>) userDao.findAll();
        return list;
    }
}