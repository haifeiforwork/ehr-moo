/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.service;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPoint;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPointPK;
import com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model.KnowledgeMonitorPortletSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Knowledge Map KnowledgeMonitorCviPointService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPointService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMonitorCviPointService extends GenericService<KnowledgeMonitorCviPoint, KnowledgeMonitorCviPointPK> {

	/**
	 * portalId, moduleCode별 지식개수 조회 (페이징에 사용)
	 * @param pageCondition - 페이징처리 관련 조건
	 * @return - 조건에 맞는 데이터 전체 개수
	 */
	int countByPortalIdModuleCode(KnowledgeRankBlockPageCondition pageCondition);

	/**
	 * portalId, moduleCode별 지식 페이징 조회
	 * @param pageCondition - 페이징처리 관련 조건
	 * @return
	 */
	List<KnowledgeMonitorCviPoint> listByPortalIdModuleCodePage(KnowledgeRankBlockPageCondition pageCondition);

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계<br/>
	 * 매일 자정을 지나고 실행되기때문에 입력조건 없이 (sysdate - 1) 을 기준으로 한다.
	 */
	void batchGatherKnowledge();

	/**
	 * portalId 별 포틀릿 조회
	 * @param SearchCondition - 상위 10건 조회 조건 (포털ID, 건수)
	 * @return
	 */
	List<KnowledgeMonitorCviPoint> listByPortalIdPortelet(KnowledgeMonitorPortletSearchCondition SearchCondition);

}
