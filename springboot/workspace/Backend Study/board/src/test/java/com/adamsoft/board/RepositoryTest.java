package com.adamsoft.board;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.adamsoft.board.model.Board;
import com.adamsoft.board.model.Member;
import com.adamsoft.board.model.Reply;
import com.adamsoft.board.persistence.BoardRepository;
import com.adamsoft.board.persistence.MemberRepository;
import com.adamsoft.board.persistence.ReplyRepository;

@SpringBootTest
public class RepositoryTest {

	@Autowired
	private MemberRepository memberRepository;
	
	//@Test
	public void insertMembers() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder().email("ggangpae" + i + "@aaa.com").password("1111")
					.name("사용자" + i).build();
			memberRepository.save(member);
		});
	}
	
	@Autowired
	private BoardRepository boardRepository;
	
	//@Test
	public void insertBoards() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Member member = Member.builder().email("ggangpae1@aaa.com").build();
			Board board = Board.builder()
					.title("제목..." + i)
					.content("내용..." + i)
					.member(member)
					.build();
			boardRepository.save(board);
			
		});
	}
	
	@Autowired
	private ReplyRepository replyRepository;
	
	//@Test
	public void insertReplys() {
		IntStream.rangeClosed(1, 300).forEach(i -> {
			//1부터 100 사이의 정수를 랜덤하게 생성해서 Board 객체 생성
			long bno = (long)(Math.random() * 100) + 1;
			Board board = Board.builder().bno(bno).build();
			
			Reply reply = Reply.builder()
					.text("댓글..." + i)
					.board(board)
					.build();
			replyRepository.save(reply);
			
		});
	}
	
	//하나의 Board 데이터를 조회하는 메서드
	@Transactional
	//@Test
	public void readBoard() {
		Optional<Board> result = boardRepository.findById(100L);
		//데이터를 출력
		System.out.println(result.get());
		System.out.println(result.get().getMember());
		
	}
	
	//하나의 Reply 데이터를 조회하는 메서드
	//@Test
	public void readReply() {
		Optional<Reply> result = replyRepository.findById(410L);
		//데이터를 출력
		System.out.println(result.get());
		System.out.println(result.get().getBoard());
		
	}

	//@Test
	public void testReadWithWriter() {
		//데이터 조회
		Object result = boardRepository.getBoardWithMember(100L);
		//JPQL의 결과가 Object 인 경우는 Object[] 로 강제 형 변환해서 사용
		//
		System.out.println(Arrays.toString((Object[])result));
	}
	
	//@Test
	public void testGetBoardWithReply() {
		List<Object []> result = boardRepository.getBoardWithReply(40L);
		for(Object [] ar : result) {
			System.out.println(Arrays.toString(ar));
		}
	}
	
	//@Test
	public void testWithReplyCount() {
		Pageable pageable = PageRequest.of(0,  10, Sort.by("bno").descending());
		Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
		
		result.get().forEach(row -> {
			Object [] ar = (Object[])row;
			System.out.println(Arrays.toString(ar));
		});
	}
	
	//@Test
	public void testWithByBno() {
		Object result = boardRepository.getBoardByBno(40L);
	
		Object [] ar = (Object[])result;
		System.out.println(Arrays.toString(ar));
	}
	
	//@Test
	public void testSearch() {
		boardRepository.search();
	}
	
	//@Test
	public void testSearchPage() {
		Pageable pageable = PageRequest.of(0,  10, 
				Sort.by("bno").descending().and(Sort.by("title").ascending()));
		Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
		System.out.println(result);
	}
	
	@Test
	public void testListByBoard() {
		List<Reply> replyList = 
				replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(7L).build());
		//System.out.println(replyList); 는 실제로는 System.out.println(replyList.toString());
		//List의 toString은 자신의 멤버 각각의 멤버.toString()을 호출합니다.
		System.out.println(replyList);
	}
}





