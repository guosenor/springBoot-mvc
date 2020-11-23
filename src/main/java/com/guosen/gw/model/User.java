package com.guosen.gw.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
@Table(name="users")
@org.hibernate.annotations.Table(appliesTo = "users",comment = "用户表")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, insertable = true, updatable = false)
    private int id;
    
    @Column(nullable = true,columnDefinition = "varchar(45) default '' comment '用户名'")
    public String name;

    @Column(nullable = true,columnDefinition = "varchar(100) default '' comment '密码'")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    @Column(nullable = true, insertable = false, updatable = false)
    private Date createDate;
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn (name = "user_id", referencedColumnName = "id"),inverseJoinColumns = @JoinColumn (name = "role_id", referencedColumnName = "id"))
    public List<Role> roles = new ArrayList<>();
    
    public void setPassword(String pwd){
        this.password = pwd;
    }
}
