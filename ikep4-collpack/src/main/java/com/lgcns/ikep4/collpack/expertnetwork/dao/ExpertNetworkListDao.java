/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkListPK;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Expert Network ExpertListDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkListDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ExpertNetworkListDao extends GenericDao<ExpertNetworkList, ExpertNetworkListPK> {

	/**
	 * 카테고리별 전문가 전체개수 반환
	 * @param categoryId
	 * @return
	 */
	int countByCategoryId(String categoryId);

	/**
	 * 카테고리별 전문가 페이징 조회
	 * @param pageCondition
	 * @return
	 */
	List<ExpertNetworkList> listByCategoryIdPage(ExpertNetworkBlockPageCondition pageCondition);

	/**
	 * 카테고리별 전문가 삭제
	 * @param categoryId
	 */
	void removeByCategoryId(String categoryId);

	/**
	 * 카테고리별 전문가를 랜덤하게 1건조회
	 * @param categoryId
	 * @return
	 */
	ExpertNetworkList getByCategoryIdRandom(String categoryId);

	/**
	 * 관리자에의해 등록된 사내인증전문가를 제외한 데이터 삭제
	 */
	void removeByAuthorized();

	/**
	 * 전문가 등록 배치
	 */
	void batchGatherExpert();

	/**
	 * 전문가 수정<br/>
	 * IS_AUTHORIZED 컬럼변경
	 * @param expertNetworkList
	 */
	void updateAuthorized(ExpertNetworkList expertNetworkList);

}
