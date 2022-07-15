package com.adam.mobileserver;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adam.mobileserver.dto.ItemDTO;
import com.adam.mobileserver.dto.MemberDTO;
import com.adam.mobileserver.dto.PageRequestItemDTO;
import com.adam.mobileserver.dto.PageResponseItemDTO;
import com.adam.mobileserver.service.ItemService;
import com.adam.mobileserver.service.MemberService;

@SpringBootTest
public class ServiceTest {
	@Autowired
	private MemberService memberService;

	//회원 가입 테스트
	//@Test
	public void testRegisterMember() {
		//처음 추가를 할 때는 성공해야 하고 email 과 name을 중복된 데이터로 설정해서 확인

		MemberDTO dto = MemberDTO.builder()
				.email("ggangpae1@gmail.com")
				.password("123456")
				.name("아담")
				.imageurl("adam.png")
				.build();
		String result = memberService.registerMember(dto);
		System.out.println(result);
	}

	//회원 정보 가져오기
	//@Test
	public void testGetMember() {
		MemberDTO dto = MemberDTO.builder()
				.email("ggangpae2@gmail.com")
				.build();
		MemberDTO result = memberService.getMemer(dto);
		System.out.println(result);
	}

	//로그인 테스트
	//@Test
	public void testLoginMember() {
		MemberDTO dto = MemberDTO.builder()
				.email("ggangpae1@gmail.com")
				.password("123456")
				.build();
		MemberDTO result = memberService.loginMemer(dto);
		System.out.println(result);

		//오늘 날짜를 문자열로 생성
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String d = sdf.format(date);
		try {
			FileOutputStream fos = new FileOutputStream(
					"C:\\Users\\tjoeun302\\lecture\\" + d + ".txt", true);
			PrintWriter pw = new PrintWriter(fos);
			pw.println("내용");
			pw.flush();
			pw.close();
		}catch(Exception e) {}

	}

	//멤버 수정 테스트
	//@Test
	public void updateMember() {
		MemberDTO dto = MemberDTO.builder()
				.email("ggangpae1@gmail.com")
				.password("123456")
				.name("군계")
				.imageurl("apple.png")
				.build();
		String result = memberService.updateMember(dto);
		System.out.println(result);
	}

	//멤버 삭제 테스트
	//@Test
	public void deleteMember() {
		MemberDTO dto = MemberDTO.builder()
				.email("ggangpae1@gmail.com")
				.build();
		String result = memberService.deleteMember(dto);
		System.out.println(result);
	}
	
	
	@Autowired
	private ItemService itemService;
	
	//데이터 삽입
	//@Test
	public void testInsertItem() {
		for(int i=0; i<100; i++) {
			ItemDTO dto = ItemDTO.builder()
					.itemname("apple_" + i)
					.price(3000)
					.description("사과_" + i)
					.pictureurl("apple_" + i + ".png")
					.email("ggangpae1@gmail.com")
					.build();
			Long itemid = itemService.registerItem(dto);
			System.out.println(itemid);
		}
	}
	
	//데이터 1개 가져오기
	//@Test
	public void testGetItem() {
		ItemDTO dto = ItemDTO.builder()
				.itemid(103L)
				.build();
		System.out.println(itemService.getItem(dto));
		dto = ItemDTO.builder()
				.itemid(10L)
				.build();
		System.out.println(itemService.getItem(dto));
	}
	
	//페이지 단위로 가져오기
	//@Test
	public void testGetList() {
		PageRequestItemDTO dto = PageRequestItemDTO.builder()
				.page(2)
				.size(10)
				.build();
		PageResponseItemDTO result = itemService.getList(dto);
		System.out.println(result);
	}
	
	//데이터 수정
	//@Test
	public void testUpdateItem() {
		ItemDTO dto = ItemDTO.builder()
				.itemid(103L)
				.itemname("apple_103")
				.price(3000)
				.description("사과_10311")
				.pictureurl("apple_10311.png")
				.email("ggangpae1@gmail.com")
				.build();
		Long itemid = itemService.updateItem(dto);
		System.out.println(itemid);
	}
	
	//Item 삭제
	//@Test
	public void testDeleteItem() {
		ItemDTO dto = ItemDTO.builder()
				.itemid(103L)
				.build();
		Long itemid = itemService.deleteItem(dto);
		System.out.println(itemid);
	}
}






