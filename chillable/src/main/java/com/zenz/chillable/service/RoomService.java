package com.zenz.chillable.service;


import com.zenz.chillable.dto.request.RoomRequest;
import com.zenz.chillable.dto.response.RoomResponse;
import com.zenz.chillable.entity.Room;
import com.zenz.chillable.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomResponse create(RoomRequest roomRequest){
        Room room = new Room();

        room.setUser(roomRequest.getUser());

        roomRepository.save(room);
        return RoomResponse.from(room);
    }

    public Room getRoom(UUID id){
        return roomRepository.findById(id).orElseThrow(()-> new RuntimeException("not found"));


    }
}
