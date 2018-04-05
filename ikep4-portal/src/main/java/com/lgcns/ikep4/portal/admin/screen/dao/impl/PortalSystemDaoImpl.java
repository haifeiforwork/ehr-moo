/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;


/**
 * 시스템 map DAO 구현 클래스
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalSystemDaoImpl.java 19022 2012-05-31 06:36:27Z malboru80 $
 */
@Repository("PortalSystemDao")
public class PortalSystemDaoImpl extends GenericDaoSqlmap<PortalSystem, String> implements PortalSystemDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "portal.admin.screen.portalSystem.";
	
	/**
	 * 포탈 별 루트 시스템을 제외한 시스템 목록
	 * @param portalId 포탈 아이디
	 * @return 포탈 별 루트 시스템을 제외한 시스템 목록
	 */
	public List<PortalSystem> portalSystemListView(String portalId) {
		
		List<PortalSystem> list = (List<PortalSystem>) this.sqlSelectForList(NAMESPACE + "portalSystemListView", portalId);
		
		return list;
		
	}
	
	@Deprecated
	public void remove(String id) {
		
		throw new UnsupportedOperationException();
		
	}
	
	/**
	 * 시스템 정보
	 * @param id 시스템 코드
	 * @return PortalSystem 시스템 정보
	 */
	public PortalSystem get(String id) {
		
		PortalSystem portalSystem = (PortalSystem) sqlSelectForObject(NAMESPACE + "get", id);
		
		return portalSystem;
		
	}
	
	/**
	 * 포탈 시스템 별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 관리 트리 목록
	 */
	public List<PortalSystem> treeList(Map<String, String> param) {
		
		List<PortalSystem> list = (List<PortalSystem>) this.sqlSelectForList(NAMESPACE + "treeList", param);
		
		return list;
		
	}
	
	/**
	 * 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 * @param param Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 이름, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 */
	public List<PortalSystem> linkList(Map<String, String> param) {
		
		List<PortalSystem> list = (List<PortalSystem>) this.sqlSelectForList(NAMESPACE + "linkList", param);
		
		return list;
		
	}
	
	/**
	 * 시스템 정보 생성 여부 확인
	 * @param systemCode 시스템 코드
	 * @return boolean 시스템 정보 생성 여부 확인(생성:true, 미생성:false)
	 */
	public boolean exists(String systemCode) {
		
		boolean exist = false;

		int count = (Integer) sqlSelectForObject(NAMESPACE + "exists", systemCode);
		
		if(count > 0) {
			exist = true;
		} else {
			exist = false;
		}
		
		return exist;
		
	}
	
	/**
	 * 하위 시스템 개수
	 * @param param Map(systemCode:부모 시스템 코드, portalId:포탈 아이디)
	 * @return int 하위 시스템 개수
	 */
	public int getChildCount(Map<String, String> param) {
		
		int count = 0;

		count = (Integer) sqlSelectForObject(NAMESPACE + "getChildCount", param);
		
		return count;
		
	}
	
	/**
	 * 시스템 정렬 순서
	 * @return String 시스템 정렬 순서
	 */
	public String getMaxSortOrder() {
		
		String sortOrder = (String) sqlSelectForObject(NAMESPACE + "getMaxSortOrder");
		
		return sortOrder;
		
	}
	
	/**
	 * 시스템 생성
	 * @param object 시스템 정보
	 * @return String 생성된 시스템 코드
	 */
	public String create(PortalSystem object) {
		
		this.sqlInsert(NAMESPACE + "create", object);
		
		return object.getSystemCode();
		
	}
	
	/**
	 * 시스템 수정
	 * @param object 시스템 정보
	 */
	public void update(PortalSystem object) {
		
		this.sqlUpdate(NAMESPACE + "update", object); 
		
	}
	
	/**
	 * 시스템 삭제
	 * @param systemCode 시스템 코드
	 */
	public void delete(String id) {
		
		this.sqlDelete(NAMESPACE + "delete", id);
		
	}

	/**
	 * 시스템 명 별 시스템 정보
	 * @param systemName 시스템 명
	 * @param portalId 포탈 아이디
	 * @return String 시스템 코드
	 */
	public String getSystemCode(String systemName, String portalId) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("systemName", systemName);
		map.put("portalId", portalId);
		
		String systemCode = (String) sqlSelectForObject(NAMESPACE + "getSystemCode", map);
		
		return systemCode;
	}

	public void updateSortOrder(PortalSystem object) {
		
		this.sqlUpdate(NAMESPACE + "updateSortOrder", object);
		
	}

	public List<PortalSystem> listSystemAll(String portalId) {
		List<PortalSystem> list = (List<PortalSystem>) this.sqlSelectForList(NAMESPACE + "listSystemAll", portalId);
		
		return list;
	}
	
}