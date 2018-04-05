/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.RelationDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Relation;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.RelationService;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: RelationServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service 
@Transactional
public class RelationServiceImpl extends GenericServiceImpl<Relation, String> implements RelationService {

	@Autowired
	private RelationDao relationDao;

	@Deprecated
	public String create(Relation relation) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public Relation read(String id) {
		return null;
	}

	@Deprecated
	public void delete(String id) {
	}

	@Deprecated
	public void update(String id) {
	}
	////////////////////////////////////
	
	//소셜 네트웍 조회-개인별
	public List<Relation> listPerson(Map<String, String> map) {
		return relationDao.listPerson(map);
	}
	//소셜 네트웍 조회-그룹별
	public List<Relation> listGroup(Map<String, String> map) {
		return relationDao.listGroup(map);
	}
	//배치
	public void batchRelation() {
		relationDao.batchRelation();
	}
}
