/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystem;

/**
 * 시스템 DAO 정의
 *
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemDao.java 19021 2012-05-31 06:36:11Z malboru80 $
 */
public interface PortalSystemDao extends GenericDao<PortalSystem, String> {
	
	/**
	 * 포탈 별 루트 시스템을 제외한 시스템 목록
	 * @param portalId 포탈 아이디
	 * @return 포탈 별 루트 시스템을 제외한 시스템 목록
	 */
	public List<PortalSystem> portalSystemListView(String portalId);
	
	/**
	 * 포탈 시스템 별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 관리 트리 목록
	 */
	public List<PortalSystem> treeList(Map<String, String> param);
	
	/**
	 * 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 * @param param Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 이름, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 */
	public List<PortalSystem> linkList(Map<String, String> param);
	
	/**
	 * 시스템 정보 확인
	 * @param systemCode 시스템 코드
	 * @return boolean 시스템 정보 생성 여부 확인(생성:true, 미생성:false)
	 */
	public boolean exists(String systemCode);
	
	/**
	 * 하위 시스템 개수
	 * @param param Map(systemCode:부모 시스템 코드, portalId:포탈 아이디)
	 * @return int 하위 시스템 개수
	 */
	public int getChildCount(Map<String, String> param);
	
	/**
	 * 시스템 삭제
	 * @param systemCode 시스템 코드
	 */
	public void delete(String systemCode);

	/**
	 * 시스템 정렬 순서
	 * @return String 시스템 정렬 순서
	 */
	public String getMaxSortOrder();
	
	/**
	 * 시스템 명 별 시스템 정보
	 * @param systemName 시스템 명
	 * @param portalId 포탈 아이디
	 * @return String 시스템 코드
	 */
	public String getSystemCode(String systemName, String portalId);

	/**
	 * 시스템 이동 후 하위 정렬순서 업데이트
	 * @param object 시스템 VO
	 */
	public void updateSortOrder(PortalSystem object);
	
	/**
	 * 포탈 별 루트 시스템을 제외한 시스템 목록
	 * @param portalId 포탈 아이디
	 * @return 포탈 별 루트 시스템을 제외한 시스템 목록
	 */
	public List<PortalSystem> listSystemAll(String portalId);
}