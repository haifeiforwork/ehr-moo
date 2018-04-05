/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.model.ApFormTpl;
import com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition;


/**
 * 결재 양식관리 서비스
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormService.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Transactional
public interface ApFormService extends GenericService<ApForm, String> {
	public SearchResult<ApForm> listApFormAll(ApFormSearchCondition apFormSearchCondition);

	public ApForm readApForm(String formId);

	public String createApForm(ApForm object);

	public String createApFormTpl(ApFormTpl object, User user);

	public void updateApForm(ApForm object);

	public void updateApFormTpl(ApFormTpl object, User user);

	public void deleteApForm(String formId);
	
	public boolean existsApForm(String formId);
	
	public boolean existsApFormTpl(String formId);
}
