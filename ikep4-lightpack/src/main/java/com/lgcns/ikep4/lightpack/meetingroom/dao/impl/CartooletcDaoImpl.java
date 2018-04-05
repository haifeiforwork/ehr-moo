/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.meetingroom.dao.CartooletcDao;
import com.lgcns.ikep4.lightpack.meetingroom.dao.MeetingRoomDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class CartooletcDaoImpl extends GenericDaoSqlmap<Cartooletc, String> implements CartooletcDao {

	String NAMESPACE = "lightpack.meeting.dao.cartooletc.";

	public String create(Cartooletc cartooletc) {
		
		return (String) sqlInsert(NAMESPACE + "insert", cartooletc);
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Cartooletc get(String cartooletcId) {
		
		return (Cartooletc) sqlSelectForObject(NAMESPACE + "get", cartooletcId);
	}

	public void remove(String cartooletcId) {
		
		sqlDelete(NAMESPACE + "deleteFavorite", cartooletcId);
		sqlDelete(NAMESPACE + "delete", cartooletcId);
	}

	public void update(Cartooletc cartooletc) {
		
		sqlUpdate(NAMESPACE + "update", cartooletc);
	}

	public List<Cartooletc> select(Cartooletc cartooletc) {
		return (List<Cartooletc>) sqlSelectForList(NAMESPACE + "select", cartooletc);
	}

	public void updateSortOrder(Cartooletc cartooletc) {
		
		sqlUpdate(NAMESPACE + "updateSortOrder", cartooletc);
	}

	public String getSortOrder(Cartooletc cartooletc) {
		
		return (String) sqlSelectForObject(NAMESPACE + "selectSortOrder", cartooletc);
	}
	
	public String addFavorite(Cartooletc cartooletc) {
		return (String) sqlInsert(NAMESPACE + "addFavorite", cartooletc);
	}

	public void delFavorite(Cartooletc cartooletc) {
		sqlDelete(NAMESPACE + "delFavorite", cartooletc);
	}

	public String checkFavorite(Cartooletc cartooletc) {
		return (String) sqlSelectForObject(NAMESPACE + "checkFavorite", cartooletc);
	}
	
	

	public List<Cartooletc> categoryList(Map<String, String> param) {
		
		List<Cartooletc> list = (List<Cartooletc>) this.sqlSelectForList(NAMESPACE + "categoryList", param);
		
		return list;
	}
	public List<Cartooletc> regionList(Map<String, String> param) {
		
		List<Cartooletc> list = (List<Cartooletc>) this.sqlSelectForList(NAMESPACE + "regionList", param);
		
		return list;
	}
	
	public List<Cartooletc> toolList(Map<String, String> param) {
		
		List<Cartooletc> list = (List<Cartooletc>) this.sqlSelectForList(NAMESPACE + "toolList", param);
		
		return list;
		
	}
	
}