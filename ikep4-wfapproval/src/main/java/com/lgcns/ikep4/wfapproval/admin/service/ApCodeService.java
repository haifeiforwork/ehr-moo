/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;

/**
 * 결재 코드관리 서비스
 *
 * @author 박희진
 * @version $Id: ApCodeService.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApCodeService extends GenericService<ApCode, String> {
	
	public List<ApCode> listRootApCode(String sLevel);
	
	public List<ApCode> listGroupApCode(String codeId);
	
	public List<ApCode> listGroupApCodeByValue(String codeValue, String localeCode);
	
	public ApCode readApCode(String codeId);

	public String createApCode(ApCode object);

	public void updateApCode(ApCode object);

	public void deleteApCode(String codeId);
	
	public boolean existsChildApCode(String parentCodeId);
}
