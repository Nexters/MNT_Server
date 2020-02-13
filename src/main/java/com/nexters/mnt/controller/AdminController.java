package com.nexters.mnt.controller;

import com.nexters.mnt.domain.User;
import com.nexters.mnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UserService userService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getMain(Model model){

        List<User> users = userService.getUserList();
        model.addAttribute("userList", users);
        return "main";
    }
}
