package com.zenz.chillable.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;

    private String correct_ans;


    @ManyToOne
    @JoinColumn(name = "room_id")

    private Room room;

    private LocalDateTime created_at;


}


