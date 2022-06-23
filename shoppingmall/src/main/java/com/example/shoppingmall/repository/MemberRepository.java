package com.example.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
}
