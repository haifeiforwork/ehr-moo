/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.sms.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.search.SmsReceiverSearchCondition;


/**
 * 
 * @author 서혜숙
 * @version $Id: SmsDao.java 15586 2011-06-24 09:08:38Z giljae $
 */
public interface SmsDao extends GenericDao<Sms, String> {
 
	/**
	 * 당월발송내역(팝업 오른쪽 하단)
	 *
	 * @param sms
	 * @return
	 */
	public List<Sms> selectRecentBottom(SmsReceiverSearchCondition smsReceiverSearchCondition);
	
	/**
	 * 당월발송건수
	 * 
	 * @param sms
	 * @return
	 */
	public int selectRecentBottomCount(SmsReceiverSearchCondition smsReceiverSearchCondition);	
	
	/**
	 * sms 수발신 보관 배치
	 * 
	 * @param id
	 * @return
	 */
	public void deleteRecentSms();	
	
	/**
	 * 발송내역목록(발신함)
	 * 
	 * @param smsReceiverSearchCondition
	 * @return
	 */	
	List<Sms> listSmsReceiverBySearchCondition(SmsReceiverSearchCondition smsReceiverSearchCondition);
	
		
	/**
	 * 발송내역수(발신함)
	 * 
	 * @param smsReceiverSearchCondition
	 * @return
	 */		
	Integer countSmsReceiverBySearchCondition(SmsReceiverSearchCondition smsReceiverSearchCondition);
	
	/**
	 * sms 발송 프로시져
	 * @param sms
	 * @return
	 */
	public String createSms(Sms sms);	
}
