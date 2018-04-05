package com.lgcns.ikep4.portal.admin.screen.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.main.model.PortalMain;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 포탈 페이지 Service
 *
 * @author 임종상
 * @version $Id: PortalPageService.java 19326 2012-06-19 06:55:36Z yu_hs $
 */
@Transactional
public interface PortalPageService extends GenericService<PortalPage, String> {
	
	/**
	 * 페이지 등록
	 * @param i18nMessageList 페이지 다국어 리스트
	 * @param portalPage 포탈 페이지 모델
	 * @param i18nMessageSystemUrlList 시스템 URL 다국어 리스트
	 * @param portalSystemUrl 시스템 URL 모델
	 */
	public void createPage(List<I18nMessage> i18nMessageList, PortalPage portalPage, List<I18nMessage> i18nMessageSystemUrlList, PortalSystemUrl portalSystemUrl);
	
	/**
	 * 페이지 수정
	 * @param i18nMessageList 페이지이름 다국어 리스트
	 * @param portalPage 포탈 페이지 모델
	 * @param i18nMessageSystemUrlList 시스템 URL 다국어 리스트
	 * @param portalSystemUrl 시스템 URL 모델
	 * @param systemAddFlag 시스템 URL 저장여부 플래그
	 */
	public void updatePage(List<I18nMessage> i18nMessageList, PortalPage portalPage, List<I18nMessage> i18nMessageSystemUrlList, PortalSystemUrl portalSystemUrl, boolean systemAddFlag);
	
	/**
	 * 페이지 조회
	 * @param pageId 페이지 ID
	 * @param localeCode 로케일 코드
	 * @return PortalPage 페이지 정보
	 */
	public PortalPage readPage(String pageId, String localeCode);
	
	/**
	 * 페이지 삭제
	 * @param pageId 페이지 ID
	 */
	public void removePage(String pageId);
	
	/**
	 * 페이지 목록
	 * @param searchCondition 검색 조건
	 * @return SearchResult<PortalPage> 페이지 리스트
	 */
	public SearchResult<PortalPage> listPage(PortalPageSearchCondition searchCondition);
	
	/**
	 * 페이지 ID 조회
	 * @param portalId 포탈 ID
	 * @return 페이지 ID
	 */
	public String readPageId(String portalId);
	
	/**
	 * 디폴트 레이아웃별 포틀릿 등록
	 * @param pageId 페이지 ID
	 * @param defLayoutDataList 디폴트 레이아웃 데이타
	 * @param defPortletDataList 디폴트 포틀릿 데이타
	 * @param commonLayoutDataList 공통 레이아웃 데이타
	 * @param commonPortletDataList 공통 포틀릿 데이타
	 * @param user 유저 객체
	 */
	public void createPageDefLayoutPortlet(String pageId, List<PortalDefPageLayout> defLayoutDataList, List<PortalDefPortletConfig> defPortletDataList, List<PortalPageLayout> commonLayoutDataList, List<PortalPortletConfig> commonPortletDataList, User user);
	
		
	/**
	 * 메인 포틀릿 디폴트 설정 조회
	 * @param ownerId 소유자 ID
	 * @param localeCode 로케일 코드
 	 * @param pageId 페이지 ID
	 * @param user 유저 객체
	 * @return PortalMain 포탈 디폴트  메인 정보
	 */
	public PortalMain readPortletDefaultMain(String ownerId, String localeCode, String pageId, User user,String portalId);
	
	/**
	 * 메인 페이지 공용 포틀릿 좌/우 너비 갱신
	 * @param portalPage
	 */
	public void updateCommonPortletWidth(PortalPage portalPage);
}