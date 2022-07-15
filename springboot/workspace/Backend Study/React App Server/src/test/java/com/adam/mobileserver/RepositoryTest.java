package com.adam.mobileserver;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.adam.mobileserver.model.Item;
import com.adam.mobileserver.model.Member;
import com.adam.mobileserver.persistence.ItemRepository;
import com.adam.mobileserver.persistence.MemberRepository;


@SpringBootTest
public class RepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	//Member에 데이터 삽입
	@Test
	public void testRegisterMember() {
		String password = BCrypt.hashpw("123456", BCrypt.gensalt());
		Member member = 
				Member.builder()
				.email("ggangpae1@gmail.com")
				.password(password)
				.name("아담")
				.imageurl("adam.png")
				.build();

		memberRepository.save(member);
	}

	//회원 정보 가져오기 - 수정이나 로그인에서 사용
	//@Test
	public void testGetMember() {
		Optional<Member> optional = memberRepository.findById("ggangpae1@gmail.com");
		if(optional.isPresent()) {
			Member member = optional.get();
			System.out.println(member);
			//로그인은 이렇게 데이터를 가져와서 비밀번호를 비교하면 됩니다.
		}else {
			System.out.println("존재하지 않는 데이터 입니다.");
		}
	}

	//데이터 수정
	//@Test
	public void testUpdateMember() {
		String password = BCrypt.hashpw("111111", BCrypt.gensalt());
		Member member = 
				Member.builder()
				.email("ggangpae1@gmail.com")
				.password(password)
				.name("군계")
				.imageurl("user.png")
				.build();

		memberRepository.save(member);
	}

	//데이터 삭제
	@Test
	public void testDeleteMember() {
		Member member = 
				Member.builder()
				.email("ggangpae1@gmail.com")
				.build();

		memberRepository.delete(member);

	}

	//이름으로 데이터 조회
	//@Test
	public void testFindName() {
		String name = "아담";
		List<Member> list = memberRepository.findMemberByName(name);
		System.out.println(list);

		name = "군계";
		list = memberRepository.findMemberByName(name);
		System.out.println(list);

		if(list.size() > 0) {
			System.out.println("데이터가 존재합니다.");
		}else {
			System.out.println("데이터가 존재하지 않습니다.");
		}

	}

	@Autowired
	private ItemRepository itemRepository;

	//Item 삽입 테스트
	//@Test
	public void testInsertItem() {
		//외래키를 생성
		Member member = Member.builder()
				.email("ggangpae1@gmail.com")
				.build();
		for(int i=0; i<100; i++) {
			Item item = Item.builder()
					.itemname("사과_" + i)
					.price(2500)
					.description("비타민 C")
					.pictureurl("apple.png")
					.member(member)
					.build();
			itemRepository.save(item);
		}
	}

	//데이터 전체 보기 테스트
	//@Test
	public void getAll() {
		List<Item> list = itemRepository.findAll();
		System.out.println(list);
	}

	//페이징 과 정렬
	//@Test
	public void getPaging() {
		Sort sort = Sort.by("itemid").descending();
		Pageable pageable = PageRequest.of(0, 10, sort);
		Page<Item> page = itemRepository.findAll(pageable);
		page.get().forEach(item -> {
			System.out.println(item);
		});
	}

	//외래키를 이용한 조회
	//@Test
	public void getFindMember() {
		Member member = Member.builder().
				email("ggangpae1@gmail.com")
				.build();
		List<Item> list = itemRepository.findItemByMember(member);
		System.out.println(list);
	}

	//데이터 1개 가져오기
	//@Test
	public void getItem() {
		Optional<Item> item = itemRepository.findById(200L);
		if(item.isPresent()) {
			System.out.println(item);
		}else {
			System.out.println("데이터가 존재하지 않습니다.");
		}
	}

	//데이터 
	//@Test
	public void updateItem() {
		Member member = Member.builder().
				email("ggangpae1@gmail.com")
				.build();

		Item item = Item.builder()
				.itemid(100L)
				.itemname("배")
				.price(3400)
				.pictureurl("pear.png")
				.member(member)
				.build();
		itemRepository.save(item);
	}

	//데이터 삭제 
	//@Test
	public void deleteItem() {
		Item item = Item.builder()
				.itemid(100L)
				.build();
		itemRepository.delete(item);
	}

}
