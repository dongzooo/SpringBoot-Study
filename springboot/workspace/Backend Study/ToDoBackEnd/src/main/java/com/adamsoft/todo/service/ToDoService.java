package com.adamsoft.todo.service;

import java.util.List;

import com.adamsoft.todo.model.ToDoEntity;

public interface ToDoService {
	
	public List<ToDoEntity> create(final ToDoEntity entity);
	
	public List<ToDoEntity> retrieve(final String userId);
	
	public ToDoEntity detail(final String id);

	public List<ToDoEntity> update(final ToDoEntity entity);
	
	public List<ToDoEntity> delete(final ToDoEntity entity);	
	
	//지난번에는 DTO와 Entity의 변환을 Service에서 했습니다
	//장점은 DTO와 Entity 변환작업을 Service에서 호출하기 떄문에 코드 읽기가 편하다.
	//단점은 비즈니스 로직과 그렇지 않은 로직이 같이 있어서 역할의 경계까 애매하다 -> 유지보수 어려움

}
