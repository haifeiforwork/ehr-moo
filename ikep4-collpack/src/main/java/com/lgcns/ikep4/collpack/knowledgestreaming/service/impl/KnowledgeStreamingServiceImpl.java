/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgestreaming.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.knowledgestreaming.dao.KnowledgeStreamingDao;
import com.lgcns.ikep4.collpack.knowledgestreaming.model.KnowledgeUse;
import com.lgcns.ikep4.collpack.knowledgestreaming.service.KnowledgeStreamingService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * TODO Javadoc주석작성
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: KnowledgeStreamingServiceImpl.java 16321 2011-08-22 00:33:29Z
 *          giljae $
 */
@Service
public class KnowledgeStreamingServiceImpl extends
		GenericServiceImpl<KnowledgeUse, String> implements
		KnowledgeStreamingService {

	@Autowired
	KnowledgeStreamingDao knowledgeStreamingDao;

	public List<KnowledgeUse> selectWeekly(Map searchMap) {

		return knowledgeStreamingDao.selectWeekly(searchMap);
	}

	public void batchInsert() {
		knowledgeStreamingDao.batchDelete();
		knowledgeStreamingDao.batchInsert();
	}

}
