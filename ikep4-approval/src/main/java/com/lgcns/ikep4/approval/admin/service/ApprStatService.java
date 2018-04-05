package com.lgcns.ikep4.approval.admin.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.admin.model.ApprStat;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;


/**
 * 통계 서비스
 * 
 * @author handul
 * @version $Id$
 */
@Transactional
public interface ApprStatService {

	/**
	 * 리드타임 통계 생성
	 */
	void generateTimeStat(ApprStat apprStat);

	/**
	 * 사용자별 통계 생성
	 */
	void generateUserStat(ApprStat apprStat);

	/**
	 * 양식별 통계 생성
	 */
	void generateFormStat(ApprStat apprStat);

	/**
	 * 리드타임 통계 리스트
	 * 
	 * @return
	 */
	public SearchResult<ApprStat> getTimeStatList(ApprListSearchCondition searchCondition);

	/**
	 * 사용자별 통계 리스트
	 * 
	 * @return
	 */
	public SearchResult<ApprStat> getUserStatList(ApprListSearchCondition searchCondition);

	/**
	 * 양식별 통계 리스트
	 * 
	 * @return
	 */
	public SearchResult<ApprStat> getFormStatList(ApprListSearchCondition searchCondition);

}
