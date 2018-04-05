/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDao;
import com.lgcns.ikep4.lightpack.meetingroom.dao.MeetingRoomMainDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.Equipment;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomMainService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Service
public class MeetingRoomMainServiceImpl implements MeetingRoomMainService {

	@Autowired
	private MeetingRoomMainDao meetingRoomDao;

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private FileService fileService;

	public String create(MeetingRoom meetingRoom) {

		User user = (User) getRequestAttribute("ikep.user");

		meetingRoom.setMeetingRoomId(idgenService.getNextId());
		meetingRoom.setRegisterId(user.getUserId());
		meetingRoom.setRegisterName(user.getUserName());
		meetingRoomDao.create(meetingRoom);

		fileService.createFileLink(meetingRoom.getFileId(), meetingRoom.getMeetingRoomId(),
				IKepConstant.ITEM_TYPE_CODE_PLANNER, user);

		return meetingRoom.getMeetingRoomId();
	}

	public boolean exists(String roomId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(MeetingRoom meetingRoom) {

		User user = (User) getRequestAttribute("ikep.user");

		meetingRoomDao.update(meetingRoom);

		fileService.createFileLink(meetingRoom.getFileId(), meetingRoom.getMeetingRoomId(),
				IKepConstant.ITEM_TYPE_CODE_PLANNER, user);
	}

	public void delete(String roomId) {
		meetingRoomDao.remove(roomId);
		fileService.removeItemFile(roomId);
	}

	public void delete(String[] roomIds) {

		for (String roomId : roomIds) {
			meetingRoomDao.remove(roomId);
			fileService.removeItemFile(roomId);
		}
	}

	public MeetingRoom read(String roomId) {

		MeetingRoom meetingRoom = (MeetingRoom) meetingRoomDao.get(roomId);

		List<Equipment> equipmenetList = equipmentDao.equipmentList(meetingRoom.getPortalId());

		if (!StringUtil.isEmpty(meetingRoom.getEquipment())) {

			StringTokenizer st = new StringTokenizer(meetingRoom.getEquipment(), "^");
			String[] list = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				list[i] = st.nextToken();
				i++;
			}
			meetingRoom.setEquipmentList(list);

			st = new StringTokenizer(meetingRoom.getEquipment(), "^");
			String tokenStr = "";
			String equipmentName = "";
			while (st.hasMoreTokens()) {
				tokenStr = st.nextToken();
				for (Equipment equipment : equipmenetList) {
					if (equipment.getEquipmentId().equals(tokenStr)) {
						equipmentName = equipmentName + equipment.getEquipmentName() + ", ";
					}
				}
			}
			if (equipmentName.length() > 0) {
				equipmentName = equipmentName.substring(0, equipmentName.length() - 2);
			}
			meetingRoom.setEquipmentName(equipmentName);
		}

		return meetingRoom;
	}

	public List<MeetingRoom> select(MeetingRoom meetingRoom) {
		List<MeetingRoom> meetingRoomList = meetingRoomDao.select(meetingRoom);
		
		List<Equipment> equipmenetList = equipmentDao.equipmentList(meetingRoom.getPortalId());

		for (MeetingRoom room : meetingRoomList) {
			if (!StringUtil.isEmpty(room.getEquipment())) {
				StringTokenizer st = new StringTokenizer(room.getEquipment(), "^");
				String tokenStr = "";
				String equipmentName = "";
				while (st.hasMoreTokens()) {
					tokenStr = st.nextToken();
					for (Equipment equipment : equipmenetList) {
						if (equipment.getEquipmentId().equals(tokenStr)) {
							equipmentName = equipmentName + equipment.getEquipmentName() + ", ";
						}
					}
				}
				if (equipmentName.length() > 0) {
					equipmentName = equipmentName.substring(0, equipmentName.length() - 2);
				}
				room.setEquipmentName(equipmentName);
			}
		}

		return meetingRoomList;
	}

	public void changeSort(List<MeetingRoom> meetingRoomList, String roomId, int sortOrder) {

		int i = 0;
		int newOrder = 100000;
		String preRoomId;

		if (meetingRoomList != null) {
			for (MeetingRoom meetingRoom : meetingRoomList) {

				preRoomId = meetingRoom.getMeetingRoomId();

				if (i == sortOrder) {

					meetingRoom.setMeetingRoomId(roomId);
					meetingRoom.setSortOrder(String.valueOf(newOrder));
					meetingRoomDao.updateSortOrder(meetingRoom);
					newOrder++;
					i++;

					meetingRoom.setMeetingRoomId(preRoomId);
					meetingRoom.setSortOrder(String.valueOf(newOrder));
					meetingRoomDao.updateSortOrder(meetingRoom);
					newOrder++;
					i++;

				} else {

					if (!roomId.equals(preRoomId)) {
						meetingRoom.setMeetingRoomId(preRoomId);
						meetingRoom.setSortOrder(String.valueOf(newOrder));
						meetingRoomDao.updateSortOrder(meetingRoom);
						newOrder++;
						i++;
					}
				}

			}

			if (sortOrder == meetingRoomList.size() - 1) {
				MeetingRoom meetingRoom = new MeetingRoom();
				meetingRoom.setMeetingRoomId(roomId);
				meetingRoom.setSortOrder(String.valueOf(newOrder));
				meetingRoomDao.updateSortOrder(meetingRoom);

			}
		}

	}

	

	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	public List<Map<String, String>> makeMeetingTimeList(String roomId, List<Map<String, Object>> scheduleList) {

		List<Map<String, String>> timeList = new ArrayList<Map<String, String>>();

		for (int i = 8; i < 21; i++) {
			Map<String, String> map = new HashMap<String, String>();
			if (i < 10) {
				map.put("isHeader", "N");
				map.put("roomId", roomId);
				map.put("id", "0" + i + "00");
				map.put("name", "0" + i + ":00");
				map.put("from", "0" + i + ":00");
				if((i + 1) < 10) {
					map.put("to", "0" + (i + 1) + ":00");
				}else {
					map.put("to",  (i + 1) + ":00");
				}
			} else {
				map.put("isHeader", "N");
				map.put("roomId", roomId);
				map.put("id", i + "00");
				map.put("name", i + ":00");
				map.put("from", i + ":00");
				map.put("to", (i + 1) + ":00");
			}

			timeList.add(map);
		}

		for (Map<String, Object> schedule : scheduleList) {
			String startTime = (String) schedule.get("startDate");
			String endTime = (String) schedule.get("endDate");
			startTime = startTime.substring(8);
			endTime = endTime.substring(8);

			for (Map<String, String> time : timeList) {
				if (Integer.parseInt(time.get("id")) >= Integer.parseInt(startTime)
						&& Integer.parseInt(time.get("id")) < Integer.parseInt(endTime)) {
					time.put("scheduleId", (String) schedule.get("scheduleId"));
					time.put("startDate", (String) schedule.get("startDate"));
					time.put("endDate", (String) schedule.get("endDate"));
					time.put("title", (String) schedule.get("title"));
					time.put("place", (String) schedule.get("place"));
					time.put("registerId", (String) schedule.get("registerId"));
					time.put("registerName", (String) schedule.get("registerName"));
					time.put("color", (String) schedule.get("color"));
				}
			}
		}

		return timeList;

	}

}
