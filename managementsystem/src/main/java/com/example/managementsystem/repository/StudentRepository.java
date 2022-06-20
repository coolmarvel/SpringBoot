package com.example.managementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.managementsystem.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
