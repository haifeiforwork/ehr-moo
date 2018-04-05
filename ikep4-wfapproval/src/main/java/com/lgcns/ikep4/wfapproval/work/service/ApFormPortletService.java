/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet;

/**
 * 결재 양식 포틀릿 서비스
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormPortletService.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Transactional
public interface ApFormPortletService extends GenericService<ApFormPortlet, String> {
	
	/**
	 * 양식 포틀릿 데이터
	 * 
	 * @param apFormPortlet
	 * @return
	 */
	public List<ApFormPortlet> listApFormPortlet(ApFormPortlet apFormPortlet);
	
	/**
	 * 양식 포틀릿 데이터 추가
	 * 
	 * @param apFormPortlet
	 * @return
	 */
	public void addApFormPortlet(ApFormPortlet apFormPortlet);
}
