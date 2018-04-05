package com.lgcns.ikep4.support.personal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.favorite.util.FavoriteUtil;
import com.lgcns.ikep4.support.personal.dao.PersonalDao;
import com.lgcns.ikep4.support.personal.model.Personal;
import com.lgcns.ikep4.support.personal.model.PersonalSearchCondition;
import com.lgcns.ikep4.support.personal.service.PersonalService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * Personal 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PersonalServiceImpl.java 18402 2012-04-29 12:24:41Z icerainbow $
 */
@Service
public class PersonalServiceImpl extends GenericServiceImpl<Personal, String> implements PersonalService {

	/**
	 * Personal Dao
	 */
	@Autowired
	private PersonalDao personalDao;

	/**
	 * 도큐먼트 리스트 검색
	 */
	public SearchResult<Personal> getAllForDocument(PersonalSearchCondition searchCondition) {

		// Integer count =
		// personalDao.countBySearchConditionForDocument(searchCondition);
		// searchCondition.terminateSearchCondition(count);

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<Personal> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<Personal> personalList = personalDao.listBySearchConditionForDocument(searchCondition);

		if (personalList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			for (Personal personal : personalList) {
				if (!user.getLocaleCode().equals("ko")) {
					personal.setRegisterName(personal.getRegisterEnglishName());
				}
				personal.setTargetUrl(FavoriteUtil.makeTargetUrl(personal.getModuleEn(), personal.getType()));
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(personalList.size());

			searchResult = new SearchResult<Personal>(personalList, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 파일 리스트 검색
	 */
	public SearchResult<Personal> getAllForFile(PersonalSearchCondition searchCondition) {

		// Integer count =
		// personalDao.countBySearchConditionForFile(searchCondition);
		// searchCondition.terminateSearchCondition(count);

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<Personal> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<Personal> personalList = personalDao.listBySearchConditionForFile(searchCondition);

		if (personalList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			for (Personal personal : personalList) {
				if (!user.getLocaleCode().equals("ko")) {
					personal.setRegisterName(personal.getRegisterEnglishName());
				}
				personal.setTargetUrl(FavoriteUtil.makeTargetUrl(personal.getModuleEn(), personal.getType()));
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(personalList.size());

			searchResult = new SearchResult<Personal>(personalList, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 코멘트 리스트 검색
	 */
	public SearchResult<Personal> getAllForComment(PersonalSearchCondition searchCondition) {
		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<Personal> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<Personal> personalList = personalDao.listBySearchConditionForComment(searchCondition);

		if (personalList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			for (Personal personal : personalList) {
				if (!user.getLocaleCode().equals("ko")) {
					personal.setRegisterName(personal.getRegisterEnglishName());
					personal.setLinereplyRegisterName(personal.getLinereplyRegisterEnglishName());
				}
				personal.setTargetUrl(FavoriteUtil.makeTargetUrl(personal.getModuleEn(), personal.getType()));
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(personalList.size());

			searchResult = new SearchResult<Personal>(personalList, searchCondition);
		}

		return searchResult;
	}
	
	/**
	 * 나의 갤러리 파일 리스트 검색
	 */
	public SearchResult<Personal> getMyGallery(PersonalSearchCondition searchCondition,String targetUserId) {

		// Integer count =
		// personalDao.countBySearchConditionForFile(searchCondition);
		// searchCondition.terminateSearchCondition(count);

		User user = (User) this.getRequestAttribute("ikep.user");

		SearchResult<Personal> searchResult = null;

		searchCondition.setStartRowIndex(searchCondition.getCurrentCount());
		searchCondition.setEndRowIndex(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());

		List<Personal> personalList = personalDao.listBySearchConditionForMyGallery(searchCondition);

		if (personalList == null) {
			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(0);
		} else {

			for (Personal personal : personalList) {
				if (!user.getLocaleCode().equals("ko")) {
					personal.setRegisterName(personal.getRegisterEnglishName());
				}
				//personal.setTargetUrl(FavoriteUtil.makeTargetUrl(personal.getModuleEn(), personal.getType()));
				personal.setTargetUrl("/lightpack/gallery/boardItem/readBoardItemView.do?targetUserId="+targetUserId+"&docPopup=true&itemId=");
			}

			searchCondition.setCurrentCount(searchCondition.getCurrentCount() + searchCondition.getPagePerRecord());
			searchCondition.setRecordCount(personalList.size());

			searchResult = new SearchResult<Personal>(personalList, searchCondition);
		}

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
