/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.planner.model.CSExcelDownSearchCondition;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.user.member.model.User;

public interface CompanyScheduleExcelService  extends GenericService<FileData, String> {
	
	public  SearchResult<Map<String, Object>> getCompanyScheduleExcelFileList(CSExcelDownSearchCondition cSExcelDownSearchCondition) ;
	
	public void upLoadCompanyScheduleExcel(String fileId, User user, String portalId);
	
	public void upLoadCompanyScheduleExcelEcm(String ecmFileName,String ecmFileUrl1,String ecmFileUrl2,String ecmFilePath,String ecmFileId, User user);
	
	public void delete(String[] chkFileIds);

}