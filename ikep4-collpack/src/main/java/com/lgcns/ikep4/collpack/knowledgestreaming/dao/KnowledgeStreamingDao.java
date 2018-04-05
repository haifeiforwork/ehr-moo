/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.knowledgestreaming.model.KnowledgeUse;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * Knowledge Monitor KnowledgeStreamingDao interface
 * 
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeStreamingDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface KnowledgeStreamingDao extends GenericDao<KnowledgeUse, String> {

	/**
	 * 지식 활용 수준 차트
	 * 
	 * @param searchMap
	 * @return
	 */
	public List<KnowledgeUse> selectWeekly(Map searchMap);

	public void batchInsert();

	public void batchDelete();

}