
/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.planner.model.CSExcelDownSearchCondition;
import com.lgcns.ikep4.support.fileupload.model.FileData;


public interface CompanyScheduleExcelDao extends GenericDao<FileData, String> {
	

	public List<Map<String, Object>> getCompanyScheduleExcelFileList(CSExcelDownSearchCondition cSExcelDownSearchCondition) ;
	
	public Integer selectCount(CSExcelDownSearchCondition searchCondition);
}