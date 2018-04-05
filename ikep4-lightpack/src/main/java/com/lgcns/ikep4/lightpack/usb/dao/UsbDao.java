/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.usb.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.usb.model.Usb;
import com.lgcns.ikep4.lightpack.usb.model.UsbSearchCondition;

/**
 * 예약 합의 관련 Dao 구현체
 * 
 * @author 박철종(cjpark@thebne.com)
 * @version $Id: UsbDao.java 16276 2011-08-18 07:09:07Z yruyo $
 */
public interface UsbDao extends GenericDao<Usb, String> {

	public void usbUseRequest(Usb usb);
	
	public String getTeamLeader(String userId);
	
	public Integer selectMyRequestCount(UsbSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectMyRequestAll(UsbSearchCondition searchCondition);
	
	public Integer selectRequestCount(UsbSearchCondition searchCondition);
	
	public Integer selectRequestAllCount(UsbSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectRequest(UsbSearchCondition searchCondition);
	
	public List<Map<String, Object>> selectRequestAll(UsbSearchCondition searchCondition);
	
	public Usb getUsbUseRequestView(String usbId);
	
	public void usbApproveUse(Usb usb);
	
	public void usbUseRequestUpdate(Usb usb);
	
	public void usbUseRequestDelete(Usb usb);
	
	public List<Usb> selectUsbManager(String usbadm);
}