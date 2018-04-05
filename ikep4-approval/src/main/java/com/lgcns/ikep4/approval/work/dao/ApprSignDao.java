/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import java.util.List;

import com.lgcns.ikep4.approval.work.model.ApprSign;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Sing 이미지 관리 DAO
 * 
 * @author 유승목
 * @version $Id$
 */
public interface ApprSignDao extends GenericDao<ApprSign, String> {

	/**
	 * 목록 조회
	 * 
	 * @param apprSign
	 * @return
	 */
	public List<ApprSign> select(ApprSign apprSign);

	/**
	 * 기본 사인 이미지 설정
	 * 
	 * @param apprSign
	 */
	public void setDefault(ApprSign apprSign);

	/**
	 * 기본 사인 이미지 초기화
	 * 
	 * @param apprSign
	 */
	public void initDefault(ApprSign apprSign);

	/**
	 * 결재 비밀번호 변경
	 * 
	 * @param user
	 */
	public void changeApprPassword(User user);

	/**
	 * 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<ApprSign> listBySearchCondition(ApprListSearchCondition searchCondition);

	/**
	 * 리스트 카운트
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchCondition(ApprListSearchCondition searchCondition);
}
