package com.guosen.gw.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.guosen.gw.auth.AuthCheck;
import com.guosen.gw.auth.AuthCode;

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
            if( code.value() == AuthCode.login){
                return this.loginCheck(request, response);
            }else{
                return this.loginCheck(request, response) && this.authCheck(request, response);
            }
        } catch (Exception e) {
            //TODO: handle exception
            return true;
        }
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
    private boolean authCheck(HttpServletRequest request,HttpServletResponse response){
        return false;
    }
}
