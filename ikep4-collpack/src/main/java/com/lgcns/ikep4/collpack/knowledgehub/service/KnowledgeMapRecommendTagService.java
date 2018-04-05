/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.service;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapRecommendTag;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapRecommendTagPK;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Knowledge Map KnowledgeRecommendTagService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendTagService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMapRecommendTagService extends GenericService<KnowledgeMapRecommendTag, KnowledgeMapRecommendTagPK> {

	/**
	 * userId별 추천태그 조회
	 * @param userId - 사용자ID
	 * @param rowCount - 결과 개수
	 * @return List<KnowledgeMapRecommendTag>
	 */
	List<KnowledgeMapRecommendTag> listByUserId(String userId, int rowCount);

	/**
	 * 배치작업<br/>
	 * 추천태그 집계 (실시간으로 변경하여 사용되지 않음)
	 */
	void batchGatherTags();

}
