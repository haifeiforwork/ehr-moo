/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface CartooletcDao extends GenericDao<Cartooletc, String> {

	/**
	 * 목록 조회
	 * 
	 * @param apprSign
	 * @return
	 */
	public List<Cartooletc> select(Cartooletc meetingRoom);

	/**
	 * 순서 변경
	 * 
	 * @param map
	 * @return
	 */
	public void updateSortOrder(Cartooletc meetingRoom);

	/**
	 * 순번 구하기
	 * 
	 * @param map
	 * @return
	 */
	public String getSortOrder(Cartooletc meetingRoom);
	
	/**
	 * Favorite 추가 하기
	 * 
	 * @param meetingRoom
	 * @return
	 */
	public String addFavorite(Cartooletc meetingRoom);

	/**
	 * Favorite 삭제 하기
	 * 
	 * @param meetingRoom
	 * @return
	 */
	public void delFavorite(Cartooletc meetingRoom);

	/**
	 * Favorite 체크
	 * 
	 * @param meetingRoom
	 * @return
	 */
	public String checkFavorite(Cartooletc meetingRoom);
	
	public List<Cartooletc> categoryList(Map<String, String> param) ;
	public List<Cartooletc> regionList(Map<String, String> param) ;
	
	public List<Cartooletc> toolList(Map<String, String> param);
}