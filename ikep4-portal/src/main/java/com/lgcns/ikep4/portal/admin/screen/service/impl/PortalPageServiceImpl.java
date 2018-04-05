package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDefPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalDefPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletCategoryDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPageService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSecurityService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemUrlService;
import com.lgcns.ikep4.portal.main.model.PortalMain;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 포탈 페이지 Service 구현 클래스
 * 
 * @author 임종상
 * @version $Id: PortalPageServiceImpl.java 4086 2011-03-25 00:30:46Z
 *          limjongsang $
 */
@Service("portalPageService")
public class PortalPageServiceImpl extends GenericServiceImpl<PortalPage, String> implements PortalPageService {

	/**
	 *
	 */
	@Autowired
	private PortalPageDao portalPageDao;
	
	@Autowired
	private PortalPortletDao portalPortletDao;
	
	@Autowired
	private PortalPortletCategoryDao portalPortletCategoryDao;
	
	@Autowired
	public PortalLayoutDao portalLayoutDao;
	
	@Autowired
	public PortalSystemDao portalSystemDao;

	@Autowired
	private I18nMessageService i18nMessageService;

	@Autowired
	private PortalDefPageLayoutDao portalDefPageLayoutDao;
	
	@Autowired
	private PortalDefPortletConfigDao portalDefPortletConfigDao;
	
	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;

	@Autowired
	private PortalSystemUrlService portalSystemUrlService;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private PortalSecurityService portalSecurityService;

	/**
	 * 페이지 등록
	 * @param i18nMessageList 페이지 다국어 리스트
	 * @param portalPage 포탈 페이지 모델
	 * @param i18nMessageSystemUrlList 시스템 URL 다국어 리스트
	 * @param portalSystemUrl 시스템 URL 모델
	 */
	public void createPage(List<I18nMessage> i18nMessageList, PortalPage portalPage, List<I18nMessage> i18nMessageSystemUrlList, PortalSystemUrl portalSystemUrl) {
		// 다국어정보 저장
		i18nMessageService.create(i18nMessageList);

		// 페이지 등록
		portalPageDao.createPage(portalPage);

		// 다국어정보 저장(시스템 URL), 시스템 URL 등록
		portalSystemUrl.setI18nMessageList(i18nMessageSystemUrlList);
		
		portalSystemUrlService.create(portalSystemUrl);

		//리소스명에 게시물 key를 넣어줍니다
		portalPage.getSecurity().setResourceName(portalPage.getPageId());
		// 게시물명으로 Security Description  생성
		portalPage.getSecurity().setResourceDescription(portalPage.getPageName());

		portalSecurityService.createSystemPermission(portalPage.getSecurity());
	}
	
	/**
	 * 페이지 수정
	 * @param i18nMessageList 페이지이름 다국어 리스트
	 * @param portalPage 포탈 페이지 모델
	 * @param i18nMessageSystemUrlList 시스템 URL 다국어 리스트
	 * @param portalSystemUrl 시스템 URL 모델
	 * @param systemAddFlag 시스템 URL 저장여부 플래그
	 */
	public void updatePage(List<I18nMessage> i18nMessageList, PortalPage portalPage, List<I18nMessage> i18nMessageSystemUrlList, PortalSystemUrl portalSystemUrl, boolean systemAddFlag) {
		
		// 다국어정보를 저장한다.
		i18nMessageService.update(i18nMessageList);
		
		// 페이지 수정
		portalPageDao.updatePage(portalPage);

		portalSystemUrl.setI18nMessageList(i18nMessageSystemUrlList);
		
		// 시스템 URL 수정
		if (systemAddFlag) {
			// 시스템 URL이 없으면 등록
			portalSystemUrlService.create(portalSystemUrl);
		} else {
			// 시스템 URL이 있으면 업데이트
			portalSystemUrlService.update(portalSystemUrl);
		}
		
		//리소스명에 게시물 key를 넣어줍니다
		portalPage.getSecurity().setResourceName(portalPage.getPageId());
		// 게시물명으로 Security Description  생성
		portalPage.getSecurity().setResourceDescription(portalPage.getPageName());

		portalSecurityService.updateSystemPermissionAndResource(portalPage.getSecurity());
	}
	
