package com.lgcns.ikep4.support.favorite.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.favorite.dao.PortalFavoriteDao;
import com.lgcns.ikep4.support.favorite.model.PortalFavorite;
import com.lgcns.ikep4.support.favorite.model.PortalFavoriteSearchCondition;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.favorite.util.FavoriteUtil;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * PortalFavorite 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PortalFavoriteServiceImpl.java 5805 2011-04-11 09:18:31Z
 *          handul32 $
 */
@Service
public class PortalFavoriteServiceImpl extends GenericServiceImpl<PortalFavorite, String> implements
		PortalFavoriteService {

	/**
	 * Favorite Dao
	 */
	@Autowired
	private PortalFavoriteDao favoriteDao;

	/**
	 * 아이디생성 Dao
	 */
	@Autowired
	private IdgenService idgenService;

	/**
	 * Favorite 등록
	 */
	public String create(PortalFavorite favorite) {

		String favoriteId = idgenService.getNextId();
		favorite.setFavoriteId(favoriteId);

		favoriteDao.create(favorite);

		return favoriteId;
	}

	/**
	 * Favorite 조회
	 */
	public PortalFavorite read(String id) {
		return (PortalFavorite) favoriteDao.get(id);
	}

	/**
	 * Favorite 삭제
	 */
	public void delete(PortalFavorite favorite) {
		favoriteDao.remove(favorite);
	}

	/**
	 * Favorite 삭제
	 */
	public void delete(String targetId, String userId) {

		PortalFavorite favorite = new PortalFavorite();
		favorite.setTargetId(targetId);
		favorite.setRegisterId(userId);

		favoriteDao.remove(favorite);
	}

	/**
	 * 도큐먼트 리스트 조회
	 */
	public SearchResult<PortalFavorite> getAllForDocument(PortalFavoriteSearchCondition searchCondition) {

		// Integer count =
		// favoriteDao.countBySearchConditionForDocument(searchCondition);
		// searchCondition.terminateSearchCondition(count);

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<PortalFavorite> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<PortalFavorite> favoriteList = favoriteDao.listBySearchConditionForDocument(searchCondition);

		if (favoriteList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			for (PortalFavorite favorite : favoriteList) {
				if (!user.getLocaleCode().equals("ko")) {
					favorite.setRegisterName(favorite.getRegisterEnglishName());
				}
				favorite.setTargetUrl(FavoriteUtil.makeTargetUrl(favorite.getModuleEn(), favorite.getType()));
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(favoriteList.size());

			searchResult = new SearchResult<PortalFavorite>(favoriteList, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 피플 리스트 조회
	 */
	public SearchResult<PortalFavorite> getAllForPeople(PortalFavoriteSearchCondition searchCondition) {

		// Integer count =
		// favoriteDao.countBySearchConditionForPeople(searchCondition);
		// searchCondition.terminateSearchCondition(count);
		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<PortalFavorite> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<PortalFavorite> favoriteList = favoriteDao.listBySearchConditionForPeople(searchCondition);

		if (favoriteList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			for (PortalFavorite favorite : favoriteList) {
				if (!user.getLocaleCode().equals("ko")) {
					favorite.setTitle(favorite.getEnglishTitle());
					favorite.setTeamName(favorite.getTeamEnglishName());
					favorite.setJobTitleName(favorite.getJobTitleEnglishName());
				}
				favorite.setTargetUrl(FavoriteUtil.makeTargetUrl("Profile", favorite.getType()));
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(favoriteList.size());

			searchResult = new SearchResult<PortalFavorite>(favoriteList, searchCondition);
		}

		return searchResult;
	}
	
	
	public SearchResult<PortalFavorite> getAllForTeam(PortalFavoriteSearchCondition searchCondition) {

		// Integer count =
		// favoriteDao.countBySearchConditionForPeople(searchCondition);
		// searchCondition.terminateSearchCondition(count);
		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<PortalFavorite> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<PortalFavorite> favoriteTeamList = favoriteDao.listBySearchConditionForTeam(searchCondition);

		if (favoriteTeamList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(favoriteTeamList.size());

			searchResult = new SearchResult<PortalFavorite>(favoriteTeamList, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 도큐먼트 요약 조회
	 */
	public SearchResult<PortalFavorite> getSummaryForDocument(PortalFavoriteSearchCondition searchCondition) {

		SearchResult<PortalFavorite> searchResult = null;

		List<PortalFavorite> favoriteList = favoriteDao.listBySearchConditionForDocument(searchCondition);

		if (favoriteList != null) {
			for (PortalFavorite favorite : favoriteList) {
				favorite.setTargetUrl(FavoriteUtil.makeTargetUrl(favorite.getModuleEn(), favorite.getType()));
			}
			searchCondition.setEmptyRecord(false);
		}

		searchResult = new SearchResult<PortalFavorite>(favoriteList, searchCondition);

		return searchResult;
	}

	/**
	 * 피플 요약 조회
	 */
	public SearchResult<PortalFavorite> getSummaryForPeople(PortalFavoriteSearchCondition searchCondition) {

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<PortalFavorite> searchResult = null;

		List<PortalFavorite> favoriteList = favoriteDao.listBySearchConditionForPeople(searchCondition);

		if (favoriteList != null) {
			for (PortalFavorite favorite : favoriteList) {
				if (!user.getLocaleCode().equals("ko")) {
					favorite.setTitle(favorite.getEnglishTitle());
					favorite.setTeamName(favorite.getTeamEnglishName());
					favorite.setJobTitleName(favorite.getJobTitleEnglishName());
				}
				favorite.setTargetUrl(FavoriteUtil.makeTargetUrl("Profile", favorite.getType()));
			}
			searchCondition.setEmptyRecord(false);
		}

		searchResult = new SearchResult<PortalFavorite>(favoriteList, searchCondition);

		return searchResult;
	}

	/**
	 * Favorite 중복 체크
	 */
	public boolean exists(PortalFavorite favorite) {
		return favoriteDao.exists(favorite);
	}

	/**
	 * Favorite 중복 체크
	 */
	public boolean exists(String targetId, String registerId) {
		PortalFavorite favorite = new PortalFavorite();
		favorite.setTargetId(targetId);
		favorite.setRegisterId(registerId);
		return favoriteDao.exists(favorite);
	}

	/**
	 * 세션 정보 읽기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	/**
	 * Favorite ID 체크
	 */
	public String getFavoriteId(PortalFavorite favorite) {

		String favoriteId = favoriteDao.getFavoriteId(favorite);

		return favoriteId;
	}
}
