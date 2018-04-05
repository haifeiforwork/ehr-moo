/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletUserConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletUserConfig;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 포틀릿 사용자 설정 서비스 구현체
 *
 * @author 한승환
 * @version $Id: PortalPortletUserConfigServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portalPortletUserConfigService")
public class PortalPortletUserConfigServiceImpl  extends GenericServiceImpl<PortalPortletUserConfig, String>  implements PortalPortletUserConfigService {

	@Autowired
	private PortalPortletUserConfigDao portalPortletUserConfigDao;
	
	
	static final String DEFAULT_PROPERTY_VALUE = "LIST";
	
	public void setListSize(String portletConfigId,int propertyValue) {
		this.setPortletUserConfig(portletConfigId,DEFAULT_PROPERTY_VALUE, String.valueOf(propertyValue));
	}
	

	public int readListSize(String portletConfigId) {
		Map<String,String> param = new HashMap <String,String>();
		param.put("portletConfigId",portletConfigId);
		param.put("propertyName",DEFAULT_PROPERTY_VALUE);
		
		PortalPortletUserConfig object = portalPortletUserConfigDao.read(param);
		
		if(object != null){
			return Integer.valueOf(object.getPropertyValue());
		}else{
			return 0;
		}
	}
	 
	public void setPortletUserConfig(String portletConfigId,String propertyName, String propertyValue) {
		Map<String,String> param = new HashMap <String,String>();
		param.put("portletConfigId",portletConfigId);
		param.put("propertyName",propertyName);
		
		boolean isExists = portalPortletUserConfigDao.exists(param);
		
		if(isExists)
		{
			this.updatePortalPortletUserConfig(portletConfigId, propertyName, propertyValue);
		}
		else
		{
			this.createPortalPortletUserConfig(portletConfigId, propertyName, propertyValue);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService#readPortalPortletUserConfig(java.lang.String)
	 */
	public String readPortalPortletUserConfig(String portletConfigId,String propertyName) {
		Map<String,String> param = new HashMap <String,String>();
		param.put("portletConfigId",portletConfigId);
		param.put("propertyName",propertyName);

		PortalPortletUserConfig object = portalPortletUserConfigDao.read(param);
		
		if(object != null){
			return object.getPropertyValue();
		}else{
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService#deletePortalPortletUserConfig(java.lang.String)
	 */
	public int deletePortalPortletUserConfig(String portletConfigId) {
		Map<String,String> param = new HashMap <String,String>();
		param.put("portletConfigId",portletConfigId);
		param.put("propertyName",DEFAULT_PROPERTY_VALUE);
		return portalPortletUserConfigDao.remove(param);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.portal.admin.screen.service.PortalPortletUserConfigService#deletePortalPortletUserConfig(java.lang.String)
	 */
	public int deletePortalPortletUserConfig(String portletConfigId,String propertyName) {
		Map<String,String> param = new HashMap <String,String>();
		param.put("portletConfigId",portletConfigId);
		param.put("propertyName",propertyName);
		return portalPortletUserConfigDao.remove(param);
	}

	public String createPortalPortletUserConfig(String portletConfigId,String propertyName,String propertyValue) {
		User user = (User) this.getRequestAttribute("ikep.user");

		PortalPortletUserConfig object = new PortalPortletUserConfig();
		object.setPortletConfigId(portletConfigId);
		object.setPropertyName(propertyName);
		object.setPropertyValue(propertyValue);
		object.setRegisterId(user.getUserId());
		object.setRegisterName(user.getUserName());
		object.setUpdaterId(user.getUserId());
		object.setUpdaterName(user.getUserName());
		
		return portalPortletUserConfigDao.create(object);
		
	}

	
	public void updatePortalPortletUserConfig(String portletConfigId,String propertyName,String propertyValue) {
		
		User user = (User) this.getRequestAttribute("ikep.user");
		
		PortalPortletUserConfig object = new PortalPortletUserConfig();
		object.setPortletConfigId(portletConfigId);
		object.setPropertyName(propertyName);
		object.setPropertyValue(propertyValue);
		object.setUpdaterId(user.getUserId());
		object.setUpdaterName(user.getUserName());
		
		portalPortletUserConfigDao.update(object);
	}
	
	
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
	
	//여기서 부터 사용안함..
	

	public PortalPortletUserConfig read(String id) {
		return null;
	}


	public boolean exists(String id) {
		return false;
	}


	public String create(PortalPortletUserConfig object) {
		return null;
	}

	public void update(PortalPortletUserConfig object) {

	}

	public void delete(String id) {
	}

}
