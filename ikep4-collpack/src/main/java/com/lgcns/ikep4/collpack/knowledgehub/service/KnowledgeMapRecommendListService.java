/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.service;

import java.util.List;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.support.tagging.model.Tag;


/**
 * Knowledge Map KnowledgeRecommendListService interface
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapRecommendListService.java 15142 2011-06-17
 *          04:19:35Z jins02 $
 */
public interface KnowledgeMapRecommendListService extends GenericService<KnowledgeMapList, KnowledgeMapListPK> {

	/**
	 * userId별 추천지식 조회 (페이징)
	 * 
	 * @param pageCondition
	 * @return List<KnowledgeList>
	 */
	List<KnowledgeMapList> listByUserIdPage(Tag tag);

	/**
	 * userId별 추천지식 조회 (메인화면에서 사용)
	 * 
	 * @param pageCondition
	 * @return List<KnowledgeList>
	 */
	List<KnowledgeMapList> listByUserIdPageSimple(Tag tag);

}
