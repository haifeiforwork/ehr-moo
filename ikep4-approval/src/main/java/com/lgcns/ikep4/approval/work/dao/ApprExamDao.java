/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprExam;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * 검토요청
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprExamDao extends GenericDao<ApprExam, String> {

	void apprExamCreate(ApprExam apprExam);

	List<ApprExam> listApprExamInfo(ApprExam apprExam);
	
	String examFirstReqId(String apprId);
	
	void updateApprExamInfoSave(ApprExam apprExam);
	
	public void remove(Map<String, String> map);
	
	boolean existExamUser (Map map);
	
	boolean existExamIsRequest (Map map);
	
	public boolean existExamStatus(Map map);
}
