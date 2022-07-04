package com.adamsoft.todo.dto;

import java.time.LocalDateTime;

import com.adamsoft.todo.model.ToDoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToDoDTO {
	private String id;
	private String title;
	private boolean done; 
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	public ToDoDTO(final ToDoEntity todo) {
		this.id = todo.getId();
		this.title = todo.getTitle();
		this.done = todo.isDone();
		this.regDate = todo.getRegDate();
		this.regDate = todo.getModDate();
		
	}
	
	public static ToDoEntity toEntity(final ToDoDTO dto) {
		return ToDoEntity.builder()
			.id(dto.getId())
			.title(dto.getTitle())
			.done(dto.isDone()) 
			.build();
	}

	
}
