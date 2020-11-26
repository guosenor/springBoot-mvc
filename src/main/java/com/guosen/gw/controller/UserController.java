
package com.guosen.gw.controller;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.alibaba.fastjson.JSONObject;
import com.guosen.gw.model.User;
import com.guosen.gw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.utility.RandomString;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    @ResponseBody
    public String Create() {
        User user = new User();
        user.setName(RandomString.make(5));
        user.setPassword("pwd");
        userService.save(user);
        return "{\"status\":\"success\"}";
    }

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
                // System.out.println("-----------name:"+name);
                Page<User> page = userService.list(null, pageable);
                return page;
            }
 
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String Index(@PathVariable("userId") Integer userId) {
        Optional<User> user = userService.findById(userId);
        return JSONObject.toJSON(user).toString();
    }
}
