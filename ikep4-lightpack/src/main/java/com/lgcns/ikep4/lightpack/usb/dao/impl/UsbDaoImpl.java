/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.usb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.usb.dao.UsbDao;
import com.lgcns.ikep4.lightpack.usb.model.Usb;
import com.lgcns.ikep4.lightpack.usb.model.UsbSearchCondition;

/**
 * TODO Javadoc주석작성
 * 
 * @author handul
 * @version $Id$
 */
@Repository
public class UsbDaoImpl extends GenericDaoSqlmap<Usb, String> implements UsbDao {

	String NAMESPACE = "lightpack.usb.dao.usb.";

	public void usbUseRequest(Usb usb){
		sqlInsert(NAMESPACE + "usbUseRequest", usb);
	}
	
	public String getTeamLeader(String userId){
		return (String) sqlSelectForObject(NAMESPACE + "getTeamLeader", userId);
	}
	
	public Integer selectMyRequestCount(UsbSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectMyRequestCount", searchCondition);
	}
	
	public List<Map<String, Object>> selectMyRequestAll(UsbSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectMyRequestAll", searchCondition);
	}
	
	public Integer selectRequestCount(UsbSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestCount", searchCondition);
	}
	
	public Integer selectRequestAllCount(UsbSearchCondition searchCondition){
		return (Integer) sqlSelectForObject(NAMESPACE + "selectRequestAllCount", searchCondition);
	}
	
	public List<Map<String, Object>> selectRequest(UsbSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectRequest", searchCondition);
	}
	
	public List<Map<String, Object>> selectRequestAll(UsbSearchCondition searchCondition){
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectRequestAll", searchCondition);
	}
	
	public Usb getUsbUseRequestView(String usbId){
		return (Usb) sqlSelectForObject(NAMESPACE + "getUseRequestView", usbId);
	}
	
	public void usbApproveUse(Usb usb){
		sqlUpdate(NAMESPACE + "usbApproveUse", usb);
	}
	
	public void usbUseRequestUpdate(Usb usb){
		sqlUpdate(NAMESPACE + "usbUseRequestUpdate", usb);
	}
	
	public void usbUseRequestDelete(Usb usb){
		sqlUpdate(NAMESPACE + "usbUseRequestDelete", usb);
	}
	
	public List<Usb> selectUsbManager(String usbadm){
		return sqlSelectForList(NAMESPACE + "selectUsbManager", usbadm);
	}

	public String create(Usb arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Usb get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(Usb arg0) {
		// TODO Auto-generated method stub
		
	}
}