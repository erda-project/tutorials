package io.terminus.dice.trial.demo.multimodule.user.service;

import java.util.List;

import com.google.common.collect.Lists;
import io.terminus.dice.trial.demo.multimodule.user.dto.UserDTO;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Effet
 */
@RestController
public class UserServiceImpl implements UserService {
    public List<UserDTO> listAll() {
        UserDTO bob = new UserDTO();
        bob.setId(1L);
        bob.setName("Bob");

        UserDTO alice = new UserDTO();
        alice.setId(2L);
        alice.setName("Alice");

        return Lists.newArrayList(bob, alice);
    }

    public List<UserDTO> listRelatedUsers() {
        UserDTO alice = new UserDTO();
        alice.setId(2L);
        alice.setName("Alice");

        return Lists.newArrayList(alice);
    }
}
