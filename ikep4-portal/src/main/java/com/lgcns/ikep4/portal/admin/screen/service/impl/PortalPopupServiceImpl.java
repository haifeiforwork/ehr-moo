package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPopupDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPopup;
import com.lgcns.ikep4.portal.admin.screen.search.PortalPopupSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPopupService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 포탈 팝업 Service 구현 객체
 * 
 *
 * @author 임종상
 * @version $Id: PortalPopupServiceImpl.java 191 2011-06-09 05:19:29Z dev07 $
 */
@Service("portalPopupService")
public class PortalPopupServiceImpl extends GenericServiceImpl<PortalPopup, String> implements PortalPopupService {

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private PortalPopupDao portalPopupDao;
	
	@Autowired
	private PortalSecurityService portalSecurityService;
	
	/**
	 * 포탈 팝업 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<PortalPopup> listPopup(PortalPopupSearchCondition searchCondition) {
		Integer count = portalPopupDao.countListPopup(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<PortalPopup> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<PortalPopup>(searchCondition);

		} else {

			List<PortalPopup> list = portalPopupDao.listPopup(searchCondition);

			searchResult = new SearchResult<PortalPopup>(list, searchCondition);
		}

		return searchResult;
	}
	
	/**
	 * 포탈 팝업 생성
	 * @param portalPopup
	 */
	public void createPopup(PortalPopup portalPopup) {
		portalPopup.setPopupId(idgenService.getNextId());
		
		portalPopupDao.createPopup(portalPopup);
		
		//리소스명에 게시물 key를 넣어줍니다
		portalPopup.getSecurity().setResourceName(portalPopup.getPopupId());
		// 게시물명으로 Security Description  생성
		portalPopup.getSecurity().setResourceDescription(portalPopup.getPopupName());

		portalSecurityService.createSystemPermission(portalPopup.getSecurity());
	}

	/**
	 * 포탈 팝업 조회
	 * @param popupId
	 */
	public PortalPopup readPopup(String popupId) {
		return portalPopupDao.getPopup(popupId);
	}

	/**
	 * 포탈 팝업 삭제
	 * @param popupId
	 */
	public void removePopup(String popupId) {
		portalPopupDao.removePopup(popupId);
	}
	
	/**
	 * 포탈 팝업 수정
	 * @param portalPopup
	 */
	public void updatePopup(PortalPopup portalPopup) {
		portalPopupDao.updatePopup(portalPopup);
		
		//리소스명에 게시물 key를 넣어줍니다
		portalPopup.getSecurity().setResourceName(portalPopup.getPopupId());
		// 게시물명으로 Security Description  생성
		portalPopup.getSecurity().setResourceDescription(portalPopup.getPopupName());

		portalSecurityService.updateSystemPermissionAndResource(portalPopup.getSecurity());
	}
	
	/**
	 * 사용하는 팝업 목록
	 * @param portalId
	 */
	public List<PortalPopup> listPopupUse(String portalId, String userId) {
		return portalPopupDao.listPopupUse(portalId, userId);
	}
}