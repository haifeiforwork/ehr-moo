package com.lgcns.ikep4.portal.admin.screen.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 시스템 관리자 Model
 *
 * @author 임종상
 * @version $Id: SystemAdmin.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class SystemAdmin extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 8829123084897326321L;

	/**
	 * resource ID
	 */
	private String resourceId;
	
	/**
	 * resource NAME
	 */
	private String resourceName;
	
	/**
	 * rowNum
	 */
	private int num;
	
	private PortalSecurity security;
	
	public PortalSecurity getSecurity() {
		return security;
	}

	public void setSecurity(PortalSecurity security) {
		this.security = security;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}