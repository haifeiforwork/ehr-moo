/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.knowledgestreaming.model.KnowledgeUse;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Knowledge Streaming Service
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: KnowledgeStreamingService.java 16236 2011-08-18 02:48:22Z
 *          giljae $
 */
public interface KnowledgeStreamingService extends
		GenericService<KnowledgeUse, String> {

	/**
	 * 지식활용수준 차트 검색
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<KnowledgeUse> selectWeekly(Map searchMap);
	
	public void batchInsert();

}
