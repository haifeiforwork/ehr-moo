/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.todo.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: TodoConstants.java 16297 2011-08-19 07:52:43Z giljae $
 */

public class TodoConstants extends BaseObject {

	private static final long serialVersionUID = -373329980454771280L;

	/**
	 * 작업등록 TODO용<br/>
	 * 시스템코드
	 */
	public static final String TODO_SYSTEM_CODE = "IKEP";	

	/**
	 * 작업등록 TODO용<br/>
	 * 업무종류
	 */
	public static final String TODO_SUBWORK_CODE = "TASK";
	
	/**
	 * 작업등록 TODO용<br/>
	 * 업무종류
	 */
	public static final String TODO_SUBWORK_CODE2 = "MYTASK";
	
	/**
	 * 작업 URL
	 */
	public static final String TASK_URL = "/lightpack/todo/readTodoView.do";
	
	/**
	 * INTERFACE용 에러
	 */
	public static final String TODO_ERROR_DATA_EMPTY = "EMPTY DATA : ";

	/**
	 * INTERFACE용 에러
	 */
	public static final String TODO_ERROR_KEY_NOT_FOUND = "DATA NOT FOUND";

}