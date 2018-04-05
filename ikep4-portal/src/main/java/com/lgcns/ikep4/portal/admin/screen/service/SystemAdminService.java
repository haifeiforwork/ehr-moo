package com.lgcns.ikep4.portal.admin.screen.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdmin;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdminSearchCondition;

/**
 * 
 * 시스템 Admin Service
 *
 * @author 임종상
 * @version $Id: SystemAdminService.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Transactional
public interface SystemAdminService extends GenericService<SystemAdmin, String> {
	
	/**
	 * 시스템 관리자 리스트
	 * @param searchCondition 검색 조건
	 * @return SearchResult<SystemAdmin> 시스템 관리자 목록
	 */
	public SearchResult<SystemAdmin> listSystemAdmin(SystemAdminSearchCondition searchCondition);
	
	/**
	 * 시스템 관리자 수정
	 * @param systemAdmin 시스템 관리자 Model
	 * @param portalId 포탈 아이디
	 */
	public void updateSystemAdmin(SystemAdmin systemAdmin, String portalId);
}
