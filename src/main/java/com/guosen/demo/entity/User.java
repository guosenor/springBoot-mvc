package com.guosen.demo.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.Getter;
import lombok.Setter;
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    private String password; 

    @Getter
    private List<Role> roles;

    public void setPwd(String pwd){
        this.password = BCrypt.hashpw(pwd, BCrypt.gensalt());
    }
    public Boolean checkPwd(String pwd){
       return BCrypt.checkpw(pwd, this.password);
    }
}
