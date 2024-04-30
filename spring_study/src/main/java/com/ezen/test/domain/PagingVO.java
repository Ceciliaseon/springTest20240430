package com.ezen.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Setter
@Getter
public class PagingVO {
	// select * from board limit 0,10
	// 피이징 => pageNo / qty
	// 검색 => type / keyword
	private int pageNo;
	private int qty; // 한 화면에 보여줄 게시글 수 (10)
	
	private String type; //검색할 타입 (title, writer, content 와 같은)
	private String keyword; // 검색어
	
	public PagingVO() {
		this.pageNo = 1;
		this.qty = 10;
	}
	
	public int getPageStart() {
		//DB 상에서 limit의 시작 번지를 구하는 메서드 
		// 1 > 0 / 2 > 10 / 3 > 20 ...
		return (this.pageNo-1)*this.qty;
	}
	
	// 여러가지의 타입을 같이 검색하기 위해서 타입을 배열로 구분
	// tcw 가 들어오면 t c w 하나씩 때서 배열로 저장
	public String[] getTypeToArray() {
		return this.type == null ? new String[] {} : this.type.split("");
	}
	
}
