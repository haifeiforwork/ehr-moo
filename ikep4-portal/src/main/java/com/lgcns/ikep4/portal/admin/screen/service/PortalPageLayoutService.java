package com.lgcns.ikep4.portal.admin.screen.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포탈 페이지 레이아웃 Service
 *
 * @author 임종상
 * @version $Id: PortalPageLayoutService.java 19098 2012-06-04 08:44:50Z malboru80 $
 */
@Transactional
public interface PortalPageLayoutService extends GenericService<PortalPageLayout, String> {
	
	/**
	 * 페이지 리셋
	 * @param pageId 페이지 ID
	 * @param ownerId 소유자 ID
	 */
	public void removePortletReset(String pageId, String ownerId);
	
	/**
	 * 포틀릿 레이아웃 설정
	 * @param pageId
	 * @param layoutId
	 * @param ownerId
	 */
	public void updateUserPageLayout(String pageId, String layoutId, User user);
}