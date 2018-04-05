/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapList;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapListPK;
import com.lgcns.ikep4.collpack.knowledgehub.search.KnowledgeMapPopularPageCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Knowledge Map KnowledgePopularListDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapPopularListDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMapPopularListDao extends GenericDao<KnowledgeMapList, KnowledgeMapListPK> {

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
	 * 모든 데이터 삭제
	 */
	void removeAll();

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (BBS)
	 * @param itemType
	 */
	void batchGatherBBS(String itemType);

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (WorkSpace)
	 * @param itemType
	 */
	void batchGatherWorkSpace(String itemType);

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (Cafe)
	 * @param itemType
	 */
	void batchGatherCafe(String itemType);

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (WorkManual)
	 * @param itemType
	 */
	void batchGatherWorkManual(String itemType);

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (SocialBlog)
	 * @param itemType
	 */
	void batchGatherSocialBlog(String itemType);

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (Q&A)
	 * @param itemType
	 */
	void batchGatherQnA(String itemType);

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (Forum)
	 * @param itemType
	 */
	void batchGatherForum(String itemType);

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (Ideation)
	 * @param itemType
	 */
	void batchGatherIdeation(String itemType);

	/**
	 * 배치작업<br/>
	 * 일별 인기지식 집계 (CoporateVoca)
	 * @param itemType
	 */
	void batchGatherCorVoca(String itemType);

}
