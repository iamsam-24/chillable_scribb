package com.zenz.chillable.dto.response;


import com.zenz.chillable.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomResponse {

    private String name;

    public static RoomResponse from(Room room){
        return new RoomResponse(
                room.getName()
        );
    }
}
