/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.overtimework.service.impl;

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
import com.lgcns.ikep4.lightpack.overtimework.dao.OvertimeWorkDao;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWork;
import com.lgcns.ikep4.lightpack.overtimework.model.OvertimeWorkSearchCondition;
import com.lgcns.ikep4.lightpack.overtimework.service.OvertimeWorkService;
import com.lgcns.ikep4.lightpack.planner.util.Utils;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 시스템 관리 Service 구현 클래스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: OvertimeWorkServiceImpl.java 17792 2012-03-30 01:32:04Z arthes $
 */
@Service("OvertimeWorkOvertimeWorkService")
public class OvertimeWorkServiceImpl extends GenericServiceImpl<OvertimeWork, String> implements OvertimeWorkService {

	@Autowired
	OvertimeWorkDao overtimeworkDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IdgenService idgenService;
	
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyy.MM.dd HH:mm");
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	UserDao userDao;
	
	
	public SearchResult<Map<String, Object>> myRequestList(OvertimeWorkSearchCondition searchCondition) {
		
		Integer count = overtimeworkDao.selectMyRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.selectMyRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> teamRequestList(OvertimeWorkSearchCondition searchCondition) {
		
		Integer count = overtimeworkDao.selectTeamRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.selectTeamRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> overtimeworkInOutAllList(OvertimeWorkSearchCondition searchCondition){
		Integer count = overtimeworkDao.overtimeworkInOutAllListCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.overtimeworkInOutAllList(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
	}
	
	public SearchResult<Map<String, Object>> overtimeworkInOutMyList(OvertimeWorkSearchCondition searchCondition){
		Integer count = overtimeworkDao.overtimeworkInOutMyListCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.overtimeworkInOutMyList(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
	}
	
	public SearchResult<Map<String, Object>> teamsRequestList(OvertimeWorkSearchCondition searchCondition) {
		
		Integer count = overtimeworkDao.selectTeamsRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.selectTeamsRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public List<OvertimeWork> teamsRequestDetailList(OvertimeWorkSearchCondition searchCondition){
		List<OvertimeWork> list = overtimeworkDao.teamsRequestDetailList(searchCondition);
		return list;
	}
	
	public List<OvertimeWork> teamRequestDetailList(OvertimeWorkSearchCondition searchCondition){
		List<OvertimeWork> list = overtimeworkDao.teamRequestDetailList(searchCondition);
		return list;
	}
	
	public void overtimeworkRequestDelete(OvertimeWork overtimework){
		String [] overtimeworkIds = StringUtils.split(overtimework.getOvertimeworkId(), "|");
		
		if(overtimework.getOvertimeworkId() != null) {
			for( int i = 0 ; i < overtimeworkIds.length ; i++ ){
				overtimeworkDao.overtimeworkRequestDelete(overtimeworkIds[i]);
			}
		}
	}
	
	public List<OvertimeWork> getStatisticsList1(OvertimeWork overtimework){
		List<OvertimeWork> list = overtimeworkDao.getStatisticsList1(overtimework);
		return list;
	}
	
	public List<OvertimeWork> getStatisticsList2(OvertimeWork overtimework){
		List<OvertimeWork> list = overtimeworkDao.getStatisticsList2(overtimework);
		return list;	
	}
	
	public List<OvertimeWork> getStatisticsList3(OvertimeWork overtimework){
		List<OvertimeWork> list = overtimeworkDao.getStatisticsList3(overtimework);
		return list;
	}
		
	public int teamsRequestPrice(OvertimeWorkSearchCondition searchCondition) {
		
		Integer price = overtimeworkDao.selectTeamsRequestPrice(searchCondition);

		return price;
		
	}
	
	public int teamRequestPrice1(OvertimeWorkSearchCondition searchCondition) {
		
		Integer price = overtimeworkDao.selectTeamRequestPrice1(searchCondition);

		return price;
		
	}
	
	public int selectRequestUserPrice(OvertimeWorkSearchCondition searchCondition){
		Integer price = overtimeworkDao.selectRequestUserPrice(searchCondition);

		return price;
	}
	
	public int selectRequestTeamPrice(OvertimeWorkSearchCondition searchCondition){
		Integer price = overtimeworkDao.selectRequestTeamPrice(searchCondition);

		return price;
	}
	
	public int selectRequestMyPrice1(OvertimeWorkSearchCondition searchCondition){
		Integer price = overtimeworkDao.selectRequestMyPrice1(searchCondition);

		return price;
	}
	
	public int selectRequestMyPrice2(OvertimeWorkSearchCondition searchCondition){
		Integer price = overtimeworkDao.selectRequestMyPrice2(searchCondition);

		return price;
	}
	
	public int teamRequestPrice2(OvertimeWorkSearchCondition searchCondition) {
		
		Integer price = overtimeworkDao.selectTeamRequestPrice2(searchCondition);

		return price;
		
	}
	
	public int getTotalPrice(OvertimeWork overtimework){
		Integer price = overtimeworkDao.getTotalPrice(overtimework);

		return price;
	}
	
	public SearchResult<Map<String, Object>> requestList(OvertimeWorkSearchCondition searchCondition) {
		
		Integer count = overtimeworkDao.selectRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.selectRequest(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> requestAllList(OvertimeWorkSearchCondition searchCondition) {
		
		Integer count = overtimeworkDao.selectRequestAllCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.selectRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> exceptOvertimeworkList(OvertimeWorkSearchCondition searchCondition) {
		
		Integer count = overtimeworkDao.selectExceptOvertimeworkCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.selectExceptOvertimeworkList(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public List<OvertimeWork> exceptOvertimeworkAllList(){
		List<OvertimeWork> exceptOvertimeworkList =  overtimeworkDao.exceptOvertimeworkAllList();
		
		return exceptOvertimeworkList;
	}
	
	public List<OvertimeWork> overtimeworkInOutExcelList(OvertimeWorkSearchCondition searchCondition){
		List<OvertimeWork> overtimeworkInOutExcelList =  overtimeworkDao.overtimeworkInOutExcelList(searchCondition);
		
		return overtimeworkInOutExcelList;
	}
	
	public List<OvertimeWork> overtimeworkUseTeamListAll(){
		List<OvertimeWork> overtimeworkUseTeamList =  overtimeworkDao.overtimeworkUseTeamListAll();
		
		return overtimeworkUseTeamList;
	}
	
	public List<OvertimeWork> overtimeworkUseTeamList(String userId){
		List<OvertimeWork> overtimeworkUseTeamList =  overtimeworkDao.overtimeworkUseTeamList(userId);
		
		return overtimeworkUseTeamList;
	}
	
	public String overtimeworkInOutRegist(OvertimeWork overtimework, User user){
		overtimework.setCheckerId(user.getUserId()); 
		if (!StringUtil.isEmpty(overtimework.getOvertimeworkId())) {
			overtimeworkDao.overtimeworkInOutRegistUpdate(overtimework);
		}else{
			overtimework.setOvertimeworkId(idgenService.getNextId());
			overtimeworkDao.overtimeworkInOutRegist(overtimework);
		}
		
		
		return overtimework.getOvertimeworkId();
	}
	
	public String overtimeworkInOutUpdate(OvertimeWork overtimework, User user){
		overtimework.setCheckerId(user.getUserId()); 
		overtimeworkDao.overtimeworkInOutUpdate(overtimework);
		
		return overtimework.getOvertimeworkId();
	}
	
	public boolean existsProductno(String productNo) {

		return overtimeworkDao.existsProductno(productNo);
	}
	
	public String cardUserId(String cardId){
		return overtimeworkDao.cardUserId(cardId);
	}
	
	public boolean periodCheck(){
		return overtimeworkDao.periodCheck();
	}
	
	public boolean teamManagerCheck(Map<String, String> map){
		return overtimeworkDao.teamManagerCheck(map);
	}
	
	public boolean teamLeaderCheck(Map<String, String> map){
		return overtimeworkDao.teamLeaderCheck(map);
	}
	
	public void overtimeworkCheckBoxUseRequest(OvertimeWork overtimework, User user){
		
		String [] overtimeworkIds = StringUtils.split(overtimework.getOvertimeworkId(), "|");
		overtimework.setUpdaterId(user.getUserId());
		overtimework.setUpdaterName(user.getUserName());
		overtimework.setTeamId(user.getGroupId());
		overtimework.setStatus1(overtimework.getStatus1());
		
		List<String> tempOvertimeworkIds = new ArrayList<String>();
		
		if(overtimework.getOvertimeworkId() != null) {
			for( int i = 0 ; i < overtimeworkIds.length ; i++ ){
				tempOvertimeworkIds.add(overtimeworkIds[i]);
				overtimework.setOvertimeWorkId(overtimeworkIds[i]);
				
				if(overtimework.getStatus1().equals("C")){
					overtimework.setTeamReviewerId(user.getUserId());
					overtimework.setTeamReviewerName(user.getUserName());
				}else if(overtimework.getStatus1().equals("A")){
					overtimework.setTeamApprId(user.getUserId());
					overtimework.setTeamApprName(user.getUserName());
				}else if(overtimework.getStatus1().equals("R")){
					overtimework.setTeamApprId(user.getUserId());
					overtimework.setTeamApprName(user.getUserName());
				}
				overtimeworkDao.overtimeworkCheckBoxUseRequest(overtimework);
			}
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("overtimeworkIds", tempOvertimeworkIds);
		
		List<OvertimeWork> itemList = overtimeworkDao.listByItemIdArray(map);
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("overtimeworkMailTemplate.vm");
		
		
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
		//dataMap.put("overtimeWorkList", itemList);
		recip = new HashMap<String, String>();
		List<OvertimeWork> tempItemList = new ArrayList<OvertimeWork>();
		String tempTeamId = "";
			if("C".equals(itemList.get(0).getStatus1())){//팀장 정보 추출
				for(OvertimeWork tempOvertimeWork : itemList){
					if(!tempOvertimeWork.getTeamId().equals(tempTeamId) && tempTeamId != ""){
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
							dataMap.put("overtimeWorkList", tempItemList);
							dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamListPayment.do");
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
							tempItemList.add(tempOvertimeWork);
						}
					}else{
						tempItemList.add(tempOvertimeWork);
					}
					tempTeamId = tempOvertimeWork.getTeamId();
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
				dataMap.put("overtimeWorkList", tempItemList);
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamListPayment.do");
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
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamListPayment.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);*/
			}else if("A".equals(itemList.get(0).getStatus1())){//팀 담당자 정보 추출

				mail.setTitle("사무용품 검토 요청");
				if(tempOvertimeworkIds.size() > 0){
					dataMap.put("overtimeWorkList", itemList);
					recip = new HashMap<String, String>();
					
					if(userList2.size() > 0){
						for(User tempUser : userList2){
							recipient = userService.read(tempUser.getUserId());
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
						mail.setToEmailList(recipients);
						
						dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamsList.do");
						mailSendService.sendMail(mail, dataMap, sender);
						
					}
				}
			}else if("S".equals(itemList.get(0).getStatus1())){//팀 담당자 정보 추출

				mail.setTitle("사무용품 검토 요청");
				if(tempOvertimeworkIds.size() > 0){
					dataMap.put("overtimeWorkList", itemList);
					recip = new HashMap<String, String>();
					
					if(userList2.size() > 0){
						for(User tempUser : userList3){
							recipient = userService.read(tempUser.getUserId());
							recip.put("email", recipient.getMail());
							recip.put("name", recipient.getUserName());
							recipients.add(recip);
						}
						mail.setToEmailList(recipients);
						
						dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamListPayment.do");
						mailSendService.sendMail(mail, dataMap, sender);
						
					}
				}
			}
			/*else if("S".equals(itemList.get(0).getStatus1())){//담당부서 담당자 정보 추출
				for(OvertimeWork tempOvertimeWork : itemList){
					if(tempOvertimeWork.getTeamId() != tempTeamId && tempTeamId != ""){
						if(userList.size() > 0){
							mail.setTitle("사무용품 검토 요청");
							for(User tempUser : userList2){
								recipient = userService.read(tempUser.getUserId());
								if(tempUser.getGroupId().equals(tempOvertimeWork.getTeamId())){
									recip.put("email", recipient.getMail());
									recip.put("name", recipient.getUserName());
									recipients.add(recip);
								}
							}
							dataMap.put("overtimeWorkList", tempItemList);
							dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamListPayment.do");
							mail.setToEmailList(recipients);
							
							mailSendService.sendMail(mail, dataMap, sender);
							tempItemList = null;
							recipients = null;
							recip = null;
							tempItemList.add(tempOvertimeWork);
						}
					}else{
						tempItemList.add(tempOvertimeWork);
					}
					tempTeamId = tempOvertimeWork.getTeamId();
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
				dataMap.put("overtimeWorkList", tempItemList);
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamsList.do");
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
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamsList.do");
				mail.setToEmailList(recipients);
				
				mailSendService.sendMail(mail, dataMap, sender);
			}*/
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OvertimeWork item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			
		

	}
	
	public List<OvertimeWork> listCategory(OvertimeWork categoryBoardId){
		return overtimeworkDao.listCategory(categoryBoardId);
	}
	
	public void insertCategoryNm(List<OvertimeWork> receiveCategoryNmList){
		for (int i = 0; i < receiveCategoryNmList.size(); i++) {
			OvertimeWork category = receiveCategoryNmList.get(i);
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
					this.overtimeworkDao.createCategoryNm(category);
				}	
			}
			
			if(!"".equals(updateIdList) ){
				for (int j = 0; j < arrayModifyId.length; j++) {		
					category.setUpdateIdList(arrayModifyId[j]);
					category.setUpdateNameList(arrayModifyNm[j]);
					category.setBoardId(boardId);
					this.overtimeworkDao.updateCategoryNm(category);
				}	
			}
			
			if(!"".equals(delIdList) ){
				for (int j = 0; j < arrayDelId.length; j++) {		
					category.setDelIdList(arrayDelId[j]);
					category.setBoardId(boardId);
					this.overtimeworkDao.deleteCategoryNm(category);
				}	
			}
			
			if(!"".equals(arrayAlignName) ){
				for (int j = 0; j < arrayAlignName.length; j++) {	
					category.setCategorySeqId(""+j);
					category.setAlignList(arrayAlignName[j]);
					category.setBoardId(boardId);
					this.overtimeworkDao.updateCategoryAlign(category);
				}	
			}
			
		}
	}
	
	public void overtimeworkManageCheckBoxUseRequest(OvertimeWork overtimework, User user){
		
		String [] overtimeworkIds = StringUtils.split(overtimework.getOvertimeworkId(), "|");
		overtimework.setUpdaterId(user.getUserId());
		overtimework.setUpdaterName(user.getUserName());
		overtimework.setTeamId(user.getGroupId());
		overtimework.setStatus2(overtimework.getStatus2());
		List<String> tempOvertimeworkIds = new ArrayList<String>();
		if(overtimework.getOvertimeworkId() != null) {
			for( int i = 0 ; i < overtimeworkIds.length ; i++ ){
				overtimework.setOvertimeWorkId(overtimeworkIds[i]);
				tempOvertimeworkIds.add(overtimeworkIds[i]);
				if(overtimework.getStatus2().equals("C")){
					overtimework.setManageReviewerId(user.getUserId());
					overtimework.setManageReviewerName(user.getUserName());
				}else if(overtimework.getStatus2().equals("A")){
					overtimework.setManageApprId(user.getUserId());
					overtimework.setManageApprName(user.getUserName());
				}else if(overtimework.getStatus2().equals("R")){
					overtimework.setManageApprId(user.getUserId());
					overtimework.setManageApprName(user.getUserName());
				}
				overtimeworkDao.overtimeworkManageCheckBoxUseRequest(overtimework);
			}
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("overtimeworkIds", tempOvertimeworkIds);
		
		List<OvertimeWork> itemList = overtimeworkDao.listByItemIdArray(map);
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("overtimeworkMailTemplate.vm");
		
		
		List<User> userList = userDao.selectRoleUser("OFML");
		
		//List<User> userList2 = userDao.selectRoleUser("OFMR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		dataMap.put("overtimeWorkList", itemList);
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
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamsList.do");
			}
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OvertimeWork item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			mail.setToEmailList(recipients);
			
			
			mailSendService.sendMail(mail, dataMap, sender);

	}
	
	public void overtimeworkCheckBoxGroupRequest(OvertimeWork overtimework, User user){
		
		String [] teamIds = StringUtils.split(overtimework.getTeamId(), "|");
		overtimework.setUpdaterId(user.getUserId());
		overtimework.setUpdaterName(user.getUserName());
		overtimework.setTeamId(user.getGroupId());
		overtimework.setStatus2(overtimework.getStatus2());
		List<String> tempTeamIds = new ArrayList<String>();
		if(overtimework.getTeamId() != null) {
			for( int i = 0 ; i < teamIds.length ; i++ ){
				overtimework.setTeamId(teamIds[i]);
				tempTeamIds.add(teamIds[i]);
				if(overtimework.getStatus2().equals("C")){
					overtimework.setManageReviewerId(user.getUserId());
					overtimework.setManageReviewerName(user.getUserName());
				}else if(overtimework.getStatus2().equals("A")){
					overtimework.setManageApprId(user.getUserId());
					overtimework.setManageApprName(user.getUserName());
				}else if(overtimework.getStatus2().equals("R")){
					overtimework.setManageApprId(user.getUserId());
					overtimework.setManageApprName(user.getUserName());
				}
				overtimeworkDao.overtimeworkCheckBoxGroupRequest(overtimework);
			}
		}
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("teamIds", tempTeamIds);
		
		List<OvertimeWork> itemList = null;
		
		if(overtimework.getStatus2().equals("C")){
			 itemList = overtimeworkDao.listByTeamItemIdArray2(map);
		}else if(overtimework.getStatus2().equals("A")){
			 itemList = overtimeworkDao.listByTeamItemIdArray1(map);
		}
		
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("overtimeworkMailTemplate.vm");
		
		
		List<User> userList = userDao.selectRoleUser("OFML");
		
		//List<User> userList2 = userDao.selectRoleUser("OFMR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		dataMap.put("overtimeWorkList", itemList);
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
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamsList.do");
			}
			/*else{//신청자 정보 추출
				mail.setTitle("사무용품 신청이 반려되었습니다");
				for(OvertimeWork item : itemList){
					recipient = userService.read(item.getRegisterId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
				}
			}*/
			
			mail.setToEmailList(recipients);
			
			
			mailSendService.sendMail(mail, dataMap, sender);

	}
	
	public void savePeriod(OvertimeWork overtimework){
		String [] startDates = StringUtils.split(overtimework.getStartDate(), "|");
		String [] endDates = StringUtils.split(overtimework.getEndDate(), "|");
		String [] months = StringUtils.split(overtimework.getMonth(), "|");
		String [] statuss = StringUtils.split(overtimework.getStatus(), "|");
		
		for( int i = 0 ; i < startDates.length ; i++ ){
			if(startDates[i].equals("N")){
				overtimework.setStartDate(null);
			}else{
				overtimework.setStartDate(startDates[i]);
			}
			if(endDates[i].equals("N")){
				overtimework.setEndDate(null);
			}else{
				overtimework.setEndDate(endDates[i]);
			}
			overtimework.setMonth(months[i]);
			overtimework.setStatus(statuss[i]);
			overtimeworkDao.savePeriod(overtimework);
		}
		
		
	}
	
	public OvertimeWork getPeriod(){
		OvertimeWork period = overtimeworkDao.getPeriod();
		return period;
	}
	
	public OvertimeWork getOvertimeworkTeamAuthInfo(String teamId){
		OvertimeWork teamAuthInfo = overtimeworkDao.getOvertimeworkTeamAuthInfo(teamId);
		return teamAuthInfo;
	}
	
	public OvertimeWork overtimeworkInOutDetail(String overtimeworkId){
		OvertimeWork overtimeWork = overtimeworkDao.overtimeworkInOutDetail(overtimeworkId);
		return overtimeWork;
	}
	
	public OvertimeWork getOvertimeworkUserCardInfo(String userId){
		OvertimeWork userCardInfo = overtimeworkDao.getOvertimeworkUserCardInfo(userId);
		return userCardInfo;
	}
	
	public List<OvertimeWork> getPeriodList(String year){
		Integer count = overtimeworkDao.selectPeriodListCount(year);
		
		if(count < 1){
			overtimeworkDao.insertDefaultPeriod(year);
		}

		List<OvertimeWork> list = overtimeworkDao.getPeriodList(year);
		return list;
	}
	
	public List<OvertimeWork> getOvertimeworkTeamAuthList(){
		List<OvertimeWork> list = overtimeworkDao.getOvertimeworkTeamAuthList();
		return list;
	}
	
	public SearchResult<Map<String, Object>> getOvertimeworkUserCardList(OvertimeWorkSearchCondition searchCondition){
		
		Integer count = overtimeworkDao.selectOvertimeworkUserCardListCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = overtimeworkDao.getOvertimeworkUserCardList(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
	}
	
	public List<OvertimeWork> getOvertimeworkUserCardListExcel(OvertimeWorkSearchCondition searchCondition){
		
		List<OvertimeWork> list = overtimeworkDao.getOvertimeworkUserCardListExcel(searchCondition);
		
		return list;
	}
	
	public void overtimeworkUseRequestUpdate(OvertimeWork overtimework, User user){
		
		String [] overtimeworkIds = StringUtils.split(overtimework.getOvertimeworkId(), "|");
		String [] productNames = StringUtils.split(overtimework.getProductName(), "|");
		String [] productNos = StringUtils.split(overtimework.getProductNo(), "|");
		//String [] categorys1 = StringUtils.split(overtimework.getCategory1(), "|");
		String [] categorys2 = StringUtils.split(overtimework.getCategory2(), "|");
		String [] remarks = StringUtils.split(overtimework.getRemark(), "|");
		String [] amounts1 = StringUtils.split(overtimework.getAmount1(), "|");
		String [] units = StringUtils.split(overtimework.getUnit(), "|");
		String [] prices1 = StringUtils.split(overtimework.getPrice1(), "|");
		String [] prices2 = StringUtils.split(overtimework.getPrice2(), "|");
		overtimework.setUpdaterId(user.getUserId());
		overtimework.setUpdaterName(user.getUserName());
		if(overtimework.getTeamId() != null){
			overtimework.setTeamId(overtimework.getTeamId());
		}else{
			overtimework.setTeamId(user.getGroupId());
		}
		
		List<String> tempOvertimeworkIds = new ArrayList<String>();
		
		if(overtimework.getProductName() != null) {
			for( int i = 0 ; i < productNames.length ; i++ ){
				
				if(!overtimeworkIds[i].equals("newcode")){
					overtimework.setOvertimeWorkId(overtimeworkIds[i]);
					overtimework.setProductName(productNames[i]);
					overtimework.setProductNo(productNos[i]);
					//overtimework.setCategory1(categorys1[i]);
					overtimework.setCategory2(categorys2[i]);
					if(!remarks[i].equals("N/A")){
						overtimework.setRemark(remarks[i]);
					}else{
						overtimework.setRemark("");
					}
					overtimework.setAmount1(amounts1[i]);
					overtimework.setUnit(units[i]);
					overtimework.setPrice1(prices1[i]);
					overtimework.setPrice2(prices2[i]);
					overtimework.setRegisterId(user.getUserId());
					overtimework.setRegisterName(user.getUserName());
					overtimeworkDao.overtimeworkUseRequestUpdate(overtimework);
					if(overtimework.getStatus1().equals("S")){
						tempOvertimeworkIds.add(overtimeworkIds[i]);
					}
				}else{
					String id = idgenService.getNextId();
					overtimework.setOvertimeWorkId(id);
					overtimework.setProductName(productNames[i]);
					overtimework.setProductNo(productNos[i]);
					//overtimework.setCategory1(categorys1[i]);
					overtimework.setCategory2(categorys2[i]);
					if(!remarks[i].equals("N/A")){
						overtimework.setRemark(remarks[i]);
					}else{
						overtimework.setRemark("");
					}
					overtimework.setAmount1(amounts1[i]);
					overtimework.setUnit(units[i]);
					overtimework.setPrice1(prices1[i]);
					overtimework.setPrice2(prices2[i]);
					overtimeworkDao.overtimeworkInOutRegist(overtimework);
					if(overtimework.getStatus1().equals("S")){
						tempOvertimeworkIds.add(id);
					}
				}
			}
		}
		
		// 발신자
		User sender = userService.read(user.getUserId());
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("overtimeworkMailTemplate.vm");
		mail.setTitle("사무용품 검토 요청");
		List<User> userList = userDao.selectRoleUser("OFTR");
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Map dataMap = new HashMap();
		if(tempOvertimeworkIds.size() > 0){
			map.put("overtimeworkIds", tempOvertimeworkIds);
			List<OvertimeWork> itemList = overtimeworkDao.listByItemIdArray(map);
			dataMap.put("overtimeWorkList", itemList);
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
				
				dataMap.put("url", Utils.getBaseUrl() + "/lightpack/overtimework/overtimeworkUseRequestTeamListPayment.do");
				mailSendService.sendMail(mail, dataMap, sender);
				
			}
		}

	}
	
	public OvertimeWork getOvertimeWorkUseRequestView(String overtimeworkId){
		
		OvertimeWork overtimework = overtimeworkDao.getOvertimeWorkUseRequestView(overtimeworkId);
		
		return overtimework;
	}
	
	public void overtimeworkApproveUse(OvertimeWork overtimework){
		overtimeworkDao.overtimeworkApproveUse(overtimework);
	}
	
	public void updateOvertimeworkExcept(OvertimeWork overtimework){
		overtimeworkDao.updateOvertimeworkExcept(overtimework);
	}
	
	public void createOvertimeworkExcept(OvertimeWork overtimework){
		overtimeworkDao.createOvertimeworkExcept(overtimework);
	}
	
	public void deleteOvertimeworkExcept(OvertimeWork overtimework){
		overtimeworkDao.deleteOvertimeworkExcept(overtimework);
	}
	
	public void overtimeworkUseRequestUpdate(OvertimeWork overtimework){
		overtimeworkDao.overtimeworkUseRequestUpdate(overtimework);
	}
	
	public void overtimeworkTeamAuthSave(OvertimeWork overtimework){
		overtimeworkDao.overtimeworkTeamAuthSave(overtimework);
	}
	
	public void overtimeworkUserCardSave(OvertimeWork overtimework){
		overtimeworkDao.overtimeworkUserCardSave(overtimework);
	}
	
	public void overtimeworkUseRequestDelete(OvertimeWork overtimework){
		overtimeworkDao.overtimeworkUseRequestDelete(overtimework);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendOvertimeWorkUseRequestMail(String flg, String message, OvertimeWork overtimework, User sender){
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("overtimeworkUseRequestMailTemplate.vm");
		
		List<OvertimeWork> managerList = overtimeworkDao.selectOvertimeWorkManager("USBADM");
		
		String teamLeader = "";
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		
		dataMap.put("startStr", overtimework.getStartDate());
		dataMap.put("endStr", overtimework.getEndDate());		
		dataMap.put("registerName", overtimework.getRegisterName());
		
		
		recip = new HashMap<String, String>();
		
		//요청-결재자(팀장)에게 메일 전송
		if(flg == "req"){
			teamLeader = overtimeworkDao.getTeamLeader(sender.getUserId());
			recipient = userService.read(teamLeader);
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			dataMap.put("title", "USB 사용 승인 요청");
			dataMap.put("contents", "USB 사용 승인을 요청합니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/overtimework/overtimeworkUseRequestList.do");
			
			mail.setToEmailList(recipients);
			mail.setTitle("USB 사용 승인 요청");

			mailSendService.sendMail(mail, dataMap, sender);
		//팀장 승인or반려 신청자,담당자에게 메일 전송
		}else if(flg == "apr"){
			mail.setTitle("USB 사용 승인 완료");
			dataMap.put("title", "USB 사용 승인 완료");
			dataMap.put("contents", "USB 사용 승인이 완료되었습니다.");
			
			if(managerList.size() > 0){
				for(OvertimeWork tovertimework : managerList){
					recipient = userService.read(tovertimework.getManagerId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
					mail.setToEmailList(recipients);
					
					dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/overtimework/overtimeworkUseRequestAllList.do");
					mailSendService.sendMail(mail, dataMap, sender);
				}
			}
			recipient = userService.read(overtimework.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/overtimework/overtimeworkUseRequestMyList.do");
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		//담당자 확인 신청자에게 메일 전송
		}else if(flg == "cfm"){
			mail.setTitle("USB 사용 권한 변경 완료");
			dataMap.put("title", "USB 사용 권한 변경 완료");
			dataMap.put("contents", "USB 사용 권한이 변경되어 USB 사용이 가능합니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/overtimework/overtimeworkUseRequestMyList.do");
			recipient = userService.read(overtimework.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		}else if(flg == "rej"){
			mail.setTitle("USB 사용 신청 반려");
			dataMap.put("title", "USB 사용 신청 반려");
			dataMap.put("contents", "USB 사용 신청이 반려 되었습니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/overtimework/overtimeworkUseRequestMyList.do");
			recipient = userService.read(overtimework.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		}else{
			
		}
		
		
	}
}