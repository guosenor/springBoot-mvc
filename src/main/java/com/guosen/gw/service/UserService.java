package com.guosen.gw.service;

import java.util.Optional;

import com.guosen.gw.dao.UserDao;
import com.guosen.gw.model.User;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User findName(String name) {
        return userDao.findOneByName(name);
    }

    public Optional<User> findById(Integer id) {
        return userDao.findById(id);
    }

    public Page<User> list(Specification<User> querySpec,Pageable pageable){
        Page<User> page = userDao.findAll(querySpec,pageable);
        return page;
    }

    public User save(User user){
        return userDao.save(user);
    }
}