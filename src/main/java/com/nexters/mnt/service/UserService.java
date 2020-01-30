package com.nexters.mnt.service;

import com.nexters.mnt.domain.User;
import com.nexters.mnt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void signIn(User user){
        userRepository.save(user);
    }
}
