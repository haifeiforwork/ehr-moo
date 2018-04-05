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

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.model.Equipment;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;

/**
 * 기자재 관리 서비스 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: MaterialsServiceImpl.java 16243 2011-08-18 04:10:43Z cjpark $ * 
 */
public interface EquipmentService extends GenericService<Equipment, String> {

	/**
	 * 기자재 조회
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<Equipment> list(MeetingRoomSearchCondition searchCondition);
	
	/**
	 * 기자재 목록
	 * 
	 * @param portalId 포탈 아이디
	 * @return
	 */
	public List<Equipment> equipmentList(String portalId);

	/**
	 * 정렬순서(Sort order) 최대값 가져오기
	 * 
	 * @return
	 */
	public String getMaxSortOrder();

	/**
	 * 해당 기자재의 정렬순서를 목록에서 한단계 위로 올림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public void goUp(Map<String, String> map);

	/**
	 * 해당 기자재의 정렬순서를 목록에서 한단계 아래로 내림
	 * 
	 * @param map 이동 조건
	 * @return
	 */
	public void goDown(Map<String, String> map);

}