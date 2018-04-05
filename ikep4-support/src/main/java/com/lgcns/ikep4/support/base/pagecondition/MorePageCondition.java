/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.base.pagecondition;


/**
 * 페이징 조건 (더보기)
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: MorePageCondition.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MorePageCondition extends CustomPageCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = 3370616911561356291L;

	/**
	 * 페이지당 아이템 건수
	 */
	private static final int DEFAULT_COUNT_OF_PAGE = 5;

	/**
	 * 시작 순번
	 */
	private static final int DEFAULT_START_ORDER = 1;

	/**
	 * 시작 순번
	 */
	private int startOrder;

	/**
	 * 종료 순번
	 */
	private int endOrder;

	/**
	 * 페이지당 조회건수
	 */
	private int countOfPage;

	/**
	 * 전체 데이터 수
	 */
	private int totalCount;

	/**
	 * 요청한 시작 순번
	 */
	private int requestStartOrder;


	/**
	 * 생성자
	 */
	public MorePageCondition() {
		startOrder = DEFAULT_START_ORDER;
		endOrder = DEFAULT_COUNT_OF_PAGE;
		countOfPage = DEFAULT_COUNT_OF_PAGE;
		requestStartOrder = DEFAULT_START_ORDER;
	}

	/**
	 * 페이징 계산
	 */
	public void calculate() {
		setStartOrder(getRequestStartOrder());
		setEndOrder(getStartOrder() + getCountOfPage() - 1);
		setRequestStartOrder(getEndOrder() + 1);
	}

	/**
	 * @return the startOrder
	 */
	public int getStartOrder() {
		return startOrder;
	}

	/**
	 * @param startOrder the startOrder to set
	 */
	public void setStartOrder(int startOrder) {
		this.startOrder = startOrder;
	}

	/**
	 * @return the endOrder
	 */
	public int getEndOrder() {
		return endOrder;
	}

	/**
	 * @param endOrder the endOrder to set
	 */
	public void setEndOrder(int endOrder) {
		this.endOrder = endOrder;
	}

	/**
	 * @return the countOfPage
	 */
	public int getCountOfPage() {
		return countOfPage;
	}

	/**
	 * @param countOfPage the countOfPage to set
	 */
	public void setCountOfPage(int countOfPage) {
		this.countOfPage = countOfPage;
	}

	/**
	 * @return the requestStartOrder
	 */
	public int getRequestStartOrder() {
		return requestStartOrder;
	}

	/**
	 * @param requestStartOrder the requestStartOrder to set
	 */
	public void setRequestStartOrder(int requestStartOrder) {
		this.requestStartOrder = requestStartOrder;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
