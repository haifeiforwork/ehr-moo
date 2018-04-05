/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.facility.model.FacilitySearchCondition;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface MeetingRoomDao extends GenericDao<MeetingRoom, String> {

	/**
	 * 목록 조회
	 * 
	 * @param apprSign
	 * @return
	 */
	public List<MeetingRoom> select(MeetingRoom meetingRoom);

	/**
	 * 순서 변경
	 * 
	 * @param map
	 * @return
	 */
	public void updateSortOrder(MeetingRoom meetingRoom);

	/**
	 * 순번 구하기
	 * 
	 * @param map
	 * @return
	 */
	public String getSortOrder(MeetingRoom meetingRoom);
	
	/**
	 * Favorite 추가 하기
	 * 
	 * @param meetingRoom
	 * @return
	 */
	public String addFavorite(MeetingRoom meetingRoom);

	/**
	 * Favorite 삭제 하기
	 * 
	 * @param meetingRoom
	 * @return
	 */
	public void delFavorite(MeetingRoom meetingRoom);

	/**
	 * Favorite 체크
	 * 
	 * @param meetingRoom
	 * @return
	 */
	public String checkFavorite(MeetingRoom meetingRoom);
	
	/**
	 * 설비 트리
	 * @param param Map
	 * @return List
	 */
	public List<MeetingRoom> getMeetingRoomList(FacilitySearchCondition facilitySearchCondition);
	
	/**
	 * 최상위 카테고리 정보
	 * @param portalId 포탈 코드
	 * @return facilityCategory 카테고리 정보
	 */
	public MeetingRoom readRootCategory(String portalId);
	
	/**
	 * 카테고리 정보
	 * @param categoryId 카테고리 코드
	 * @return facilityCategory 카테고리 정보
	 */
	public MeetingRoom readCategory(String categoryId);
	
	/**
	 * 카테고리 목록 : 상위 카테고리(root까지)
	 * @param categoryId 카테고리 코드
	 * @return facilityCategory 카테고리 정보
	 */
	public List<MeetingRoom> readParentsCategory(String categoryId);
	
	/**
	 * 카테고리 목록 : 하위 카테고리
	 * @param categoryId 카테고리 코드
	 * @return facilityCategory 카테고리 정보
	 */
	public List<MeetingRoom> readChildCategory(String categoryId);
	
	public void createToolRoomMap(MeetingRoom meetingRoom);
	
	public void deleteToolRoomMap(MeetingRoom meetingRoom);

}