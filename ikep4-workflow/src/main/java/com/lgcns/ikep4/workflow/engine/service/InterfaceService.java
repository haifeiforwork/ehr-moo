/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.workflow.engine.model.InterfaceBean;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: InterfaceService.java 16245 2011-08-18 04:28:59Z giljae $ InterfaceService.java 오후 7:50:06
 */
@Transactional
public interface InterfaceService {

	public List<InterfaceBean> readInterface();
	
	public void executeInterface();
}
