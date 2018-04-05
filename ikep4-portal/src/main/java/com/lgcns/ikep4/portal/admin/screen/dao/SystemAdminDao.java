package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdmin;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdminSearchCondition;

/**
 * 
 * 시스템 관리자 DAO
 *
 * @author 임종상
 * @version $Id: SystemAdminDao.java 16243 2011-08-18 04:10:43Z giljae $
 */
public interface SystemAdminDao extends GenericDao<SystemAdmin, String> {
	
	/**
	 * 시스템 관리자 리스트
	 * @param searchCondition 검색 조건
	 * @return List<SystemAdmin> 시스템 관리자 목록
	 */
	public List<SystemAdmin> listSystemAdmin(SystemAdminSearchCondition searchCondition);
	
	/**
	 * 시스템 관리자 리스트 카운트 반환
	 * @param searchCondition 검색 조건
	 * @return 시스템 관리자 목록 갯수
	 */
	public Integer countSystemAdmin(SystemAdminSearchCondition searchCondition);
}