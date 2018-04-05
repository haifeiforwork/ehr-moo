/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.annotations;

/**
 * 접근 권한 Attribute 어노테이션<br/>
 * <p/>
 * - 사용 예시 : <br/>
 * className : 권한 자원 종류 = "BOARD"<br/>
 * operationName : 권한 자원에 대한 오퍼레이션 = "READ" 또는 {"READ","WRITE"} <br/>
 * resourceName : 권한 자원의 이름이 저장된 변수명 or static한 리소스이름 = "boardId"<br/>
 * 
 * <pre>
 * <code>
 * @Attribute(className="BOARD", operationName={"READ","WRITE"}, resourceName="boardId")
 * <code>
 * </pre>
 * 
 * @author 주길재
 * @version $Id: Attribute.java 16258 2011-08-18 05:37:22Z giljae $
 */
public @interface Attribute {
	/**
	 * Access Type을 기술
	 * @return
	 */
	AccessType type();
	
	/**
	 * 권한 자원의 종류를 기술
	 * 
	 * @return
	 */
	String className();

	/**
	 * 권한 자원에 대한 오퍼레이션을 기술
	 * 
	 * @return
	 */
	String[] operationName();

	/**
	 * 권한 자원을 담고 있는 파라메터명을 기술<br/>
	 * Value Object일 경우엔 권한 자원을 담고 있는 멤버필드명을 기술
	 * 
	 * @return
	 */
	String resourceName();
}
