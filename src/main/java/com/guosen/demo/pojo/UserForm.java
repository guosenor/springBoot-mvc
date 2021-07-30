package com.guosen.demo.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
@ApiModel(value = "UserForm")
public class UserForm {
    @NotNull
    @Size(min = 6,max = 16)
    public String name;
    @NotNull
    @Size(min = 6,max = 16)
    public String password;
}
