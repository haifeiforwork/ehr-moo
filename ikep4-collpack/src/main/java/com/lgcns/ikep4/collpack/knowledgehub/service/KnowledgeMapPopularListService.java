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
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Knowledge Map KnowledgePopularListService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPopularListService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMapPopularListService extends GenericService<KnowledgeMapList, KnowledgeMapListPK> {

	/**
	 * portalId별 데이터 건수
	 * @param portalId
	 * @return
	 */
	int countByPortalId(String portalId);

	/**
	 * portalId별 데이터 건수 (페이징)
	 * @param portalId
	 * @return
	 */
	int countByPortalIdPage(KnowledgeMapPopularPageCondition pageCondition);

	/**
	 * portalId별 인기지식 조회 (페이징)
	 * @param pageCondition
	 * @return
	 */
	List<KnowledgeMapList> listByPortalIdPage(KnowledgeMapPopularPageCondition pageCondition);

	/**
	 * 배치작업<br/>
	 * 인기지식 집계
	 */
	void batchGatherKnowledge();

}
