package com.adam.mobileserver.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//Builder 패턴으로 인스턴스를 생성하도록 해주는 어노테이션
@Builder
//모든 속성을 매개변수로 하는 생성자를 생성
@AllArgsConstructor
//매개변수가 없는 DefaultConstructor를 생성
@NoArgsConstructor

//getter, setter, toString 모두 생성
@Data

//모든 속성의 toString을 호출한 결과를 가지고 toString 을 생성
//member.toString은 제외
@ToString(exclude="member")
public class Item {
	@Id
	//기본키 값을 auto_increment 나 sequence를 이용해서 자동 생성
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long itemid;
	
	@Column(length=100, nullable = false)
	private String itemname;
	@Column
	private Integer price;
	@Column(length=200)
	private String description;
	@Column(length=255)
	private String pictureurl;
	
	//사용을 할 때 데이터를 가져오겠다는 옵션
	@ManyToOne(fetch=FetchType.LAZY)
	private Member member;
	
}
