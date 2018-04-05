/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.approval.work.dao.ApprEntrustDao;
import com.lgcns.ikep4.approval.work.model.ApprEntrust;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprEntrustService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 위임 관리 Service 구현
 * 
 * @author jeehye
 * @version $Id: ApprEntrustServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apprEntrustService")
public class ApprEntrustServiceImpl extends GenericServiceImpl<ApprEntrust, String> implements ApprEntrustService {

	@Autowired
	private ApprEntrustDao apprEntrustDao;

	@Autowired
	private IdgenService idgenService;

	/**
	 * 위임정보 생성
	 * 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void entrustCreate(ApprEntrust apprEntrust) {

		apprEntrust.setEntrustId(idgenService.getNextId());
		this.apprEntrustDao.entrustCreate(apprEntrust);
	}

	/**
	 * 위임정보 생성
	 * 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void entrustUpdate(ApprEntrust apprEntrust) {
		this.apprEntrustDao.entrustUpdate(apprEntrust);
	}

	/**
	 * 위임 상태만 변경
	 * 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void entrustUpdateUsage(ApprEntrust apprEntrust, String[] entrustIds) {

		for (String entrustId : entrustIds) {
			apprEntrust.setEntrustId(entrustId);
			this.apprEntrustDao.entrustUpdateUsage(apprEntrust);
		}

	}

	/**
	 * 위임정보 조회
	 * 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public ApprEntrust entrustDetail(ApprEntrust apprEntrust) {
		return (ApprEntrust) this.apprEntrustDao.entrustDetail(apprEntrust);
	}

	/**
	 * 위임정보 리스트 조회
	 */
	public List<ApprEntrust> entrustList(ApprEntrust apprEntrust) {
		return this.apprEntrustDao.entrustList(apprEntrust);
	}

	/**
	 * 위임정보 리스트 조회(페이징)
	 */
	public SearchResult<ApprEntrust> listBySearchCondition(ApprListSearchCondition searchCondition) {

		Integer count = this.apprEntrustDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<ApprEntrust> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprEntrust>(searchCondition);
		} else {
			List<ApprEntrust> apprList = (List<ApprEntrust>) this.apprEntrustDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<ApprEntrust>(apprList, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 위임 유효성 체크
	 */
	public boolean isEntrust(String entrustUserId, String signUserId) {

		String portalId = (String) getRequestAttribute("ikep.portalId");

		ApprEntrust entrust = new ApprEntrust();
		entrust.setUserId(entrustUserId);
		entrust.setSignUserId(signUserId);
		entrust.setPortalId(portalId);
		entrust.setUsage("1");
		entrust.setIsValidDate("1");

		ApprEntrust apprEntrustDetail = apprEntrustDao.entrustDetail(entrust);

		if (apprEntrustDetail == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 수임 유효성 체크
	 */
	public boolean isSignUser(String entrustId) {

		String portalId = (String) getRequestAttribute("ikep.portalId");

		ApprEntrust entrust = new ApprEntrust();
		entrust.setEntrustId(entrustId);
		entrust.setPortalId(portalId);

		ApprEntrust apprEntrustDetail = apprEntrustDao.entrustDetail(entrust);

		if (apprEntrustDetail == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 수임 여부 체크
	 */
	public boolean hasSignUser(Map map) {

		return this.apprEntrustDao.hasSignUser(map);

	}

	/**
	 * 세션 정보 읽기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

}
