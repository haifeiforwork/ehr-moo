/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.socialpack.microblogging.dao.FavoriteDao;
import com.lgcns.ikep4.socialpack.microblogging.model.Favorite;


/**
 * 
 * FavoriteDao 구현 클래스
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: FavoriteDaoImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Repository
public class FavoriteDaoImpl extends GenericDaoSqlmap<Favorite, Favorite> implements FavoriteDao {
	private static final String NAMESPACE = "socialpack.microblogging.dao.favorite.";

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FavoriteDao#selectFavoriteing(com.lgcns.ikep4.socialpack.microblogging.model.Favorite)
	 */
	public List<Favorite> selectFavoriteing(Favorite favorite) {
		return sqlSelectForList(NAMESPACE + "selectFavoriteing" ,favorite);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public Favorite create(Favorite favorite) {
		return (Favorite) sqlInsert(NAMESPACE + "insert", favorite);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	public void remove(Favorite favorite) {
		sqlDelete(NAMESPACE + "delete", favorite);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(Favorite favorite) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public Favorite get(Favorite favorite) {
		return (Favorite)sqlSelectForObject(NAMESPACE + "select", favorite);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(Favorite favorite) {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FavoriteDao#deleteByMblogId(java.lang.String)
	 */
	public void deleteByMblogId(String mblogId) {
		sqlDelete(NAMESPACE + "deleteByMblogId", mblogId);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.microblogging.dao.FavoriteDao#selectCountByUserId(java.lang.String)
	 */
	public int selectCountByUserId(String userId) {
		return (Integer)sqlSelectForObject(NAMESPACE + "selectCountByUserId", userId);
	}

}
