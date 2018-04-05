/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.usb.service;

import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.usb.model.Usb;
import com.lgcns.ikep4.lightpack.usb.model.UsbSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
public interface UsbService extends GenericService<Usb, String> {

	
	public SearchResult<Map<String, Object>> myRequestList(UsbSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> requestList(UsbSearchCondition searchCondition);
	
	public SearchResult<Map<String, Object>> requestAllList(UsbSearchCondition searchCondition);
	
	public String usbUseRequest(Usb usb, User user);
	
	public Usb getUsbUseRequestView(String usbId);
	
	public void usbApproveUse(Usb usb);
	
	public void usbUseRequestUpdate(Usb usb);
	
	public void usbUseRequestDelete(Usb usb);
	
	public void sendUsbUseRequestMail(String flg, String message, Usb usb, User sender);
}