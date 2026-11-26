package com.zenz.chillable.controller;


import com.zenz.chillable.dto.request.RoomRequest;
import com.zenz.chillable.dto.response.RoomResponse;
import com.zenz.chillable.entity.Room;
import com.zenz.chillable.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping

    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest roomRequest){
        RoomResponse roomResponse= roomService.create(roomRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(roomResponse);
    }


    @GetMapping("/{id}")

    public ResponseEntity<Room> get(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getRoom(id));
    }




}
