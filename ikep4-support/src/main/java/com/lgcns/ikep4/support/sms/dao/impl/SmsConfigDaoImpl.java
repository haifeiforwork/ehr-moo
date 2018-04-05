package com.lgcns.ikep4.support.sms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.sms.dao.SmsConfigDao;
import com.lgcns.ikep4.support.sms.model.SmsConfig;
import com.lgcns.ikep4.support.sms.search.SmsSearchCondition;

/**
 * 
 * sms 사용자별 건수 설정 Dao 구현 객체
 *
 * @author 
 * @version $Id: SmsConfigDaoImpl.java 3620 2011-07-27 00:59:22Z dev07 $
 */
@Repository("smsConfigDao")
public class SmsConfigDaoImpl extends GenericDaoSqlmap<SmsConfig, String> implements SmsConfigDao {

	/**
	 * sms 사용자별 건수 설정 리스트 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countListSmsConfig(SmsSearchCondition searchCondition) {
		return (Integer) sqlSelectForObject("smsConfig.countListSmsConfig", searchCondition);
	}
	
	/**
	 * sms 사용자별 건수 설정 리스트
	 * @param searchCondition
	 * @return
	 */
	public List<SmsConfig> listSmsConfig(SmsSearchCondition searchCondition) {
		return sqlSelectForList("smsConfig.listSmsConfig", searchCondition);
	}
	
	/**
	 * sms 사용자별 건수 조회
	 * @param userId
	 * @return
	 */
	public SmsConfig getSmsConfig(String userId) {
		return (SmsConfig) sqlSelectForObject("smsConfig.getSmsConfig", userId);
	}
	
	/**
	 * sms 사용자별 건수 등록
	 * @param smsConfig
	 */
	public void createSmsConfig(SmsConfig smsConfig) {
		sqlInsert("smsConfig.createSmsConfig", smsConfig);
	}

	/**
	 * sms 사용자별 건수 수정
	 * @param smsConfig
	 */
	public void updateSmsConfig(SmsConfig smsConfig) {
		sqlUpdate("smsConfig.updateSmsConfig", smsConfig);
	}
	
	/**
	 * sms 사용자별 건수 삭제
	 * @param userId
	 */
	public void removeSmsConfig(String userId) {
		sqlDelete("smsConfig.removeSmsConfig", userId);
	}
	
	@Deprecated
	public SmsConfig get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(SmsConfig object) {
		return null;
	}

	@Deprecated
	public void update(SmsConfig object) {}

	@Deprecated
	public void remove(String id) {}
}