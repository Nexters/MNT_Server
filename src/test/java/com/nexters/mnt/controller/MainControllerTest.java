package com.nexters.mnt.controller;

import com.nexters.mnt.domain.User;
import com.nexters.mnt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MainControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void signUp_test() {
        User user = new User();
        user.setId("1");
        user.setName("2");
        user.setProfilePic("3");

        userRepository.save(user);
    }
}