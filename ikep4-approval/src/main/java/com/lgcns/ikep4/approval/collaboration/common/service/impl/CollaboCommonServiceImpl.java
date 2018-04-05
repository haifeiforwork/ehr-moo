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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.collaboration.common.dao.CollaboCommonDao;
import com.lgcns.ikep4.approval.collaboration.common.dao.CommonCodeDao;
import com.lgcns.ikep4.approval.collaboration.common.service.CollaboCommonService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 공통코드 ColaboCommonService 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Service
public class CollaboCommonServiceImpl implements CollaboCommonService{
	
	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());
	
	/** The ColaboCommonDao dao. */
	@Autowired
	private CollaboCommonDao collaboCommonDao;
	
	/** The User dao. */
	@Autowired
	private UserDao userDao;
	
	/** The CommonCodeDao dao. */
	@Autowired
	private CommonCodeDao commonCodeDao;
	
	/**
	 * 임직원 메일주소 목록
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<?> getUserMailAddrList(Map<String, Object> paramMap) throws Exception {
		
		return collaboCommonDao.getUserMailAddrList( paramMap);
	}
	
	/**
	 * EMC User 확인
	 * @param user
	 * @return
	 */
	public boolean isEcmUser( User user) {
		
		boolean isEmcUser = false;
		// To-do반영시 체크해제할것
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", user.getUserId());
		emap.put("roleName", "ECM");
		int ecmRole = userDao.getUserRoleCheck(emap);
		if(ecmRole > 0){
			isEmcUser = true;
		}
		return isEmcUser;
	}
	
	/**
	 * 해당 사원의 부서 팀장 조회
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTeamLeaderInfo( String empNo) throws Exception {
		
		return commonCodeDao.getTeamLeaderInfo( empNo);
	}
	
	/**
	 * 메일주소 조회
	 * @param empNO
	 * @return
	 * @throws Exception
	 */
	public String getUserMailAddr(String empNO) throws Exception{
		
		String mailAddr = collaboCommonDao.getUserMailAddr( empNO);
		
		if( StringUtils.isEmpty( mailAddr)) {
			
			mailAddr = "admin@eptest.co.kr";
		}
		
		return mailAddr;
	}
}
