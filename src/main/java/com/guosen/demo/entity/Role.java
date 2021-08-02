package com.guosen.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Role {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Getter @Setter 
    private String name;
}
