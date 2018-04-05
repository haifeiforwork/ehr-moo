/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;


/**
 * 시스템 관리 Service
 * 
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalSystemService.java 17156 2012-01-09 06:56:28Z yu_hs $
 */
@Transactional
public interface BuildingFloorService extends GenericService<BuildingFloor, String> {
	
	/**
	 * 포탈 별 루트 시스템을 제외한 시스템 목록
	 * @param portalId 포탈 아이디
	 * @return 포탈 별 루트 시스템을 제외한 시스템 목록
	 */
	public List<BuildingFloor> portalSystemListView(String portalId);
	
	/**
	 * 포탈 시스템 별 관리 트리 목록
	 * @param param Map(portalId:포탈 아이디, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 관리 트리 목록
	 */
	public List<BuildingFloor> treeList(Map<String, String> param);
	
	/**
	 * 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 * @param param Map(portalId:포탈 아이디, operationName:권한명, className:클래스명, userId:사용자 이름, fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드)
	 * @return List<PortalSystem> 포탈 시스템 별 사용자 접근 가능한 시스템 목록
	 */
	public List<BuildingFloor> portalSystemLinkList(Map<String, String> param);
	
	/**
	 * 빌딩 목록
	 * @param param Map(portalId:포탈 아이디, useFlag:사용여부)
	 * @return List<BuildingFloor> 빌딩 목록
	 */
	public List<BuildingFloor> buildingList(Map<String, String> param);
	
	/**
	 * 층 목록
	 * @param param Map(parentBuildingFloorId:빌딩 아이디, portalId:포탈 아이디, useFlag:사용여부)
	 * @return List<BuildingFloor> 층 목록
	 */
	public List<BuildingFloor> floorList(Map<String, String> param);
	
	/**
	 * 시스템 정보
	 * @param param Map(fieldName:다국어로 관리되는 field 이름, localCode:사용자 locale 코드, systemCode:시스템 코드)
	 * @return PortalSystem 시스템 정보
	 */
	public BuildingFloor read(String buildingFloorId);
	
	/**
	 * 시스템 정보 생성 여부 확인
	 * @param systemCode 시스템 코드
	 * @return boolean 시스템 정보 생성 여부 확인(생성:true, 미생성:false)
	 */
	public boolean exists(String buildingFloorId);
	
	/**
	 * 시스템 정보 생성 여부 확인
	 * @param systemCode 시스템 코드
	 * @return boolean 시스템 정보 생성 여부 확인(생성:true, 미생성:false)
	 */
	public boolean existsRoot(String buildingFloorId);
	
	public boolean existsMeetingRoomByFloorId(String portalId, String buildingFloorId);
	
	/**
	 * 하위 시스템 개수
	 * @param param Map(systemCode:부모 시스템 코드, portalId:포탈 아이디)
	 * @return int 하위 시스템 개수
	 */
	public int getChildCount(Map<String, String> param);
	
	/**
	 * 시스템 명 별 시스템 정보
	 * @param systemName 시스템 명
	 * @param portalId 포탈 아이디
	 * @return String 시스템 코드
	 */
	public String getSystemCode(String systemName, String portalId);
	
	/**
	 * 정렬순서(Sort order) 최대값 가져오기
	 * 
	 * @return String 정렬순서 (Sort order)
	 */
	public String getMaxSortOrder();
	
	/**
	 * 시스템 생성
	 * @param portalSystem 시스템 정보
	 * @return String 생성된 시스템 코드
	 */
	public String create(BuildingFloor portalSystem);
	
	/**
	 * 빌딩/층 수정
	 * @param buildingFloor 빌딩/층 정보
	 */
	public void update(BuildingFloor buildingFloor);
	
	/**
	 * 시스템 삭제
	 * @param systemCode 시스템 코드
	 */
	public void delete(String buildingFloorId);
	
	/**
	 * 빌딩/층 트리에서의 빌딩/층 위치를 위로 이동
	 * @param map Map(prevSortOrder:트리 위치상 바로 위의 빌딩/층의 순서, prevNodeId:트리 위치상 바로 위의 빌딩/층 코드, curSortOrder:이동할 빌딩/층의 순서, curNodeId:이동할 빌딩/층 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveUpBuildingFloor(Map<String, String> map);
	
	/**
	 * 빌딩/층 트리에서의 빌딩/층 위치를 아래로 이동
	 * @param map Map(nextSortOrder:트리 위치상 바로 아래의 빌딩/층의 순서, nextNodeId:트리 위치상 바로 아래의 빌딩/층 코드, curSortOrder:이동할 빌딩/층의 순서, curNodeId:이동할 빌딩/층 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveDownBuildingFloor(Map<String, String> map);
	
	/**
	 * 시스템 트리에서의 드래그앤 드랍으로 시스템 위치를 트리 위치상 바로 위의 시스템의 하위로 이동
	 * @param map Map(systemCode:이동할 시스템 코드, parentSystemCode:이동 후 상위 시스템 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveDndOneNode(Map<String, String> map);
	
	/**
	 * 시스템 트리에서의 드래그앤 드랍으로 이동 전 상위 시스템은 동일하면서 노드내에 순서만 변경
	 * @param map Map(systemCode:이동할 시스템 코드, parentSystemCode:이동 후 상위 시스템 코드, prevSystemCode:트리 위치상 바로 위의 시스템 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveDndSameNode(Map<String, String> map);
	
	/**
	 * 시스템 트리에서의 드래그앤 드랍으로 시스템 위치를 트리 위치상 바로 위의 시스템의 하위로 이동하거나 이동 전 상위 시스템은 동일하면서 노드내에 순서만 변경하는 제외한 위치 이동
	 * @param map Map(systemCode:이동할 시스템 코드, parentSystemCode:이동 후 상위 시스템 코드, updaterId:수정인 아이디, updaterName:수정인 이름)
	 */
	public void moveDndOtherNode(Map<String, String> map);
	
}