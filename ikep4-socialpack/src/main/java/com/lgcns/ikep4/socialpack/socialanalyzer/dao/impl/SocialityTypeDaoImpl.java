/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.SocialityTypeDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityType;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.SocialityTypePk;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialityTypeDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class SocialityTypeDaoImpl extends GenericDaoSqlmap<SocialityType, SocialityTypePk> implements SocialityTypeDao {
	private static final String NAMESPACE = "socialpack.socialanalyzer.dao.socialityType.";
	
	public SocialityTypePk create(SocialityType socialityType) {
		sqlInsert(NAMESPACE + "create", socialityType);
		return (SocialityTypePk) socialityType;
	}

	public boolean exists(SocialityTypePk socialityTypePk) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", socialityTypePk);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public SocialityType get(SocialityTypePk socialityTypePk) {
		return (SocialityType) sqlSelectForObject(NAMESPACE + "get", socialityTypePk);
	}

	public void remove(SocialityTypePk socialityTypePk) {
		sqlDelete(NAMESPACE + "remove", socialityTypePk);
	}

	public void update(SocialityType socialityType) {
		sqlUpdate(NAMESPACE + "update", socialityType);
	}
	////////////////////////////////////
	
}
