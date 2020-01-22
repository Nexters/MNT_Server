package com.nexters.mnt.controller;


import com.nexters.mnt.controller.exception.DataDuplicateException;
import com.nexters.mnt.domain.User;
import com.nexters.mnt.repository.UserRepository;
import com.nexters.mnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public void signUp(@RequestBody User user){
        userRepository.save(user);
    }

}
