package com.guosen.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private String password; 
    public Long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String pwd){
        this.password = pwd;
    }
    public void setPwd(String pwd){
        this.password = BCrypt.hashpw(pwd, BCrypt.gensalt());
    }
    public Boolean checkPwd(String pwd){
       return BCrypt.checkpw(pwd, this.password);
    }
}
