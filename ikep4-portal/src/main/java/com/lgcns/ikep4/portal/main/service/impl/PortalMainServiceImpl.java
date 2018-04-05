/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDefPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDefPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;
import com.lgcns.ikep4.portal.main.model.PortalMain;
import com.lgcns.ikep4.portal.main.service.PortalMainService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 포탈 메인 Service 구현 클래스
 *
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalMainServiceImpl.java 19572 2012-07-02 04:46:38Z malboru80 $
 */
@Service("portalMainService")
public class PortalMainServiceImpl extends GenericServiceImpl<String, String> implements PortalMainService {

	@Autowired
	private PortalPageDao portalPageDao;
	
	@Autowired
	private PortalPortletDao portalPortletDao;
	
	@Autowired
	private PortalPortletCategoryDao portalPortletCategoryDao;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	@Autowired
	private PortalDefPortletConfigDao portalDefPortletConfigDao;
	
	@Autowired
	private PortalDefPageLayoutDao portalDefPageLayoutDao;
	
	@Autowired
	public PortalLayoutDao portalLayoutDao;
	
	@Autowired
	public PortalSystemDao portalSystemDao;

	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalThemeDao portalThemeDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 메인 포틀릿 조회 
	 * @param ownerId 소유자 ID
	 * @param localeCode 로케일 코드
	 * @param pageId 페이지 ID
	 * @param user 유저 객체
	 * @return PortalMain 모델객체
	 */
	public PortalMain readPortletMain(String ownerId, String localeCode, String pageId, User user) {
		
		PortalMain portalMain = new PortalMain();
		
		//페이지 조회
		PortalPage page = portalPageDao.getPage(pageId, "pageName", localeCode);
		
		if(page != null) {
			//공통 페이지 레이아웃 리스트
			List<PortalPageLayout> commonPageLayoutList = portalPageLayoutDao.listPageLayout(page.getPageId());

			//공통 active 포틀릿 리스트 조회
			if(commonPageLayoutList != null && commonPageLayoutList.size() > 0) {
				
				//공용 active 포틀릿 리스트 캐시에서 조회
				List<PortalPortlet> commonActivePortletList = (List<PortalPortlet>) cacheService.cacheCheck("commonPortlet");
				
				if(commonActivePortletList == null) {
					commonActivePortletList = portalPortletDao.listActivePortlet(page.getSystemCode(), commonPageLayoutList, user.getUserId(), user.getLocaleCode(), "portletName");
					
					// 캐시에 저장
					cacheService.addCacheElement("commonPortlet", commonActivePortletList);
				}
				
				portalMain.setCommonActivePortletList(commonActivePortletList);
				portalMain.setCommonPageLayoutList(commonPageLayoutList);
				
				Double commonMarginLeft = getMarginLeft(commonPageLayoutList);
				
				portalMain.setCommonMarginLeft(commonMarginLeft);
			}
			
			//owner 페이지 레이아웃 체크count
			int ownerPageLayoutCount = portalPageLayoutDao.countOwnerPageLayout(page.getPageId(), ownerId);
			
			if(ownerPageLayoutCount == 0) {
				//디폴트 레이아웃 리스트 조회
				List<PortalDefPageLayout> defPageLayoutList = portalDefPageLayoutDao.listDefPageLayout(page.getPageId());
				
				//페이지 레이아웃 생성
				PortalPageLayout createPortalPageLayout = new PortalPageLayout();
				createPortalPageLayout.setRegisterId(user.getUserId());
				createPortalPageLayout.setRegisterName(user.getUserName());
				createPortalPageLayout.setUpdaterId(user.getUserId());
				createPortalPageLayout.setUpdaterName(user.getUserName());
				
				for(int i = 0; i < defPageLayoutList.size(); i++) {
					PortalDefPageLayout portalDefPageLayoutTemp = defPageLayoutList.get(i);
					
					createPortalPageLayout.setPageLayoutId(idgenService.getNextId());
					createPortalPageLayout.setPageId(page.getPageId());
					createPortalPageLayout.setOwnerId(ownerId);
					createPortalPageLayout.setWidth(portalDefPageLayoutTemp.getWidth());
					createPortalPageLayout.setLayoutId(portalDefPageLayoutTemp.getLayoutId());
					createPortalPageLayout.setColIndex(portalDefPageLayoutTemp.getColIndex());
					
					portalPageLayoutDao.createPageLayout(createPortalPageLayout);
					
					//레이아웃별 디폴트 포틀릿 리스트 조회
					List<PortalDefPortletConfig> defPageLayoutPortletList = portalDefPortletConfigDao.listDefPageLayoutPortlet(portalDefPageLayoutTemp.getDefaultPageLayoutId(), user.getUserId());
					
					//포틀릿config 생성
					PortalPortletConfig createPortletConfig = new PortalPortletConfig();
					createPortletConfig.setRegisterId(user.getUserId());
					createPortletConfig.setRegisterName(user.getUserName());
					createPortletConfig.setUpdaterId(user.getUserId());
					createPortletConfig.setUpdaterName(user.getUserName());
					
					for(int j = 0; j < defPageLayoutPortletList.size(); j++) {
						PortalDefPortletConfig defPortletConfigTemp = defPageLayoutPortletList.get(j);
						
						createPortletConfig.setPortletConfigId(idgenService.getNextId());
						createPortletConfig.setPageLayoutId(createPortalPageLayout.getPageLayoutId());
						createPortletConfig.setPortletId(defPortletConfigTemp.getPortletId());
						createPortletConfig.setColIndex(defPortletConfigTemp.getColIndex());
						createPortletConfig.setRowIndex(j+1);
						createPortletConfig.setViewMode(defPortletConfigTemp.getViewMode());
						
						portalPortletConfigDao.createPortletConfig(createPortletConfig);
					}
				}
			}
			
			//owner 페이지 레이아웃 리스트
			List<PortalPageLayout> pageLayoutList = portalPageLayoutDao.listOwnerPageLayout(page.getPageId(), ownerId);
			
			Double marginLeft = getMarginLeft(pageLayoutList);
			
			portalMain.setMarginLeft(marginLeft);
			
			List<PortalPortlet> activePortletList = null;
			List<PortalPortlet> activePortletNomoveList = null;
			
			//active 포틀릿 리스트
			if(pageLayoutList.size() > 0) {
				
				//active 포틀릿 리스트 캐시에서 조회
				activePortletList = (List<PortalPortlet>) cacheService.cacheCheck("userPortlet");
				
				if(activePortletList == null) {
					activePortletList = portalPortletDao.listActivePortlet(page.getSystemCode(), pageLayoutList, user.getUserId(), user.getLocaleCode(), "portletName");
					
					// 캐시에 저장
					cacheService.addCacheElement("userPortlet", activePortletList);
				}
				
				//active 포틀릿 리스트 캐시에서 조회
				activePortletNomoveList = (List<PortalPortlet>) cacheService.cacheCheck("userNomovePortlet");
				
				if(activePortletNomoveList == null) {
					activePortletNomoveList = portalPortletDao.listActiveNomovePortlet(page.getSystemCode(), pageLayoutList, user.getUserId(), user.getLocaleCode(), "portletName");
					
					// 캐시에 저장
					cacheService.addCacheElement("userNomovePortlet", activePortletNomoveList);
				}
				
				portalMain.setUserLayoutId(pageLayoutList.get(0).getLayoutId());
			}
			
			//포틀릿 전체 리스트 캐시에서 조회
			List<PortalPortlet> portletList = (List<PortalPortlet>) cacheService.cacheCheck("portlet");
			
			//포탈메인 포틀릿 전체 리스트
			if(portletList == null) {
				portletList = portalPortletDao.listPortletCommonCheck(page.getSystemCode(), user.getUserId(), user.getLocaleCode(), "portletName");
				
				// 캐시에 저장
				cacheService.addCacheElement("portlet", portletList);
			}
			
			//포틀릿 카테고리 리스트
			List<PortalPortletCategory> portletCategoryList = portalPortletCategoryDao.listPortletCategory(page.getSystemCode());
			
			//테마 리스트 조회
			PortalTheme portalTheme = new PortalTheme();
			portalTheme.setPortalId(user.getPortalId());
			
			List<PortalTheme> themeList = portalThemeDao.listTheme(portalTheme);
			
			List<PortalLayout> layoutList = portalLayoutDao.listLayoutCommonCheck(0);
			
			portalMain.setPageLayoutList(pageLayoutList);
			portalMain.setActivePortletList(activePortletList);
			//portalMain.setActivePortletNomoveList(activePortletNomoveList);
			portalMain.setPortletList(portletList);
			portalMain.setPortletCategoryList(portletCategoryList);
			portalMain.setThemeList(themeList);
			portalMain.setLayoutList(layoutList);
			
			portalMain.setPage(page);
		}
		
		return portalMain;
	}
	
	/**
	 * 공통 레이아웃 왼쪽 마진, 레이아웃 왼쪽 마진 
	 * @param commonPageLayoutList 레이아웃 리스트
	 * @return
	 */
	private Double getMarginLeft(List<PortalPageLayout> pageLayoutList) {
		Double marginLeft = 0.0;
		
		if(pageLayoutList.size() == 2) {
			marginLeft = 1.0;
		} else if(pageLayoutList.size() == 3) {
			marginLeft = 1.25;
		}
		
		return marginLeft;
	}
}
