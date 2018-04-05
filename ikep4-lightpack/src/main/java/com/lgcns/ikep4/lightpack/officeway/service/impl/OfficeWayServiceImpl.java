/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.officeway.service.impl;

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
import com.lgcns.ikep4.lightpack.officeway.dao.OfficeWayDao;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWay;
import com.lgcns.ikep4.lightpack.officeway.model.OfficeWaySearchCondition;
import com.lgcns.ikep4.lightpack.officeway.service.OfficeWayService;
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
 * @version $Id: OfficeWayServiceImpl.java 17792 2012-03-30 01:32:04Z arthes $
 */
@Service("OfficeWayOfficeWayService")
public class OfficeWayServiceImpl extends GenericServiceImpl<OfficeWay, String> implements OfficeWayService {

	@Autowired
	OfficeWayDao officewayDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IdgenService idgenService;
	
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyy.MM.dd HH:mm");
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	UserDao userDao;
	
	
	public SearchResult<Map<String, Object>> myRequestList(OfficeWaySearchCondition searchCondition) {
		
		Integer count = officewayDao.selectMyRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officewayDao.selectMyRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> teamRequestList(OfficeWaySearchCondition searchCondition) {
		
		Integer count = officewayDao.selectTeamRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officewayDao.selectTeamRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> teamsRequestList(OfficeWaySearchCondition searchCondition) {
		
		Integer count = officewayDao.selectTeamsRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officewayDao.selectTeamsRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public List<OfficeWay> teamsRequestDetailList(OfficeWaySearchCondition searchCondition){
		List<OfficeWay> list = officewayDao.teamsRequestDetailList(searchCondition);
		return list;
	}
	
	public List<OfficeWay> teamRequestDetailList(OfficeWaySearchCondition searchCondition){
		List<OfficeWay> list = officewayDao.teamRequestDetailList(searchCondition);
		return list;
	}
	
	public void officewayRequestDelete(OfficeWay officeway){
		String [] officewayIds = StringUtils.split(officeway.getOfficewayId(), "|");
		
		if(officeway.getOfficewayId() != null) {
			for( int i = 0 ; i < officewayIds.length ; i++ ){
				officewayDao.officewayRequestDelete(officewayIds[i]);
			}
		}
	}
	
	public List<OfficeWay> getStatisticsList1(OfficeWay officeway){
		List<OfficeWay> list = officewayDao.getStatisticsList1(officeway);
		return list;
	}
	
	public List<OfficeWay> getStatisticsList2(OfficeWay officeway){
		List<OfficeWay> list = officewayDao.getStatisticsList2(officeway);
		return list;	
	}
	
	public List<OfficeWay> getStatisticsList3(OfficeWay officeway){
		List<OfficeWay> list = officewayDao.getStatisticsList3(officeway);
		return list;
	}
		
	public int teamsRequestPrice(OfficeWaySearchCondition searchCondition) {
		
		Integer price = officewayDao.selectTeamsRequestPrice(searchCondition);

		return price;
		
	}
	
	public int teamRequestPrice1(OfficeWaySearchCondition searchCondition) {
		
		Integer price = officewayDao.selectTeamRequestPrice1(searchCondition);

		return price;
		
	}
	
	public int selectRequestUserPrice(OfficeWaySearchCondition searchCondition){
		Integer price = officewayDao.selectRequestUserPrice(searchCondition);

		return price;
	}
	
	public int selectRequestTeamPrice(OfficeWaySearchCondition searchCondition){
		Integer price = officewayDao.selectRequestTeamPrice(searchCondition);

		return price;
	}
	
	public int selectRequestMyPrice1(OfficeWaySearchCondition searchCondition){
		Integer price = officewayDao.selectRequestMyPrice1(searchCondition);

		return price;
	}
	
	public int selectRequestMyPrice2(OfficeWaySearchCondition searchCondition){
		Integer price = officewayDao.selectRequestMyPrice2(searchCondition);

		return price;
	}
	
	public int teamRequestPrice2(OfficeWaySearchCondition searchCondition) {
		
		Integer price = officewayDao.selectTeamRequestPrice2(searchCondition);

		return price;
		
	}
	
	public int getTotalPrice(OfficeWay officeway){
		Integer price = officewayDao.getTotalPrice(officeway);

		return price;
	}
	
	public SearchResult<Map<String, Object>> requestList(OfficeWaySearchCondition searchCondition) {
		
		Integer count = officewayDao.selectRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officewayDao.selectRequest(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> requestAllList(OfficeWaySearchCondition searchCondition) {
		
		Integer count = officewayDao.selectRequestAllCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officewayDao.selectRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> exceptOfficewayList(OfficeWaySearchCondition searchCondition) {
		
		Integer count = officewayDao.selectExceptOfficewayCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = officewayDao.selectExceptOfficewayList(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public List<OfficeWay> exceptOfficewayAllList(){
		List<OfficeWay> exceptOfficewayList =  officewayDao.exceptOfficewayAllList();
		
		return exceptOfficewayList;
	}
	
	public List<OfficeWay> officewayUseTeamListAll(){
		List<OfficeWay> officewayUseTeamList =  officewayDao.officewayUseTeamListAll();
		
		return officewayUseTeamList;
	}
	
	public List<OfficeWay> officewayUseTeamList(String userId){
		List<OfficeWay> officewayUseTeamList =  officewayDao.officewayUseTeamList(userId);
		
		return officewayUseTeamList;
	}
	
	public void officewayUseRequest(OfficeWay officeway, User user){
		
		String [] productNames = StringUtils.split(officeway.getProductName(), "|");
		String [] productNos = StringUtils.split(officeway.getProductNo(), "|");
		//String [] categorys1 = StringUtils.split(officeway.getCategory1(), "|");
		String [] categorys2 = StringUtils.split(officeway.getCategory2(), "|");
		String [] remarks = StringUtils.split(officeway.getRemark(), "|");
		String [] amounts1 = StringUtils.split(officeway.getAmount1(), "|");
		String [] units = StringUtils.split(officeway.getUnit(), "|");
		String [] prices1 = StringUtils.split(officeway.getPrice1(), "|");
		String [] prices2 = StringUtils.split(officeway.getPrice2(), "|");
		officeway.setRegisterId(user.getUserId());
		officeway.setRegisterName(user.getUserName());
		officeway.setUpdaterId(user.getUserId());
		officeway.setUpdaterName(user.getUserName());
		officeway.setTeamId(user.getGroupId());
		
		List<OfficeWay> officeWayList = new ArrayList<OfficeWay>();
		List<String> tempOfficewayIds = new ArrayList<String>();
		if(officeway.getProductName() != null) {
			for( int i = 0 ; i < productNames.length ; i++ ){
				OfficeWay tempOfficeWay = new OfficeWay();
				String id = idgenService.getNextId();
				
				officeway.setOfficeWayId(id);
				officeway.setProductName(productNames[i]);
				officeway.setProductNo(productNos[i]);
				//officeway.setCategory1(categorys1[i]);
				officeway.setCategory2(categorys2[i]);
				if(!remarks[i].equals("N/A")){
					officeway.setRemark(remarks[i]);
				}else{
					officeway.setRemark("");
				}
				officeway.setAmount1(amounts1[i]);
				officeway.setUnit(units[i]);
				officeway.setPrice1(prices1[i]);
				officeway.setPrice2(prices2[i]);
				
				tempOfficeWay.setOfficeWayId(id);
				tempOfficeWay.setProductName(productNames[i]);
				tempOfficeWay.setProductNo(productNos[i]);
				//tempOfficeWay.setCategory1(categorys1[i]);
				tempOfficeWay.setCategory2(categorys2[i]);
				if(!remarks[i].equals("N/A")){
					tempOfficeWay.setRemark(remarks[i]);
				}else{
					tempOfficeWay.setRemark("");
				}
				tempOfficeWay.setAmount1(amounts1[i]);
				tempOfficeWay.setUnit(units[i]);
				tempOfficeWay.setPrice1(prices1[i]);
				tempOfficeWay.setPrice2(prices2[i]);
				tempOfficeWay.setRegisterName(user.getUserName());
				
				if(officeway.getStatus1().equals("S")){
					officeWayList.add(tempOfficeWay);
					tempOfficewayIds.add(id);
				}
				
				officewayDao.officewayUseRequest(officeway);
				
			}
		}
		
		
		// 발신자
		/*User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officewayMailTemplate.vm");
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
		if(tempOfficewayIds.size() > 0){
			map.put("officewayIds", tempOfficewayIds);
			List<OfficeWay> itemList = officewayDao.listByItemIdArray(map);
			dataMap.put("officeWayList", itemList);
			recip = new HashMap<String, String>();
			
			if(userList.size() > 0){
				for(User tempUser : userList){
					recipient = userService.read(tempUser.getUserId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
				mail.setToEmailList(recipients);
				
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamListPayment.do");
				mailSendService.sendMail(mail, dataMap, sender);
				
			}
		}*/
		
		
		
		
	}
	
	public boolean existsProductno(String productNo) {

		return officewayDao.existsProductno(productNo);
	}
	
	public boolean periodCheck(){
		return officewayDao.periodCheck();
	}
	
	public boolean teamManagerCheck(Map<String, String> map){
		return officewayDao.teamManagerCheck(map);
	}
	
	public boolean teamLeaderCheck(Map<String, String> map){
		return officewayDao.teamLeaderCheck(map);
	}
	
	public void officewayCheckBoxUseRequest(OfficeWay officeway, User user){
		
		String [] officewayIds = StringUtils.split(officeway.getOfficewayId(), "|");
		officeway.setUpdaterId(user.getUserId());
		officeway.setUpdaterName(user.getUserName());
		officeway.setTeamId(user.getGroupId());
		officeway.setStatus1(officeway.getStatus1());
		
		List<String> tempOfficewayIds = new ArrayList<String>();
		
		if(officeway.getOfficewayId() != null) {
			for( int i = 0 ; i < officewayIds.length ; i++ ){
				tempOfficewayIds.add(officewayIds[i]);
				officeway.setOfficeWayId(officewayIds[i]);
				
				if(officeway.getStatus1().equals("C")){
					officeway.setTeamReviewerId(user.getUserId());
					officeway.setTeamReviewerName(user.getUserName());
				}else if(officeway.getStatus1().equals("A")){
					officeway.setTeamApprId(user.getUserId());
					officeway.setTeamApprName(user.getUserName());
				}else if(officeway.getStatus1().equals("R")){
					officeway.setTeamApprId(user.getUserId());
					officeway.setTeamApprName(user.getUserName());
				}
				officewayDao.officewayCheckBoxUseRequest(officeway);
			}
		}
		/*Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("officewayIds", tempOfficewayIds);
		
		List<OfficeWay> itemList = officewayDao.listByItemIdArray(map);
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officewayMailTemplate.vm");
		
		
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
		//dataMap.put("officeWayList", itemList);
		recip = new HashMap<String, String>();
		List<OfficeWay> tempItemList = new ArrayList<OfficeWay>();
		String tempTeamId = "";
			if("C".equals(itemList.get(0).getStatus1())){//팀장 정보 추출
				for(OfficeWay tempOfficeWay : itemList){
					if(!tempOfficeWay.getTeamId().equals(tempTeamId) && tempTeamId != ""){
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
							dataMap.put("officeWayList", tempItemList);
							dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamListPayment.do");
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
							tempItemList.add(tempOfficeWay);
						}
					}else{
						tempItemList.add(tempOfficeWay);
					}
					tempTeamId = tempOfficeWay.getTeamId();
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
				dataMap.put("officeWayList", tempItemList);
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamListPayment.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);
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
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamListPayment.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);
			}else if("A".equals(itemList.get(0).getStatus1())){//팀 담당자 정보 추출

				mail.setTitle("사무용품 검토 요청");
				if(tempOfficewayIds.size() > 0){
					dataMap.put("officeWayList", itemList);
					recip = new HashMap<String, String>();
					
					if(userList2.size() > 0){
						for(User tempUser : userList2){
							recipient = userService.read(tempUser.getUserId());
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
						mail.setToEmailList(recipients);
						
						dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamsList.do");
						mailSendService.sendMail(mail, dataMap, sender);
						
					}
				}
			}else if("S".equals(itemList.get(0).getStatus1())){//팀 담당자 정보 추출

				mail.setTitle("사무용품 검토 요청");
				if(tempOfficewayIds.size() > 0){
					dataMap.put("officeWayList", itemList);
					recip = new HashMap<String, String>();
					
					if(userList2.size() > 0){
						for(User tempUser : userList3){
							recipient = userService.read(tempUser.getUserId());
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
						mail.setToEmailList(recipients);
						
						dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamListPayment.do");
						mailSendService.sendMail(mail, dataMap, sender);
						
					}
				}
			}*/
			/*else if("S".equals(itemList.get(0).getStatus1())){//담당부서 담당자 정보 추출
				for(OfficeWay tempOfficeWay : itemList){
					if(tempOfficeWay.getTeamId() != tempTeamId && tempTeamId != ""){
						if(userList.size() > 0){
							mail.setTitle("사무용품 검토 요청");
							for(User tempUser : userList2){
								recipient = userService.read(tempUser.getUserId());
								if(tempUser.getGroupId().equals(tempOfficeWay.getTeamId())){
									recip.put("email", recipient.getMail());
									recip.put("name", recipient.getUserName());
									recipients.add(recip);
								}
							}
							dataMap.put("officeWayList", tempItemList);
							dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamListPayment.do");
							mail.setToEmailList(recipients);
							
							mailSendService.sendMail(mail, dataMap, sender);
							tempItemList = null;
							recipients = null;
							recip = null;
							tempItemList.add(tempOfficeWay);
						}
					}else{
						tempItemList.add(tempOfficeWay);
					}
					tempTeamId = tempOfficeWay.getTeamId();
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
				dataMap.put("officeWayList", tempItemList);
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamsList.do");
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
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamsList.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);
			}*/
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OfficeWay item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			
		

	}
	
	public List<OfficeWay> listCategory(OfficeWay categoryBoardId){
		return officewayDao.listCategory(categoryBoardId);
	}
	
	public void insertCategoryNm(List<OfficeWay> receiveCategoryNmList){
		for (int i = 0; i < receiveCategoryNmList.size(); i++) {
			OfficeWay category = receiveCategoryNmList.get(i);
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
					this.officewayDao.createCategoryNm(category);
				}	
			}
			
			if(!"".equals(updateIdList) ){
				for (int j = 0; j < arrayModifyId.length; j++) {		
					category.setUpdateIdList(arrayModifyId[j]);
					category.setUpdateNameList(arrayModifyNm[j]);
					category.setBoardId(boardId);
					this.officewayDao.updateCategoryNm(category);
				}	
			}
			
			if(!"".equals(delIdList) ){
				for (int j = 0; j < arrayDelId.length; j++) {		
					category.setDelIdList(arrayDelId[j]);
					category.setBoardId(boardId);
					this.officewayDao.deleteCategoryNm(category);
				}	
			}
			
			if(!"".equals(arrayAlignName) ){
				for (int j = 0; j < arrayAlignName.length; j++) {	
					category.setCategorySeqId(""+j);
					category.setAlignList(arrayAlignName[j]);
					category.setBoardId(boardId);
					this.officewayDao.updateCategoryAlign(category);
				}	
			}
			
		}
	}
	
	public void officewayManageCheckBoxUseRequest(OfficeWay officeway, User user){
		
		String [] officewayIds = StringUtils.split(officeway.getOfficewayId(), "|");
		officeway.setUpdaterId(user.getUserId());
		officeway.setUpdaterName(user.getUserName());
		officeway.setTeamId(user.getGroupId());
		officeway.setStatus2(officeway.getStatus2());
		List<String> tempOfficewayIds = new ArrayList<String>();
		if(officeway.getOfficewayId() != null) {
			for( int i = 0 ; i < officewayIds.length ; i++ ){
				officeway.setOfficeWayId(officewayIds[i]);
				tempOfficewayIds.add(officewayIds[i]);
				if(officeway.getStatus2().equals("C")){
					officeway.setManageReviewerId(user.getUserId());
					officeway.setManageReviewerName(user.getUserName());
				}else if(officeway.getStatus2().equals("A")){
					officeway.setManageApprId(user.getUserId());
					officeway.setManageApprName(user.getUserName());
				}else if(officeway.getStatus2().equals("R")){
					officeway.setManageApprId(user.getUserId());
					officeway.setManageApprName(user.getUserName());
				}
				officewayDao.officewayManageCheckBoxUseRequest(officeway);
			}
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("officewayIds", tempOfficewayIds);
		
		List<OfficeWay> itemList = officewayDao.listByItemIdArray(map);
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officewayMailTemplate.vm");
		
		
		List<User> userList = userDao.selectRoleUser("OFML");
		
		//List<User> userList2 = userDao.selectRoleUser("OFMR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		dataMap.put("officeWayList", itemList);
		recip = new HashMap<String, String>();
		
		
			if("C".equals(itemList.get(0).getStatus2())){//팀장 정보 추출
				mail.setTitle("사무용품 승인 요청");
				if(userList.size() > 0){
					for(User tempUser : userList){
						recipient = userService.read(tempUser.getUserId());
						recip.put("email", recipient.getMail());
						recip.put("name", recipient.getUserName());
						recipients.add(recip);
					}
				}
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamsList.do");
			}
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OfficeWay item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			mail.setToEmailList(recipients);
			
			
			mailSendService.sendMail(mail, dataMap, sender);

	}
	
	public void officewayCheckBoxGroupRequest(OfficeWay officeway, User user){
		
		String [] teamIds = StringUtils.split(officeway.getTeamId(), "|");
		officeway.setUpdaterId(user.getUserId());
		officeway.setUpdaterName(user.getUserName());
		officeway.setTeamId(user.getGroupId());
		officeway.setStatus2(officeway.getStatus2());
		List<String> tempTeamIds = new ArrayList<String>();
		if(officeway.getTeamId() != null) {
			for( int i = 0 ; i < teamIds.length ; i++ ){
				officeway.setTeamId(teamIds[i]);
				tempTeamIds.add(teamIds[i]);
				if(officeway.getStatus2().equals("C")){
					officeway.setManageReviewerId(user.getUserId());
					officeway.setManageReviewerName(user.getUserName());
				}else if(officeway.getStatus2().equals("A")){
					officeway.setManageApprId(user.getUserId());
					officeway.setManageApprName(user.getUserName());
				}else if(officeway.getStatus2().equals("R")){
					officeway.setManageApprId(user.getUserId());
					officeway.setManageApprName(user.getUserName());
				}
				officewayDao.officewayCheckBoxGroupRequest(officeway);
			}
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("teamIds", tempTeamIds);
		
		List<OfficeWay> itemList = null;
		
		if(officeway.getStatus2().equals("C")){
			 itemList = officewayDao.listByTeamItemIdArray2(map);
		}else if(officeway.getStatus2().equals("A")){
			 itemList = officewayDao.listByTeamItemIdArray1(map);
		}
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officewayMailTemplate.vm");
		
		
		List<User> userList = userDao.selectRoleUser("OFML");
		
		//List<User> userList2 = userDao.selectRoleUser("OFMR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		dataMap.put("officeWayList", itemList);
		recip = new HashMap<String, String>();
		
		
			if("C".equals(itemList.get(0).getStatus2())){
				mail.setTitle("사무용품 승인 요청");
				if(userList.size() > 0){
					for(User tempUser : userList){
						recipient = userService.read(tempUser.getUserId());
						recip.put("email", recipient.getMail());
						recip.put("name", recipient.getUserName());
						recipients.add(recip);
					}
				}
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamsList.do");
			}
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OfficeWay item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			mail.setToEmailList(recipients);
			
			
			mailSendService.sendMail(mail, dataMap, sender);

	}
	
	public void savePeriod(OfficeWay officeway){
		String [] startDates = StringUtils.split(officeway.getStartDate(), "|");
		String [] endDates = StringUtils.split(officeway.getEndDate(), "|");
		String [] months = StringUtils.split(officeway.getMonth(), "|");
		String [] statuss = StringUtils.split(officeway.getStatus(), "|");
		
		for( int i = 0 ; i < startDates.length ; i++ ){
			if(startDates[i].equals("N")){
				officeway.setStartDate(null);
			}else{
				officeway.setStartDate(startDates[i]);
			}
			if(endDates[i].equals("N")){
				officeway.setEndDate(null);
			}else{
				officeway.setEndDate(endDates[i]);
			}
			officeway.setMonth(months[i]);
			officeway.setStatus(statuss[i]);
			officewayDao.savePeriod(officeway);
		}
		
		
	}
	
	public OfficeWay getPeriod(){
		OfficeWay period = officewayDao.getPeriod();
		return period;
	}
	
	public OfficeWay periodInfo(){
		OfficeWay period = officewayDao.periodInfo();
		return period;
	}
	
	public OfficeWay getOfficewayTeamAuthInfo(String teamId){
		OfficeWay teamAuthInfo = officewayDao.getOfficewayTeamAuthInfo(teamId);
		return teamAuthInfo;
	}
	
	public List<OfficeWay> getPeriodList(String year){
		Integer count = officewayDao.selectPeriodListCount(year);
		
		if(count < 1){
			officewayDao.insertDefaultPeriod(year);
		}

		List<OfficeWay> list = officewayDao.getPeriodList(year);
		return list;
	}
	
	public List<OfficeWay> getOfficewayTeamAuthList(){
		List<OfficeWay> list = officewayDao.getOfficewayTeamAuthList();
		return list;
	}
	
	public void officewayUseRequestUpdate(OfficeWay officeway, User user){
		
		String [] officewayIds = StringUtils.split(officeway.getOfficewayId(), "|");
		String [] productNames = StringUtils.split(officeway.getProductName(), "|");
		String [] productNos = StringUtils.split(officeway.getProductNo(), "|");
		//String [] categorys1 = StringUtils.split(officeway.getCategory1(), "|");
		String [] categorys2 = StringUtils.split(officeway.getCategory2(), "|");
		String [] remarks = StringUtils.split(officeway.getRemark(), "|");
		String [] amounts1 = StringUtils.split(officeway.getAmount1(), "|");
		String [] units = StringUtils.split(officeway.getUnit(), "|");
		String [] prices1 = StringUtils.split(officeway.getPrice1(), "|");
		String [] prices2 = StringUtils.split(officeway.getPrice2(), "|");
		officeway.setUpdaterId(user.getUserId());
		officeway.setUpdaterName(user.getUserName());
		if(officeway.getTeamId() != null){
			officeway.setTeamId(officeway.getTeamId());
		}else{
			officeway.setTeamId(user.getGroupId());
		}
		
		List<String> tempOfficewayIds = new ArrayList<String>();
		
		if(officeway.getProductName() != null) {
			for( int i = 0 ; i < productNames.length ; i++ ){
				
				if(!officewayIds[i].equals("newcode")){
					officeway.setOfficeWayId(officewayIds[i]);
					officeway.setProductName(productNames[i]);
					officeway.setProductNo(productNos[i]);
					//officeway.setCategory1(categorys1[i]);
					officeway.setCategory2(categorys2[i]);
					if(!remarks[i].equals("N/A")){
						officeway.setRemark(remarks[i]);
					}else{
						officeway.setRemark("");
					}
					officeway.setAmount1(amounts1[i]);
					officeway.setUnit(units[i]);
					officeway.setPrice1(prices1[i]);
					officeway.setPrice2(prices2[i]);
					officeway.setRegisterId(user.getUserId());
					officeway.setRegisterName(user.getUserName());
					officewayDao.officewayUseRequestUpdate(officeway);
					if(officeway.getStatus1().equals("S")){
						tempOfficewayIds.add(officewayIds[i]);
					}
				}else{
					String id = idgenService.getNextId();
					officeway.setOfficeWayId(id);
					officeway.setProductName(productNames[i]);
					officeway.setProductNo(productNos[i]);
					//officeway.setCategory1(categorys1[i]);
					officeway.setCategory2(categorys2[i]);
					if(!remarks[i].equals("N/A")){
						officeway.setRemark(remarks[i]);
					}else{
						officeway.setRemark("");
					}
					officeway.setAmount1(amounts1[i]);
					officeway.setUnit(units[i]);
					officeway.setPrice1(prices1[i]);
					officeway.setPrice2(prices2[i]);
					officewayDao.officewayUseRequest(officeway);
					if(officeway.getStatus1().equals("S")){
						tempOfficewayIds.add(id);
					}
				}
			}
		}
		
		// 발신자
		/*User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officewayMailTemplate.vm");
		mail.setTitle("사무용품 검토 요청");
		List<User> userList = userDao.selectRoleUser("OFTR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Map dataMap = new HashMap();
		if(tempOfficewayIds.size() > 0){
			map.put("officewayIds", tempOfficewayIds);
			List<OfficeWay> itemList = officewayDao.listByItemIdArray(map);
			dataMap.put("officeWayList", itemList);
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
				
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/officeway/officewayUseRequestTeamListPayment.do");
				mailSendService.sendMail(mail, dataMap, sender);
				
			}
		}*/

	}
	
	public void officewayUseRequestAdminUpdate(OfficeWay officeway, User user){
		
		officeway.setUpdaterId(user.getUserId());
		officeway.setUpdaterName(user.getUserName());
		if(officeway.getRemark().equals("N/A")){
			officeway.setRemark("");
		}
		officewayDao.officewayUseRequestAdminUpdate(officeway);
		

	}
	
	public OfficeWay getOfficeWayUseRequestView(String officewayId){
		
		OfficeWay officeway = officewayDao.getOfficeWayUseRequestView(officewayId);
		
		return officeway;
	}
	
	public void officewayApproveUse(OfficeWay officeway){
		officewayDao.officewayApproveUse(officeway);
	}
	
	public void updateOfficewayExcept(OfficeWay officeway){
		officewayDao.updateOfficewayExcept(officeway);
	}
	
	public void createOfficewayExcept(OfficeWay officeway){
		officewayDao.createOfficewayExcept(officeway);
	}
	
	public void deleteOfficewayExcept(OfficeWay officeway){
		officewayDao.deleteOfficewayExcept(officeway);
	}
	
	public void officewayUseRequestUpdate(OfficeWay officeway){
		officewayDao.officewayUseRequestUpdate(officeway);
	}
	
	public void officewayTeamAuthSave(OfficeWay officeway){
		officewayDao.officewayTeamAuthSave(officeway);
	}
	
	public void officewayUseRequestDelete(OfficeWay officeway){
		officewayDao.officewayUseRequestDelete(officeway);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendOfficeWayUseRequestMail(String flg, String message, OfficeWay officeway, User sender){
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("officewayUseRequestMailTemplate.vm");
		
		List<OfficeWay> managerList = officewayDao.selectOfficeWayManager("USBADM");
		
		String teamLeader = "";
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		
		dataMap.put("startStr", officeway.getStartDate());
		dataMap.put("endStr", officeway.getEndDate());		
		dataMap.put("registerName", officeway.getRegisterName());
		
		
		recip = new HashMap<String, String>();
		
		//요청-결재자(팀장)에게 메일 전송
		if(flg == "req"){
			teamLeader = officewayDao.getTeamLeader(sender.getUserId());
			recipient = userService.read(teamLeader);
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			dataMap.put("title", "USB 사용 승인 요청");
			dataMap.put("contents", "USB 사용 승인을 요청합니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officeway/officewayUseRequestList.do");
			
			mail.setToEmailList(recipients);
			mail.setTitle("USB 사용 승인 요청");

			mailSendService.sendMail(mail, dataMap, sender);
		//팀장 승인or반려 신청자,담당자에게 메일 전송
		}else if(flg == "apr"){
			mail.setTitle("USB 사용 승인 완료");
			dataMap.put("title", "USB 사용 승인 완료");
			dataMap.put("contents", "USB 사용 승인이 완료되었습니다.");
			
			if(managerList.size() > 0){
				for(OfficeWay tofficeway : managerList){
					recipient = userService.read(tofficeway.getManagerId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
					mail.setToEmailList(recipients);
					
					dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officeway/officewayUseRequestAllList.do");
					mailSendService.sendMail(mail, dataMap, sender);
				}
			}
			recipient = userService.read(officeway.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officeway/officewayUseRequestMyList.do");
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		//담당자 확인 신청자에게 메일 전송
		}else if(flg == "cfm"){
			mail.setTitle("USB 사용 권한 변경 완료");
			dataMap.put("title", "USB 사용 권한 변경 완료");
			dataMap.put("contents", "USB 사용 권한이 변경되어 USB 사용이 가능합니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officeway/officewayUseRequestMyList.do");
			recipient = userService.read(officeway.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		}else if(flg == "rej"){
			mail.setTitle("USB 사용 신청 반려");
			dataMap.put("title", "USB 사용 신청 반려");
			dataMap.put("contents", "USB 사용 신청이 반려 되었습니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/officeway/officewayUseRequestMyList.do");
			recipient = userService.read(officeway.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		}else{
			
		}
		
		
	}
}