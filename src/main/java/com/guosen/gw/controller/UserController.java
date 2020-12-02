
package com.guosen.gw.controller;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.guosen.gw.form.UserForm;
import com.guosen.gw.model.User;
import com.guosen.gw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value = "类描述", tags = {"User"})

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private HttpSession session;

    @ApiOperation(value = "创建用户", produces = "application/json")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    @ResponseBody
    public String Create(@RequestBody UserForm form) {
        User user = new User();
        user.setName(form.name);
        user.setPassword(form.password);
        userService.save(user);
        return "{\"status\":\"success\"}";
    }

    @ApiOperation(value = "用户登录", produces = "application/json")
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public String Login(@RequestBody UserForm form) {
        User user = userService.findName(form.name);
        if(user!= null){
            if(user.checkPwd(form.password)){
                session.setAttribute("userId", user.getId());
                session.setAttribute("user", JSONObject.toJSON(user));
                return "{\"status\":\"success\"}";
            }
        }
        return "{\"status\":\"fail name or password error\"}";
    }

    @ApiOperation(value = "用户登出", produces = "application/json")
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    @ResponseBody
    public String Logout() {
        session.setAttribute("user", null);
        session.setAttribute("userId", null);
        return "{\"status\":\"success\"}";
    }

    @ApiOperation(value = "用户登录检查", produces = "application/json")
    @RequestMapping(value = "/user/myself", method = RequestMethod.GET)
    @ResponseBody
    public String Myself() {
        if(session.getAttribute("userId")!=null){
            System.out.println(session.getAttribute("user"));
            return JSONObject.toJSONString(session.getAttribute("user"));
        }
        return "{\"status\":\"fail\"}";
    }

    @ApiOperation(value = "列表", produces = "application/json")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize,
            @RequestParam(name = "sort", defaultValue = "id", required = false) String sort,
            @RequestParam(name = "name",defaultValue = "",required = false) String name) {
            Pageable pageable = PageRequest.of(pageNum, pageSize,Sort.Direction.DESC,sort);
            if (!name.equals("")) {
                Specification<User> queryName = new Specification<User>() {
                    /**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@Override
                    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        Predicate p1 = criteriaBuilder.like(root.get("name"),name+"%");
                        Predicate p2 = criteriaBuilder.like(root.get("password"),name+"%");
                        return criteriaBuilder.or(p1,p2);
                    }
                };
                Specification<User> queryPwd = new Specification<User>() {
                    /**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@Override
                    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        Predicate p1 = criteriaBuilder.like(root.get("name"),name+"%");
                        Predicate p2 = criteriaBuilder.like(root.get("password"),name+"%");
                        return criteriaBuilder.or(p1,p2);
                    }
                };
                Page<User> page = userService.list(Specification.where(queryName).and(queryPwd), pageable);
                return page;
            } else {
    
                Page<User> page = userService.list(null, pageable);
                return page;
            }
 
    }

    @ApiOperation(value = "findById", produces = "application/json")
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String Index(@PathVariable("userId") Integer userId) {
        Optional<User> user = userService.findById(userId);
        return JSONObject.toJSON(user).toString();
    }
}
