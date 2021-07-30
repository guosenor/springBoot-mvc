package com.guosen.demo.auth;

public enum AuthCode {
    login("login","登录"),
    none("none","无");

    private String code;
    private String name;
    private AuthCode(String code,String name){
        this.code = code;
        this.name = name;
    }

    public String getAuthCode(){
        return this.code;
    }

    public String getAuthName(){
        return this.name;
    }
}
