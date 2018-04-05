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
import com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.Equipment;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;

/**
 * 기자재 관련 Dao 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: MeetingRoomBuildingDaoImpl.java 16276 2011-08-18 07:09:07Z yruyo $
 */
@Repository("EquipmentDao")
public class EquipmentDaoImpl extends GenericDaoSqlmap<Equipment, String> implements EquipmentDao {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDaoImp#selectAll(com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition)
	 */
	public List<Equipment> selectAll(MeetingRoomSearchCondition searchCondition) {

		return sqlSelectForList("lightpack.meetingroom.dao.equipment.selectAll", searchCondition);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDaoImp#equipmentList(java.lang.String)
	 */
	public List<Equipment> equipmentList(String portalId) {

		return sqlSelectForList("lightpack.meetingroom.dao.equipment.equipmentList", portalId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDaoImp#selectCount(com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition)
	 */
	public Integer selectCount(MeetingRoomSearchCondition searchCondition) {

		return (Integer) sqlSelectForObject("lightpack.meetingroom.dao.equipment.selectCount", searchCondition);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(Equipment equipment) {

		return (String) sqlInsert("lightpack.meetingroom.dao.equipment.insert", equipment);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String equipmentId) {
		
		String count = (String) sqlSelectForObject("lightpack.meetingroom.dao.equipment.exists", equipmentId);

		return !count.equals("0");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Equipment get(String equipmentId) {

		return (Equipment) sqlSelectForObject("lightpack.meetingroom.dao.equipment.select", equipmentId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Equipment equipment) {

		sqlUpdate("lightpack.meetingroom.dao.equipment.update", equipment);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(String equipmentId) {

		sqlDelete("lightpack.meetingroom.dao.equipment.delete", equipmentId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDaoImp#getMaxSortOrder()
	 */
	public String getMaxSortOrder() {

		return (String) sqlSelectForObject("lightpack.meetingroom.dao.equipment.getMaxSortOrder");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDaoImp#goUp(java.util.Map)
	 */
	public Equipment goUp(Map<String, String> map) {

		return (Equipment) sqlSelectForObject("lightpack.meetingroom.dao.equipment.selectGoUp", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDaoImp#goDown(java.util.Map)
	 */
	public Equipment goDown(Map<String, String> map) {

		return (Equipment) sqlSelectForObject("lightpack.meetingroom.dao.equipment.selectGoDown", map);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDaoImp#updateSortOrder(com.lgcns.ikep4.lightpack.meetingroom.model.Equipment)
	 */
	public void updateSortOrder(Equipment equipment) {

		sqlUpdate("lightpack.meetingroom.dao.equipment.updateSortOrder", equipment);
	}

}