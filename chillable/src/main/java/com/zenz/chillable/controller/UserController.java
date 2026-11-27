package com.zenz.chillable.controller;


import com.zenz.chillable.dto.request.UserRequest;
import com.zenz.chillable.dto.response.UserResponse;
import com.zenz.chillable.entity.Room;
import com.zenz.chillable.entity.User;
import com.zenz.chillable.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private  UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create (@RequestBody UserRequest request){

        UserResponse response=userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")

    public ResponseEntity<User> get(UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.get(id));
    }

}

