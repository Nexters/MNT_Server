package com.nexters.mnt.service;

import com.nexters.mnt.domain.User;
import com.nexters.mnt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void signIn(User user){
        userRepository.save(user);
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

}
