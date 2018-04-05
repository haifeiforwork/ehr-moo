package com.lgcns.ikep4.portal.admin.screen.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포탈 포틀릿 셋팅 Service
 *
 * @author 임종상
 * @version $Id: PortalPortletConfigService.java 16243 2011-08-18 04:10:43Z giljae $
 */

@Transactional
public interface PortalPortletConfigService extends GenericService<PortalPortletConfig, String> {
	
	/**
	 * 포틀릿 config 등록
	 * @param portalPortletConfig 포틀릿 셋팅 모델
	 * @param systemCode 시스템 코드
	 * @param localeCode 사용자 로케일 코드
	 * @param portalId 포탈 ID
	 */
	public void createPortletConfig(PortalPortletConfig portalPortletConfig, String systemCode, String portalId, String localeCode);
	
	/**
	 * 포틀릿 config 수정
	 * @param portalPortletConfig 포틀릿 셋팅 모델
	 * @param localeCode 사용자 로케일 코드
	 * @param systemCode 시스템 코드
	 */
	public void updatePortletConfig(PortalPortletConfig portalPortletConfig, String systemCode, String localeCode);
	
	/**
	 * 포틀릿 config 삭제
	 * @param portalPortletConfig 포틀릿 셋팅 모델
	 * @param systemCode 시스템 코드
	 * @param portalId 포탈 ID
	 * @param user 유저 객체
	 */
	public void removePortletConfig(PortalPortletConfig portalPortletConfig, String systemCode, String portalId, User user);
	
	/**
	 * 포틀릿 config viewMode 수정
	 * @param portalPortletConfig 포틀릿 셋팅 모델
	 */
	public void updatePortletConfigViewMode(PortalPortletConfig portalPortletConfig);
}
