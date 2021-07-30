package com.guosen.demo.controller;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guosen.demo.auth.AuthCheck;
import com.guosen.demo.auth.AuthCode;
import com.guosen.demo.entity.User;
import com.guosen.demo.pojo.UserForm;
import com.guosen.demo.service.UserService;
import com.guosen.demo.util.HandleFormValidateError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "类描述", tags = { "User" })
@RestController
public class UserController {
    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/user/register", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String Create(@RequestBody @Validated UserForm form, BindingResult userFormBindingResult) {
        if (userFormBindingResult.hasErrors()) {
            return HandleFormValidateError.format(userFormBindingResult.getFieldErrors());
        }
        User user = new User();
        user.setName(form.name);
        user.setPwd(form.password);
        session.setAttribute("register", 1);
        userService.save(user);
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("data", form);
        res.put("count", userService.selectCountAll());
        return JSON.toJSONString(res);
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String Login(@RequestBody @Validated UserForm form, BindingResult userFormBindingResult) {
        
        if (userFormBindingResult.hasErrors()) {
            return HandleFormValidateError.format(userFormBindingResult.getFieldErrors());
        }
        User user = userService.findOneByName(form.name);
        JSONObject res = new JSONObject();
        if(user!=null && user.checkPwd(form.password)){
            session.setAttribute("userId", user.getId());
            session.setAttribute("user", JSONObject.toJSON(user));
            res.put("code", 0);
            res.put("data", JSONObject.toJSON(user));
        }else{
            res.put("code", -1);
            res.put("msg", "name or password error");
        }
        return JSON.toJSONString(res);
    }


    @ApiOperation(value = "用户登录检查")
    @RequestMapping(value = "/user/myself", method = RequestMethod.GET,  produces = "application/json")
    @AuthCheck(AuthCode.login)
    @ResponseBody
    public String Myself() {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        if(session.getAttribute("userId")!=null){
            res.put("code", 0);
            res.put("data", session.getAttribute("user"));
        }else{
            res.put("code", -1);
            res.put("msg", "please login");
        }
        return JSON.toJSONString(res);
    }

    @ApiOperation(value = "用户退出登录")
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET,  produces = "application/json")
    @AuthCheck(AuthCode.login)
    @ResponseBody
    public String Logout() {
        JSONObject res = new JSONObject();
        session.setAttribute("userId", null);
        session.setAttribute("user", null);
        res.put("code", 0);
        res.put("msg", "success");
        return JSON.toJSONString(res);
    }
}
