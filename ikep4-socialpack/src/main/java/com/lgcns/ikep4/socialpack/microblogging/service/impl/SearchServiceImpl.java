/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.microblogging.dao.SearchDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Search;
import com.lgcns.ikep4.socialpack.microblogging.search.MblogSearchCondition;
import com.lgcns.ikep4.socialpack.microblogging.service.SearchService;


/**
 * 
 * SearchService 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: SearchServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service
public class SearchServiceImpl extends GenericServiceImpl<Search, String> implements SearchService {

	@Autowired
	private SearchDao searchDao;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang
	 * .Object)
	 */
	public String create(Search object) {
		return searchDao.create(object);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#update(java.lang.Object)
	 */
	public void update(Search object) {
		this.searchDao.update(object);
	}


	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#read(java.io.Serializable)
	 */
	public Search read(String id) {
		Search search = searchDao.get(id);
		
		return search;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl#delete(java.io.Serializable)
	 */
	public void delete(String id) {
		searchDao.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.SearchService#count(com.lgcns.ikep4.socialpack.microblogging.model.Search)
	 */
	public int count(Search search) {
		return searchDao.selectCount(search);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.SearchService#listBySearchUserId(com.lgcns.ikep4.socialpack.microblogging.model.Search)
	 */
	public List<Search> listBySearchUserId(Search search) {
		return searchDao.selectListBySearchUserId(search);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.service.SearchService#trendList(java.lang.String)
	 */
	public List<String> trendList(MblogSearchCondition mblogSearchCondition) {
		return searchDao.selectTrendList(mblogSearchCondition);
	}

}
