package com.guosen.gw.dao;

import com.guosen.gw.model.User;

import org.springframework.data.repository.CrudRepository;

public interface  UserDao extends CrudRepository<User, Integer> {
    public User findOneByName(String name);
    public User save(User user);
}