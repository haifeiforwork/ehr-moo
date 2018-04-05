/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.socialpack.socialanalyzer.dao.SocialityDao;
import com.lgcns.ikep4.socialpack.socialanalyzer.model.Sociality;
import com.lgcns.ikep4.socialpack.socialanalyzer.search.SocialAnalyzerSearchCondition;
import com.lgcns.ikep4.socialpack.socialanalyzer.service.SocialityService;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialityServiceImpl.java 19578 2012-07-02 05:35:42Z malboru80 $
 */
@Service 
@Transactional
public class SocialityServiceImpl extends GenericServiceImpl<Sociality, String> implements SocialityService {

	@Autowired
	private SocialityDao socialityDao;
	
	@Deprecated
	public String create(Sociality sociality) {
		return socialityDao.create(sociality);
	}

	@Deprecated
	public boolean exists(String userId) {
		return socialityDao.exists(userId);
	}

	@Deprecated
	public Sociality read(String userId) {
		return socialityDao.get(userId);
	}

	@Deprecated
	public void delete(String userId) {
		socialityDao.remove(userId);
	}

	@Deprecated
	public void update(Sociality sociality) {
		socialityDao.update(sociality);
	}
	////////////////////////////////////
	
	//랭킹 조회 검색
	public SearchResult<Sociality> listSociality(SocialAnalyzerSearchCondition socialAnalyzerSearchCondition) {
		Integer count = socialityDao.countSociality(socialAnalyzerSearchCondition);
		socialAnalyzerSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<Sociality> searchResult = null; 
		if(socialAnalyzerSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<Sociality>(socialAnalyzerSearchCondition);
		} else {
			List<Sociality> socialityList = socialityDao.listSociality(socialAnalyzerSearchCondition); 
			searchResult = new SearchResult<Sociality>(socialityList, socialAnalyzerSearchCondition);  			
		}  
		
		return searchResult;
	}
	//나의 랭킹
	public Integer getMyRanking(String userId) {
		return socialityDao.getMyRanking(userId);
	}
	//배치
	public void batchSociality() {
		socialityDao.batchSociality();
	}
}
