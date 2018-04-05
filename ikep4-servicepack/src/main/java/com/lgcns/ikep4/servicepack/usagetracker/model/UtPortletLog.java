/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.model;

import java.util.Date;
/**
 * 
 * 사용량통계 포틀릿 히스토리
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtPortletLog.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtPortletLog extends UtBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 9110385067501028881L;

	/**
	 * PORTLET_HISTORY_ID[포틀릿 사용이력 ID]
	 */
	private String portletHistoryId;


	/**
	 * OWNER_ID[포틀릿 소유자 ID]
	 */
	private String ownerId;

	/**
	 * ACTION[동작 구분 ( 0 : 등록, 1 : 삭제)]
	 */
	private String action;

	/**
	 * PORTAL_ID[포탈 ID]
	 */
	private String portalId;

	/**
	 * REGISTER_ID[등록자 ID]
	 */
	private String registerId;

	/**
	 * REGISTER_NAME[등록자 이름]
	 */
	private String registerName;
	
	private String portletId;
	

	/**
	 * REGIST_DATE[등록일시]
	 */
	private Date registDate;

	public String getPortletHistoryId() {
		return portletHistoryId;
	}

	public void setPortletHistoryId(String portletHistoryId) {
		this.portletHistoryId = portletHistoryId == null ? null : portletHistoryId.trim();
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId == null ? null : ownerId.trim();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action == null ? null : action.trim();
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId == null ? null : portalId.trim();
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId == null ? null : registerId.trim();
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName == null ? null : registerName.trim();
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}
}