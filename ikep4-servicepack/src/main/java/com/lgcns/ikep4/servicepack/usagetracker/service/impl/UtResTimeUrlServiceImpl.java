package com.lgcns.ikep4.servicepack.usagetracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtResTimeUrlDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeUrl;
import com.lgcns.ikep4.servicepack.usagetracker.search.UtSearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeUrlService;

@Service
@Transactional
public class UtResTimeUrlServiceImpl extends GenericServiceImpl<UtResTimeUrl, String> implements UtResTimeUrlService {

	@Autowired
	private UtResTimeUrlDao utResTimeUrlDao;
	
	/**
	 * 응답시간 URL 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<UtResTimeUrl> listBySearchCondition(UtSearchCondition searchCondition) {
		SearchResult<UtResTimeUrl> searchResult = null;
		
		Integer count = this.utResTimeUrlDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<UtResTimeUrl>(searchCondition);
		} else {
			List<UtResTimeUrl> resTimeUrlList = utResTimeUrlDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<UtResTimeUrl>(resTimeUrlList, searchCondition);
		}
		
		return searchResult;
	}
	
	/**
	 * 응답시간 URL 목록
	 * @param portalId
	 * @return
	 */
	public List<UtResTimeUrl> listResTimeUrl(String portalId) {
		List<UtResTimeUrl> resTimeUrlList = utResTimeUrlDao.listResTimeUrl(portalId);
		
		return resTimeUrlList;
	}

	/**
	 * 선택된것 삭제
	 * @param resTimeUrlId
	 */
	public void removeCheckedAll(String[] resTimeUrlId) {
		for (int i = 0; i < resTimeUrlId.length; i++) {
			utResTimeUrlDao.remove(resTimeUrlId[i]);
		}
	}
	
	/**
	 * 상세
	 */
	@Override
	public UtResTimeUrl read(String id) {
		return utResTimeUrlDao.get(id);
	}

	/**
	 * URL존재여부
	 * @param resTimeUrl
	 */
	public boolean existsURL(String resTimeUrl) {
		return utResTimeUrlDao.existsURL(resTimeUrl);
	}

	/**
	 * 응답시간 URL 추가
	 */
	@Override
	public String create(UtResTimeUrl utResTimeUrl) {
		return utResTimeUrlDao.create(utResTimeUrl);
	}
	
	/**
	 * 수정
	 */
	@Override
	public void update(UtResTimeUrl utResTimeUrl) {
		utResTimeUrlDao.update(utResTimeUrl);
	}

	/**
	 * 등록된 URL, 사용여부 체크 조회
	 * @param utResTimeUrl
	 * @return
	 */
	public UtResTimeUrl getResTimeUrl(UtResTimeUrl utResTimeUrl) {
		return utResTimeUrlDao.getResTimeUrl(utResTimeUrl);
	}
}