/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.work.dao.ApprNoticeDao;
import com.lgcns.ikep4.approval.work.model.ApprNotice;
import com.lgcns.ikep4.approval.work.service.ApprNoticeService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * 위임 관리 Service 구현
 *
 * @author jeehye
 * @version $Id: ApprNoticeServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apprNoticeService")
public class ApprNoticeServiceImpl extends GenericServiceImpl<ApprNotice, String> implements ApprNoticeService {

	@Autowired
	private ApprNoticeDao apprNoticeDao;

	/**
	 * 위임정보 생성
	 * @param apprNotice 모델
	 * @return ID
	 */
	public void noticeCreate(ApprNotice apprNotice) { 
		this.apprNoticeDao.noticeCreate(apprNotice);
	}
	
	/**
	 * 위임정보 생성
	 * @param apprNotice 모델
	 * @return ID
	 */
	public void noticeUpdate(ApprNotice apprNotice) { 
		this.apprNoticeDao.noticeUpdate(apprNotice);
	}
	
	/**
	 * 위임 상태만 변경
	 * @param apprNotice 모델
	 * @return ID
	 */
	public void noticeUpdateUsage(ApprNotice apprNotice) { 
		this.apprNoticeDao.noticeUpdateUsage(apprNotice);
	}
	
	/**
	 * 위임정보 조회
	 * @param apprNotice 모델
	 * @return ID
	 */
	public ApprNotice noticeDetail(String userId, String portalId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", 	userId); 
		map.put("portalId", 	portalId);
		
		return (ApprNotice)this.apprNoticeDao.noticeDetail(map);
	}
	
}
