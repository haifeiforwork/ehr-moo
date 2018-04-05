/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.sms.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.sms.constants.SmsConstants;
import com.lgcns.ikep4.support.sms.dao.SmsDao;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.search.SmsReceiverSearchCondition;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * SmsService 구현 클래스
 * 
 * @author 서혜숙
 * @version $Id: SmsServiceImpl.java 15608 2011-06-27 04:32:02Z handul32 $
 */
@Service("smsService")
public class SmsServiceImpl extends GenericServiceImpl<Sms, String> implements SmsService {

	@Autowired
	private SmsDao smsDao;

	@Autowired
	private ActivityStreamService activityStreamService;
	
	@Autowired
	private UserDao userDao;	
  
	 /**
	 * 120927 최재영
	 * LMS 전송, SMS 전송도 같이 처리한다.
	 */
	public String create(Sms sms) {	
		String smsId = "";
		String activityCode = IKepConstant.ACTIVITY_CODE_SMS_SEND;		
		String[] receiverPhonenos = sms.getReceiverPhonenos();
		String[] receiverIds = sms.getReceiverIds();
		
		if(!StringUtil.isEmpty(sms.getSenderPhoneno())) {
			sms.setSenderPhoneno(sms.getSenderPhoneno().replaceAll("-", ""));
		}
		

		if ( sms.getReceiverIds() == null ) {
			receiverIds = sms.getReceiverId().split("[:]");		//SMS팝업창에서 복수명에게 메세지 전송시 :로 구분   
		}
		if ( sms.getReceiverPhonenos() == null ) {
			receiverPhonenos = sms.getReceiverPhoneno().split("[:]");
		}
		
		String tmpNo = "";
		String tmpId = "";
		
		sms.setContents(StringUtil.replaceSQLString(sms.getContents()));
		
		String contents = sms.getContents();
		//LMS 발신 여부 확인
		boolean isLMS = false;	
		//80byte 보다 크면
		if(getCurrentByteSize(contents) > SmsConstants.MAX_SMS_LEN){
			isLMS = true;
		}		

		int smsCount = 0;	
		
		for (int i = 0; i < receiverPhonenos.length ; i++ ) {
			if ( !"".equals(receiverPhonenos[i]) || receiverPhonenos[i] != null ) {
				tmpNo = receiverPhonenos[i];
				tmpNo = tmpNo.replaceAll("-", "");
				tmpId = receiverIds[i];
				sms.setReceiverPhoneno(tmpNo);
				sms.setReceiverId(tmpId);
				
				//장문 단문 여부를 가려서 발송한다.
				if(isLMS)
				{
					sms.setSubject( ResizeContents(sms.getContents(), 16) + "...");
					sms.setServiceCode(SmsConstants.LMS_SERVICE_CODE);
					sms.setMessageType(SmsConstants.LMS_MESSAGE_TYPE);	
				}
				else
				{
					sms.setServiceCode(SmsConstants.SMS_SERVICE_CODE);
					sms.setMessageType(SmsConstants.SMS_MESSAGE_TYPE);
				}
				
				smsId = smsDao.createSms(sms);				
				sms.setSmsId(smsId);				
				smsDao.create(sms);

				// Activity Stream 추가 
				activityCode = getActivityCode(smsCount);
				
				User user = new User();
				user.setUserId(sms.getRegisterId());
				user.setUserName(sms.getRegisterName());
				
				//Activity Stream 추가 시 길이 문제를 일으킴
				sms.setContents(ResizeContents(sms.getContents(), 1000));			
				
				activityStreamService.createForMessage(IKepConstant.ITEM_TYPE_CODE_SMS, activityCode, smsId, sms.getContents() , tmpId, smsCount, user);
			}
		}		
			
		return smsId;
	}

	/**
	 * 바이트수 가져오기
	 */		
	public int getCurrentByteSize(String contents){
		
		int byteSize = 0;
		byte[] tmpContents;
		try {
			tmpContents = contents.getBytes("UTF-8");
			byteSize = tmpContents.length;
		} catch (UnsupportedEncodingException e) {
			return byteSize;
		}	
		return byteSize;		
	}
	
