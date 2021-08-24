package io.terminus.dice.trial.demo.demomysql.controller;

import java.util.List;

import io.terminus.dice.trial.demo.demomysql.dao.UserMapper;
import io.terminus.dice.trial.demo.demomysql.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloMysqlController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/hello")
    public String hello() {
        List<UserDO> users = userMapper.selectAll();
        StringBuilder sb = new StringBuilder("Hello! ");
        for (UserDO u : users) {
            sb.append(",").append(u.getName());
        }
        return sb.toString();
    }

    @GetMapping("/insert-user")
    public String insertUser(@RequestParam String name) {
        UserDO user = new UserDO();
        user.setName(name);
        userMapper.insert(user);
        return "ok";
    }

    @GetMapping("/all-users")
    public List<UserDO> allUsers() {
        return userMapper.selectAll();
    }
}
