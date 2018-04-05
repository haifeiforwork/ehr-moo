package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.SystemAdminDao;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdmin;
import com.lgcns.ikep4.portal.admin.screen.model.SystemAdminSearchCondition;

/**
 * 
 * 시스템 관리자 DAO 구현 클래스
 *
 * @author 임종상
 * @version $Id: SystemAdminDaoImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Repository("systemAdminDao")
public class SystemAdminDaoImpl extends GenericDaoSqlmap<SystemAdmin, String> implements SystemAdminDao {

	/**
	 * 시스템 관리자 리스트 카운트 반환
	 * @param searchCondition 검색 조건
	 * @return 시스템 관리자 목록 갯수
	 */
	public Integer countSystemAdmin(SystemAdminSearchCondition searchCondition) {
		Integer count = (Integer) sqlSelectForObject("portal.admin.screen.systemAdmin.countSystemAdmin", searchCondition);

		return count;
	}
	
	/**
	 * 시스템 관리자 리스트
	 * @param searchCondition 검색 조건
	 * @return List<SystemAdmin> 시스템 관리자 목록
	 */
	public List<SystemAdmin> listSystemAdmin(SystemAdminSearchCondition searchCondition) {
		return sqlSelectForList("portal.admin.screen.systemAdmin.listSystemAdmin", searchCondition);
	}
	
	@Deprecated
	public SystemAdmin get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(SystemAdmin object) {
		return null;
	}

	@Deprecated
	public void update(SystemAdmin object) {}

	@Deprecated
	public void remove(String id) {}
}