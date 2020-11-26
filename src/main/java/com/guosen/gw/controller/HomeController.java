package com.guosen.gw.controller;


import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.guosen.gw.model.User;
import com.guosen.gw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String Index(HttpSession session) {
        User user = userService.findName("guosen");
        user.setPassword("1234569");
        userService.save(user);
        return JSONObject.toJSON(user).toString();
    }
}
