package com.adamsoft.todo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adamsoft.todo.model.ToDoEntity;

public interface ToDoRepository extends JpaRepository<ToDoEntity, String> {
	//userId에 해당하는 ToDoEntity를 가져오는 메서드
	public List<ToDoEntity> findByUserId(String userid);
}
