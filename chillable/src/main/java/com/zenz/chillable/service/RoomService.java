package com.zenz.chillable.service;


import com.zenz.chillable.dto.request.RoomRequest;
import com.zenz.chillable.dto.response.RoomResponse;
import com.zenz.chillable.entity.Room;
import com.zenz.chillable.entity.User;
import com.zenz.chillable.repository.RoomRepository;
import com.zenz.chillable.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    //first we should validate the user that it exist to create the room

    @Transactional
    public RoomResponse create(RoomRequest roomRequest){


        //get the user

        User user =new User();

        userRepository.findById(roomRequest.getUserId())
                .orElseThrow(()->
                new RuntimeException("User not found"));

        Room room = new Room();
        room.setName(roomRequest.getUsername());
        room.setUser(user);

        roomRepository.save(room);
        return RoomResponse.from(room);
    }

    @Transactional
    public Room getRoom(UUID id){
        return roomRepository.findById(id).orElseThrow(()-> new RuntimeException("not found"));


    }
}
