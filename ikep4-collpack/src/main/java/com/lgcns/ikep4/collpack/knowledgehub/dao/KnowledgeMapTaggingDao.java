/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapTagging;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapTaggingPK;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Knowledge Map KnowledgeTaggingDao interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapTaggingDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMapTaggingDao extends GenericDao<KnowledgeMapTagging, KnowledgeMapTaggingPK> {

	/**
	 * Tagging List By CategoryId
	 * @param categoryId
	 * @return
	 */
	List<KnowledgeMapTagging> listByCategoryId(String categoryId);

	/**
	 * Tagging Delete (categoryId 에 해당하는 모든 Tag 삭제)
	 * @param categoryId
	 * @return
	 */
	int removeByCategoryId(String categoryId);

	/**
	 * Tagging Delete (categoryId 에 해당하는 모든 Tag 삭제 및 카테고리 모든 자식들의 Tag 삭제)
	 * @param categoryId
	 * @return
	 */
	int removeByCategoryIdHierarchy(String categoryId);

}
