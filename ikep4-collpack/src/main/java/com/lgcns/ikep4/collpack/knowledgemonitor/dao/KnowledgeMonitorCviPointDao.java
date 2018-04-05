/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPoint;
import com.lgcns.ikep4.collpack.knowledgemonitor.model.KnowledgeMonitorCviPointPK;
import com.lgcns.ikep4.collpack.knowledgemonitor.portlet.model.KnowledgeMonitorPortletSearchCondition;
import com.lgcns.ikep4.collpack.knowledgemonitor.search.KnowledgeRankBlockPageCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Knowledge Monitor KnowledgeMonitorCviPointDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorCviPointDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMonitorCviPointDao extends GenericDao<KnowledgeMonitorCviPoint, KnowledgeMonitorCviPointPK> {

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
	 * 기존자료 삭제
	 */
	void batchRemove();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (BBS)
	 */
	void batchGatherBBS();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (WorkSpace)
	 */
	void batchGatherWorkSpace();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (Cafe)
	 */
	void batchGatherCafe();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (WorkManual)
	 */
	void batchGatherWorkManual();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (SocialBlog)
	 */
	void batchGatherSocialBlog();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (Q&A)
	 */
	void batchGatherQnA();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (Forum)
	 */
	void batchGatherForum();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (Ideation)
	 */
	void batchGatherIdeation();

	/**
	 * 배치작업<br/>
	 * 일별 지식 집계 (CoporateVoca)
	 */
	void batchGatherCorVoca();

	/**
	 * portalId 별 포틀릿 조회
	 * @param SearchCondition - 상위 10건 조회 조건 (포털ID, 건수)
	 * @return
	 */
	List<KnowledgeMonitorCviPoint> listByPortalIdPortelet(KnowledgeMonitorPortletSearchCondition SearchCondition);

}
