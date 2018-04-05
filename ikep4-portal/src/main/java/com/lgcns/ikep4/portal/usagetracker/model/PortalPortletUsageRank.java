package com.lgcns.ikep4.portal.usagetracker.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 포틀릿 통계 로그 Model
 *
 * @author 임종상
 * @version $Id: PortalPortletUsageRank.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalPortletUsageRank extends BaseObject{

	private static final long serialVersionUID = 7874089789790091728L;

	/**
	 * 포틀릿 사용이력 ID
	 */
	private String portletHistoryId;
	
	/**
	 * 포틀릿 ID
	 */
	private String portletId;
	
	/**
	 * 포틀릿 소유자 ID
	 */
	private String ownerId;
	
	/**
	 * 동작 구분( 0 : 등록, 1 : 삭제)
	 */
	private String action;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자 이름
	 */
	private String registerName;
	
	public String getPortletHistoryId() {
		return portletHistoryId;
	}
	public void setPortletHistoryId(String portletHistoryId) {
		this.portletHistoryId = portletHistoryId;
	}
	public String getPortletId() {
		return portletId;
	}
	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
}
