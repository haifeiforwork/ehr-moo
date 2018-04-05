/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgehub.service;

import java.util.List;

import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapTagging;
import com.lgcns.ikep4.collpack.knowledgehub.model.KnowledgeMapTaggingPK;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Knowledge Map KnowledgeTaggingService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMapTaggingService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeMapTaggingService extends GenericService<KnowledgeMapTagging, KnowledgeMapTaggingPK> {

	/**
	 * Tagging List By CategoryId
	 * @param categoryId
	 * @return
	 */
	List<KnowledgeMapTagging> listByCategoryId(String categoryId);

	/**
	 * Tagging Create (,로 구분된 Tags 를 분리하여 입력)
	 * @param categoryId
	 * @param tags
	 * @return
	 */
	int createTags(String categoryId, String tags);

	/**
	 * Tagging(,로 분리된) By CategoryId
	 * @param categoryId
	 * @return
	 */
	String getTagsByCategoryId(String categoryId);

	/**
	 * Tagging Delete (categoryId 에 해당하는 모든 Tag 삭제)
	 * @param categoryId
	 * @return
	 */
	int deleteByCategoryId(String categoryId);

	/**
	 * Tagging Delete (categoryId 에 해당하는 모든 Tag 삭제)
	 * @param categoryId
	 * @return
	 */
	int deleteByCategoryIdHierarchy(String categoryId);

}
