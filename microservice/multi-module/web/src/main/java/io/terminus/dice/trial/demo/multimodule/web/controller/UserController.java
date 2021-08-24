package io.terminus.dice.trial.demo.multimodule.web.controller;

import java.util.List;

import io.terminus.dice.trial.demo.multimodule.user.dto.UserDTO;
import io.terminus.dice.trial.demo.multimodule.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Effet
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserDTO> listAllUsers() {
        return userService.listRelatedUsers();
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
