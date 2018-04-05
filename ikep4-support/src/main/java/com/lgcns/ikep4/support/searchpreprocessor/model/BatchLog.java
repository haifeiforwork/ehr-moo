/**
 * LG CNS IKEP4 
 */
package com.lgcns.ikep4.support.searchpreprocessor.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BatchLog extends SpBaseObject {
	/**    
	 *
	 */
	private static final long serialVersionUID = -7451924595722760564L;

	/**
	 * ID[배치 프로그램 실행 ID]
	 */
	private String id;

	/**
	 * 배치이름
	 */
	private String name;

	/**
	 * RESULT[배치 프로그램 실행 결과( 0 : 성공, 1 : 실패)]
	 */
	private String result;

	/**
	 * START_DATE[배치 프로그램 실행 시작일시]
	 */
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date startDate;

	/**
	 * END_DATE[배치 프로그램 실행 종료일시]
	 */
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date endDate;

	/**
	 * DESCRIPTION[배치 프로그램 실행 결과 설명 (성공인 경우 적용대상 검색어 총 갯수, 실패인 경우 실패사유 또는 에러코드)]
	 */
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result == null ? null : result.trim();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}