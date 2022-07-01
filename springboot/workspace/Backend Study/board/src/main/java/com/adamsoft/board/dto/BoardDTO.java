package com.adamsoft.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	private Long bno;
	private String title;
	private String content;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	//작성자 정보
	private String memberEmail;
	private String memberName;
	
	//댓글의 개수
	private int replyCount;
}
