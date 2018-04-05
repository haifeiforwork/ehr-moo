/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.meetingroom.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.lightpack.meetingroom.dao.CartooletcDao;
import com.lgcns.ikep4.lightpack.meetingroom.dao.EquipmentDao;
import com.lgcns.ikep4.lightpack.meetingroom.dao.MeetingRoomDao;
import com.lgcns.ikep4.lightpack.meetingroom.model.BuildingFloor;
import com.lgcns.ikep4.lightpack.meetingroom.model.Cartooletc;
import com.lgcns.ikep4.lightpack.meetingroom.model.Equipment;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoom;
import com.lgcns.ikep4.lightpack.meetingroom.service.CartooletcService;
import com.lgcns.ikep4.lightpack.meetingroom.service.MeetingRoomService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Service
public class CartooletcServiceImpl implements  CartooletcService {

	@Autowired
	private CartooletcDao cartooletcDao;

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private FileService fileService;

	public String create( Cartooletc  cartooletc) {

		User user = (User) getRequestAttribute("ikep.user");

		if("new".equals(cartooletc.getCategoryId())){
			cartooletc.setCategoryId(idgenService.getNextId());
		}
		
		if("new".equals(cartooletc.getRegionId())){
			cartooletc.setRegionId(idgenService.getNextId());
		}
		
		cartooletc.setCartooletcId(idgenService.getNextId());
		ModelBeanUtil.bindRegisterInfo(cartooletc, user.getUserId(), user.getUserName());
		
		cartooletcDao.create(cartooletc);

		if(!StringUtil.isEmpty(cartooletc.getFileId())) {
			
			fileService.createFileLink(cartooletc.getFileId(), cartooletc.getCartooletcId(),IKepConstant.ITEM_TYPE_CODE_PLANNER, user);
		}
		
		return cartooletc.getCartooletcId();
	}

	public boolean exists(String roomId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(Cartooletc cartooletc) {

		User user = (User) getRequestAttribute("ikep.user");
		
		ModelBeanUtil.bindRegisterInfo(cartooletc, user.getUserId(), user.getUserName());
		
		if("new".equals(cartooletc.getCategoryId())){
			cartooletc.setCategoryId(idgenService.getNextId());
		}
		
		if("new".equals(cartooletc.getRegionId())){
			cartooletc.setRegionId(idgenService.getNextId());
		}

		cartooletcDao.update(cartooletc);

		if(!StringUtil.isEmpty(cartooletc.getFileId())) {
		
			fileService.createFileLink(cartooletc.getFileId(), cartooletc.getCartooletcId(),IKepConstant.ITEM_TYPE_CODE_PLANNER, user);
		}
	}

	public void delete(String roomId) {
		
		cartooletcDao.remove(roomId);
		
		fileService.removeItemFile(roomId);
	}

	public void delete(String[] roomIds) {

		for (String roomId : roomIds) {
			
			cartooletcDao.remove(roomId);
			
			fileService.removeItemFile(roomId);
		}
	}

	public Cartooletc read(String cartooletcId) {

		Cartooletc cartooletc = (Cartooletc) cartooletcDao.get(cartooletcId);


		return cartooletc;
	}

	public List<Cartooletc> select(Cartooletc cartooletc) {
		
		List<Cartooletc> meetingRoomList = cartooletcDao.select(cartooletc);
		
		
		return meetingRoomList;
	}

	public void changeSort(List<Cartooletc> cartooletcList, String cartooletcId, int sortOrder) {


	}
	
	public String checkFavorite(String cartooletcId) {

		User user = (User) getRequestAttribute("ikep.user");

		Cartooletc cartooletc = new Cartooletc();
		cartooletc.setCartooletcId(cartooletcId);
		cartooletc.setRegisterId(user.getUserId());

		String cnt = cartooletcDao.checkFavorite(cartooletc);

		if (Integer.parseInt(cnt) > 0) {
			
			return "Y";
			
		} else {
			
			return "N";
		}
	}
	
	public void addFavorite(String cartooletcId) {

		User user = (User) getRequestAttribute("ikep.user");
		
		Cartooletc cartooletc = new Cartooletc();
		cartooletc.setCartooletcId(cartooletcId);
		cartooletc.setRegisterId(user.getUserId());

		cartooletcDao.addFavorite(cartooletc);

	}

	public void delFavorite(String cartooletcId) {

		User user = (User) getRequestAttribute("ikep.user");

		Cartooletc cartooletc = new Cartooletc();
		cartooletc.setCartooletcId(cartooletcId);
		cartooletc.setRegisterId(user.getUserId());

		cartooletcDao.delFavorite(cartooletc);

	}
	
	public List<Map<String, String>> makeMeetingTimeList(String meetingRoomId, List<Map<String, Object>> scheduleList) {

		List<Map<String, String>> timeList = new ArrayList<Map<String, String>>();

		for (int i = 8; i < 20; i++) {
			
			Map<String, String> map1 = new HashMap<String, String>();
			Map<String, String> map2 = new HashMap<String, String>();
			
			if (i < 10) {
				
				map1.put("isHeader", "N");
				map1.put("meetingRoomId", meetingRoomId);
				map1.put("id", "0" + i + "00");
				map1.put("name", "0" + i + ":00");
				map1.put("from", "0" + i + ":00");
				map1.put("to", "0" + i + ":30");
				
				map2.put("isHeader", "N");
				map2.put("meetingRoomId", meetingRoomId);
				map2.put("id", "0" + i + "30");
				map2.put("name", "0" + i + ":30");
				map2.put("from", "0" + i + ":30");
				
				if((i + 1) < 10) {
					
					map2.put("to", "0" + (i + 1) + ":00");
				}else {
					
					map2.put("to", (i + 1) + ":00");
				}
			} else {
				
				map1.put("isHeader", "N");
				map1.put("meetingRoomId", meetingRoomId);
				map1.put("id", i + "00");
				map1.put("name", i + ":00");
				map1.put("from", i + ":00");
				map1.put("to", i + ":30");
				
				map2.put("isHeader", "N");
				map2.put("meetingRoomId", meetingRoomId);
				map2.put("id", i + "30");
				map2.put("name", i + ":30");
				map2.put("from", i + ":30");
				map2.put("to", (i + 1) + ":00");
			}

			timeList.add(map1);
			timeList.add(map2);
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
					time.put("approveStatus", (String) schedule.get("approveStatus"));
					time.put("schedulePublic", String.valueOf(schedule.get("schedulePublic")));
				}
			}
		}

