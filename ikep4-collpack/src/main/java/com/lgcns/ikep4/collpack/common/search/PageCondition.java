/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.common.search;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 페이징 조건 (더이상 사용하지 않음) support-common.base 로 이전됨
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: PageCondition.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class PageCondition extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -7359971992761931613L;

	/**
	 * 보기유형 (목록보기:0, 요약보기:1, 간단목록보기:2)
	 */
	private int viewType;

	/**
	 * 레이아웃 (2프레임:0, 1프레임:1)
	 */
	private int layoutType;


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
	 * 재설정 여부 (1:재설정)
	 */
	private boolean reInit;

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

	/**
	 * @return the reInit
	 */
	public boolean isReInit() {
		return reInit;
	}

	/**
	 * @param reInit the reInit to set
	 */
	public void setReInit(boolean reInit) {
		this.reInit = reInit;
	}

	/**
	 * @return the viewType
	 */
	public int getViewType() {
		return viewType;
	}

	/**
	 * @param viewType the viewType to set
	 */
	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	/**
	 * @return the layoutType
	 */
	public int getLayoutType() {
		return layoutType;
	}

	/**
	 * @param layoutType the layoutType to set
	 */
	public void setLayoutType(int layoutType) {
		this.layoutType = layoutType;
	}

}
