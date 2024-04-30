package com.ezen.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class FileVO {
	private String uuid;
	private String save_dir;
	private String file_name;
	private int file_type;
	private int bno;
	private long file_size; // 파일 사이즈는 long 형태로 리턴이 되기 때문에 long으로 설정
	private String reg_Date;
	
}