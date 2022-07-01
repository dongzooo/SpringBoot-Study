package com.adamsoft.board.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.adamsoft.board.model.Board;
import com.adamsoft.board.model.QBoard;
import com.adamsoft.board.model.QMember;
import com.adamsoft.board.model.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

	public SearchBoardRepositoryImpl() {
		super(Board.class);
	}

	@Override
	public Board search() {
		/*
		//결과를 Board Entity으로 받음

		//쿼리를 수행할 수 있는 Querydsl 객체를 찾아옵니다.
		QBoard board = QBoard.board;
		QMember member = QMember.member;
		QReply reply = QReply.reply;


		//쿼리 객체를 생성
		JPQLQuery<Board> jpqlQuery = from(board);

		//member 와 join
		jpqlQuery.leftJoin(member).on(board.member.eq(member));
		//reply 와 join
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

		//필요한 데이터를 조회하는 구문을 추가
		//조인한 데이터를 board 별로 묶어서 board 와 회원의 email 그리고 댓글의 개수 조회
		jpqlQuery.select(board, member.email, reply.count()).groupBy(board);

		//결과 가져오기
		List<Board> result = jpqlQuery.fetch();

		System.out.println(result);
		 */

		//결과를 Tuple로 받기
		QBoard board = QBoard.board;
		QMember member = QMember.member;
		QReply reply = QReply.reply;

		//Tuple은 관계형 데이터베이스에서는 하나의 행을 지칭하는 용어
		//프로그래밍에서는 일반적으로 여러 종류의 데이터가 묶여서 하나의 데이터를 나타내는 자료형
		//Map 과 다른 점은 Map은 key로 세부 데이터를 접근하지만 Tuple은 인덱스로도 접근이 가능하고
		//대부분의 경우 Tuple은 수정이 불가능
		JPQLQuery<Board> jpqlQuery = from(board);
		//member 와 join
		jpqlQuery.leftJoin(member).on(board.member.eq(member));
		//reply 와 join
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

		JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
		tuple.groupBy(board);

		//결과 가져오기
		List<Tuple> result = tuple.fetch();

		System.out.println(result);
		return null;
	}

	@Override
	public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
		//결과를 Tuple로 받기
		QBoard board = QBoard.board;
		QMember member = QMember.member;
		QReply reply = QReply.reply;

		//Tuple은 관계형 데이터베이스에서는 하나의 행을 지칭하는 용어
		//프로그래밍에서는 일반적으로 여러 종류의 데이터가 묶여서 하나의 데이터를 나타내는 자료형
		//Map 과 다른 점은 Map은 key로 세부 데이터를 접근하지만 Tuple은 인덱스로도 접근이 가능하고
		//대부분의 경우 Tuple은 수정이 불가능
		JPQLQuery<Board> jpqlQuery = from(board);
		//member 와 join
		jpqlQuery.leftJoin(member).on(board.member.eq(member));
		//reply 와 join
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

		//검색 결과를 만들어주는 부분
		JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

		//동적인 쿼리 수행을 위한 객체 생성
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		//bno 가 0보다 큰 데이터를 추출하는 조건
		BooleanExpression expression = board.bno.gt(0L);

		//type이 검색 항목
		if(type != null) {
			String [] typeArr = type.split("");
			BooleanBuilder conditionBuilder = new BooleanBuilder();
			for(String t:typeArr) {
				switch(t) {
				case "t":
					conditionBuilder.or(board.title.contains(keyword));
					break;
				case "c":
					conditionBuilder.or(board.content.contains(keyword));
					break;
				case "w":
					conditionBuilder.or(member.email.contains(keyword));
					break;
				}

			}
			booleanBuilder.and(conditionBuilder);
		}

		//조건 적용
		tuple.where(booleanBuilder);

		//데이터 정렬 - 하나의 조건으로만 정렬
		//tuple.orderBy(board.bno.desc());

		//정렬 조건 가져오기
		Sort sort = pageable.getSort();

		//설정된 모든 정렬 조건을 순회해서 tuple에 적용
		sort.stream().forEach(order -> {
			Order direction = order.isAscending()?Order.ASC:Order.DESC;
			String prop = order.getProperty();

			PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
			tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
		});

		//그룹화
		tuple.groupBy(board);

		//페이징 처리
		tuple.offset(pageable.getOffset());
		tuple.limit(pageable.getPageSize());

		//결과를 가져오기
		List<Tuple> result = tuple.fetch();

		//결과를 리턴
		return new PageImpl<Object[]>(
				result.stream().map(t->t.toArray()).collect(Collectors.toList()),
				pageable,
				tuple.fetchCount());

	}

}







