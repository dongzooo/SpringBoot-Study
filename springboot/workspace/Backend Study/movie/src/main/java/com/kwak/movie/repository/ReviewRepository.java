package com.kwak.movie.repository;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kwak.movie.model.Member;
import com.kwak.movie.model.Movie;
import com.kwak.movie.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	//영화 정보를 가지고 리뷰를 가져오는 메서드
	//영화정보를 자세히 출력할 떄 필요
	@EntityGraph(attributePaths = {"속성이름"}, type=EntityGraph.EntityGraphType.FETCH)
	List<Review> findByMovie(Movie movie);
	
	//회원정보를 가지고 삭제하는 메서드
//	void deleteByMember(Member member);
	@Modifying
	@Query("delete from Review mr where mr.member = :member")
	void deleteByMember(@Param("member") Member member);
}
