package com.lgcns.ikep4.approval.work.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Sing 이미지
 * 
 * @author 유승목
 * @version $Id$
 */
public class ApprSign extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2723300559410261094L;

	/**
	 * sign Id
	 */
	private String signId;

	/**
	 * user ID
	 */
	private String userId;

	/**
	 * sign file id
	 */
	private String signFileId;

	/**
	 * 기본 사용 여부
	 */
	private String isDefault;

	/**
	 * sign name
	 */
	private String signName;

	/**
	 * 
	 */
	private String portalId;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSignFileId() {
		return signFileId;
	}

	public void setSignFileId(String signFileId) {
		this.signFileId = signFileId;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
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

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

}
