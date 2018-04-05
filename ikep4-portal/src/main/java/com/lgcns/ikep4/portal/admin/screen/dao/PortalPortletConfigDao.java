package com.lgcns.ikep4.portal.admin.screen.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;

/**
 * 포틀릿 config DAO 정의
 * 
 *
 * @author 임종상
 * @version $Id: PortalPortletConfigDao.java 19098 2012-06-04 08:44:50Z malboru80 $
 */
public interface PortalPortletConfigDao extends GenericDao<PortalPortletConfig, String> {
	
	/**
	 * 포틀릿 config 생성
	 * @param portalPortletConfig 포틀릿 config 모델
	 */
	public void createPortletConfig(PortalPortletConfig portalPortletConfig);
	
	/**
	 * 포틀릿config 수정
	 * @param portalPortletConfig 포틀릿 config 모델
	 */
	public void updatePortletConfig(PortalPortletConfig portalPortletConfig);
	
	/**
	 * 포틀릿 config 삭제
	 * @param portalPortletConfig 포틀릿 config 모델
	 */
	public void removePortletConfig(PortalPortletConfig portalPortletConfig);
	
	/**
	 * 포틀릿 Config 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @return PortalPortletConfig 포틀릿 config 정보
	 */
	public PortalPortletConfig getPortletConfig(String portletConfigId);
	
	/**
	 * 포틀릿 config rowIndex 수정
	 * @param portletConfigId 포틀릿 config ID
	 * @param rowIndex 행 인덱스
	 */
	public void updatePortletConfigRowIndex(String portletConfigId, int rowIndex);
	
	/**
	 * 포틀릿 config viewMode 수정
	 * @param portalPortletConfig 포틀릿 config 모델
	 */
	public void updatePortletConfigViewMode(PortalPortletConfig portalPortletConfig);
	
	/**
	 * 페이지 레이아웃 포틀릿 config 삭제
	 * @param pageLayoutId 페이지 레이아웃 ID
	 */
	public void removePageLayoutPortletConfig(String pageLayoutId);
	
	/**
	 * 전체 페이지 레이아웃 포틀릿 삭제
	 * @param pageLayoutList 페이지 레이아웃 리스트
	 */
	public void removePageLayoutPortletConfigAll(List<PortalPageLayout> pageLayoutList);
	
	/**
	 * 포틀릿 config삭제
	 * @param portletId 포틀릿 config ID
	 */
	public void removePortletConfigAll(String portletId);
	
	/**
	 * 레이아웃 변경시 이동해야될 포틀릿 config 목록
	 * @param ownerId
	 * @param pageId
	 * @param colIndex
	 * @return
	 */
	public List<PortalPortletConfig> listLayoutMovePortletConfig(String ownerId, String pageId, Integer colIndex);
	
	/**
	 * 페이지 레이아웃 Max RowIndex 조회
	 * @param pageLayoutId
	 * @return
	 */
	public Integer pageLayoutMaxRowIndex(String pageLayoutId);
}
