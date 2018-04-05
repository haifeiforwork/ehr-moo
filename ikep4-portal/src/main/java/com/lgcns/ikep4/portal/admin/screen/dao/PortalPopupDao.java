package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPopup;
import com.lgcns.ikep4.portal.admin.screen.search.PortalPopupSearchCondition;

/**
 * 포탈 팝업 Dao
 * 
 *
 * @author 임종상
 * @version $Id: PortalPopupDao.java 191 2011-06-09 05:19:29Z dev07 $
 */
public interface PortalPopupDao extends GenericDao<PortalPopup, String> {
	
	/**
	 * 포탈 팝업 목록 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countListPopup(PortalPopupSearchCondition searchCondition);
	
	/**
	 * 포탈 팝업 목록
	 * @param searchCondition
	 * @return
	 */
	public List<PortalPopup> listPopup(PortalPopupSearchCondition searchCondition);
	
	/**
	 * 포탈 팝업 생성
	 * @param portalPopup
	 */
	public void createPopup(PortalPopup portalPopup);
	
	/**
	 * 포탈 팝업 조회
	 * @param popupId
	 */
	public PortalPopup getPopup(String popupId);
	
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
	 * @return
	 */
	public List<PortalPopup> listPopupUse(String portalId, String userId);
}