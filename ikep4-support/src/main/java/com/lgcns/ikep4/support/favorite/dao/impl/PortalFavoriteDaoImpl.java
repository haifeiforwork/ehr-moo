package com.lgcns.ikep4.support.favorite.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.favorite.dao.PortalFavoriteDao;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;


/**
 * PortalFavorite 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PortalFavoriteDaoImpl.java 17752 2012-03-29 04:48:03Z yu_hs $
 */
@Repository("aaa")
public class PortalFavoriteDaoImpl extends GenericDaoSqlmap<PortalFavorite, String> implements PortalFavoriteDao {

	/**
	 * Favorite 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.favorite.";

	/**
	 * Favorite 등록
	 */
	public String create(PortalFavorite favorite) {

		return (String) sqlInsert(NAMESPACE + "insert", favorite);
	}

	/**
	 * Favorite 조회
	 */
	public PortalFavorite get(String favoriteId) {
		return (PortalFavorite) sqlSelectForObject(NAMESPACE + "select", favoriteId);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Favorite 체크
	 */
	public boolean exists(PortalFavorite favorite) {
		boolean check = false;
		PortalFavorite reObj = (PortalFavorite) sqlSelectForObject(NAMESPACE + "exists", favorite);
		if (reObj != null) {
			check = true;
		}
		return check;
	}

	public void update(PortalFavorite object) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * Favorite 삭제
	 */
	public void remove(PortalFavorite favorite) {
		sqlDelete(NAMESPACE + "delete", favorite);
	}

	/**
	 * 도큐먼트 리스트 조회
	 */
	public List<PortalFavorite> listBySearchConditionForDocument(PortalFavoriteSearchCondition searchCondition) {
		return (List<PortalFavorite>) sqlSelectForList(NAMESPACE + "listBySearchConditionForDocument", searchCondition);
	}

	/**
	 * 피플 리스트 조회
	 */
	public List<PortalFavorite> listBySearchConditionForPeople(PortalFavoriteSearchCondition searchCondition) {
		return (List<PortalFavorite>) sqlSelectForList(NAMESPACE + "listBySearchConditionForPeople", searchCondition);
	}
	
	public List<PortalFavorite> listBySearchConditionForTeam(PortalFavoriteSearchCondition searchCondition) {
		return (List<PortalFavorite>) sqlSelectForList(NAMESPACE + "listBySearchConditionForTeam", searchCondition);
	}
	
	/**
	 * Favorite ID 체크
	 */
	public String getFavoriteId(PortalFavorite favorite) {
		return (String) sqlSelectForObject(NAMESPACE + "getFavoriteId", favorite);
	}

}
