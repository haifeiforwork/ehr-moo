/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * 결재 양식선택 서비스
 *
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApProcessViewService.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Transactional
public interface ApProcessViewService {

	/**
	 * 결재 프로세스 상태 데이터를 데이터를 XML형태로 반환.
	 * 
	 * @param instanceId
	 * @param res
	 */
	public String getApProcessXMLData(String apprId);

}
