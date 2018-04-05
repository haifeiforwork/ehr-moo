
/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.meetingroom.model.MeetingRoomSearchCondition;
import com.lgcns.ikep4.lightpack.planner.dao.AlarmDao;
import com.lgcns.ikep4.lightpack.planner.dao.CompanyScheduleExcelDao;
import com.lgcns.ikep4.lightpack.planner.model.Alarm;
import com.lgcns.ikep4.lightpack.planner.model.CSExcelDownSearchCondition;
import com.lgcns.ikep4.support.fileupload.model.FileData;

@Repository(value = "companyScheduleExcelDao")
public class CompanyScheduleExcelDaoImpl extends GenericDaoSqlmap<FileData, String> implements CompanyScheduleExcelDao {
	
	
	String NAMESPACE = "lightpack.planner.dao.Schedule.";
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getCompanyScheduleExcelFileList(CSExcelDownSearchCondition cSExcelDownSearchCondition) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "selectCompanyScheduleExcelFile", cSExcelDownSearchCondition);
	}

	
	public Integer selectCount(CSExcelDownSearchCondition searchCondition) {
	
		return (Integer) sqlSelectForObject(NAMESPACE + "selectCount", searchCondition);
	}
	
	
	public String create(FileData arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public FileData get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(FileData arg0) {
		// TODO Auto-generated method stub
		
	}
}