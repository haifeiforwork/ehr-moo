/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.SocialityDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Sociality;
import com.lgcns.ikep4.socialpack.socialanalyzer.search.SocialAnalyzerSearchCondition;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialityDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class SocialityDaoImpl extends GenericDaoSqlmap<Sociality, String> implements SocialityDao {
	private static final String NAMESPACE = "socialpack.socialanalyzer.dao.sociality.";

	@Deprecated
	public String create(Sociality sociality) {
		return null;
	}

	@Deprecated
	public boolean exists(String userId) {
		return false;
	}

	@Deprecated
	public Sociality get(String userId) {
		return null;
	}

	@Deprecated
	public void remove(String userId) {
	}

	@Deprecated
	public void update(Sociality sociality) {
	}
	////////////////////////////////////

	//랭킹 조회 검색수
	public Integer countSociality(SocialAnalyzerSearchCondition socialAnalyzerSearchCondition) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countSociality", socialAnalyzerSearchCondition);
	}
	//랭킹 조회 검색
	public List<Sociality> listSociality(SocialAnalyzerSearchCondition socialAnalyzerSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listSociality", socialAnalyzerSearchCondition);
	}
	//나의 랭킹
	public Integer getMyRanking(String userId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "getMyRanking", userId);
	}
	//배치
	public void batchSociality() {
		sqlInsert(NAMESPACE + "batchSociality");
	}
}
