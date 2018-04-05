package com.lgcns.ikep4.portal.admin.screen.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;

/**
 * 포틀릿 셋팅 DAO 구현 클래스
 * 
 * @author 임종상
 * @version $Id: PortalPortletConfigDaoImpl.java 19098 2012-06-04 08:44:50Z malboru80 $
 */
@Repository("portalPortletConfigDao")
public class PortalPortletConfigDaoImpl extends GenericDaoSqlmap<PortalPortletConfig, String> implements
		PortalPortletConfigDao {

	/**
	 * 포틀릿 config 생성
	 * @param portalPortletConfig 포틀릿 config 모델
	 */
	public void createPortletConfig(PortalPortletConfig portalPortletConfig) {
		sqlInsert("portal.admin.screen.portalPortletConfig.createPortletConfig", portalPortletConfig);
	}

	/**
	 * 포틀릿config 수정
	 * @param portalPortletConfig 포틀릿 config 모델
	 */
	public void updatePortletConfig(PortalPortletConfig portalPortletConfig) {
		sqlUpdate("portal.admin.screen.portalPortletConfig.updatePortletConfig", portalPortletConfig);
	}

	/**
	 * 포틀릿 config 삭제
	 * @param portalPortletConfig 포틀릿 config 모델
	 */
	public void removePortletConfig(PortalPortletConfig portalPortletConfig) {
		sqlDelete("portal.admin.screen.portalPortletConfig.removePortletConfig", portalPortletConfig);
	}
	
	/**
	 * 포틀릿 Config 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortalPortletConfig 포틀릿 config 정보
	 */
	public PortalPortletConfig getPortletConfig(String portletConfigId) {
		return (PortalPortletConfig) sqlSelectForObject("portal.admin.screen.portalPortletConfig.getPortletConfig", portletConfigId);
	}
	
	/**
	 * 포틀릿 config rowIndex 수정
	 * @param portletConfigId 포틀릿 config ID
	 * @param rowIndex 행 인덱스
	 */
	public void updatePortletConfigRowIndex(String portletConfigId, int rowIndex) {
		PortalPortletConfig portalPortletConfig = new PortalPortletConfig();
		
		portalPortletConfig.setPortletConfigId(portletConfigId);
		portalPortletConfig.setRowIndex(rowIndex);
		
		sqlUpdate("portal.admin.screen.portalPortletConfig.updatePortletConfigRowIndex", portalPortletConfig);
	}
	
	/**
	 * 포틀릿 config viewMode 수정
	 * @param portalPortletConfig 포틀릿 config 모델
	 */
	public void updatePortletConfigViewMode(PortalPortletConfig portalPortletConfig) {
		sqlUpdate("portal.admin.screen.portalPortletConfig.updatePortletConfigViewMode", portalPortletConfig);
	}
	
	/**
	 * 페이지 레이아웃 포틀릿 config 삭제
	 * @param pageLayoutId 페이지 레이아웃 ID
	 */
	public void removePageLayoutPortletConfig(String pageLayoutId) {
		sqlDelete("portal.admin.screen.portalPortletConfig.removePageLayoutPortletConfig", pageLayoutId);
	}
	
	/**
	 * 전체 페이지 레이아웃 포틀릿 삭제
	 * @param pageLayoutList 페이지 레이아웃 리스트
	 */
	public void removePageLayoutPortletConfigAll(List<PortalPageLayout> pageLayoutList) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pageLayoutList", pageLayoutList);
		
		sqlDelete("portal.admin.screen.portalPortletConfig.removePageLayoutPortletConfigAll", map);
	}
	
	/**
	 * 포틀릿 config삭제
	 * @param portletId 포틀릿 config ID
	 */
	public void removePortletConfigAll(String portletId) {
		sqlDelete("portal.admin.screen.portalPortletConfig.removePortletConfigAll", portletId);
	}
	
	/**
	 * 레이아웃 변경시 이동해야될 포틀릿 config 목록
	 * @param ownerId
	 * @param pageId
	 * @param colIndex
	 * @return
	 */
	public List<PortalPortletConfig> listLayoutMovePortletConfig(String ownerId, String pageId, Integer colIndex) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ownerId", ownerId);
		map.put("pageId", pageId);
		map.put("colIndex", colIndex);
		
		return sqlSelectForList("portal.admin.screen.portalPortletConfig.listLayoutMovePortletConfig", map);
	}
	
	public Integer pageLayoutMaxRowIndex(String pageLayoutId) {
		Integer maxRowIndex = (Integer) this.sqlSelectForObject("portal.admin.screen.portalPortletConfig.pageLayoutMaxRowIndex", pageLayoutId);

		if(maxRowIndex == null) {
			maxRowIndex = 0;
		}
		
		return maxRowIndex;
	}
	
	@Deprecated
	public PortalPortletConfig get(String id) {
		return null;
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public String create(PortalPortletConfig object) {
		return null;
	}

	@Deprecated
	public void update(PortalPortletConfig object) {}

	@Deprecated
	public void remove(String id) {}
}
