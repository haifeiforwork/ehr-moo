/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.favorite.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;


/**
 * PortalFavorite DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PortalFavoriteDao.java 17322 2012-02-13 09:50:12Z yu_hs $
 */
public interface PortalFavoriteDao extends GenericDao<PortalFavorite, String> {

	/**
	 * 도큐먼트 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<PortalFavorite> listBySearchConditionForDocument(PortalFavoriteSearchCondition searchCondition);

	/**
	 * 피플 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public List<PortalFavorite> listBySearchConditionForPeople(PortalFavoriteSearchCondition searchCondition);
	
	public List<PortalFavorite> listBySearchConditionForTeam(PortalFavoriteSearchCondition searchCondition);


	/**
	 * 중복 체크
	 * 
	 * @param favorite
	 * @return
	 */
	public boolean exists(PortalFavorite favorite);

	/**
	 * 삭제
	 * 
	 * @param favorite
	 */
	public void remove(PortalFavorite favorite);
	
	/**
	 * Favorite ID 체크
	 * 
	 * @param favorite
	 * @return
	 */
	public String getFavoriteId(PortalFavorite favorite);

}
