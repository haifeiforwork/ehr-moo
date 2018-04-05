/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.userauthmgnt.service.impl;

import java.util.List;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;
import com.lgcns.ikep4.approval.collaboration.common.service.CommonCodeService;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.dao.UserAuthMgntDao;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.model.UserAuthMgnt;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.search.UserAuthMgntSearchCondition;
import com.lgcns.ikep4.approval.collaboration.userauthmgnt.service.UserAuthMgntService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 사용자 권한 관리 UserAuthMgntService 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Service
public class UserAuthMgntServiceImpl implements UserAuthMgntService{
	
	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());
	
	/** The UserAuthMgnt dao. */
	@Autowired
	private UserAuthMgntDao userAuthMgntDao;
	
	/** The MessageSource. */
	@Autowired
	private MessageSource messageSource;
	
	/** The acl service. */
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private CommonCodeService commonCodeService;
	
	/**
	 * 사용자 권한 관리 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SearchResult<UserAuthMgnt> getUserAuthMgntList( UserAuthMgntSearchCondition userAuthMgntSearchCondition, User user) throws Exception {
		
		SearchResult<UserAuthMgnt> searchResult = null;
		
		int totalCnt = 0;
		
		boolean isEnableView = checkPermission( user, userAuthMgntSearchCondition);
		
		if( isEnableView) {
			
			totalCnt = userAuthMgntDao.getUserAuthMgntCount( userAuthMgntSearchCondition);
		}
		
		userAuthMgntSearchCondition.terminateSearchCondition( totalCnt);
		
		if( userAuthMgntSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UserAuthMgnt>( userAuthMgntSearchCondition);
		} else {
			List<UserAuthMgnt> userAuthMgntList = userAuthMgntDao.getUserAuthMgntList( userAuthMgntSearchCondition);
			searchResult = new SearchResult<UserAuthMgnt>( userAuthMgntList, userAuthMgntSearchCondition);
		}
		
		return searchResult;
	}
	
	/**
	 * 사용자 권한 관리 목록
	 * @param userAuthMgntSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<UserAuthMgnt> getUserAuthMgntList( UserAuthMgntSearchCondition userAuthMgntSearchCondition) throws Exception {
		userAuthMgntSearchCondition.setIsSystemAdmin(true);
		userAuthMgntSearchCondition.setStartRowIndex(0);
		userAuthMgntSearchCondition.setEndRowIndex(9999999);
		return userAuthMgntDao.getUserAuthMgntList( userAuthMgntSearchCondition);
	}
	
	/**
	 * 사용자 권한 관리 등록
	 * @param userAuthMgnt
	 */
	public void createUserAuthMgnt( UserAuthMgnt userAuthMgnt, User user) throws Exception {
		
		boolean isEnableView = checkPermission( user, null);
		if( !isEnableView) {
			throw new Exception( messageSource.getMessage( "ui.approval.collaboration.message.permission.denied", null, Locale.getDefault()));
		}
		
		int dupCnt = userAuthMgntDao.checkDupUserAuthMgnt( userAuthMgnt);
												
		if( dupCnt > 0 ) {
			throw new IKEP4AjaxValidationException( "empNo", messageSource.getMessage( "ui.approval.collaboration.message.duplicated", null, Locale.getDefault()));
		}
		userAuthMgntDao.create( userAuthMgnt);
	}
	
	/**
	 * 사용자 권한 관리 조회
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public UserAuthMgnt getUserAuthMgnt( UserAuthMgntSearchCondition userAuthMgntSearchCondition, User user) throws Exception {
		
		UserAuthMgnt userAuthMgnt = userAuthMgntDao.getUserAuthMgnt( userAuthMgntSearchCondition);
		
		String workGbnCd = userAuthMgnt.getWorkGbnCd();
		
		boolean isSystemAdmin = this.isSystemAdmin( user);
		
		userAuthMgnt.setSaveBntActive( isSystemAdmin);
		
		if( !isSystemAdmin) {
			
			List<CommonCode> workGnbCdList = commonCodeService.getCommonCodeList( "C00001");
			for (CommonCode commonCode : workGnbCdList) {
				if( StringUtils.equals( workGbnCd, commonCode.getComCd())) {
					
					if( StringUtils.equals(commonCode.getCharCol1(), workGbnCd) || StringUtils.equals(commonCode.getCharCol2(), workGbnCd)) {
						userAuthMgnt.setSaveBntActive( true);
					}
					break;
				}
			}
		}
		
		return userAuthMgnt;
	}
	
	/**
	 * 사용자 권한 관리 수정
	 * @param userAuthMgnt
	 * @throws Exception
	 */
	public int updateUserAuthMgnt( UserAuthMgnt userAuthMgnt , User user) throws Exception {
		
		boolean isEnableView = checkPermission( user, null);
		if( !isEnableView) {
			
			throw new Exception( messageSource.getMessage( "ui.approval.collaboration.message.permission.denied", null, Locale.getDefault()));
		}
		
		return userAuthMgntDao.updateUserAuthMgnt( userAuthMgnt);
	}
	
	/**
	 * 사용자 권한 관리 삭제
	 * @param userAuthMgnt
	 * @return
	 * @throws Exception
	 */
	public int deleteUserAuthMgnt( String delUserAuthMgntList , User user) throws Exception {
		
		boolean isEnableView = checkPermission( user, null);
		if( !isEnableView) {
			
			throw new Exception( messageSource.getMessage( "ui.approval.collaboration.message.permission.denied", null, Locale.getDefault()));
		}
		
		JSONArray addAdminArray = JSONArray.fromObject(JSONSerializer.toJSON( delUserAuthMgntList));
		int totalResult = 0;
		for (Object object : addAdminArray) {
			
			JSONObject obj = (JSONObject)object;
			String workGbnCd = obj.getString( "workGbnCd");
			String deptId = obj.getString( "deptId");
			String empNo = obj.getString( "empNo");
			
			UserAuthMgnt userAuthMgnt = new UserAuthMgnt();
			userAuthMgnt.setWorkGbnCd( workGbnCd);
			userAuthMgnt.setDeptId( deptId);
			userAuthMgnt.setEmpNo( empNo);
			
			int delResult = userAuthMgntDao.deleteUserAuthMgnt( userAuthMgnt);
			if( delResult != 1) {
				throw new Exception( messageSource.getMessage( "ui.approval.collaboration.message.deleteFail", null, Locale.getDefault()));
			}
			
			totalResult = totalResult + delResult;
		}
		
		return totalResult;
	}
	
	/**
	 * 권한 확인
	 * @param user
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 */
	public boolean checkPermission( User user, UserAuthMgntSearchCondition searchCondition) throws Exception {
		
		boolean isSystemAdmin = this.isSystemAdmin( user);
		boolean isUserAuthAdmin = false;
		
		if( !isSystemAdmin) {
			
			isUserAuthAdmin = this.isSystemGroup( user);
		}
		if( searchCondition != null) {
			
			searchCondition.setIsSystemAdmin( isSystemAdmin);
			searchCondition.setIsAdmin( isUserAuthAdmin || isSystemAdmin);
		}
		
		
		return isUserAuthAdmin || isSystemAdmin; 
	}
	
	/**
	 * 시스템 Admin확인
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean isSystemAdmin( User user) throws Exception {
		
		return aclService.isSystemAdmin( "Approval", user.getUserId());
	}
	
	/**
	 * 시스템 그룹(부서) Admin 확인
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean isSystemGroup( User user)throws Exception {
		
		boolean isSystemGroup = false;
		String groupIds = messageSource.getMessage( "permission.groupId.list", null, Locale.getDefault());
		String[] arrGroupId = StringUtils.split( groupIds, "|");
		
		for (String groupId : arrGroupId) {
			
			if( StringUtils.equals( groupId, user.getGroupId())) {
				
				isSystemGroup = true;
				break;
			}
		}
		
		return isSystemGroup;
	}
}
