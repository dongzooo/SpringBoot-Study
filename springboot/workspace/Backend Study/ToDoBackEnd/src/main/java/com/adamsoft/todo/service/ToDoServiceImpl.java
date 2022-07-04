package com.adamsoft.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.adamsoft.todo.model.ToDoEntity;
import com.adamsoft.todo.persistence.ToDoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {
	//유효성 검사 메서드 인터페이스에서 만들면 public 이여서 외부호출 가능해짐
	// 외부해서 로출할 수 없더록 impl에서 private으로 구현하고 외부에서 호출가능하게 하려면
	// Service 인터페이스에 default로 만들면 된다.
	public final ToDoRepository toDoRepository;
	
	private void validate(final ToDoEntity entity) {
		if(entity == null) {
			log.warn("Entity is Null");
			throw new RuntimeException("Entity cannot be null");
		}
		if(entity.getUserId()==null) {
			log.warn("Unknowm User");
			throw new RuntimeException("Unknown user");
		}
	}
	
	@Override
	//데이터삽입
	public List<ToDoEntity> create(ToDoEntity entity) {
		// 유효성 검사
		validate(entity);
		//데이터 삽입
		toDoRepository.save(entity);
		//삽입한 유저의 테이블을 조회해서 리턴
		return toDoRepository.findByUserId(entity.getUserId());
	}
	
	@Override
	//데이터 조회
	public List<ToDoEntity> retrieve(String userId) {
		// 유저의 데이터를 전부 조회해서 리턴
		return toDoRepository.findByUserId(userId);
	}

	@Override
	//데이터 상세보기
	public ToDoEntity detail(String id) {
		ToDoEntity toDo = null;
		Optional<ToDoEntity> result = toDoRepository.findById(id);
		if(result.isPresent()) {
			toDo = result.get();
		}
		
		return toDo;
	}

	@Override
	//데이터 수정
	public List<ToDoEntity> update(ToDoEntity entity) {
		validate(entity);
		//데이터 존재여부 확인
		ToDoEntity toDo = detail(entity.getId());
		//데이터가 존재하면 수정
		if(toDo != null) {
			toDoRepository.save(entity);
		}
		return toDoRepository.findByUserId(entity.getUserId());
	}

	@Override
	public List<ToDoEntity> delete(ToDoEntity entity){
		validate(entity);
		//데이터 존재여부 확인
		ToDoEntity toDo = detail(entity.getId());
		//데이터가 존재하면 수정
		if(toDo != null) {
			toDoRepository.delete(entity);
		}
		
		return toDoRepository.findByUserId(entity.getUserId());
	}
}
