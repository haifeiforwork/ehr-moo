package com.lgcns.ikep4.support.recent.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.favorite.util.FavoriteUtil;
import com.lgcns.ikep4.support.recent.dao.RecentDao;
import com.lgcns.ikep4.support.recent.model.Recent;
import com.lgcns.ikep4.support.recent.model.RecentSearchCondition;
import com.lgcns.ikep4.support.recent.service.RecentService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Recent 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: RecentServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service
public class RecentServiceImpl extends GenericServiceImpl<Recent, String> implements RecentService {

	/**
	 * Recent Dao
	 */
	@Autowired
	private RecentDao recentDao;

	/**
	 * 도큐먼트 리스트 검색
	 */
	public SearchResult<Recent> getAllForDocument(RecentSearchCondition searchCondition) {

		// Integer count =
		// recentDao.countBySearchConditionForDocument(searchCondition);
		// searchCondition.terminateSearchCondition(count);

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<Recent> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<Recent> recentList = recentDao.listBySearchConditionForDocument(searchCondition);

		if (recentList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			for (Recent recent : recentList) {
				if (!user.getLocaleCode().equals("ko")) {
					recent.setRegisterName(recent.getRegisterEnglishName());
				}
				recent.setTargetUrl(FavoriteUtil.makeTargetUrl(recent.getModuleEn(), recent.getType()));
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(recentList.size());
		}

		searchResult = new SearchResult<Recent>(recentList, searchCondition);

		return searchResult;
	}

	/**
	 * 피플 리스트 검색
	 */
	public SearchResult<Recent> getAllForPeople(RecentSearchCondition searchCondition) {

		// Integer count =
		// recentDao.countBySearchConditionForPeople(searchCondition);
		// searchCondition.terminateSearchCondition(count);

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<Recent> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<Recent> recentList = recentDao.listBySearchConditionForPeople(searchCondition);

		if (recentList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {
			for (Recent recent : recentList) {
				if (!user.getLocaleCode().equals("ko")) {
					recent.setTitle(recent.getEnglishTitle());
					recent.setTeamName(recent.getTeamEnglishName());
					recent.setJobTitleName(recent.getJobTitleEnglishName());
				}
				recent.setTargetUrl(FavoriteUtil.makeTargetUrl("Profile", recent.getType()));
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(recentList.size());
		}

		searchResult = new SearchResult<Recent>(recentList, searchCondition);

		return searchResult;
	}

	/**
	 * 도큐먼트 요약 검색
	 */
	public SearchResult<Recent> getSummaryForDocument(RecentSearchCondition searchCondition) {

		SearchResult<Recent> searchResult = null;

		List<Recent> recentList = recentDao.listBySearchConditionForDocument(searchCondition);

		if (recentList != null) {
			for (Recent recent : recentList) {
				recent.setTargetUrl(FavoriteUtil.makeTargetUrl(recent.getModuleEn(), recent.getType()));
			}
			searchCondition.setEmptyRecord(false);
		}

		searchResult = new SearchResult<Recent>(recentList, searchCondition);

		return searchResult;
	}

	/**
	 * 피플 요약 검색
	 */
	public SearchResult<Recent> getSummaryForPeople(RecentSearchCondition searchCondition) {

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<Recent> searchResult = null;

		List<Recent> recentList = recentDao.listBySearchConditionForPeople(searchCondition);

		if (recentList != null) {
			for (Recent recent : recentList) {
				if (!user.getLocaleCode().equals("ko")) {
					recent.setTitle(recent.getEnglishTitle());
					recent.setTeamName(recent.getTeamEnglishName());
					recent.setJobTitleName(recent.getJobTitleEnglishName());
				}
				recent.setTargetUrl(FavoriteUtil.makeTargetUrl("Profile", recent.getType()));
			}
			searchCondition.setEmptyRecord(false);
		}

		searchResult = new SearchResult<Recent>(recentList, searchCondition);

		return searchResult;
	}

	/**
	 * 마이 워크스페이스 검색
	 */
	public List<Recent> selectCollaboration(Map<String, String> map) {
		return recentDao.selectCollaboration(map);
	}

	/**
	 * 마이 카페 검색
	 */
	public List<Recent> selectCafe(Map<String, String> map) {
		return recentDao.selectCafe(map);
	}

	/**
	 * 마이 블로그 검색
	 */
	public List<Recent> selectMicroblog(Map<String, String> map) {
		return recentDao.selectMicroblog(map);
	}

	/**
	 * 마이 Follower 검색
	 */
	public SearchResult<Recent> selectFollower(RecentSearchCondition searchCondition) {

		SearchResult<Recent> searchResult = null;

		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(searchCondition.getPagePerRecord());

		List<Recent> recentList = recentDao.selectFollower(searchCondition);

		searchResult = new SearchResult<Recent>(recentList, searchCondition);

		return searchResult;
	}

	/**
	 * 마이 Following 검색
	 */
	public SearchResult<Recent> selectFollowing(RecentSearchCondition searchCondition) {

		SearchResult<Recent> searchResult = null;

		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(searchCondition.getPagePerRecord());

		List<Recent> recentList = recentDao.selectFollowing(searchCondition);

		searchResult = new SearchResult<Recent>(recentList, searchCondition);

		return searchResult;
	}

	/**
	 * 마이 친밀도 검색
	 */
	public SearchResult<Recent> selectIntimate(RecentSearchCondition searchCondition) {

		SearchResult<Recent> searchResult = null;

		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(searchCondition.getPagePerRecord());

		List<Recent> recentList = recentDao.selectIntimate(searchCondition);

		searchResult = new SearchResult<Recent>(recentList, searchCondition);

		return searchResult;
	}

	/**
	 * 마이 워크스페이스 멤버 검색
	 */
	public SearchResult<Recent> selectCollaborationMember(RecentSearchCondition searchCondition) {

		SearchResult<Recent> searchResult = null;

		searchCondition.setStartRowIndex(0);
		searchCondition.setEndRowIndex(searchCondition.getPagePerRecord());

		List<Recent> recentList = recentDao.selectCollaborationMember(searchCondition);

		searchResult = new SearchResult<Recent>(recentList, searchCondition);

		return searchResult;
	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

}
