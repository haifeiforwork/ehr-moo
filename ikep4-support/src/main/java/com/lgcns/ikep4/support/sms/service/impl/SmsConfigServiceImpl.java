package com.lgcns.ikep4.support.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.sms.dao.SmsConfigDao;
import com.lgcns.ikep4.support.sms.model.SmsConfig;
import com.lgcns.ikep4.support.sms.search.SmsSearchCondition;
import com.lgcns.ikep4.support.sms.service.SmsConfigService;

/**
 * 
 * sms 사용자별 건수 설정 Service 구현 객체
 *
 * @author 
 * @version $Id: SmsConfigServiceImpl.java 3626 2011-07-27 01:01:00Z dev07 $
 */
@Service("smsConfigService")
public class SmsConfigServiceImpl extends GenericServiceImpl<SmsConfig, String> implements SmsConfigService {

	@Autowired
	private SmsConfigDao smsConfigDao;
	
	/**
	 * sms 사용자별 건수 설정 리스트 카운트
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<SmsConfig> listSmsConfig(SmsSearchCondition searchCondition) {
		Integer count = smsConfigDao.countListSmsConfig(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<SmsConfig> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<SmsConfig>(searchCondition);
		} else {
			List<SmsConfig> list = smsConfigDao.listSmsConfig(searchCondition);

			searchResult = new SearchResult<SmsConfig>(list, searchCondition);
		}

		return searchResult;
	}

	/**
	 * sms 사용자별 건수 조회
	 * @param userId
	 * @return
	 */
	public SmsConfig readSmsConfig(String userId) {
		return smsConfigDao.getSmsConfig(userId);
	}

	/**
	 * sms 사용자별 건수 등록
	 * @param smsConfig
	 */
	public void createSmsConfig(SmsConfig smsConfig) {
		smsConfigDao.createSmsConfig(smsConfig);
	}

	/**
	 * sms 사용자별 건수 수정
	 * @param smsConfig
	 */
	public void updateSmsConfig(SmsConfig smsConfig) {
		smsConfigDao.updateSmsConfig(smsConfig);
	}

	/**
	 * sms 사용자별 건수 삭제
	 * @param userIds
	 */
	public void removeSmsConfig(String[] userIds) {
		
		if(userIds != null) {
			for(int i = 0; i < userIds.length; i++) {
				smsConfigDao.removeSmsConfig(userIds[i]);
			}
		}
	}
}

