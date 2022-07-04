package com.adamsoft.todo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adamsoft.todo.dto.ResponseDTO;
import com.adamsoft.todo.dto.ToDoDTO;
import com.adamsoft.todo.model.ToDoEntity;
import com.adamsoft.todo.persistence.ToDoRepository;
import com.adamsoft.todo.service.ToDoService;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;

import lombok.RequiredArgsConstructor;

//데이터를 리턴하기 위한 COntroller를 만들기 위한 어노테이션
@RestController
//공통된 URL 작성 - localhost:포트번호/todo/
@RequestMapping("todo")
@RequiredArgsConstructor
public class ToDoController {
	private final ToDoService toDoService;
	
	//데이터 삽입
	@PostMapping
	public ResponseEntity<?> createToDo(@RequestBody ToDoDTO dto){	//데이터 조회
		try {
			//회원 정보를 만들 수 없어서 임시로 회원 아이디 설정
			String temporaryUserId = "temporary-user";
			//DTO를 entity로 변환
			ToDoEntity entity = ToDoDTO.toEntity(dto);
			entity.setId(null);
			entity.setUserId(temporaryUserId);
			//서비스의 삽입을 호출하고 결과를 저장
			List<ToDoEntity> entities = toDoService.create(entity);
			
			//TODOENTITY의 LIST를 TODODTO의 LIST로 변롼
			List<ToDoDTO> list = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
			
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(list).build();

			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	//데이터조회
	@GetMapping
	public ResponseEntity<?> retrieveToDoList(){
		String temporaryUserId = "temporary-user";
		
		List<ToDoEntity> entities = toDoService.retrieve(temporaryUserId);
		//ToDoEntity릐 List를 ToDoDTO의 LIST로 변환
		List<ToDoDTO> list = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		//응답객체를 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(list).build();
		//정상 응감 객체를 만든루 리턴
		return ResponseEntity.badRequest().body(response);
	}
	
	//데이터 수정
	@PutMapping
	public ResponseEntity<?> updateToDo(@RequestBody ToDoDTO dto){
		String temporaryUserId = "temporary-user";
		
		ToDoEntity entity = ToDoDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		
		List<ToDoEntity> entities = toDoService.update(entity);
		//ToDoEntity릐 List를 ToDoDTO의 LIST로 변환
		List<ToDoDTO> list = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		//응답객체를 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(list).build();
		//정상 응감 객체를 만든루 리턴
		return ResponseEntity.badRequest().body(response);
	}
	
	//데이터삭제
	@DeleteMapping
	public ResponseEntity<?> deleteToDo(@RequestBody ToDoDTO dto){
		String temporaryUserId = "temporary-user";
		
		ToDoEntity entity = ToDoDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		
		List<ToDoEntity> entities = toDoService.delete(entity);
		//ToDoEntity릐 List를 ToDoDTO의 LIST로 변환
		List<ToDoDTO> list = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
		//응답객체를 생성
		ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(list).build();
		//정상 응감 객체를 만든루 리턴
		return ResponseEntity.badRequest().body(response);
	}
	 
}
