package com.adamsoft.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adamsoft.board.dto.BoardDTO;
import com.adamsoft.board.dto.PageRequestDTO;
import com.adamsoft.board.dto.PageResultDTO;
import com.adamsoft.board.dto.ReplyDTO;
import com.adamsoft.board.service.BoardService;
import com.adamsoft.board.service.ReplyService;

@SpringBootTest
public class ServiceTest {
	@Autowired
	private BoardService boardService;
	
	//@Test
	public void testRegister() {
		BoardDTO dto = BoardDTO.builder().title("Test").content("Test...")
				.memberEmail("ggangpae55@aaa.com")
				.build();
		
		Long bno = boardService.register(dto);
		System.out.println(bno);
	}
	
	//@Test
	public void testList() {
		PageRequestDTO pageRequestDTO = new PageRequestDTO();
		
		PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
		
		for(BoardDTO boardDTO : result.getDtoList()){
			System.out.println(boardDTO);
		}
	}
	
	//@Test
	public void testGetBoard() {
		Long bno = 40L;
		BoardDTO boardDTO = boardService.getBoard(bno);
		System.out.println(boardDTO);
	}
	
	//@Test
	public void testDeleteBoard() {
		Long bno = 40L;
		boardService.removeWithReplies(bno);
	}
	
//	@Test
	public void testModifyBoard() {
		BoardDTO boardDTO = BoardDTO.builder().bno(1L)
				.title("제목을 수정").content("내용을 수정").build();
		boardService.modifyBoard(boardDTO);
	}
	
	@Autowired
	private ReplyService replyService;
	
	//댓글 목록 가져오기 테스트
	@Test
	public void testGetList() {
		Long bno = 7L;
		List<ReplyDTO> replyDTOList = replyService.getList(bno);
		replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));
	}
	
}