	/**
	 * 바이트 길이로 문자를 제한
	 */	
	public String ResizeContents(String contents, int size){
		
		int stringSize = 0;		
		byte[] contentsBuffer;
		
		try {
			contentsBuffer = contents.getBytes("UTF-8");
			
			if(contentsBuffer.length > size)
			{
				int nCursor = 0;
								
				 while (nCursor < size) {
				      
				      if ((contentsBuffer[nCursor] & 0x80) == 0){ 
				    	  nCursor += 1;
				    	  stringSize += 1;
				      }
				      else if ((contentsBuffer[nCursor] & 0xC0) == 0x80){
				    	  nCursor += 2;
				    	  stringSize += 1;
				      }
				      else if ((contentsBuffer[nCursor] & 0xE0) == 0xC0){
				    	  nCursor += 3;
				    	  stringSize += 1;
				      }
				      else { 
				    	  nCursor += 4;  
				    	  stringSize += 2;
				      }
				 }
	
				 contents =  contents.substring(0,stringSize);
				
			}			
			
		} catch (UnsupportedEncodingException e) {
			
		}	
		return contents;	
	}
	
	/**
	 * ActivityCode 셋팅
	 */		
	public String getActivityCode(int smsCount) {
		String activityCode = IKepConstant.ACTIVITY_CODE_SMS_SEND;	
		if (smsCount > 1) {
			activityCode = IKepConstant.ACTIVITY_CODE_SMS_SEND_Multi;
		} 
		return activityCode;
	}	

	/**
	 * 데이터 존재유무 확인
	 */	
	public boolean exists(String id) {
		return smsDao.exists(id);
	}

	/**
	 * 데이터 보기
	 */	
	public Sms read(String id) {
		Sms sms = smsDao.get(id);
		return sms;
	}

	/**
	 * 데이터 삭제
	 */	
	public void delete(String id) {
		smsDao.remove(id);
	}

	/**
	 * sms 수발신 보관 배치
	 */	
	public void deleteRecentSms() {
		smsDao.deleteRecentSms();
	}
	
	/**
	 * 데이터 업데이트
	 */	
	public void update(Sms object) {
		smsDao.update(object);
	}
	
	/**
	 * 당월발송내역수(팝업 오른쪽 하단)
	 */		
	public int listRecentBottomCount(SmsReceiverSearchCondition smsReceiverSearchCondition) {
		return smsDao.selectRecentBottomCount(smsReceiverSearchCondition);
	}

	/**
	 * 발송내역목록(발신함)
	 */	
	public SearchResult<Sms> listSmsReceiverBySearchCondition(SmsReceiverSearchCondition searchCondition) {
		//이달에 발신한것
		Integer count = this.smsDao.countSmsReceiverBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);  
		
		SearchResult<Sms> searchResult = null;
		
		searchCondition.setParamMobile(searchCondition.getReceiverId());
			
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Sms>(searchCondition);
			
		} else {	
			
			//최근발송내역(최근발송탭에서 6건 보여줌)
			List<Sms> boardItemList = this.smsDao.listSmsReceiverBySearchCondition(searchCondition);
			
			Sms sms = new Sms();
			if ( searchCondition.getPagePerRecord() == SmsConstants.RECENT_SMS_PAGE_PER_RECORD ) {
				for (int i = 0; i < searchCondition.getPagePerRecord() ; i++ ) {

					if ( i >= boardItemList.size() ) {
						boardItemList.add(sms);
					}
				}
			}
			searchResult = new SearchResult<Sms>(boardItemList, searchCondition); 
		}  
		
		return searchResult;
	}	
	
	/**
	 * 당월발송내역(팝업 오른쪽 하단)
	 */	
	public List<Sms> list(SmsReceiverSearchCondition smsReceiverSearchCondition) {
		return smsDao.selectRecentBottom(smsReceiverSearchCondition);
	}	

}
