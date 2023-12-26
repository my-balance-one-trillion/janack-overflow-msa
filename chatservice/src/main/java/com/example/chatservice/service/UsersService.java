package com.example.chatservice.service;


import com.example.chatservice.entity.Users;
import com.example.chatservice.global.exception.BusinessLogicException;
import com.example.chatservice.global.exception.ExceptionCode;
import com.example.chatservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }



    public Users findByEmail(String email) { //UserDetails loadUserByUsername() 에 중요한 리포지토리

        System.out.println("findByEmail start! email : " + email);

        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.UNAUTHORIZED));
    }

}
