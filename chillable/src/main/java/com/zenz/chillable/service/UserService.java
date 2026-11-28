package com.zenz.chillable.service;


import com.zenz.chillable.dto.request.UserRequest;
import com.zenz.chillable.dto.response.UserResponse;
import com.zenz.chillable.entity.User;
import com.zenz.chillable.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public UserResponse create(UserRequest request){

        User user= new User();

        user.setUsername(request.getUsername());
        user.setCreated_at(LocalDateTime.now());

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public User get(UUID id){
        return userRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("User not found"));
    }

}
