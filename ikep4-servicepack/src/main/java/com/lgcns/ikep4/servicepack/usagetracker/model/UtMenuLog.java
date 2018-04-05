/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.model;

import java.util.Date;
/**
 * 
 * 사용량통계 메뉴 히스토리
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtMenuLog.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtMenuLog extends UtBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -6574487776585994866L;

	/**
	 * MENU_ACCESS_ID[메뉴 접속 이력 ID]
	 */
	private String menuAccessId;

	/**
	 * MENU_ID[메뉴 ID]
	 */
	private String menuId;
	
	private String menuName;
	
	private String subMenuId;
	
	private String subMenuName;

	
	/**
	 * USER_ID[메뉴 접속 사용자 ID]
	 */
	private String userId;

	/**
	 * ACCESS_DATE[메뉴 접속 일시]
	 */
	private Date accessDate;

	public String getMenuAccessId() {
		return menuAccessId;
	}

	public void setMenuAccessId(String menuAccessId) {
		this.menuAccessId = menuAccessId == null ? null : menuAccessId.trim();
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId == null ? null : menuId.trim();
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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