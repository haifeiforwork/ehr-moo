/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.wfapproval.work.model.ApDoc;


/**
 * 품위서 작성 서비스
 *
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApDocService.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Transactional
public interface ApDocService extends GenericService<ApDoc, Integer> {
	public List<ApDoc> list(ApDoc apdoc);
	
	public int listCount(ApDoc apdoc);
	
	public Integer createApUser(ApDoc object);
	
	public List<ApDoc> listApUser(ApDoc object);
	
	public List<ApDoc> listApUserList(ApDoc object);
	
	public List<ApDoc> listApProcess(ApDoc object);
	
	public List<ApDoc> listApProcessUserId(ApDoc object);
	
	public List<ApDoc> listApReceive(ApDoc object);
	
	public List<ApDoc> listApAuthUser(ApDoc object);
	
	public List<ApDoc> listApRelations(ApDoc object);
	
	public ApDoc readApDoc(ApDoc object);
	
	public void updateApProcess(ApDoc object);
	
	public void deleteApDocData(String apprId);
}
