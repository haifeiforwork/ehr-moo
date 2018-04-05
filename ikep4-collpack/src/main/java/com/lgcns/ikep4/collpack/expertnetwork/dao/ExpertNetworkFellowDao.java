/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkFellow;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkFellowPK;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkPageCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Expert Network ExpertFellowDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkFellowDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ExpertNetworkFellowDao extends GenericDao<ExpertNetworkFellow, ExpertNetworkFellowPK> {

	/**
	 * 해당하는 전문가 전체 Record Count 반환 (최대 20개)
	 * @param userId
	 * @return
	 */
	int countByUserId(String userId);

	/**
	 * 관심분야 전문가 검색 (페이징 단위)
	 * @param pageCondition
	 * @return
	 */
	List<ExpertNetworkFellow> listByUserIdPage(ExpertNetworkPageCondition pageCondition);

}
