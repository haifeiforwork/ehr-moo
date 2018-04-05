/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Sociality;
import com.lgcns.ikep4.socialpack.socialanalyzer.search.SocialAnalyzerSearchCondition;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialityDao.java 16246 2011-08-18 04:48:28Z giljae $
 */
public interface SocialityDao extends GenericDao<Sociality, String> {
	/**
	 * 랭킹 조회 검색수
	 * @param socialAnalyzerSearchCondition
	 * @return
	 */
	public Integer countSociality(SocialAnalyzerSearchCondition socialAnalyzerSearchCondition);
	/**
	 * 랭킹 조회 검색
	 * @param socialAnalyzerSearchCondition
	 * @return
	 */
	public List<Sociality> listSociality(SocialAnalyzerSearchCondition socialAnalyzerSearchCondition);
	/**
	 * 나의 랭킹
	 * @param userId
	 * @return
	 */
	public Integer getMyRanking(String userId);
	/**
	 * 배치
	 * 
	 */
	public void batchSociality();
}
