package com.zenz.chillable.dto.request;


import com.zenz.chillable.dto.response.RoomResponse;
import com.zenz.chillable.entity.Room;
import com.zenz.chillable.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RoomRequest {

    private UUID userId;
    private String username;


}
