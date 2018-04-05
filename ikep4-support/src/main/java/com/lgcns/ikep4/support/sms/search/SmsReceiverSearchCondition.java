package com.lgcns.ikep4.support.sms.search;

import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.support.sms.constants.SmsConstants;

public class SmsReceiverSearchCondition extends SearchCondition {
	private static final long serialVersionUID = -6809397882380002043L;
	
	private final static String DEFAULT_CUR_MODE = "1";
	@Override
	protected Integer getDefaultPagePerRecord() {
		return SmsConstants.LIST_SMS_PAGE_PER_RECORD;
	}
	@Override
	public Integer getDefaultPaginationBlock() {
		return SmsConstants.LIST_SMS_SCREEN_PAGE_PER_RECORD;
	}	
	private String curMode = DEFAULT_CUR_MODE;
	
	/**
	 * 발신자 아이디
	 */	
	private String registerId;
	
	/**
	 * 파라미터로 넘어온 인자 gubun(내부사용자일경우 1, 외부사용자일 경우 2)
	 */		
	public String gubun;
	
	/**
	 * 파라미터로 넘어온 인자 receiverId(내부사용자일경우 userId, 외부사용자일 경우 phoneNum)
	 */	
	public String receiverId;	

	/**
	 * 파라미터로 셋팅할 수신자 아이디
	 */		
	public String paramReceiverId;

	/**
	 * 파라미터로 셋팅할 수신자 전화번호
	 */		
	public String paramMobile;
	
	/**
	 * 파라미터로 셋팅할 수신자 이름
	 */		
	public String paramName;
	
	public String getParamReceiverId() {
		return paramReceiverId;
	}
	
	public void setParamReceiverId(String paramReceiverId) {
		this.paramReceiverId = paramReceiverId;
	}
	
	public String getParamMobile() {
		return paramMobile;
	}
	
	public void setParamMobile(String paramMobile) {
		this.paramMobile = paramMobile;
	}
	
	public String getParamName() {
		return paramName;
	}
	
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public String getCurMode() {
		return curMode;
	}

	public void setCurMode(String curMode) {
		this.curMode = curMode;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	} 

	public String getGubun() {
		return gubun;
	}
	
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	
	public String getReceiverId() {
		return receiverId;
	}
	
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}	
}
