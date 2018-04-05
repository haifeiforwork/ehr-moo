/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.admin.model.ApprCode;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 결재 코드관리 DAO 정의
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApprCodeDao.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApprCodeDao extends GenericDao<ApprCode, String> {
	
	/**
	 * 코드관리 초기목록 가져오기
	 * 
	 * @return
	 */
	public List<ApprCode> listRootApprCode(Map<String, String> map);
	
	/**
	 * 코드 하위목록 가져오기
	 * 
	 * @return
	 */
	public List<ApprCode> listGroupApprCode(String codeId);
	
	/**
	 * 코드 하위목록 가져오기 [Korea]
	 * 
	 * @return
	 */
	public List<ApprCode> listGroupApprCodeByValueKr(String codeId);
	
	/**
	 * 코드 하위목록 가져오기 [English]
	 * 
	 * @return
	 */
	public List<ApprCode> listGroupApprCodeByValueEn(String codeValue);
	
	/**
	 * 코드 하위목록 유무
	 * @param parentCodeId
	 * @return
	 */
	public boolean existsChild(String parentCodeId);
	
	
	/**
	 * 코드관리 초기목록 가져오기
	 * 
	 * @return
	 */
	public List<ApprCode> getApprCodeList(Map<String, Object> map);
	
	/**
	 * 코드 하위목록 유무
	 * @param parentCodeId
	 * @return
	 */
	public String getCodeIdByCodeValue(Map<String, Object> map);
}
