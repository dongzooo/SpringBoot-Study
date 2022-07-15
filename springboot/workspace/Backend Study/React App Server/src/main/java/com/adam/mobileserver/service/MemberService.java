package com.adam.mobileserver.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;

import com.adam.mobileserver.dto.MemberDTO;
import com.adam.mobileserver.model.Member;

public interface MemberService {
	//데이터 삽입
	public String registerMember(MemberDTO dto);
	public MemberDTO loginMemer(MemberDTO memberDTO);
	public MemberDTO getMemer(MemberDTO memberDTO);
	public String updateMember(MemberDTO dto);
	public String deleteMember(MemberDTO dto);
	public ResponseEntity<Object> download(String path);
	
	//DTO 클래스의 객치를 Model 클래스의 객체로 변환
	public default Member dtoToEntity(MemberDTO dto) {
		String password = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
		Member member = Member.builder()
				.email(dto.getEmail())
				.name(dto.getName())
				.password(password)
				.imageurl(dto.getImageurl())
				.lastlogindate(dto.getLastlogindate())
				.build();
		
		return member;
	}
	
	//Model 객체를 DTO 클래스로 변환
	public default MemberDTO entityToDto(Member member) {
		MemberDTO dto = MemberDTO.builder()
				.email(member.getEmail())
				.name(member.getName())
				.imageurl(member.getImageurl())
				.regdate(member.getRegDate())
				.moddate(member.getModDate())
				.lastlogindate(member.getLastlogindate())
				.build();
		
		return dto;
	}
}
