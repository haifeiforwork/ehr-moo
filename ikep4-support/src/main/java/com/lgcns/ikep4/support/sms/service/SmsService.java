/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.sms.service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.search.SmsReceiverSearchCondition;

/**
 *
 * @author 서혜숙
 * @version $Id: SmsService.java 15586 2011-06-24 09:08:38Z giljae $
 */
@Transactional
public interface SmsService extends GenericService<Sms, String> {
	public List<Sms> list(SmsReceiverSearchCondition smsReceiverSearchCondition);
	
	 public SearchResult<Sms> listSmsReceiverBySearchCondition(SmsReceiverSearchCondition smsReceiverSearchCondition);
	
	/**
	 * 당월사용건수
	 * @param sms
	 * @return
	 */
	public int listRecentBottomCount(SmsReceiverSearchCondition smsReceiverSearchCondition);
	
	/**
	 * sms 수발신 보관 배치
	 * @return
	 */	
	public void deleteRecentSms();
	
	/**
	 * 120927 최재영
	 * LMS 전송, SMS 전송도 같이 처리한다.
	 */
	public String create(Sms sms);
}
