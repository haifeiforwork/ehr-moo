/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.support.user.code.model.I18nMessage;

public class UtMenu extends UtBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 2620348450158196801L;

	
	
	public UtMenu() {
		super();
	}

	public UtMenu(String menuId, String menuName, String menuUrl, String requestParameter, Integer usage,
			String registerId, String registerName, Date registDate) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.requestParameter = requestParameter;
		this.usage = usage;
		this.registerId = registerId;
		this.registerName = registerName;
		this.registDate = registDate;
	}
	
	/**
	 * MENU_ID[메뉴 ID]
	 */
	private String menuId;

	/**
	 * MENU_NAME[메뉴 이름_ko]
	 */
	private String menuName;
	
	private String subMenuId;
	
	private String subMenuName;
	
	/**
	 * MENU_URL[메뉴 URL]
	 */
	private String menuUrl;

	/**
	 * REQUEST_PARAMETER[메뉴 요청 파라미터]
	 */
	private String requestParameter;

	/**
	 * USAGE[사용 여부 ( 0 : 사용, 1 : 미사용)]
	 */
	private Integer usage;

	/**
	 * REGISTER_ID[등록자 ID]
	 */
	private String registerId;

	/**
	 * REGISTER_NAME[등록자 이름]
	 */
	private String registerName;

	/**
	 * REGIST_DATE[등록일시]
	 */
	private Date registDate;
	
	/**
	 * i18nMessage[언어]
	 */
	private List<I18nMessage> i18nMessageList;

	
	
	


	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId == null ? null : menuId.trim();
	}


	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}

	
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl == null ? null : menuUrl.trim();
	}

	public String getRequestParameter() {
		return requestParameter;
	}

	public void setRequestParameter(String requestParameter) {
		this.requestParameter = requestParameter == null ? null : requestParameter.trim();
	}

	public Integer getUsage() {
		return usage;
	}

	public void setUsage(Integer usage) {
		this.usage = usage;
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

	public List<I18nMessage> getI18nMessageList() {
		return i18nMessageList;
	}

	public void setI18nMessageList(List<I18nMessage> i18nMessageList) {
		this.i18nMessageList = i18nMessageList;
	}

	public String getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}
	

}