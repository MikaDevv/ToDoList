package com.mika.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mika.entity.User;
import com.mika.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public User createUser(String name, String email, String password){
        Optional<User> resultado = userRepository.findByEmail(email);
        if(!resultado.isPresent()){
            User u = new User();
            u.setEmail(email);
            u.setName(name);
            u.setPassword(password);
            userRepository.save(u);
            return u;
        }else{
            throw new RuntimeException("Usuário já cadastrado!");
        }

    }

}
