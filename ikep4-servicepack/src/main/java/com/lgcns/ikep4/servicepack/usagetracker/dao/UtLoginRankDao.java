/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.usagetracker.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtLoginRank;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtStatistics;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;

/**
 * 현황 통계
 *
 * @author ihko11
 * @version $Id: UtLoginRankDao.java 16244 2011-08-18 04:11:42Z giljae $
 */
public interface UtLoginRankDao extends GenericDao<UtLoginRank,String> {
	/**
	 * 배치 유저 로그인 통계 읽기
	 * @return
	 */
	public List<UtLoginRank> selectUserBatchReadItems();
	
	/**
	 * 통계 메뉴구성 리스트
	 * @param searchCondition
	 * @return
	 */
	List<UtStatistics> listBySearchCondition(UtSearchCondition searchCondition);
	/**
	 * 통계 메뉴 카운트
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(UtSearchCondition searchCondition); 
}
