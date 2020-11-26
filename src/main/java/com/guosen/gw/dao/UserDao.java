package com.guosen.gw.dao;

import com.guosen.gw.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface  UserDao extends CrudRepository<User, Integer>,JpaSpecificationExecutor<User>  {
    public User findOneByName(String name);

	public Page<User> findAll(Specification<User> querySpec,Pageable pageable);
}