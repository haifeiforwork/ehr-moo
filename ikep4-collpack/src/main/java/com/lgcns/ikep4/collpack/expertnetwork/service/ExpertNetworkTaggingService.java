/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.service;

import java.util.List;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkTagging;
import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkTaggingPK;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Expert Network ExpertTaggingService interface
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: ExpertNetworkTaggingService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ExpertNetworkTaggingService extends GenericService<ExpertNetworkTagging, ExpertNetworkTaggingPK> {

	/**
	 * Tagging List By CategoryId
	 * @param categoryId
	 * @return List<ExpertTagging>
	 */
	List<ExpertNetworkTagging> listByCategoryId(String categoryId);

	/**
	 * Tagging Create (,로 구분된 Tags 를 분리하여 입력)
	 * @param categoryId
	 * @param tags
	 * @return createTags
	 */
	int createTags(String categoryId, String tags);

	/**
	 * Tagging(,로 분리된) By CategoryId
	 * @param categoryId
	 * @return String
	 */
	String getTagsByCategoryId(String categoryId);

	/**
	 * Tagging Delete (categoryId 에 해당하는 모든 Tag 삭제)
	 * @param categoryId
	 * @return int
	 */
	int deleteByCategoryId(String categoryId);

	/**
	 * Tagging Delete (categoryId 에 해당하는 모든 Tag 삭제)
	 * @param categoryId
	 * @return int
	 */
	int deleteByCategoryIdHierarchy(String categoryId);

}
