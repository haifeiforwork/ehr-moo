package com.lgcns.ikep4.support.sms.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.sms.model.SmsConfig;
import com.lgcns.ikep4.support.sms.search.SmsSearchCondition;

/**
 * 
 * sms 사용자별 건수 설정 Service
 *
 * @author 
 * @version $Id: SmsConfigService.java 3627 2011-07-27 01:01:14Z dev07 $
 */
@Transactional
public interface SmsConfigService extends GenericService<SmsConfig, String> {

	/**
	 * sms 사용자별 건수 설정 리스트 카운트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<SmsConfig> listSmsConfig(SmsSearchCondition searchCondition);
	
	/**
	 * sms 사용자별 건수 조회
	 * @param userId
	 * @return
	 */
	public SmsConfig readSmsConfig(String userId);
	
	/**
	 * sms 사용자별 건수 등록
	 * @param smsConfig
	 */
	public void createSmsConfig(SmsConfig smsConfig);
	
	/**
	 * sms 사용자별 건수 수정
	 * @param smsConfig
	 */
	public void updateSmsConfig(SmsConfig smsConfig);
	
	/**
	 * sms 사용자별 건수 삭제
	 * @param userIds
	 */
	public void removeSmsConfig(String[] userIds);
}