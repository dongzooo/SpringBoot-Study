package com.kwak.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kwak.movie.model.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long>{
    
}