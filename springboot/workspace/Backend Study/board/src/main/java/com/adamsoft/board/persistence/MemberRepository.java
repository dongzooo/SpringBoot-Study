package com.adamsoft.board.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adamsoft.board.model.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

}
