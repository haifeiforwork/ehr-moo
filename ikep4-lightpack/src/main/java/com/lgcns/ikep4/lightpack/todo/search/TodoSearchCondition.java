package com.lgcns.ikep4.lightpack.todo.search;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.web.SearchCondition;

public class TodoSearchCondition extends SearchCondition {
	private static final long serialVersionUID = 6059979642138466263L;
	
	/**
	 *사용자별  페이지당 조회 수
	*/
	private Integer pageViewNum;
	/**
	 *todo 검색 상태값
	*/
	private String todoStatus = "0";
	/**
	 *조회 시작일
	*/
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private Date fromDay;
	/**
	 *조회 종료일
	*/
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private Date toDay;
	/**
	 *검색타입
	*/
	private String searchType = "A";
	/**
	 *검색어
	*/
	private String searchText;
	/**
	 *지시자 아이디
	*/
	private String directorId;
	/**
	 *작업자 아이디
	*/
	private String workerId;
	/**
	 *시스템코드
	*/
	private String systemCode;
	/**
	 *업무종류
	*/
	private String subworkCode;
	/**
	 *페이지당 조회 갯수
	*/
	private Integer rowNum;

	/**
	 * @return the pageViewNum
	 */
	public Integer getPageViewNum() {
		return pageViewNum;
	}

	/**
	 * @param pageViewNum the pageViewNum to set
	 */
	public void setPageViewNum(Integer pageViewNum) {
		this.pageViewNum = pageViewNum;
	}

	/**
	 * @return the todoStatus
	 */
	public String getTodoStatus() {
		return todoStatus;
	}

	/**
	 * @param todoStatus the todoStatus to set
	 */
	public void setTodoStatus(String todoStatus) {
		this.todoStatus = todoStatus;
	}

	/**
	 * @return the fromDay
	 */
	public Date getFromDay() {
		return fromDay;
	}

	/**
	 * @param fromDay the fromDay to set
	 */
	public void setFromDay(Date fromDay) {
		this.fromDay = fromDay;
	}

	/**
	 * @return the toDay
	 */
	public Date getToDay() {
		return toDay;
	}

	/**
	 * @param toDay the toDay to set
	 */
	public void setToDay(Date toDay) {
		this.toDay = toDay;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	/**
	 * @return the directorId
	 */
	public String getDirectorId() {
		return directorId;
	}

	/**
	 * @param directorId the directorId to set
	 */
	public void setDirectorId(String directorId) {
		this.directorId = directorId;
	}

	/**
	 * @return the workerId
	 */
	public String getWorkerId() {
		return workerId;
	}

	/**
	 * @param workerId the workerId to set
	 */
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @return the subworkCode
	 */
	public String getSubworkCode() {
		return subworkCode;
	}

	/**
	 * @param subworkCode the subworkCode to set
	 */
	public void setSubworkCode(String subworkCode) {
		this.subworkCode = subworkCode;
	}

	/**
	 * @return the rowNum
	 */
	public Integer getRowNum() {
		return rowNum;
	}

	/**
	 * @param rowNum the rowNum to set
	 */
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
}
