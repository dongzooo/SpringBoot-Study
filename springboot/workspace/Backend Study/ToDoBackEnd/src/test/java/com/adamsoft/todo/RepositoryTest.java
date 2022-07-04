package com.adamsoft.todo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adamsoft.todo.model.ToDoEntity;
import com.adamsoft.todo.persistence.ToDoRepository;

@SpringBootTest
public class RepositoryTest {
	@Autowired
	private ToDoRepository toDoRepository;

	//삽입 확인
	//@Test
	public void testInsert() {
		ToDoEntity toDoEntity = ToDoEntity.builder().userId("emp").title("테스트용 데이터").build();
		toDoRepository.save(toDoEntity);
	}

	//수정 확인
	//@Test
	public void testUpdate() {
		ToDoEntity toDoEntity = ToDoEntity.builder().
				id("40288ab680f442a30180f442ad6c0000").userId("emp")
				.title("수정 데이터").build();
		toDoRepository.save(toDoEntity);
		
	}
	
//	@Test
	public void testDetail() {
		Optional<ToDoEntity> result = 
				toDoRepository.findById("40288ab680f442a30180f442ad6c0000");
		
		//데이터가 존재할 떄
		if(result.isPresent()) {
			System.out.println(result.get());
		}else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
	}
	
//	@Test
	public void testList() {
		List<ToDoEntity> result = 
				toDoRepository.findByUserId("emp");
		
		//데이터가 존재할 떄
		if(result.size() > 0) {
			for(ToDoEntity todo : result) {
				System.out.println(todo);
			}
		}else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
	}
	
	public void testDelete() {
		toDoRepository.deleteById("40288ab680f442a30180f442ad6c0000");
	}
	
}
