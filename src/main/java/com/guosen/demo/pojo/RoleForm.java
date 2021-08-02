package com.guosen.demo.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
@ApiModel(value = "RoleForm")
public class RoleForm {
    @NotNull
    @Size(min = 6,max = 16)
    public String name;
}
