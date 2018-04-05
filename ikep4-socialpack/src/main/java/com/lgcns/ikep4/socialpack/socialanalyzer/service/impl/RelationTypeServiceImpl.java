/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.RelationTypeDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationType;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.RelationTypePk;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationTypeService;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: RelationTypeServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service 
@Transactional
public class RelationTypeServiceImpl extends GenericServiceImpl<RelationType, RelationTypePk> implements RelationTypeService {

	@Autowired
	private RelationTypeDao relationTypeDao;
	
	public RelationTypePk create(RelationType relationType) {
		return relationTypeDao.create(relationType);
	}

	public boolean exists(RelationTypePk relationTypePk) {
		return relationTypeDao.exists(relationTypePk);
	}

	public RelationType read(RelationTypePk relationTypePk) {
		return relationTypeDao.get(relationTypePk);
	}

	public void delete(RelationTypePk relationTypePk) {
		relationTypeDao.remove(relationTypePk);
	}

	public void update(RelationType relationType) {
		relationTypeDao.update(relationType);
	}
	////////////////////////////////////
}
