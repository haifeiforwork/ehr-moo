/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.security.acl.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 접근 권한  어노테이션<br/>
 * <p/>
 * - 사용 예시 : <br/>
 * @IsAccess annotation은 컨트롤러 클래스 메소드 파라메터에 선언한다.
 * 
 * <pre>
 * <code>
 * //단일 Attribute일 경우
 * public String getForm(<b>@IsAccess(@Attribute(className="BOARD", operationName={"READ","WRITE"}, resourceName="boardId")) String boardId, AccessingResult result</b>) { //AccessingResult parameter를 명시하여 접근 권한 결과를 받는다.
 * if(!result.isAccess) { //true:승인, false:비승인
 * //접근 권한이 없을 경우 처리 하는 로직 작성	
 * }
 *  
 * return "...";
 * }
 * 
 * //멀티 Attribute일 경우
 * public String getForm(<b>@IsAccess({@Attribute(className="BOARD", operationName={"READ,"WRITE"}, resourceName="qaBoard"),@Attribute(className="BOARD", operationName={"READ","WRITE"}, resourceName="boardId")}) String boardId, AccessingResult result</b>) { //AccessingResult parameter를 명시하여 접근 권한 결과를 받는다.
 * if(!result.isAccess) { //true:승인, false:비승인
 * //접근 권한이 없을 경우 처리 하는 로직 작성	
 * }
 *  
 * return "...";
 * }
 * <code>
 * </pre>
 *
 * @author 주길재
 * @version $Id: IsAccess.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsAccess {
	/**
	 * 접근 권한 Attribute를 설정
	 * @return
	 */
	public abstract Attribute[] value();
}
