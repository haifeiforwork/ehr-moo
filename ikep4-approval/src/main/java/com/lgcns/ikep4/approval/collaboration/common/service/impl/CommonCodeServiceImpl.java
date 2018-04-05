/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.collaboration.common.dao.CommonCodeDao;
import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.approval.collaboration.common.service.CommonCodeService;


/**
 * 공통코드 CommonCodeService 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Service
public class CommonCodeServiceImpl implements CommonCodeService{
	
	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());
	
	/** The CommonCode dao. */
	@Autowired
	private CommonCodeDao commonCodeDao;
	
	/**
	 * 공통코드 목록 조회
	 * @param grpCd
	 * @return
	 * @throws Exception
	 */
	public List<CommonCode> getCommonCodeList( String grpCd) throws Exception {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put( "grpCd", grpCd);
		
		return getCommonCodeList( paramMap);
	}
	
	/**
	 * 공통코드 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CommonCode> getCommonCodeList( Map<String, String> paramMap) throws Exception {
		
		return commonCodeDao.getCommonCodeList( paramMap);
	}
}
