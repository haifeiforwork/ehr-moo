/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import java.util.List;

import com.lgcns.ikep4.approval.work.model.ApprOfficial;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * 공문 시행 DAO
 * 
 * @author 유승목
 * @version $Id$
 */
public interface ApprOfficialDao extends GenericDao<ApprOfficial, String> {

	/**
	 * 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<ApprOfficial> listBySearchCondition(ApprListSearchCondition searchCondition);

	/**
	 * 리스트 카운트
	 * 
	 * @param searchCondition
	 * @return
	 */
	public Integer countBySearchCondition(ApprListSearchCondition searchCondition);

}
