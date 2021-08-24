package io.terminus.dice.trial.demo.multimodule.user.service;

import java.util.List;

import io.terminus.dice.trial.demo.multimodule.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Effet
 */
@FeignClient(name = "multi-module-provider-user")
public interface UserService {

    @GetMapping("/list-all-users")
    List<UserDTO> listAll();

    @GetMapping("/list-related-users")
    List<UserDTO> listRelatedUsers();
}
