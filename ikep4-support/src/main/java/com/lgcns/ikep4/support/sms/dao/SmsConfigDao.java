package com.lgcns.ikep4.support.sms.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.sms.model.SmsConfig;
import com.lgcns.ikep4.support.sms.search.SmsSearchCondition;

/**
 * 
 * sms 사용자별 건수 설정 Dao
 *
 * @author 
 * @version $Id: SmsConfigDao.java 3621 2011-07-27 00:59:41Z dev07 $
 */
public interface SmsConfigDao extends GenericDao<SmsConfig, String> {
	
	/**
	 * sms 사용자별 건수 설정 리스트 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countListSmsConfig(SmsSearchCondition searchCondition);
	
	/**
	 * sms 사용자별 건수 설정 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<SmsConfig> listSmsConfig(SmsSearchCondition searchCondition);
	
	/**
	 * sms 사용자별 건수 조회
	 * @param userId
	 * @return
	 */
	public SmsConfig getSmsConfig(String userId);
	
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
	 * @param userId
	 */
	public void removeSmsConfig(String userId);
}