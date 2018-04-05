/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.meetingroom.dao.BuildingFloorDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;


/**
 * 시스템 map DAO 구현 클래스
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalSystemDaoImpl.java 17792 2012-03-30 01:32:04Z arthes $
 */
@Repository("BuildingFloorDao")
public class BuildingFloorDaoImpl extends GenericDaoSqlmap<BuildingFloor, String> implements BuildingFloorDao {

	/**
	 * sql namespace
	 */
	private static final String NAMESPACE = "lightpack.meetingroom.buildingfloor.";
	
	/**
	 * 포탈 별 루트 시스템을 제외한 시스템 목록
	 * @param portalId 포탈 아이디
	 * @return 포탈 별 루트 시스템을 제외한 시스템 목록
	 */
	public List<BuildingFloor> portalSystemListView(String portalId) {
		
		List<BuildingFloor> list = (List<BuildingFloor>) this.sqlSelectForList(NAMESPACE + "portalSystemListView", portalId);
		
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
	public BuildingFloor get(String id) {
		
		BuildingFloor portalSystem = (BuildingFloor) sqlSelectForObject(NAMESPACE + "get", id);
		
		return portalSystem;
		
	}
	
	/**
	 * 포탈 시스템 별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 관리 트리 목록
	 */
	public List<BuildingFloor> treeList(Map<String, String> param) {
		
		List<BuildingFloor> list = (List<BuildingFloor>) this.sqlSelectForList(NAMESPACE + "treeList", param);
		
		return list;
		
	}
	
	/**
	 * 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 * @param param Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 이름, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 */
	public List<BuildingFloor> linkList(Map<String, String> param) {
		
		List<BuildingFloor> list = (List<BuildingFloor>) this.sqlSelectForList(NAMESPACE + "linkList", param);
		
		return list;
		
	}
	
	/**
	 * 빌딩 목록
	 * @param param Map(portalId:포탈 아이디, useFlag:사용여부)
	 * @return List<BuildingFloor> 빌딩 목록
	 */
	public List<BuildingFloor> buildingList(Map<String, String> param) {
		
		List<BuildingFloor> list = (List<BuildingFloor>) this.sqlSelectForList(NAMESPACE + "buildingList", param);
		
		return list;
	}
	
	/**
	 * 층 목록
	 * @param param Map(parentBuildingFloorId:빌딩 아이디, portalId:포탈 아이디, useFlag:사용여부)
	 * @return List<BuildingFloor> 층 목록
	 */
	public List<BuildingFloor> floorList(Map<String, String> param) {
		
		List<BuildingFloor> list = (List<BuildingFloor>) this.sqlSelectForList(NAMESPACE + "floorList", param);
		
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
	 * 시스템 정보 생성 여부 확인
	 * @param systemCode 시스템 코드
	 * @return boolean 시스템 정보 생성 여부 확인(생성:true, 미생성:false)
	 */
	public boolean existsRoot(String buildingFloor) {
		
		boolean exist = false;

		int count = (Integer) sqlSelectForObject(NAMESPACE + "existsRoot", buildingFloor);
		
		if(count > 0) {
			exist = true;
		} else {
			exist = false;
		}
		
		return exist;
		
	}
	
	public boolean existsMeetingRoomByFloorId(String portalId, String buildingFloorId) {
		
		boolean check = false;
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("portalId", portalId);
		paramMap.put("buildingFloorId", buildingFloorId);

		int count = (Integer) sqlSelectForObject(NAMESPACE + "existsMeetingRoomByFloorId", paramMap);
		
		if(count > 0) {
			check = true;
		} else {
			check = false;
		}
		
		return check;
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
	public String create(BuildingFloor object) {
		
		this.sqlInsert(NAMESPACE + "create", object);
		
		return object.getBuildingFloorId();
		
	}
	
	/**
	 * 빌딩/층 수정
	 * @param object 빌딩/층 정보
	 */
	public void update(BuildingFloor object) {
		
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

	public void updateSortOrder(BuildingFloor object) {
		
		this.sqlUpdate(NAMESPACE + "updateSortOrder", object);
		
	}
	
}