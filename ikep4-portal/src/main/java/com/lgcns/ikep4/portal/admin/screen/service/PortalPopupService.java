package com.lgcns.ikep4.portal.admin.screen.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPopup;
import com.lgcns.ikep4.portal.admin.screen.search.PortalPopupSearchCondition;

/**
 * 
 * 포탈 팝업 Service
 *
 * @author 임종상
 * @version $Id: PortalPopupService.java 191 2011-06-09 05:19:29Z dev07 $
 */
@Transactional
public interface PortalPopupService extends GenericService<PortalPopup, String> {
	
	/**
	 * 포탈 팝업 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<PortalPopup> listPopup(PortalPopupSearchCondition searchCondition);
	
	/**
	 * 포탈 팝업 생성
	 * @param portalPopup
	 */
	public void createPopup(PortalPopup portalPopup);
	
	/**
	 * 포탈 팝업 조회
	 * @param popupId
	 */
	public PortalPopup readPopup(String popupId);
	
	/**
	 * 포탈 팝업 삭제
	 * @param popupId
	 */
	public void removePopup(String popupId);
	
	/**
	 * 포탈 팝업 수정
	 * @param portalPopup
	 */
	public void updatePopup(PortalPopup portalPopup);
	
	/**
	 * 사용하는 팝업 목록
	 * @param portalId
	 */
	public List<PortalPopup> listPopupUse(String portalId, String userId);
}