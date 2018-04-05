/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.admin.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 워크플로우 - 알림현황관리 - SMS관리
 *
 * @author 이재경
 * @version $Id: AdminSms.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class AdminSms extends BaseObject{
	
	static final long serialVersionUID = 231767328317453351L;
	
	private String registerId;       	//발신자 id        
	private String registerName;		//발신자 이름
	private String receiverName;       	//수신자 이름
	private String receiverPhoneno;  	//수신자 전화번호
	private String registDate;  		//등록일, 발신일
	private String contents;			//발신내용
	private String resultCode;			//발신결과
	
	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}
	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}
	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	/**
	 * @return the receiverName
	 */
	public String getReceiverName() {
		return receiverName;
	}
	/**
	 * @param receiverName the receiverName to set
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	/**
	 * @return the receiverPhoneno
	 */
	public String getReceiverPhoneno() {
		return receiverPhoneno;
	}
	/**
	 * @param receiverPhoneno the receiverPhoneno to set
	 */
	public void setReceiverPhoneno(String receiverPhoneno) {
		this.receiverPhoneno = receiverPhoneno;
	}
	/**
	 * @return the registDate
	 */
	public String getRegistDate() {
		return registDate;
	}
	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}
	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}
