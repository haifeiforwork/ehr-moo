package com.lgcns.ikep4.support.favorite.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;


/**
 * PortalFavorite 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PortalFavoriteService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface PortalFavoriteService extends GenericService<PortalFavorite, String> {

	/**
	 * 도큐먼트 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<PortalFavorite> getAllForDocument(PortalFavoriteSearchCondition searchCondition);

	/**
	 * 피플 리스트 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<PortalFavorite> getAllForPeople(PortalFavoriteSearchCondition searchCondition);
	
	public SearchResult<PortalFavorite> getAllForTeam(PortalFavoriteSearchCondition searchCondition);

	/**
	 * 도큐먼트 요약 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<PortalFavorite> getSummaryForDocument(PortalFavoriteSearchCondition searchCondition);

	/**
	 * 피플 요약 검색
	 * 
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<PortalFavorite> getSummaryForPeople(PortalFavoriteSearchCondition searchCondition);

	/**
	 * 중복체크
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
	public void delete(PortalFavorite favorite);

	public void delete(String targetId, String userId);

	/**
	 * 중복체크
	 * 
	 * @param favorite
	 */
	public boolean exists(String targetId, String registerId);
	
	/**
	 * Favorite ID 체크
	 * 
	 * @param favorite
	 * @return
	 */
	public String getFavoriteId(PortalFavorite favorite);

}
