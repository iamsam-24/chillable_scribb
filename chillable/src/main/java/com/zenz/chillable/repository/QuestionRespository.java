package com.zenz.chillable.repository;

import com.zenz.chillable.entity.Questions;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRespository extends JpaRepository<Questions,UUID> {
}
