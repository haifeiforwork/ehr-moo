/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.sms.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.sms.dao.SmsDao;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.search.SmsReceiverSearchCondition;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 
 * @author 서혜숙
 * @version $Id: SmsDaoImpl.java 15586 2011-06-24 09:08:38Z giljae $
 */
@Repository("smsDao")
public class SmsDaoImpl extends GenericDaoSqlmap<Sms, String> implements SmsDao {
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 당월발송내역(팝업 오른쪽 하단)
	 */
	public List<Sms> selectRecentBottom(SmsReceiverSearchCondition smsReceiverSearchCondition) {
		return sqlSelectForList("sms.selectRecentBottom", smsReceiverSearchCondition);
	}
	
	/**
	 * 발송내역목록(발신함)
	 */
	public List<Sms> listSmsReceiverBySearchCondition(SmsReceiverSearchCondition smsReceiverSearchCondition) {
		return sqlSelectForList("sms.selectAll", smsReceiverSearchCondition);
	}
	

	/**
	 * 발송내역수(발신함)
	 */	
	public Integer countSmsReceiverBySearchCondition(SmsReceiverSearchCondition smsReceiverSearchCondition) {
		return (Integer)sqlSelectForObject("sms.countSmsReceiverBySearchCondition", smsReceiverSearchCondition);
	}
	
	/**
	 * 전송
	 */
	public String create(Sms sms) {
		// id 채번
		//String id = idgenService.getNextId();
		//sms.setSmsId(id);
		sqlInsert("sms.insert", sms);
		return sms.getSmsId();
	}

	/**
	 * 전송
	 */
	public String createSms(Sms sms) {

		//120927 최재영
		//오러클에 맞추어 생성된 로직을 주석처리한다.
		//String smsTrnum = (String) sqlSelectForObject("sms.smsTrnum");
		//sms.setSmsId(smsTrnum);
			
		return (String)sqlInsert("sms.insertSms", sms);
	
	}

	/**
	 * 전송내역수
	 */
	public boolean exists(String id) {
		Integer count = (Integer) sqlSelectForObject("sms.selectCount", id);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 전송내역
	 */
	public Sms get(String id) {
		return (Sms) sqlSelectForObject("sms.select", id);
	}

	/**
	 * 단건 삭제
	 */
	public void remove(String id) {
		sqlDelete("sms.delete", id);
	}

	/**
	 * sms 수발신 보관 배치
	 */
	public void deleteRecentSms() {
		sqlDelete("sms.deleteRecentSms");
	}
	
	/**
	 * update
	 */
	public void update(Sms sms) {
		sqlUpdate("sms.update", sms);
	}
	
	/**
	 * 당월발송내역수(팝업 오른쪽 하단)
	 */	
	public int selectRecentBottomCount(SmsReceiverSearchCondition smsReceiverSearchCondition) {
		return (Integer)sqlSelectForObject("sms.selectRecentBottomCount", smsReceiverSearchCondition);
	}		
}
