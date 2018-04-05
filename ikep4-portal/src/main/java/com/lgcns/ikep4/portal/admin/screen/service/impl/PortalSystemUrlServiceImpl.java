/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalSystemUrlDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalSystemUrl;
import com.lgcns.ikep4.portal.admin.screen.search.PortalSystemUrlSearchCondition;
import com.lgcns.ikep4.portal.admin.screen.service.PortalSystemUrlService;
import com.lgcns.ikep4.support.user.code.model.I18nMessage;
import com.lgcns.ikep4.support.user.code.service.I18nMessageService;

/**
 * 기타 페이지 관리 서비스 구현체
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: PortalSystemUrlServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("SystemUrlService")
public class PortalSystemUrlServiceImpl extends GenericServiceImpl<PortalSystemUrl, String> implements PortalSystemUrlService {

	/**
	 * 시스템 URL 관리 dao
	 */
	@Autowired
	private PortalSystemUrlDao portalSystemUrlDao;
	
	/**
	 * 다국어 관리 service
	 */
	@Autowired
	private I18nMessageService i18nMessageService;

	/**
	 * 페이지 별 시스템 URL 리스트
	 * @param searchCondition PortalSystemUrlSearchCondition
	 * @return SearchResult<PortalSystemUrl> 전체 시스템 URL 리스트
	 */
	public SearchResult<PortalSystemUrl> listBySearchCondition(PortalSystemUrlSearchCondition searchCondition) {
		
		Integer count = portalSystemUrlDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<PortalSystemUrl> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			
			searchResult = new SearchResult<PortalSystemUrl>(searchCondition);
		} else {

			List<PortalSystemUrl> systemUrlList = portalSystemUrlDao.listBySearchCondition(searchCondition);

			searchResult = new SearchResult<PortalSystemUrl>(systemUrlList, searchCondition);
		}
		
		return searchResult;
	}
	
	/**
	 * 시스템 URL 정보
	 * @param param Map(fieldName:다국어로 관리되는 field 이름, localeCode:사용자별 locale 코드, systemUrlId:시스템 URL 코드)
	 * @return PortalSystemUrl 시스템 URL 정보
	 */
	public PortalSystemUrl read(Map<String, String> param) {
		
		PortalSystemUrl portalSystemUrl = portalSystemUrlDao.read(param);
		
		return portalSystemUrl;
	}

	/**
	 * 시스템 URL 생성
	 * @param PortalSystemUrl 시스템 URL 정보
	 * @return 생성된 시스템 URL 아이디
	 */
	public String create(PortalSystemUrl portalSystemUrl) {
		
		String id = portalSystemUrl.getSystemUrlId();
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
		//화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
		List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrl.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrl.getSystemUrlId());
		//다국어정보 저장
		i18nMessageService.create(i18nMessageList);
		
		portalSystemUrlDao.create(portalSystemUrl);
		
		return id;
	}

	/**
	 * 시스템 URL 수정
	 * @param PortalSystemUrl 시스템 URL 정보
	 */
	public void update(PortalSystemUrl portalSystemUrl) {
		
		//fillMandatoryField(vo.messageList,UserVO,"아이템 타입 코드","아이템 아이디");
	    //화면에서 넘어오지 않은 필수 값을 i18nMessageList List에 채워줍니다.
	    List <I18nMessage> i18nMessageList = i18nMessageService.fillMandatoryField(portalSystemUrl.getI18nMessageList(), IKepConstant.ITEM_TYPE_CODE_PORTAL, portalSystemUrl.getSystemUrlId());

	    //다국어정보를 저장합니다.
	    i18nMessageService.update(i18nMessageList);
		
		portalSystemUrlDao.update(portalSystemUrl);
	}

	/**
	 * 시스템 URL 삭제
	 * @param systemUrlId 시스템 URL 아이디
	 */
	public void delete(String systemUrlId) {
		
		i18nMessageService.deleteMessagesByItemId(IKepConstant.ITEM_TYPE_CODE_PORTAL, systemUrlId);
		
		portalSystemUrlDao.delete(systemUrlId);
	}

	/**
	 * URL 별 시스템 URL 아이디
	 * @param url URL
	 * @return String 시스템 URL 아이디
	 */
	public String readSystemUrlId(String url) {
		return portalSystemUrlDao.readSystemUrlId(url);
	}
}
