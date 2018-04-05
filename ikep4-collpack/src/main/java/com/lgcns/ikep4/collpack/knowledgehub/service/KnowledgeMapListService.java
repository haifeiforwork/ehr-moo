/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.service;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapBlockPageCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Knowledge Map KnowledgeListService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapListService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMapListService extends GenericService<KnowledgeMapList, KnowledgeMapListPK> {

	/**
	 * categoryId별 데이터 건수
	 * @param categoryId
	 * @return
	 */
	int countByCategoryId(String categoryId);

	/**
	 * 카테고리별 Knowledge 조회 (페이징)
	 * @param pageCondition
	 * @return
	 */
	List<KnowledgeMapList> listByCategoryIdPage(KnowledgeMapBlockPageCondition pageCondition);

	/**
	 * 배치작업<br/>
	 * 카테고리별 지식 집계
	 */
	void batchGatherKnowledge();
}