	/**
	 * 페이지 조회
	 * @param pageId 페이지 ID
	 * @param localeCode 로케일 코드
	 * @return PortalPage 페이지 정보
	 */
	public PortalPage readPage(String pageId, String localeCode) {
		// 메인 페이지 조회
		PortalPage portalPage = portalPageDao.getPage(pageId, "pageName", localeCode);

		return portalPage;
	}

	/**
	 * 페이지 삭제
	 * @param pageId 페이지 ID
	 */
	public void removePage(String pageId) {
		
		//시스템 ID 조회
		String systemUrlId = portalSystemUrlService.readSystemUrlId("/portal/main/body.do?pageId="+pageId);
		
		if(!StringUtil.isEmpty(systemUrlId)) {
			//시스템 URL 삭제
			portalSystemUrlService.delete(systemUrlId);
		}
		
		// 다국어 메세지 삭제
		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, pageId);

		// 페이지 삭제
		portalPageDao.removePage(pageId);

		// 페이지 권한 삭제
		portalSecurityService.deleteSystemPermission("Portal-Page", pageId);
	}

	/**
	 * 페이지 목록
	 * @param searchCondition 검색 조건
	 * @return SearchResult<PortalPage> 페이지 리스트
	 */
	public SearchResult<PortalPage> listPage(PortalPageSearchCondition searchCondition) {

		Integer count = portalPageDao.countPage(searchCondition);
		searchCondition.terminateSearchCondition(count);

		SearchResult<PortalPage> searchResult = null;

		if (searchCondition.isEmptyRecord()) {

			searchResult = new SearchResult<PortalPage>(searchCondition);
		} else {

			List<PortalPage> pageList = portalPageDao.listPage(searchCondition);

			searchResult = new SearchResult<PortalPage>(pageList, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 디폴트 레이아웃별 포틀릿 등록
	 * @param pageId 페이지 ID
	 * @param defLayoutDataList 디폴트 레이아웃 데이타
	 * @param defPortletDataList 디폴트 포틀릿 데이타
	 * @param commonLayoutDataList 공통 레이아웃 데이타
	 * @param commonPortletDataList 공통 포틀릿 데이타
	 * @param user 유저 객체
	 */
	public void createPageDefLayoutPortlet(String pageId, List<PortalDefPageLayout> defLayoutDataList, List<PortalDefPortletConfig> defPortletDataList, List<PortalPageLayout> commonLayoutDataList, List<PortalPortletConfig> commonPortletDataList, User user) {
		
		//기존 데이터 삭제
		List<PortalPageLayout> commonPageLayoutList = portalPageLayoutDao.listPageLayout(pageId);
		if(commonPageLayoutList != null && commonPageLayoutList.size() > 0) {
			//공용 포틀릿 삭제
			portalPortletConfigDao.removePageLayoutPortletConfigAll(commonPageLayoutList);
			
			//공용 레이아웃 삭제
			portalPageLayoutDao.removePageLayoutAll(commonPageLayoutList);
		}
		
		List<PortalDefPageLayout> defPageLayoutList = portalDefPageLayoutDao.listDefPageLayout(pageId);
		
		if(defPageLayoutList != null && defPageLayoutList.size() > 0) {
			//디폴트 포틀릿 삭제
			portalDefPortletConfigDao.removeDefPortletAll(defPageLayoutList);
			
			//디폴트 페이지 레이아웃 삭제
			portalDefPageLayoutDao.removeDefPageLayout(pageId);
		}
		
		String[] commonPageLayoutIds = new String[commonLayoutDataList.size()];
		
		//공용 페이지 레이아웃 등록
		for(int i = 0; i < commonLayoutDataList.size(); i++) {
			PortalPageLayout createCommonPageLayout = commonLayoutDataList.get(i);
			String commonPageLayoutId = idgenService.getNextId();
			
			createCommonPageLayout.setPageLayoutId(commonPageLayoutId);
			createCommonPageLayout.setOwnerId(user.getUserId());
			createCommonPageLayout.setRegisterId(user.getUserId());
			createCommonPageLayout.setRegisterName(user.getUserName());
			createCommonPageLayout.setUpdaterId(user.getUserId());
			createCommonPageLayout.setUpdaterName(user.getUserName());
			
			portalPageLayoutDao.createPageLayout(createCommonPageLayout);
			
			commonPageLayoutIds[createCommonPageLayout.getColIndex()-1] = commonPageLayoutId;
		}
		
		//공용 포틀릿 등록
		for(int i = 0; i < commonPortletDataList.size(); i++) {
			PortalPortletConfig createCommonPortletConfig = commonPortletDataList.get(i);
			
			createCommonPortletConfig.setPortletConfigId(idgenService.getNextId());
			createCommonPortletConfig.setPageLayoutId(commonPageLayoutIds[createCommonPortletConfig.getColIndex()-1]);
			createCommonPortletConfig.setRegisterId(user.getUserId());
			createCommonPortletConfig.setRegisterName(user.getUserName());
			createCommonPortletConfig.setUpdaterId(user.getUserId());
			createCommonPortletConfig.setUpdaterName(user.getUserName());
			
			portalPortletConfigDao.createPortletConfig(createCommonPortletConfig);
		}
		
		String[] defPageLayoutIds = new String[defLayoutDataList.size()];
		
		//디폴트 페이지 레이아웃 등록
		for(int i = 0; i < defLayoutDataList.size(); i++) {
			PortalDefPageLayout createDefPageLayout = defLayoutDataList.get(i);
			String defaultPageLayoutId = idgenService.getNextId();
			
			createDefPageLayout.setDefaultPageLayoutId(defaultPageLayoutId);
			createDefPageLayout.setRegisterId(user.getUserId());
			createDefPageLayout.setRegisterName(user.getUserName());
			createDefPageLayout.setUpdaterId(user.getUserId());
			createDefPageLayout.setUpdaterName(user.getUserName());
			
			portalDefPageLayoutDao.createDefPageLayout(createDefPageLayout);
			
			defPageLayoutIds[createDefPageLayout.getColIndex()-1] = defaultPageLayoutId;
		}
		
		//디폴트 포틀릿 등록
		for(int i = 0; i < defPortletDataList.size(); i++) {
			PortalDefPortletConfig createDefPortletConfig = defPortletDataList.get(i);
			
			createDefPortletConfig.setDefaultPortletConfigId(idgenService.getNextId());
			createDefPortletConfig.setDefaultPageLayoutId(defPageLayoutIds[createDefPortletConfig.getColIndex()-1]);
			createDefPortletConfig.setRegisterId(user.getUserId());
			createDefPortletConfig.setRegisterName(user.getUserName());
			createDefPortletConfig.setUpdaterId(user.getUserId());
			createDefPortletConfig.setUpdaterName(user.getUserName());	
			
			portalDefPortletConfigDao.createDefPortlet(createDefPortletConfig);
		}
	}
	
	
	/**
	 * 메인 포틀릿 디폴트 설정 조회
	 * @param ownerId 소유자 ID
	 * @param localeCode 로케일 코드
 	 * @param pageId 페이지 ID
	 * @param user 유저 객체
	 * @return PortalMain 포탈 디폴트  메인 정보
	 */
	public PortalMain readPortletDefaultMain(String ownerId, String localeCode, String pageId, User user,String portalId) {
		
		PortalMain portalDefaultMain = new PortalMain();
		
		//페이지 조회
		PortalPage page = portalPageDao.getPage(pageId, "pageName", localeCode);
		
		if(page != null) {
			//공통 페이지 레이아웃 리스트
			List<PortalPageLayout> commonPageLayoutList = portalPageLayoutDao.listPageLayout(page.getPageId());

			//common 레이아웃 리스트
			List<PortalLayout> commonLayoutList = portalLayoutDao.listLayoutCommonCheck(1);
			
			//공통 active 포틀릿 리스트 조회
			if(commonPageLayoutList.size() > 0) {
				List<PortalPortlet> commonActivePortletList = portalPortletDao.listActivePortletSetting(page.getSystemCode(), commonPageLayoutList);
				
				portalDefaultMain.setCommonPageLayoutList(commonPageLayoutList);
				portalDefaultMain.setCommonActivePortletList(commonActivePortletList);
				portalDefaultMain.setCommonLayoutId(commonPageLayoutList.get(0).getLayoutId());
				
				Double commonMarginLeft = getCommonMarginLeft(commonPageLayoutList);
				
				portalDefaultMain.setCommonMarginLeft(commonMarginLeft);
			}
			/*
			else 
			{
				if(commonLayoutList.size() > 0) {
					PortalLayout portalCommonLayoutTemp = commonLayoutList.get(0);
					
					Double commonMarginLeft = getCommonMarginLeft(portalCommonLayoutTemp);
					Double commonWidth = getCommonWidth(portalCommonLayoutTemp);
					
					portalDefaultMain.setCommonLayoutId(portalCommonLayoutTemp.getLayoutId());
					portalDefaultMain.setCommonMarginLeft(commonMarginLeft);
					
					for(int i = 0; i < Integer.parseInt(portalCommonLayoutTemp.getLayoutNum()); i++) {
						PortalPageLayout addCommonPageLayout = new PortalPageLayout();
						
						addCommonPageLayout.setPageId(page.getPageId());
						addCommonPageLayout.setLayoutId(portalCommonLayoutTemp.getLayoutId());
						addCommonPageLayout.setColIndex(i+1);
						addCommonPageLayout.setWidth(commonWidth);
						
						commonPageLayoutList.add(addCommonPageLayout);
					}
					
					portalDefaultMain.setCommonPageLayoutList(commonPageLayoutList);
				}
			}
			*/

			//개인 레이아웃 리스트
			List<PortalLayout> layoutList = portalLayoutDao.listLayoutCommonCheck(0);
			
			//개인 디폴트 레이아웃 리스트 조회
			List<PortalDefPageLayout> defPageLayoutList = portalDefPageLayoutDao.listDefPageLayout(page.getPageId());
			
			if(defPageLayoutList.size() > 0) {
				List<PortalDefPortletConfig> defPortletList = portalDefPortletConfigDao.listDefPortlet(defPageLayoutList);
				
				portalDefaultMain.setDefPageLayoutList(defPageLayoutList);
				portalDefaultMain.setDefPortletList(defPortletList);
				portalDefaultMain.setDefLayoutId(defPageLayoutList.get(0).getLayoutId());
				
				Double marginLeft = getMarginLeft(defPageLayoutList);
				
				portalDefaultMain.setMarginLeft(marginLeft);
			} else {
				if(layoutList.size() > 0) {
					PortalLayout portalLayoutTemp = layoutList.get(0);
					
					Double marginLeft = getMarginLeft(portalLayoutTemp);
					Double defWidth = getDefWidth(portalLayoutTemp);
					
					portalDefaultMain.setDefLayoutId(portalLayoutTemp.getLayoutId());
					portalDefaultMain.setMarginLeft(marginLeft);
					
					for(int i = 0; i < Integer.parseInt(portalLayoutTemp.getLayoutNum()); i++) {
						PortalDefPageLayout addDefPageLayout = new PortalDefPageLayout();
						
						addDefPageLayout.setPageId(page.getPageId());
						addDefPageLayout.setLayoutId(portalLayoutTemp.getLayoutId());
						addDefPageLayout.setColIndex(i+1);
						addDefPageLayout.setWidth(defWidth);
						
						defPageLayoutList.add(addDefPageLayout);
					}
					
					portalDefaultMain.setDefPageLayoutList(defPageLayoutList);
				}
			}
			
			//포탈메인 포틀릿 전체 리스트
			List<PortalPortlet> portletList =  null;
			if(portalId != null && !this.readPageId(portalId).equals(pageId)){
				portletList = portalPortletDao.listPortletPrivate(page.getSystemCode());
			}else{
				portletList = portalPortletDao.listPortlet(page.getSystemCode());
			}
			
			//포틀릿 카테고리 리스트
			List<PortalPortletCategory> portletCategoryList = portalPortletCategoryDao.listPortletCategory(page.getSystemCode());
			
			portalDefaultMain.setPortletList(portletList);
			portalDefaultMain.setPortletCategoryList(portletCategoryList);
			portalDefaultMain.setCommonLayoutList(commonLayoutList);
			portalDefaultMain.setLayoutList(layoutList);
			portalDefaultMain.setPage(page);
		}
		
		return portalDefaultMain;
	}

	/**
	 * 페이지 ID 조회
	 * @param portalId 포탈 ID
	 * @return 페이지 ID
	 */
	public String readPageId(String portalId) {
		String systemCode = portalSystemDao.getSystemCode("Portal", portalId);
		
		String pageId = portalPageDao.getPageId(systemCode);
		
		return pageId;
	}
	
	/**
	 * 메인 페이지 공용 포틀릿 좌/우 너비 갱신
	 * @param portalPage
	 */
	public void updateCommonPortletWidth(PortalPage portalPage) {
		portalPageDao.updateCommonPortletWidth(portalPage);
		
	}
	
	/**
	 * 공통 레이아웃 왼쪽 마진 
	 * @param commonPageLayoutList 공통 레이아웃 리스트
	 * @return
	 */
	private Double getCommonMarginLeft(List<PortalPageLayout> commonPageLayoutList) {
		Double commonMarginLeft = 0.0;
		
		if(commonPageLayoutList.size() == 2) {
			commonMarginLeft = 1.0;
		} else if(commonPageLayoutList.size() == 3) {
			commonMarginLeft = 1.25;
		}
		
		return commonMarginLeft;
	}
	
	/**
	 * 공통 레이아웃 왼쪽 마진(레이아웃 없을경우) 
	 * @param portalLayout 공통 레이아웃
	 * @return
	 */
	/*
	private Double getCommonMarginLeft(PortalLayout portalLayout) {
		Double commonMarginLeft = 0.0;
		
		if("2".equals(portalLayout.getLayoutNum())) {
			commonMarginLeft = 1.0;
		} else if("3".equals(portalLayout.getLayoutNum())) {
			commonMarginLeft = 1.25;
		}
		
		return commonMarginLeft;
	}
	*/
	
	/**
	 * 공통 레이아웃  너비(레이아웃 없을경우) 
	 * @param portalLayout 공통 레이아웃
	 * @return
	 */
	/*
	private Double getCommonWidth(PortalLayout portalLayout) {
		Double commonWidth = 100.0;
		
		if("2".equals(portalLayout.getLayoutNum())) {
			commonWidth = 1.0;
		} else if("3".equals(portalLayout.getLayoutNum())) {
			commonWidth = 1.25;
		}
		
		return commonWidth;
	}
	*/
	
	/**
	 * 레이아웃 왼쪽 마진
	 * @param defPageLayoutList 디폴트 레이아웃 리스트
	 * @return
	 */
	private Double getMarginLeft(List<PortalDefPageLayout> defPageLayoutList) {
		Double marginLeft = 0.0;
		
		if(defPageLayoutList.size() == 2) {
			marginLeft = 1.0;
		} else if(defPageLayoutList.size() == 3) {
			marginLeft = 1.25;
		}
		
		return marginLeft;
	}
	
	/**
	 * 레이아웃 왼쪽 마진(레이아웃이 없는경우)
	 * @param portalLayout 레이아웃
	 * @return
	 */
	private Double getMarginLeft(PortalLayout portalLayout) {
		Double marginLeft = 0.0;
		
		if("2".equals(portalLayout.getLayoutNum())) {
			marginLeft = 1.0;
		} else if("3".equals(portalLayout.getLayoutNum())) {
			marginLeft = 1.25;
		}
		
		return marginLeft;
	}
	
	/**
	 * 레이아웃 너비(레이아웃이 없는경우)
	 * @param portalLayout 레이아웃
	 * @return
	 */
	private Double getDefWidth(PortalLayout portalLayout) {
		Double defWidth = 100.0;
		
		if("2".equals(portalLayout.getLayoutNum())) {
			defWidth = 49.5;
		} else if("3".equals(portalLayout.getLayoutNum())) {
			defWidth = 32.5;
		}
		
		return defWidth;
	}
}