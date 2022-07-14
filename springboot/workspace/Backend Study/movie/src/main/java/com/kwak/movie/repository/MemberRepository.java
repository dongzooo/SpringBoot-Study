package com.kwak.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kwak.movie.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