		return timeList;

	}
	
	public List<Map<String, Object>> makeMeetingWeekTimeList(String meetingRoomId, List<Map<String, Object>> scheduleList, String startDayOfWeek) {

		List<Map<String, Object>> timeList = new ArrayList<Map<String, Object>>();
		
		String startYear = startDayOfWeek.substring(0, 4);
		String startMonth = startDayOfWeek.substring(5, 7);
		String startDay = startDayOfWeek.substring(8, 10);
		
		Date firstDayOfWeek = DateUtil.toDate(startYear + startMonth + startDay);
		
		

		for (int i = 1; i < 8; i++) {
			
			Date day = new Date();
			
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(firstDayOfWeek);
			cal.add(Calendar.DATE, i - 1);
			
			day = cal.getTime();
			String convertDay = DateUtil.getFmtDateString(day, "yyyy-MM-dd");
			
			Map<String, Object> map1 = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			Map<String, Object> map3 = new HashMap<String, Object>();
			Map<String, Object> map4 = new HashMap<String, Object>();
				
			map1.put("isHeader", "N");
			map1.put("meetingRoomId", meetingRoomId);
			map1.put("start", "0800");
			map1.put("from", "08:00");
			map1.put("to", "08:30");
			map1.put("dayOfWeek", String.valueOf(i));
			map1.put("day", convertDay);
			
			map2.put("isHeader", "N");
			map2.put("meetingRoomId", meetingRoomId);
			map2.put("start", "1100");
			map2.put("from", "11:00");
			map2.put("to", "11:30");
			map2.put("dayOfWeek", String.valueOf(i));
			map2.put("day", convertDay);
			
			map3.put("isHeader", "N");
			map3.put("meetingRoomId", meetingRoomId);
			map3.put("start", "1400");
			map3.put("from", "14:00");
			map3.put("to", "14:30");
			map3.put("dayOfWeek", String.valueOf(i));
			map3.put("day", convertDay);
			
			map4.put("isHeader", "N");
			map4.put("meetingRoomId", meetingRoomId);
			map4.put("start", "1700");
			map4.put("from", "17:00");
			map4.put("to", "17:30");
			map4.put("dayOfWeek", String.valueOf(i));
			map4.put("day", convertDay);
				
			timeList.add(map1);
			timeList.add(map2);
			timeList.add(map3);
			timeList.add(map4);
		}

		for (Map<String, Object> schedule : scheduleList) {
			
			String startTime = (String) schedule.get("startDate");
			String endTime = (String) schedule.get("endDate");
			
			String tempDate = startTime.substring(0, 8);
			
			startTime = startTime.substring(8);
			endTime = endTime.substring(8);
			
			Date scheduleDate = DateUtil.toDate(tempDate);
			
			Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(scheduleDate);
			
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			
			for (Map<String, Object> time : timeList) {
				
				if (Integer.parseInt((String) time.get("dayOfWeek")) == dayOfWeek && Integer.parseInt((String) time.get("start")) <= Integer.parseInt(startTime) && Integer.parseInt((String) time.get("start")) + 300 > Integer.parseInt(endTime)) {
					
					time.put("scheduleId", (String) schedule.get("scheduleId"));
					time.put("startDate", (String) schedule.get("startDate"));
					time.put("endDate", (String) schedule.get("endDate"));
					time.put("title", (String) schedule.get("title"));
					time.put("place", (String) schedule.get("place"));
					time.put("registerId", (String) schedule.get("registerId"));
					time.put("registerName", (String) schedule.get("registerName"));
					time.put("approveStatus", (String) schedule.get("approveStatus"));
				}
			}
		}

		return timeList;

	}

	public Object getRequestAttribute(String name) {
		
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	public List<Cartooletc> categoryList(Map<String, String> param) {
		
		List<Cartooletc> list = cartooletcDao.categoryList(param);
		
		return list;
	}
	public List<Cartooletc> regionList(Map<String, String> param) {
		
		List<Cartooletc> list = cartooletcDao.regionList(param);
		
		return list;
	}
	
	public List<Cartooletc> toolList(Map<String, String> param) {
		
		List<Cartooletc> list = cartooletcDao.toolList(param);
		
		return list;
	}
}
