package com.lgcns.ikep4.approval.work.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.work.model.ApprSign;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Sing 이미지 관리 서비스
 * 
 * @author 유승목
 * @version $Id$
 */
@Transactional
public interface ApprSignService extends GenericService<ApprSign, String> {

	/**
	 * 목록 조회
	 * 
	 * @param apprSign
	 * @return
	 */
	public List<ApprSign> select(ApprSign apprSign);

	/**
	 * 삭제
	 * 
	 * @param signIds
	 */
	public void delete(String[] signIds);

	/**
	 * 기본 사인 이미지 변경
	 * 
	 * @param apprSign
	 */
	public void changeDefault(ApprSign apprSign);

	public void clearDefault(ApprSign apprSign);

	/**
	 * 결재 비밀번호 변경
	 * 
	 * @param user
	 */
	public void changeApprPassword(User user);

	/**
	 * 사용자 현재 서명이미지 정보
	 * 
	 * @param userId
	 * @return
	 */
	public ApprSign selectUserSign(String userId);

	/**
	 * 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<ApprSign> listBySearchCondition(ApprListSearchCondition searchCondition);

}
