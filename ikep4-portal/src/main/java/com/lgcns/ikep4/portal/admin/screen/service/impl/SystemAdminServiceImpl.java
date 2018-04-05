package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.SystemAdminDao;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdmin;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdminSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService;
import com.lgcns.ikep4.portal.admin.screen.service.SystemAdminService;

/**
 * 
 * 시스템 관리자 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: SystemAdminServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("systemAdminService")
@Transactional
public class SystemAdminServiceImpl extends GenericServiceImpl<SystemAdmin, String> implements SystemAdminService {

	@Autowired
	SystemAdminDao systemAdminDao;

	@Autowired
	private PortalSecurityService portalSecurityService;

	/**
	 * 시스템 관리자 리스트
	 * @param searchCondition 검색 조건
	 * @return SearchResult<SystemAdmin> 시스템 관리자 목록
	 */
	public SearchResult<SystemAdmin> listSystemAdmin(SystemAdminSearchCondition searchCondition) {
		
		// 리스트 전체 건수 조회
		Integer count = systemAdminDao.countSystemAdmin(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<SystemAdmin> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<SystemAdmin>(searchCondition);
		} else {
			// 시스템 관리자 리스트 조회
			List<SystemAdmin> systemAdminList = systemAdminDao.listSystemAdmin(searchCondition);

			searchResult = new SearchResult<SystemAdmin>(systemAdminList, searchCondition);
		}

		return searchResult;
	}
	
	/**
	 * 시스템 관리자 수정
	 * @param systemAdmin 시스템 관리자 Model
	 * @param portalId 포탈 아이디
	 */
	public void updateSystemAdmin(SystemAdmin systemAdmin, String portalId) {
		
		//리소스명에 게시물 key를 넣어줍니다
		systemAdmin.getSecurity().setResourceName(systemAdmin.getResourceName());
		// 게시물명으로 Security Description  생성
		systemAdmin.getSecurity().setResourceDescription(systemAdmin.getResourceName());

		// 권한 업데이트
		portalSecurityService.updateSystemPermission(systemAdmin.getSecurity(), portalId);
	}
}