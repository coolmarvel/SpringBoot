package com.example.thymeleaflayout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.thymeleaflayout.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
