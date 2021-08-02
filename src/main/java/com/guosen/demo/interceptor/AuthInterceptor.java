package com.guosen.demo.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.guosen.demo.auth.AuthCheck;
import com.guosen.demo.auth.AuthCode;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    public AuthInterceptor(){

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            AuthCheck code = ((HandlerMethod) handler).getMethodAnnotation(AuthCheck.class);
            if(code==null){
                return true;
            }
            if(!this.loginCheck(request, response)){
                PrintWriter pw = response.getWriter();
                response.setHeader("Content-Type", "application/json");
                HashMap<String,Object> error = new HashMap<String,Object>();
                error.put("status", 401);
                error.put("message", "please login");
                pw.write(JSON.toJSONString(error));
                pw.flush();
                pw.close();
                return false;
            }
            if( code.value() != AuthCode.login && this.authCheck(request, code)!=true){
                PrintWriter pw = response.getWriter();
                response.setHeader("Content-Type", "application/json");
                HashMap<String,Object> error = new HashMap<String,Object>();
                error.put("status", 401);
                error.put("message", "no auth");
                pw.write(JSON.toJSONString(error));
                pw.flush();
                pw.close();
                return false;
            }
           
        } catch (Exception e) {
            //TODO: handle exception   
        }
        return true;
    }

    private boolean loginCheck(HttpServletRequest request,HttpServletResponse response){
    
        //判断登录
        HttpSession session = request.getSession();
        Integer userId  = (Integer) session.getAttribute("userId");
        if(userId != null){
            return true;
        }
        return false;
    }
    private boolean authCheck(HttpServletRequest request,AuthCheck code){
        return false;
    }
}