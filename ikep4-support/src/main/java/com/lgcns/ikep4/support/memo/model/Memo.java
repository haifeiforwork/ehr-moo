/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.memo.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 *메모
 *
 * @author 배성훤(sunghwonbae@gmail.com)
 * @version $Id: Memo.java 2006 2011-03-08 08:15:33Z combinet $
 */
public class Memo extends BaseObject {


	
	public Memo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *
	 */
	private static final long serialVersionUID = -3060315972080748419L;


	private String portalId;
	private String registerId;
	private String registerName;
	private String registDate;
	private String contents;
	private String rnum;
	private String itemId;
	
	
	
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
