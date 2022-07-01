package com.adamsoft.board.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.adamsoft.board.dto.ReplyDTO;
import com.adamsoft.board.model.Board;
import com.adamsoft.board.model.Reply;
import com.adamsoft.board.persistence.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	 private final ReplyRepository replyRepository;
	@Override
	public Long register(ReplyDTO replyDTO) {
		Reply reply = dtoToEntity(replyDTO);
		replyRepository.save(reply);
		return reply.getRno();
	}

	@Override
	public List<ReplyDTO> getList(Long bno) {
		List<Reply> result = 
				replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(bno).build());
		
		result.sort(new Comparator<Reply>() {

			@Override
			public int compare(Reply o1, Reply o2) {
				// 수정시간의 내림차순
				//오름차순의 걍우 01과 02의 위치 바꾸기
				return o2.getModDate().compareTo(o1.getModDate());
			}
		});
		return result.stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
	}

	@Override
	public void modify(ReplyDTO replyDTO) {
		// TODO Auto-generated method stub
		Reply reply = dtoToEntity(replyDTO);
		replyRepository.save(reply);

	}

	@Override
	public void remove(Long rno) {
		replyRepository.deleteById(rno);
	}
	
	

}
