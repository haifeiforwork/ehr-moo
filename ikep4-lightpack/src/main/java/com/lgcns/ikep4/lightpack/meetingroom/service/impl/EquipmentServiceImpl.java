/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.Equipment;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.meetingroom.service.EquipmentService;

/**
 * 기자재 관리 서비스 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: EquipmentServiceImpl.java 16243 2011-08-18 04:10:43Z cjpark $ * 
 */
@Service("EquipmentService")
@Transactional
public class EquipmentServiceImpl extends GenericServiceImpl<Equipment, String> implements EquipmentService {

	@Autowired
	private EquipmentDao equipmentDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#create
	 * (java.lang.Object)
	 */
	public String create(Equipment equipment) {

		String result = equipmentDao.create(equipment);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#exists
	 * (java.io.Serializable)
	 */
	public boolean exists(String equipmentId) {

		return equipmentDao.exists(equipmentId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java
	 * .io.Serializable)
	 */
	public Equipment read(String equipmentId) {

		return equipmentDao.get(equipmentId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete
	 * (java.io.Serializable)
	 */
	public void delete(String equipmentId) {

		equipmentDao.remove(equipmentId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update
	 * (java.lang.Object)
	 */
	public void update(Equipment equipment) {

		equipmentDao.update(equipment);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.service.impl.EquipmentServiceImpl#list(com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition)
	 */
	public SearchResult<Equipment> list(MeetingRoomSearchCondition searchCondition) {

		Integer count = equipmentDao.selectCount(searchCondition);
		
		searchCondition.terminateSearchCondition(count);

		SearchResult<Equipment> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			
			searchResult = new SearchResult<Equipment>(searchCondition);
		} else {

			List<Equipment> equipmentList = equipmentDao.selectAll(searchCondition);
			
			searchResult = new SearchResult<Equipment>(equipmentList, searchCondition);
		}

		return searchResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.service.impl.EquipmentServiceImpl#equipmentList(java.lang.String)
	 */
	public List<Equipment> equipmentList(String portalId) {

		List<Equipment> equipmentList = new ArrayList<Equipment>();
		
		equipmentList = equipmentDao.equipmentList(portalId);

		return equipmentList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.service.impl.EquipmentServiceImpl#getMaxSortOrder
	 * ()
	 */
	public String getMaxSortOrder() {

		return equipmentDao.getMaxSortOrder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.service.impl.EquipmentServiceImpl#goUp(java.util.Map)
	 */
	public void goUp(Map<String, String> map) {

		Equipment upEquipment = equipmentDao.goUp(map);
		
		String upSortOrder = upEquipment.getSortOrder();

		upEquipment.setSortOrder((String) map.get("sortOrder"));

		equipmentDao.updateSortOrder(upEquipment);

		Equipment equipment = new Equipment();
		equipment.setEquipmentId((String) map.get("equipmentId"));
		equipment.setSortOrder(upSortOrder);

		equipmentDao.updateSortOrder(equipment);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.lightpack.meetingroom.service.impl.EquipmentServiceImpl#goDown(java.util.Map)
	 */
	public void goDown(Map<String, String> map) {

		Equipment downEquipment = equipmentDao.goDown(map);
		
		String downSortOrder = downEquipment.getSortOrder();

		downEquipment.setSortOrder((String) map.get("sortOrder"));

		equipmentDao.updateSortOrder(downEquipment);

		Equipment equipment = new Equipment();
		equipment.setEquipmentId((String) map.get("equipmentId"));
		equipment.setSortOrder(downSortOrder);

		equipmentDao.updateSortOrder(equipment);
	}

}
