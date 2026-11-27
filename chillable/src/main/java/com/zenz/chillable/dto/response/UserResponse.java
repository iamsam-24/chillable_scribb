package com.zenz.chillable.dto.response;

import com.zenz.chillable.dto.request.UserRequest;
import com.zenz.chillable.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor

public class UserResponse {

    private UUID id;

    private String username;

    private LocalDateTime created_at;

    public static UserResponse from(User user){
        return new UserResponse (
                user.getId(),
                user.getUsername(),
                user.getCreated_at()
        );

    }
}
