package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPopupDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPopup;
import com.lgcns.ikep4.portal.admin.screen.search.PortalPopupSearchCondition;

/**
 * 
 * 포탈 팝업 Dao 구현 객체
 *
 * @author 임종상
 * @version $Id: PortalPopupDaoImpl.java 191 2011-06-09 05:19:29Z dev07 $
 */
@Repository("portalPopupDao")
public class PortalPopupDaoImpl extends GenericDaoSqlmap<PortalPopup, String> implements PortalPopupDao {

	/**
	 * 포탈 팝업 목록 카운트
	 * @param searchCondition
	 * @return
	 */
	public Integer countListPopup(PortalPopupSearchCondition searchCondition) {
		return (Integer) sqlSelectForObject("portal.admin.screen.popup.countListPopup", searchCondition);
	}
	
	/**
	 * 포탈 팝업 목록
	 * @param searchCondition
	 * @return
	 */
	public List<PortalPopup> listPopup(PortalPopupSearchCondition searchCondition) {
		return sqlSelectForList("portal.admin.screen.popup.listPopup", searchCondition);
	}
	
	/**
	 * 포탈 팝업 생성
	 * @param portalPopup
	 */
	public void createPopup(PortalPopup portalPopup) {
		sqlInsert("portal.admin.screen.popup.createPopup", portalPopup);
	}
	
	/**
	 * 포탈 팝업 조회
	 * @param popupId
	 */
	public PortalPopup getPopup(String popupId) {
		return (PortalPopup) sqlSelectForObject("portal.admin.screen.popup.getPopup", popupId);
	}
	
	/**
	 * 포탈 팝업 삭제
	 * @param popupId
	 */
	public void removePopup(String popupId) {
		sqlDelete("portal.admin.screen.popup.removePopup", popupId);
	}
	
	
	
	
	/**
	 * 포탈 팝업 수정
	 * @param portalPopup
	 */
	public void updatePopup(PortalPopup portalPopup) {
		sqlUpdate("portal.admin.screen.popup.updatePopup", portalPopup);
	}
	
	/**
	 * 사용하는 팝업 목록
	 * @return
	 */
	public List<PortalPopup> listPopupUse(String portalId, String userId) {
		HashMap<String, String> map = new HashMap<String, String>(); 
		map.put("portalId", portalId);
		map.put("userId", userId);
		
		return sqlSelectForList("portal.admin.screen.popup.listPopupUse", map);
	}
	
	@Deprecated
	public PortalPopup get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalPopup object) {
		return null;
	}

	@Deprecated
	public void update(PortalPopup object) {}

	@Deprecated
	public void remove(String id) {}
}

