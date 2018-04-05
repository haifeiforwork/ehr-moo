/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service;


import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprReference;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * 참조자 의견 서비스
 *
 * @author 
 * @version $Id: ApprReferenceService.java 16234 2011-08-18 02:44:36Z jeehye $
 */
public interface ApprReferenceService extends GenericService<ApprReference, String> {

	/**
	 * 참조자 의견 삭제
	 * @param map
	 */
	public void remove(Map<String, String> map);
	
	/**
	 * 참조 의견 입력
	 */
	public void update(ApprReference object);
	/**
	 * 참조자 조회정보 등록
	 * @param map
	 */
	public void updateRead(Map<String, String> map);
	
	/**
	 * 참조자 의견 목록조회
	 * @param apprId
	 * @return
	 */
	public List<ApprReference>	list(String	apprId);
	
	/**
	 * 참조자 존재유무
	 * @param map
	 * @return
	 */
	public boolean exists(Map<String, String> map);
	
	/**
	 * 참조자 정보조회
	 * @param map
	 */
	public ApprReference get(Map<String, String> map);
}
