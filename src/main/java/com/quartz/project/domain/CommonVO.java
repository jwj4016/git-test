package com.quartz.project.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CommonVO {
	LocalDateTime registDate;					//등록일시
	LocalDateTime updateDate;					//수정일시
	LocalDateTime deleteDate;					//삭제일시
	String status;								//상태값(0 - 사용, 1 - 미사용)
	
	@JsonIgnore
	LocalDateTime searchRegistDateStart;		//등록일시 검색을 위한 필드(검색 시작일)
	@JsonIgnore
	LocalDateTime searchRegistDateEnd;			//등록일시 검색을 위한 필드(검색 종료일)
}
