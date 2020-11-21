package com.guosen.gw.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="roles")
@org.hibernate.annotations.Table(appliesTo = "roles",comment = "角色表")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, insertable = true, updatable = false)
    private int id;
    
    @Column(nullable = true,columnDefinition = "varchar(45) default '' comment '用户名'")
    public String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    @Column(nullable = true, insertable = false, updatable = false)
    private Date createDate;
}
