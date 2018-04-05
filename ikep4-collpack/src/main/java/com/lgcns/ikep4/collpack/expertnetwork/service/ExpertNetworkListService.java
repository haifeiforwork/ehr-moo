/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service;

import java.util.List;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkListPK;
import com.lgcns.ikep4.collpack.expertnetwork.search.ExpertNetworkBlockPageCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Expert Network ExpertListService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkListService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ExpertNetworkListService extends GenericService<ExpertNetworkList, ExpertNetworkListPK> {

	/**
	 * 카테고리별 전문가 전체개수 반환
	 * @param categoryId
	 * @return int
	 */
	int countByCategoryId(String categoryId);

	/**
	 * 카테고리별 전문가 페이징 조회
	 * @param pageCondition
	 * @return List<ExpertList>
	 */
	List<ExpertNetworkList> listByCategoryIdPage(ExpertNetworkBlockPageCondition pageCondition);
	
	/**
	 * 카테고리별 전문가 삭제
	 * @param categoryId
	 */
	void deleteByCategoryId(String categoryId);
	
	/**
	 * 카테고리별 전문가를 랜덤하게 조회
	 * @param categoryCount - 카테고리 개수
	 * @param portalId - portalId
	 * @return List<ExpertList>
	 */
	List<ExpertNetworkList> listRandom(int categoryCount, String portalId);

	/**
	 * 전문가 등록 (추가등록)
	 * 기존데이터가 존재하면 수정 아니면 입력
	 * @param expertlist
	 */
	void createOrUpdateExpertList(ExpertNetworkList expertNetworkList);

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
