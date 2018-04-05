/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.work.dao.ApprNoticeDao;
import com.lgcns.ikep4.approval.work.model.ApprNotice;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * 알람 관리 DAO 구현
 * 
 * @author jeehye
 * @version $Id: ApprNoticeDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apprNoticeDao")
public class ApprNoticeDaoImpl extends GenericDaoSqlmap<ApprNotice, String> implements ApprNoticeDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.approval.work.dao.ApprNotice.";

	public String create(ApprNotice arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public ApprNotice get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(ApprNotice arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void noticeCreate(ApprNotice apprNotice){
		this.sqlInsert(NAMESPACE + "noticeCreate", apprNotice);
	}
	
	public void noticeUpdate(ApprNotice apprNotice){
		this.sqlInsert(NAMESPACE + "noticeUpdate", apprNotice);
	}
	
	public void noticeUpdateUsage(ApprNotice apprNotice){
		this.sqlUpdate(NAMESPACE + "noticeUpdateUsage", apprNotice);
	}
	
	public ApprNotice noticeDetail(Map<String, String> map){
		return (ApprNotice)this.sqlSelectForObject(NAMESPACE + "noticeDetail", map);
	}
}
