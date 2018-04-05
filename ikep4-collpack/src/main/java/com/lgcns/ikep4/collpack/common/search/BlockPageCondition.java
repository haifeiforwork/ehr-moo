/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.common.search;

/**
 * 페이징조건 (Block 기능 추가) (더이상 사용하지 않음) support-common.base 로 이전됨
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: BlockPageCondition.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class BlockPageCondition extends PageCondition {

	/**
	 *
	 */
	private static final long serialVersionUID = -5129181337211199752L;

	/**
	 * 페이지 개수
	 */
	private static final int BLOCK_COUNT = 10;

	/**
	 * 현재 페이지
	 */
	private int page;

	/**
	 * 전체 페이지
	 */
	private int totalPage;

	/**
	 * 요청 페이지
	 */
	private int requestPage;

	/**
	 * 이전 페이지
	 */
	private int previousPage;

	/**
	 * 다음 페이지
	 */
	private int nextPage;

	/**
	 * 현재 표시되는 페이지 시작
	 */
	private int thisStartPage;

	/**
	 * 현재 표시되는 페이지 마지막
	 */
	private int thisEndPage;


	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return the requestPage
	 */
	public int getRequestPage() {
		return requestPage;
	}

	/**
	 * @param requestPage the requestPage to set
	 */
	public void setRequestPage(int requestPage) {
		this.requestPage = requestPage;
	}

	/**
	 * @return the previousPage
	 */
	public int getPreviousPage() {
		return previousPage;
	}

	/**
	 * @param previousPage the previousPage to set
	 */
	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	/**
	 * @return the nextPage
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * @param nextPage the nextPage to set
	 */
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * @return the thisStartPage
	 */
	public int getThisStartPage() {
		return thisStartPage;
	}

	/**
	 * @param thisStartPage the thisStartPage to set
	 */
	public void setThisStartPage(int thisStartPage) {
		this.thisStartPage = thisStartPage;
	}

	/**
	 * @return the thisEndPage
	 */
	public int getThisEndPage() {
		return thisEndPage;
	}

	/**
	 * @param thisEndPage the thisEndPage to set
	 */
	public void setThisEndPage(int thisEndPage) {
		this.thisEndPage = thisEndPage;
	}

	/**
	 * 페이징 계산
	 */
	@Override
	public void calculate() {
		super.calculate();

		int iBuf = 0;

		// 전체페이지계산
		setTotalPage((int)Math.ceil((double)getTotalCount() / (double)getCountOfPage()));
		// 요청페이지
		if (getTotalPage() < getRequestPage()) {
			setPage(getTotalPage());
		} else {
			setPage(getRequestPage());
		}
		// 현재 표시되는 페이지 시작
		setThisStartPage((int)((getPage() - 1) / BLOCK_COUNT) * BLOCK_COUNT + 1);
		// 현재 표시되는 페이지 마지막
		iBuf = getThisStartPage() + BLOCK_COUNT - 1;
		setThisEndPage((getTotalPage() < iBuf) ? getTotalPage() : iBuf);
		// 이전페이지
		iBuf = getPage() - BLOCK_COUNT;
		setPreviousPage((1 > iBuf) ? 1 : iBuf);
		// 다음페이지
		iBuf = getPage() + BLOCK_COUNT;
		setNextPage((getTotalPage() < iBuf) ? getTotalPage() : iBuf);
		// 시작순번
		setStartOrder((getPage() - 1) * getCountOfPage() + 1);
	}
}
