/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officesupplies.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.officesupplies.dao.OfficeSuppliesDao;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSupplies;
import com.lgcns.ikep4.lightpack.officesupplies.model.OfficeSuppliesSearchCondition;
import com.lgcns.ikep4.lightpack.officesupplies.service.OfficeSuppliesService;
import com.lgcns.ikep4.lightpack.planner.util.Utils;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 시스템 관리 Service 구현 클래스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: OfficeSuppliesServiceImpl.java 17792 2012-03-30 01:32:04Z arthes $
 */
@Service("OfficeSuppliesOfficeSuppliesService")
public class OfficeSuppliesServiceImpl extends GenericServiceImpl<OfficeSupplies, String> implements OfficeSuppliesService {

	@Autowired
	OfficeSuppliesDao officesuppliesDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IdgenService idgenService;
	
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyy.MM.dd HH:mm");
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	UserDao userDao;
	
	
	public SearchResult<Map<String, Object>> myRequestList(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer count = officesuppliesDao.selectMyRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officesuppliesDao.selectMyRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> teamRequestList(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer count = officesuppliesDao.selectTeamRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officesuppliesDao.selectTeamRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> teamsRequestList(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer count = officesuppliesDao.selectTeamsRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officesuppliesDao.selectTeamsRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public List<OfficeSupplies> teamsRequestDetailList(OfficeSuppliesSearchCondition searchCondition){
		List<OfficeSupplies> list = officesuppliesDao.teamsRequestDetailList(searchCondition);
		return list;
	}
	
	public List<OfficeSupplies> teamRequestDetailList(OfficeSuppliesSearchCondition searchCondition){
		List<OfficeSupplies> list = officesuppliesDao.teamRequestDetailList(searchCondition);
		return list;
	}
	
	public void officesuppliesRequestDelete(OfficeSupplies officesupplies){
		String [] officesuppliesIds = StringUtils.split(officesupplies.getOfficesuppliesId(), "|");
		
		if(officesupplies.getOfficesuppliesId() != null) {
			for( int i = 0 ; i < officesuppliesIds.length ; i++ ){
				officesuppliesDao.officesuppliesRequestDelete(officesuppliesIds[i]);
			}
		}
	}
	
	public List<OfficeSupplies> getStatisticsList1(OfficeSupplies officesupplies){
		List<OfficeSupplies> list = officesuppliesDao.getStatisticsList1(officesupplies);
		return list;
	}
	
	public List<OfficeSupplies> getStatisticsList2(OfficeSupplies officesupplies){
		List<OfficeSupplies> list = officesuppliesDao.getStatisticsList2(officesupplies);
		return list;	
	}
	
	public List<OfficeSupplies> getStatisticsList3(OfficeSupplies officesupplies){
		List<OfficeSupplies> list = officesuppliesDao.getStatisticsList3(officesupplies);
		return list;
	}
		
	public int teamsRequestPrice(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer price = officesuppliesDao.selectTeamsRequestPrice(searchCondition);

		return price;
		
	}
	
	public int teamRequestPrice1(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer price = officesuppliesDao.selectTeamRequestPrice1(searchCondition);

		return price;
		
	}
	
	public int selectRequestUserPrice(OfficeSuppliesSearchCondition searchCondition){
		Integer price = officesuppliesDao.selectRequestUserPrice(searchCondition);

		return price;
	}
	
	public int selectRequestTeamPrice(OfficeSuppliesSearchCondition searchCondition){
		Integer price = officesuppliesDao.selectRequestTeamPrice(searchCondition);

		return price;
	}
	
	public int selectRequestMyPrice1(OfficeSuppliesSearchCondition searchCondition){
		Integer price = officesuppliesDao.selectRequestMyPrice1(searchCondition);

		return price;
	}
	
	public int selectRequestMyPrice2(OfficeSuppliesSearchCondition searchCondition){
		Integer price = officesuppliesDao.selectRequestMyPrice2(searchCondition);

		return price;
	}
	
	public int teamRequestPrice2(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer price = officesuppliesDao.selectTeamRequestPrice2(searchCondition);

		return price;
		
	}
	
	public int getTotalPrice(OfficeSupplies officesupplies){
		Integer price = officesuppliesDao.getTotalPrice(officesupplies);

		return price;
	}
	
	public SearchResult<Map<String, Object>> requestList(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer count = officesuppliesDao.selectRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officesuppliesDao.selectRequest(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> requestAllList(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer count = officesuppliesDao.selectRequestAllCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officesuppliesDao.selectRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> exceptOfficesuppliesList(OfficeSuppliesSearchCondition searchCondition) {
		
		Integer count = officesuppliesDao.selectExceptOfficesuppliesCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officesuppliesDao.selectExceptOfficesuppliesList(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public List<OfficeSupplies> exceptOfficesuppliesAllList(){
		List<OfficeSupplies> exceptOfficesuppliesList =  officesuppliesDao.exceptOfficesuppliesAllList();
		
		return exceptOfficesuppliesList;
	}
	
	public List<OfficeSupplies> officesuppliesUseTeamListAll(){
		List<OfficeSupplies> officesuppliesUseTeamList =  officesuppliesDao.officesuppliesUseTeamListAll();
		
		return officesuppliesUseTeamList;
	}
	
	public List<OfficeSupplies> officesuppliesUseTeamList(String userId){
		List<OfficeSupplies> officesuppliesUseTeamList =  officesuppliesDao.officesuppliesUseTeamList(userId);
		
		return officesuppliesUseTeamList;
	}
	
	public void officesuppliesUseRequest(OfficeSupplies officesupplies, User user){
		
		String [] productNames = StringUtils.split(officesupplies.getProductName(), "|");
		String [] productNos = StringUtils.split(officesupplies.getProductNo(), "|");
		//String [] categorys1 = StringUtils.split(officesupplies.getCategory1(), "|");
		String [] categorys2 = StringUtils.split(officesupplies.getCategory2(), "|");
		String [] remarks = StringUtils.split(officesupplies.getRemark(), "|");
		String [] amounts1 = StringUtils.split(officesupplies.getAmount1(), "|");
		String [] units = StringUtils.split(officesupplies.getUnit(), "|");
		String [] prices1 = StringUtils.split(officesupplies.getPrice1(), "|");
		String [] prices2 = StringUtils.split(officesupplies.getPrice2(), "|");
		officesupplies.setRegisterId(user.getUserId());
		officesupplies.setRegisterName(user.getUserName());
		officesupplies.setUpdaterId(user.getUserId());
		officesupplies.setUpdaterName(user.getUserName());
		officesupplies.setTeamId(user.getGroupId());
		
		List<OfficeSupplies> officeSuppliesList = new ArrayList<OfficeSupplies>();
		List<String> tempOfficesuppliesIds = new ArrayList<String>();
		if(officesupplies.getProductName() != null) {
			for( int i = 0 ; i < productNames.length ; i++ ){
				OfficeSupplies tempOfficeSupplies = new OfficeSupplies();
				String id = idgenService.getNextId();
				
				officesupplies.setOfficeSuppliesId(id);
				officesupplies.setProductName(productNames[i]);
				officesupplies.setProductNo(productNos[i]);
				//officesupplies.setCategory1(categorys1[i]);
				officesupplies.setCategory2(categorys2[i]);
				if(!remarks[i].equals("N/A")){
					officesupplies.setRemark(remarks[i]);
				}else{
					officesupplies.setRemark("");
				}
				officesupplies.setAmount1(amounts1[i]);
				officesupplies.setUnit(units[i]);
				officesupplies.setPrice1(prices1[i]);
				officesupplies.setPrice2(prices2[i]);
				
				tempOfficeSupplies.setOfficeSuppliesId(id);
				tempOfficeSupplies.setProductName(productNames[i]);
				tempOfficeSupplies.setProductNo(productNos[i]);
				//tempOfficeSupplies.setCategory1(categorys1[i]);
				tempOfficeSupplies.setCategory2(categorys2[i]);
				if(!remarks[i].equals("N/A")){
					tempOfficeSupplies.setRemark(remarks[i]);
				}else{
					tempOfficeSupplies.setRemark("");
				}
				tempOfficeSupplies.setAmount1(amounts1[i]);
				tempOfficeSupplies.setUnit(units[i]);
				tempOfficeSupplies.setPrice1(prices1[i]);
				tempOfficeSupplies.setPrice2(prices2[i]);
				tempOfficeSupplies.setRegisterName(user.getUserName());
				
				if(officesupplies.getStatus1().equals("S")){
					officeSuppliesList.add(tempOfficeSupplies);
					tempOfficesuppliesIds.add(id);
				}
				
				officesuppliesDao.officesuppliesUseRequest(officesupplies);
				
			}
		}
		
		
		// 발신자
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officesuppliesMailTemplate.vm");
		mail.setTitle("사무용품 검토 요청");
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("teamId", user.getGroupId());
		map1.put("role", "OFTR");
		List<User> userList = userDao.selectOfficeRoleUser(map1);
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Map dataMap = new HashMap();
		if(tempOfficesuppliesIds.size() > 0){
			map.put("officesuppliesIds", tempOfficesuppliesIds);
			List<OfficeSupplies> itemList = officesuppliesDao.listByItemIdArray(map);
			dataMap.put("officeSuppliesList", itemList);
			recip = new HashMap<String, String>();
			
			if(userList.size() > 0){
				for(User tempUser : userList){
					recipient = userService.read(tempUser.getUserId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
				mail.setToEmailList(recipients);
				
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do");
				mailSendService.sendMail(mail, dataMap, sender);
				
			}
		}
		
		
		
		
	}
	
	public boolean existsProductno(String productNo) {

		return officesuppliesDao.existsProductno(productNo);
	}
	
	public boolean periodCheck(){
		return officesuppliesDao.periodCheck();
	}
	
	public boolean teamManagerCheck(Map<String, String> map){
		return officesuppliesDao.teamManagerCheck(map);
	}
	
	public boolean teamLeaderCheck(Map<String, String> map){
		return officesuppliesDao.teamLeaderCheck(map);
	}
	
	public void officesuppliesCheckBoxUseRequest(OfficeSupplies officesupplies, User user){
		
		String [] officesuppliesIds = StringUtils.split(officesupplies.getOfficesuppliesId(), "|");
		officesupplies.setUpdaterId(user.getUserId());
		officesupplies.setUpdaterName(user.getUserName());
		officesupplies.setTeamId(user.getGroupId());
		officesupplies.setStatus1(officesupplies.getStatus1());
		
		List<String> tempOfficesuppliesIds = new ArrayList<String>();
		
		if(officesupplies.getOfficesuppliesId() != null) {
			for( int i = 0 ; i < officesuppliesIds.length ; i++ ){
				tempOfficesuppliesIds.add(officesuppliesIds[i]);
				officesupplies.setOfficeSuppliesId(officesuppliesIds[i]);
				
				if(officesupplies.getStatus1().equals("C")){
					officesupplies.setTeamReviewerId(user.getUserId());
					officesupplies.setTeamReviewerName(user.getUserName());
				}else if(officesupplies.getStatus1().equals("A")){
					officesupplies.setTeamApprId(user.getUserId());
					officesupplies.setTeamApprName(user.getUserName());
				}else if(officesupplies.getStatus1().equals("R")){
					officesupplies.setTeamApprId(user.getUserId());
					officesupplies.setTeamApprName(user.getUserName());
				}
				officesuppliesDao.officesuppliesCheckBoxUseRequest(officesupplies);
			}
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("officesuppliesIds", tempOfficesuppliesIds);
		
		List<OfficeSupplies> itemList = officesuppliesDao.listByItemIdArray(map);
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officesuppliesMailTemplate.vm");
		
		
		List<User> userList = userDao.selectOfficeRoleUsers("OFTL");
		
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("teamId", user.getGroupId());
		map1.put("role", "OFTR");
		List<User> userList3 = userDao.selectOfficeRoleUser(map1);
		List<User> userList2 = userDao.selectRoleUser("OFMR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		//dataMap.put("officeSuppliesList", itemList);
		recip = new HashMap<String, String>();
		List<OfficeSupplies> tempItemList = new ArrayList<OfficeSupplies>();
		String tempTeamId = "";
			if("C".equals(itemList.get(0).getStatus1())){//팀장 정보 추출
				for(OfficeSupplies tempOfficeSupplies : itemList){
					if(!tempOfficeSupplies.getTeamId().equals(tempTeamId) && tempTeamId != ""){
						if(userList.size() > 0){
							mail.setTitle("사무용품 승인 요청");
							for(User tempUser : userList){
								if(tempUser.getGroupId().equals(tempTeamId)){
									recipient = userService.read(tempUser.getUserId());
									recip.put("email", recipient.getMail());
									recip.put("name", recipient.getUserName());
									recipients.add(recip);
								}
							}
							dataMap.put("officeSuppliesList", tempItemList);
							dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do");
							mail.setToEmailList(recipients);
							
							mailSendService.sendMail(mail, dataMap, sender);
							dataMap.clear();
							int tempItemListSize = tempItemList.size()-1;
							while(tempItemListSize >= 0){
								tempItemList.remove(tempItemListSize);
								tempItemListSize--;
							}
							recipients.remove(recip);
							recip.clear();
							tempItemList.add(tempOfficeSupplies);
						}
					}else{
						tempItemList.add(tempOfficeSupplies);
					}
					tempTeamId = tempOfficeSupplies.getTeamId();
				}
				mail.setTitle("사무용품 승인 요청");
				for(User tempUser : userList){
					recipient = userService.read(tempUser.getUserId());
					if(tempUser.getGroupId().equals(tempTeamId)){
						recip.put("email", recipient.getMail());
						recip.put("name", recipient.getUserName());
						recipients.add(recip);
					}
				}
				dataMap.put("officeSuppliesList", tempItemList);
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);
				/*mail.setTitle("사무용품 승인 요청");
				if(userList.size() > 0){
					for(User tempUser : userList){
						recipient = userService.read(tempUser.getUserId());
						if(recipient.getGroupId().equals(user.getGroupId())){
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
					}
				}
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);*/
			}else if("A".equals(itemList.get(0).getStatus1())){//팀 담당자 정보 추출

				mail.setTitle("사무용품 검토 요청");
				if(tempOfficesuppliesIds.size() > 0){
					dataMap.put("officeSuppliesList", itemList);
					recip = new HashMap<String, String>();
					
					if(userList2.size() > 0){
						for(User tempUser : userList2){
							recipient = userService.read(tempUser.getUserId());
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
						mail.setToEmailList(recipients);
						
						dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamsList.do");
						mailSendService.sendMail(mail, dataMap, sender);
						
					}
				}
			}else if("S".equals(itemList.get(0).getStatus1())){//팀 담당자 정보 추출

				mail.setTitle("사무용품 검토 요청");
				if(tempOfficesuppliesIds.size() > 0){
					dataMap.put("officeSuppliesList", itemList);
					recip = new HashMap<String, String>();
					
					if(userList2.size() > 0){
						for(User tempUser : userList3){
							recipient = userService.read(tempUser.getUserId());
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
						mail.setToEmailList(recipients);
						
						dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do");
						mailSendService.sendMail(mail, dataMap, sender);
						
					}
				}
			}
			/*else if("S".equals(itemList.get(0).getStatus1())){//담당부서 담당자 정보 추출
				for(OfficeSupplies tempOfficeSupplies : itemList){
					if(tempOfficeSupplies.getTeamId() != tempTeamId && tempTeamId != ""){
						if(userList.size() > 0){
							mail.setTitle("사무용품 검토 요청");
							for(User tempUser : userList2){
								recipient = userService.read(tempUser.getUserId());
								if(tempUser.getGroupId().equals(tempOfficeSupplies.getTeamId())){
									recip.put("email", recipient.getMail());
									recip.put("name", recipient.getUserName());
									recipients.add(recip);
								}
							}
							dataMap.put("officeSuppliesList", tempItemList);
							dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do");
							mail.setToEmailList(recipients);
							
							mailSendService.sendMail(mail, dataMap, sender);
							tempItemList = null;
							recipients = null;
							recip = null;
							tempItemList.add(tempOfficeSupplies);
						}
					}else{
						tempItemList.add(tempOfficeSupplies);
					}
					tempTeamId = tempOfficeSupplies.getTeamId();
				}
				mail.setTitle("사무용품 검토 요청");
				for(User tempUser : userList){
					recipient = userService.read(tempUser.getUserId());
					if(tempUser.getGroupId().equals(tempTeamId)){
						recip.put("email", recipient.getMail());
						recip.put("name", recipient.getUserName());
						recipients.add(recip);
					}
				}
				dataMap.put("officeSuppliesList", tempItemList);
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamsList.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);*/
				/*mail.setTitle("사무용품 검토 요청");
				if(userList2.size() > 0){
					for(User tempUser2 : userList2){
						recipient = userService.read(tempUser2.getUserId());
						recip.put("email", recipient.getMail());
						recip.put("name", recipient.getUserName());
						recipients.add(recip);
					}
				}
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamsList.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);
			}*/
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OfficeSupplies item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			
		

	}
	
	public List<OfficeSupplies> listCategory(OfficeSupplies categoryBoardId){
		return officesuppliesDao.listCategory(categoryBoardId);
	}
	
	public void insertCategoryNm(List<OfficeSupplies> receiveCategoryNmList){
		for (int i = 0; i < receiveCategoryNmList.size(); i++) {
			OfficeSupplies category = receiveCategoryNmList.get(i);
			String addNameList = category.getAddNameList();
			String delIdList   = category.getDelIdList();
			String updateIdList   = category.getUpdateIdList();
			String updateNameList = category.getUpdateNameList();
			String boardId = category.getBoardId();
			String alignList   = category.getAlignList();
			
			String[] arrayReceive;
			arrayReceive  = addNameList.split(",");
			
			String[] arrayModifyId;
			arrayModifyId = updateIdList.split(",");
			
			String[] arrayModifyNm;
			arrayModifyNm = updateNameList.split(",");
			
			String[] arrayDelId;
			arrayDelId    = delIdList.split(",");

			
			
			String[] arrayAlignName;
			arrayAlignName    = alignList.split(",");
			
			
			if(!"".equals(addNameList) ){
				for (int j = 0; j < arrayReceive.length; j++) {		
					category.setCategoryId(idgenService.getNextId());
					category.setAddNameList(arrayReceive[j]);
					category.setBoardId(boardId);
					this.officesuppliesDao.createCategoryNm(category);
				}	
			}
			
			if(!"".equals(updateIdList) ){
				for (int j = 0; j < arrayModifyId.length; j++) {		
					category.setUpdateIdList(arrayModifyId[j]);
					category.setUpdateNameList(arrayModifyNm[j]);
					category.setBoardId(boardId);
					this.officesuppliesDao.updateCategoryNm(category);
				}	
			}
			
			if(!"".equals(delIdList) ){
				for (int j = 0; j < arrayDelId.length; j++) {		
					category.setDelIdList(arrayDelId[j]);
					category.setBoardId(boardId);
					this.officesuppliesDao.deleteCategoryNm(category);
				}	
			}
			
			if(!"".equals(arrayAlignName) ){
				for (int j = 0; j < arrayAlignName.length; j++) {	
					category.setCategorySeqId(""+j);
					category.setAlignList(arrayAlignName[j]);
					category.setBoardId(boardId);
					this.officesuppliesDao.updateCategoryAlign(category);
				}	
			}
			
		}
	}
	
	public void officesuppliesManageCheckBoxUseRequest(OfficeSupplies officesupplies, User user){
		
		String [] officesuppliesIds = StringUtils.split(officesupplies.getOfficesuppliesId(), "|");
		officesupplies.setUpdaterId(user.getUserId());
		officesupplies.setUpdaterName(user.getUserName());
		officesupplies.setTeamId(user.getGroupId());
		officesupplies.setStatus2(officesupplies.getStatus2());
		List<String> tempOfficesuppliesIds = new ArrayList<String>();
		if(officesupplies.getOfficesuppliesId() != null) {
			for( int i = 0 ; i < officesuppliesIds.length ; i++ ){
				officesupplies.setOfficeSuppliesId(officesuppliesIds[i]);
				tempOfficesuppliesIds.add(officesuppliesIds[i]);
				if(officesupplies.getStatus2().equals("C")){
					officesupplies.setManageReviewerId(user.getUserId());
					officesupplies.setManageReviewerName(user.getUserName());
				}else if(officesupplies.getStatus2().equals("A")){
					officesupplies.setManageApprId(user.getUserId());
					officesupplies.setManageApprName(user.getUserName());
				}else if(officesupplies.getStatus2().equals("R")){
					officesupplies.setManageApprId(user.getUserId());
					officesupplies.setManageApprName(user.getUserName());
				}
				officesuppliesDao.officesuppliesManageCheckBoxUseRequest(officesupplies);
			}
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("officesuppliesIds", tempOfficesuppliesIds);
		
		List<OfficeSupplies> itemList = officesuppliesDao.listByItemIdArray(map);
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officesuppliesMailTemplate.vm");
		
		
		List<User> userList = userDao.selectRoleUser("OFML");
		
		//List<User> userList2 = userDao.selectRoleUser("OFMR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		dataMap.put("officeSuppliesList", itemList);
		recip = new HashMap<String, String>();
		
		
			if("C".equals(itemList.get(0).getStatus2())){//팀장 정보 추출
				mail.setTitle("사무용품 승인 요청");
				if(userList.size() > 0){
					for(User tempUser : userList){
						recipient = userService.read(tempUser.getUserId());
						if(recipient.getGroupId().equals(user.getGroupId())){
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
					}
				}
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamsList.do");
			}
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OfficeSupplies item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			mail.setToEmailList(recipients);
			
			
			mailSendService.sendMail(mail, dataMap, sender);

	}
	
	public void officesuppliesCheckBoxGroupRequest(OfficeSupplies officesupplies, User user){
		
		String [] teamIds = StringUtils.split(officesupplies.getTeamId(), "|");
		officesupplies.setUpdaterId(user.getUserId());
		officesupplies.setUpdaterName(user.getUserName());
		officesupplies.setTeamId(user.getGroupId());
		officesupplies.setStatus2(officesupplies.getStatus2());
		List<String> tempTeamIds = new ArrayList<String>();
		if(officesupplies.getTeamId() != null) {
			for( int i = 0 ; i < teamIds.length ; i++ ){
				officesupplies.setTeamId(teamIds[i]);
				tempTeamIds.add(teamIds[i]);
				if(officesupplies.getStatus2().equals("C")){
					officesupplies.setManageReviewerId(user.getUserId());
					officesupplies.setManageReviewerName(user.getUserName());
				}else if(officesupplies.getStatus2().equals("A")){
					officesupplies.setManageApprId(user.getUserId());
					officesupplies.setManageApprName(user.getUserName());
				}else if(officesupplies.getStatus2().equals("R")){
					officesupplies.setManageApprId(user.getUserId());
					officesupplies.setManageApprName(user.getUserName());
				}
				officesuppliesDao.officesuppliesCheckBoxGroupRequest(officesupplies);
			}
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("teamIds", tempTeamIds);
		
		List<OfficeSupplies> itemList = null;
		
		if(officesupplies.getStatus2().equals("C")){
			 itemList = officesuppliesDao.listByTeamItemIdArray2(map);
		}else if(officesupplies.getStatus2().equals("A")){
			 itemList = officesuppliesDao.listByTeamItemIdArray1(map);
		}
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officesuppliesMailTemplate.vm");
		
		
		List<User> userList = userDao.selectRoleUser("OFML");
		
		//List<User> userList2 = userDao.selectRoleUser("OFMR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		dataMap.put("officeSuppliesList", itemList);
		recip = new HashMap<String, String>();
		
		
			if("C".equals(itemList.get(0).getStatus2())){//팀장 정보 추출
				mail.setTitle("사무용품 승인 요청");
				if(userList.size() > 0){
					for(User tempUser : userList){
						recipient = userService.read(tempUser.getUserId());
						if(recipient.getGroupId().equals(user.getGroupId())){
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
					}
				}
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamsList.do");
			}
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OfficeSupplies item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			mail.setToEmailList(recipients);
			
			
			mailSendService.sendMail(mail, dataMap, sender);

	}
	
	public void savePeriod(OfficeSupplies officesupplies){
		String [] startDates = StringUtils.split(officesupplies.getStartDate(), "|");
		String [] endDates = StringUtils.split(officesupplies.getEndDate(), "|");
		String [] months = StringUtils.split(officesupplies.getMonth(), "|");
		String [] statuss = StringUtils.split(officesupplies.getStatus(), "|");
		
		for( int i = 0 ; i < startDates.length ; i++ ){
			if(startDates[i].equals("N")){
				officesupplies.setStartDate(null);
			}else{
				officesupplies.setStartDate(startDates[i]);
			}
			if(endDates[i].equals("N")){
				officesupplies.setEndDate(null);
			}else{
				officesupplies.setEndDate(endDates[i]);
			}
			officesupplies.setMonth(months[i]);
			officesupplies.setStatus(statuss[i]);
			officesuppliesDao.savePeriod(officesupplies);
		}
		
		
	}
	
	public OfficeSupplies getPeriod(){
		OfficeSupplies period = officesuppliesDao.getPeriod();
		return period;
	}
	
	public OfficeSupplies getOfficesuppliesTeamAuthInfo(String teamId){
		OfficeSupplies teamAuthInfo = officesuppliesDao.getOfficesuppliesTeamAuthInfo(teamId);
		return teamAuthInfo;
	}
	
	public List<OfficeSupplies> getPeriodList(String year){
		Integer count = officesuppliesDao.selectPeriodListCount(year);
		
		if(count < 1){
			officesuppliesDao.insertDefaultPeriod(year);
		}

		List<OfficeSupplies> list = officesuppliesDao.getPeriodList(year);
		return list;
	}
	
	public List<OfficeSupplies> getOfficesuppliesTeamAuthList(){
		List<OfficeSupplies> list = officesuppliesDao.getOfficesuppliesTeamAuthList();
		return list;
	}
	
	public void officesuppliesUseRequestUpdate(OfficeSupplies officesupplies, User user){
		
		String [] officesuppliesIds = StringUtils.split(officesupplies.getOfficesuppliesId(), "|");
		String [] productNames = StringUtils.split(officesupplies.getProductName(), "|");
		String [] productNos = StringUtils.split(officesupplies.getProductNo(), "|");
		//String [] categorys1 = StringUtils.split(officesupplies.getCategory1(), "|");
		String [] categorys2 = StringUtils.split(officesupplies.getCategory2(), "|");
		String [] remarks = StringUtils.split(officesupplies.getRemark(), "|");
		String [] amounts1 = StringUtils.split(officesupplies.getAmount1(), "|");
		String [] units = StringUtils.split(officesupplies.getUnit(), "|");
		String [] prices1 = StringUtils.split(officesupplies.getPrice1(), "|");
		String [] prices2 = StringUtils.split(officesupplies.getPrice2(), "|");
		officesupplies.setUpdaterId(user.getUserId());
		officesupplies.setUpdaterName(user.getUserName());
		if(officesupplies.getTeamId() != null){
			officesupplies.setTeamId(officesupplies.getTeamId());
		}else{
			officesupplies.setTeamId(user.getGroupId());
		}
		
		List<String> tempOfficesuppliesIds = new ArrayList<String>();
		
		if(officesupplies.getProductName() != null) {
			for( int i = 0 ; i < productNames.length ; i++ ){
				
				if(!officesuppliesIds[i].equals("newcode")){
					officesupplies.setOfficeSuppliesId(officesuppliesIds[i]);
					officesupplies.setProductName(productNames[i]);
					officesupplies.setProductNo(productNos[i]);
					//officesupplies.setCategory1(categorys1[i]);
					officesupplies.setCategory2(categorys2[i]);
					if(!remarks[i].equals("N/A")){
						officesupplies.setRemark(remarks[i]);
					}else{
						officesupplies.setRemark("");
					}
					officesupplies.setAmount1(amounts1[i]);
					officesupplies.setUnit(units[i]);
					officesupplies.setPrice1(prices1[i]);
					officesupplies.setPrice2(prices2[i]);
					officesupplies.setRegisterId(user.getUserId());
					officesupplies.setRegisterName(user.getUserName());
					officesuppliesDao.officesuppliesUseRequestUpdate(officesupplies);
					if(officesupplies.getStatus1().equals("S")){
						tempOfficesuppliesIds.add(officesuppliesIds[i]);
					}
				}else{
					String id = idgenService.getNextId();
					officesupplies.setOfficeSuppliesId(id);
					officesupplies.setProductName(productNames[i]);
					officesupplies.setProductNo(productNos[i]);
					//officesupplies.setCategory1(categorys1[i]);
					officesupplies.setCategory2(categorys2[i]);
					if(!remarks[i].equals("N/A")){
						officesupplies.setRemark(remarks[i]);
					}else{
						officesupplies.setRemark("");
					}
					officesupplies.setAmount1(amounts1[i]);
					officesupplies.setUnit(units[i]);
					officesupplies.setPrice1(prices1[i]);
					officesupplies.setPrice2(prices2[i]);
					officesuppliesDao.officesuppliesUseRequest(officesupplies);
					if(officesupplies.getStatus1().equals("S")){
						tempOfficesuppliesIds.add(id);
					}
				}
			}
		}
		
		// 발신자
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officesuppliesMailTemplate.vm");
		mail.setTitle("사무용품 검토 요청");
		List<User> userList = userDao.selectRoleUser("OFTR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Map dataMap = new HashMap();
		if(tempOfficesuppliesIds.size() > 0){
			map.put("officesuppliesIds", tempOfficesuppliesIds);
			List<OfficeSupplies> itemList = officesuppliesDao.listByItemIdArray(map);
			dataMap.put("officeSuppliesList", itemList);
			recip = new HashMap<String, String>();
			
			if(userList.size() > 0){
				for(User tempUser : userList){
					recipient = userService.read(tempUser.getUserId());
					if(recipient.getGroupId().equals(user.getGroupId())){
						recip.put("email", recipient.getMail());
						recip.put("name", recipient.getUserName());
						recipients.add(recip);
					}
					
					
				}
				mail.setToEmailList(recipients);
				
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officesupplies/officesuppliesUseRequestTeamListPayment.do");
				mailSendService.sendMail(mail, dataMap, sender);
				
			}
		}

	}
	
	public OfficeSupplies getOfficeSuppliesUseRequestView(String officesuppliesId){
		
		OfficeSupplies officesupplies = officesuppliesDao.getOfficeSuppliesUseRequestView(officesuppliesId);
		
		return officesupplies;
	}
	
	public void officesuppliesApproveUse(OfficeSupplies officesupplies){
		officesuppliesDao.officesuppliesApproveUse(officesupplies);
	}
	
	public void updateOfficesuppliesExcept(OfficeSupplies officesupplies){
		officesuppliesDao.updateOfficesuppliesExcept(officesupplies);
	}
	
	public void createOfficesuppliesExcept(OfficeSupplies officesupplies){
		officesuppliesDao.createOfficesuppliesExcept(officesupplies);
	}
	
	public void deleteOfficesuppliesExcept(OfficeSupplies officesupplies){
		officesuppliesDao.deleteOfficesuppliesExcept(officesupplies);
	}
	
	public void officesuppliesUseRequestUpdate(OfficeSupplies officesupplies){
		officesuppliesDao.officesuppliesUseRequestUpdate(officesupplies);
	}
	
	public void officesuppliesTeamAuthSave(OfficeSupplies officesupplies){
		officesuppliesDao.officesuppliesTeamAuthSave(officesupplies);
	}
	
	public void officesuppliesUseRequestDelete(OfficeSupplies officesupplies){
		officesuppliesDao.officesuppliesUseRequestDelete(officesupplies);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendOfficeSuppliesUseRequestMail(String flg, String message, OfficeSupplies officesupplies, User sender){
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officesuppliesUseRequestMailTemplate.vm");
		
		List<OfficeSupplies> managerList = officesuppliesDao.selectOfficeSuppliesManager("USBADM");
		
		String teamLeader = "";
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		
		dataMap.put("startStr", officesupplies.getStartDate());
		dataMap.put("endStr", officesupplies.getEndDate());		
		dataMap.put("registerName", officesupplies.getRegisterName());
		
		
		recip = new HashMap<String, String>();
		
		//요청-결재자(팀장)에게 메일 전송
		if(flg == "req"){
			teamLeader = officesuppliesDao.getTeamLeader(sender.getUserId());
			recipient = userService.read(teamLeader);
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			dataMap.put("title", "USB 사용 승인 요청");
			dataMap.put("contents", "USB 사용 승인을 요청합니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officesupplies/officesuppliesUseRequestList.do");
			
			mail.setToEmailList(recipients);
			mail.setTitle("USB 사용 승인 요청");

			mailSendService.sendMail(mail, dataMap, sender);
		//팀장 승인or반려 신청자,담당자에게 메일 전송
		}else if(flg == "apr"){
			mail.setTitle("USB 사용 승인 완료");
			dataMap.put("title", "USB 사용 승인 완료");
			dataMap.put("contents", "USB 사용 승인이 완료되었습니다.");
			
			if(managerList.size() > 0){
				for(OfficeSupplies tofficesupplies : managerList){
					recipient = userService.read(tofficesupplies.getManagerId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
					mail.setToEmailList(recipients);
					
					dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officesupplies/officesuppliesUseRequestAllList.do");
					mailSendService.sendMail(mail, dataMap, sender);
				}
			}
			recipient = userService.read(officesupplies.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officesupplies/officesuppliesUseRequestMyList.do");
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		//담당자 확인 신청자에게 메일 전송
		}else if(flg == "cfm"){
			mail.setTitle("USB 사용 권한 변경 완료");
			dataMap.put("title", "USB 사용 권한 변경 완료");
			dataMap.put("contents", "USB 사용 권한이 변경되어 USB 사용이 가능합니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officesupplies/officesuppliesUseRequestMyList.do");
			recipient = userService.read(officesupplies.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		}else if(flg == "rej"){
			mail.setTitle("USB 사용 신청 반려");
			dataMap.put("title", "USB 사용 신청 반려");
			dataMap.put("contents", "USB 사용 신청이 반려 되었습니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officesupplies/officesuppliesUseRequestMyList.do");
			recipient = userService.read(officesupplies.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		}else{
			
		}
		
		
	}
}