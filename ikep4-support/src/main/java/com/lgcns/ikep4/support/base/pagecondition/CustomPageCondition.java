/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.base.pagecondition;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 페이징 조건
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: CustomPageCondition.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class CustomPageCondition extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -2543668344071403253L;

	/**
	 * 정렬방식 오름차순
	 */
	public static final int ASC = 0;

	/**
	 * 정렬방식 내림차순
	 */
	public static final int DESC = 1;

	/**
	 * 모든 상속 클래스를 위한 로그
	 */
	protected final Log log = LogFactory.getLog(this.getClass());

	/**
	 * 보기유형 (목록보기:0, 요약보기:1, 간단목록보기:2)
	 */
	private int viewType;

	/**
	 * 레이아웃 (2프레임:0, 1프레임:1)
	 */
	private int layoutType;

	/**
	 * 최초 화면 접근 여부 <br/>
	 * 최초일경우 초기 값을 세팅해야 한다.
	 */
	private boolean notFirstAccess;

	/**
	 * 재설정 여부
	 */
	private boolean reInit;

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

	/**
	 * @return the notFirstAccess
	 */
	public boolean isNotFirstAccess() {
		return notFirstAccess;
	}

	/**
	 * @param notFirstAccess the notFirstAccess to set
	 */
	public void setNotFirstAccess(boolean notFirstAccess) {
		this.notFirstAccess = notFirstAccess;
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

}
