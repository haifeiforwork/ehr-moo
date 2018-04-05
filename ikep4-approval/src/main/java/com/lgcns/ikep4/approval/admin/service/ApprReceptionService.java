/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.admin.model.ApprReception;
import com.lgcns.ikep4.approval.work.model.ApprExam;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * 접수 담당자 서비스
 *
 * @author jeehye
 * @version $Id:  ApprReceptionService.java 16234 2011-08-18 02:44:36Z jeehye $
 */
public interface ApprReceptionService extends GenericService< ApprReception, String> {
	
	/**
	 * 접수 담당자 저장
	 * @param ApprReception 모델
	 * @return  void
	 */
	public void createApprReceptionSave(ApprReception apprReception);
	
	/**
	 * 접수 담당자 목록
	 * @param ApprReception 모델
	 * @return  List
	 */
	public List<ApprReception> listByReception(String groupId);
	
	/**
	 * 접수 담당자 여부 
	 * @param map
	 * @return  void
	 */
	public boolean existReceptionUser (Map<String, String> map);
	
}
