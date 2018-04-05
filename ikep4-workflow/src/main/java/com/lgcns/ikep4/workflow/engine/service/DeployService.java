/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;


/**
 * TODO Javadoc주석작성
 * 
 * @author 박철순(sniper28@naver.com)
 * @version $Id: DeployService.java 오후 2:44:44
 */
@Transactional
public interface DeployService {
	
	public boolean deployProcess(String xmlStream, String userId);
	
	public boolean deployProcess(String xmlStream, String userId, String partitionId);
	
	public boolean deployProcess(String xpdlStream, String userId, String partitionId, String processType);
	
	public boolean deployProcess(String xpdlStream, String userId, String partitionId, String processType, Date executeDate);
	
		
	public boolean undeployProcess(String processId);
	
	public boolean deleteProcess(String processId);
	
	public boolean deleteProcess(String processId, String processVer);
	
}
