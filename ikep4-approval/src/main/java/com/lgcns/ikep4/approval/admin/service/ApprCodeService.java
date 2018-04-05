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

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.admin.model.ApprCode;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * 결재 코드관리 서비스
 *
 * @author 박희진
 * @version $Id: ApprCodeService.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Transactional
public interface ApprCodeService extends GenericService<ApprCode, String> {
	
	public List<ApprCode> listGroupApprCodeByValue(String codeValue, String localeCode);
	
	public List<ApprCode> listRootApprCode(Map<String, String> map);
	
	public List<ApprCode> listGroupApprCode(String codeId);
	
	public ApprCode readApprCode(String codeId);

	public String createApprCode(ApprCode object);
	
	public void updateApprCode(ApprCode object);

	public void deleteApprCode(String codeId);
	
	public boolean existsChildApprCode(String parentCodeId);
	
	
	//- 추가 메소드
	public String getCodeIdByCodeValue(String codeValue, String portalId);
	
	public List<ApprCode> getApprCodeList(String codeId, String localeCode, String useage);
}
