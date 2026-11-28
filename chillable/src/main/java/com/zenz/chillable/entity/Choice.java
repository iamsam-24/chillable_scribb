package com.zenz.chillable.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Choice {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID id;

    private String choices;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Questions questions;






}
