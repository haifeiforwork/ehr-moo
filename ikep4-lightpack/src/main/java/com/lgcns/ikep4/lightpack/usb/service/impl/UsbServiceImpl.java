/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.usb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.planner.util.Utils;
import com.lgcns.ikep4.lightpack.usb.dao.UsbDao;
import com.lgcns.ikep4.lightpack.usb.model.Usb;
import com.lgcns.ikep4.lightpack.usb.model.UsbSearchCondition;
import com.lgcns.ikep4.lightpack.usb.service.UsbService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 시스템 관리 Service 구현 클래스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: UsbServiceImpl.java 17792 2012-03-30 01:32:04Z arthes $
 */
@Service("UsbUsbService")
public class UsbServiceImpl extends GenericServiceImpl<Usb, String> implements UsbService {

	@Autowired
	UsbDao usbDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IdgenService idgenService;
	
	private FastDateFormat fdfDateTime = FastDateFormat.getInstance("yyyy.MM.dd HH:mm");
	
	@Autowired
	private MailSendService mailSendService;
	
	
	
	public SearchResult<Map<String, Object>> myRequestList(UsbSearchCondition searchCondition) {
		
		Integer count = usbDao.selectMyRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = usbDao.selectMyRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> requestList(UsbSearchCondition searchCondition) {
		
		Integer count = usbDao.selectRequestCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = usbDao.selectRequest(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public SearchResult<Map<String, Object>> requestAllList(UsbSearchCondition searchCondition) {
		
		Integer count = usbDao.selectRequestAllCount(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<Map<String, Object>> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Map<String, Object>>(searchCondition);

		} else {

			List<Map<String, Object>> list = usbDao.selectRequestAll(searchCondition);

			searchResult = new SearchResult<Map<String, Object>>(list, searchCondition);
		}

		return searchResult;
		
	}
	
	public String usbUseRequest(Usb usb, User user){

		String id = idgenService.getNextId();
		usb.setUsbId(id);
		usb.setRegisterId(user.getUserId());
		usb.setRegisterName(user.getUserName());
		usb.setUpdaterId(user.getUserId());
		usb.setUpdaterName(user.getUserName());
		String teamLeader = usbDao.getTeamLeader(user.getUserId());
		usb.setApproveUserId(teamLeader);
		usb.setApproveStatus("S");
		usbDao.usbUseRequest(usb);

		return id;
	}
	
	public Usb getUsbUseRequestView(String usbId){
		
		Usb usb = usbDao.getUsbUseRequestView(usbId);
		
		return usb;
	}
	
	public void usbApproveUse(Usb usb){
		usbDao.usbApproveUse(usb);
	}
	
	public void usbUseRequestUpdate(Usb usb){
		usbDao.usbUseRequestUpdate(usb);
	}
	
	public void usbUseRequestDelete(Usb usb){
		usbDao.usbUseRequestDelete(usb);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendUsbUseRequestMail(String flg, String message, Usb usb, User sender){
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("usbUseRequestMailTemplate.vm");
		
		List<Usb> managerList = usbDao.selectUsbManager("USBADM");
		
		String teamLeader = "";
		
		User recipient = new User();
		
		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		
		Map dataMap = new HashMap();
		dataMap.put("reqType", usb.getReqType());
		dataMap.put("startStr", usb.getStartDate());
		dataMap.put("endStr", usb.getEndDate());		
		dataMap.put("registerName", usb.getRegisterName());
		dataMap.put("requestReason", usb.getRequestReason());
		
		
		recip = new HashMap<String, String>();
		
		//요청-결재자(팀장)에게 메일 전송
		if(flg == "req"){
			teamLeader = usbDao.getTeamLeader(sender.getUserId());
			recipient = userService.read(teamLeader);
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			String tmpTitle = "";
			if(usb.getReqType().equals("01")){
				tmpTitle="USB 허용 신청";
			}else if(usb.getReqType().equals("02")){
				tmpTitle="워터마킹 해제 신청";
			}else if(usb.getReqType().equals("03")){
				tmpTitle="ECM 다운로드 권한 신청";
			}else{
				tmpTitle="ECM 로컬 편집 권한 신청";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			}
			mail.setTitle(tmpTitle);
			dataMap.put("title", tmpTitle);
			//dataMap.put("contents", " 신청합니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/usb/usbUseRequestList.do");
			
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		//팀장 승인or반려 신청자,담당자에게 메일 전송
		}else if(flg == "apr"){
			String tmpTitle = "";
			if(usb.getReqType().equals("01")){
				tmpTitle="USB 허용 신청 승인 완료";
			}else if(usb.getReqType().equals("02")){
				tmpTitle="워터마킹 해제 신청 승인 완료";
			}else if(usb.getReqType().equals("03")){
				tmpTitle="ECM 다운로드 권한 신청 승인 완료";
			}else{
				tmpTitle="ECM 로컬 편집 권한 신청 승인 완료";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			}
			mail.setTitle(tmpTitle);
			dataMap.put("title", tmpTitle);
			//dataMap.put("contents", " 신청이 승인 되었습니다.");
			
			if(managerList.size() > 0){
				for(Usb tusb : managerList){
					recipient = userService.read(tusb.getManagerId());
					recip.put("email", recipient.getMail());
					recip.put("name", recipient.getUserName());
					recipients.add(recip);
					mail.setToEmailList(recipients);
					
					dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/usb/usbUseRequestAllList.do");
					mailSendService.sendMail(mail, dataMap, sender);
					recipients.clear();
				}
			}
			recipient = userService.read(usb.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/usb/usbUseRequestMyList.do");
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		//담당자 확인 신청자에게 메일 전송
		}else if(flg == "cfm"){
			String tmpTitle = "";
			if(usb.getReqType().equals("01")){
				tmpTitle="USB 허용 권한 변경 완료";
			}else if(usb.getReqType().equals("02")){
				tmpTitle="워터마킹 해제 권한 변경 완료";
			}else if(usb.getReqType().equals("03")){
				tmpTitle="ECM 다운로드 권한 변경 완료";
			}else{
				tmpTitle="ECM 로컬 편집 권한 변경 완료";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			}
			mail.setTitle(tmpTitle);
			dataMap.put("title", tmpTitle);
			//dataMap.put("contents", " 권한이 변경되었습니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/usb/usbUseRequestMyList.do");
			recipient = userService.read(usb.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		}else if(flg == "rej"){
			String tmpTitle = "";
			if(usb.getReqType().equals("01")){
				tmpTitle="USB 허용 신청 반려";
			}else if(usb.getReqType().equals("02")){
				tmpTitle="워터마킹 해제 신청 반려";
			}else if(usb.getReqType().equals("03")){
				tmpTitle="ECM 다운로드 권한 신청 반려";
			}else{
				tmpTitle="ECM 로컬 편집 권한 신청 반려";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			}
			mail.setTitle(tmpTitle);
			dataMap.put("title", tmpTitle);
			//dataMap.put("contents", "권한 신청이 반려 되었습니다.");
			dataMap.put("url", Utils.getBaseUrl() + "/" + "lightpack/usb/usbUseRequestMyList.do");
			recipient = userService.read(usb.getRegisterId());
			recip.put("email", recipient.getMail());
			recip.put("name", recipient.getUserName());
			recipients.add(recip);
			mail.setToEmailList(recipients);

			mailSendService.sendMail(mail, dataMap, sender);
		}else{
			
		}
		
		
	}
}