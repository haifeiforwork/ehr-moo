/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lgcns.ikep4.workflow.engine.dao.InterfaceDao;
import com.lgcns.ikep4.workflow.engine.dao.impl.InterfaceDaoImpl;
import com.lgcns.ikep4.workflow.engine.demon.InterfaceDemon;
import com.lgcns.ikep4.workflow.engine.model.InterfaceBean;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.workflow.engine.service.InterfaceService;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: InterfaceController.java 16245 2011-08-18 04:28:59Z giljae $ WorkflowController.java 오후 6:47:40
 */
@Controller
public class InterfaceController {
	
	Log log = LogFactory.getLog(InterfaceController.class);
	
	
	@Autowired
	private InterfaceDao interfaceDao;		
	
	@Autowired
	private InterfaceService interfaceService;
	


	@RequestMapping(value = "workflow/interface.do", method = RequestMethod.GET)
	public void interfaceHandler() {
		
		//List<InterfaceBean> list		= interfaceDao.readInterface();
		//log.info("# list : " + list.size());
		
		int activeThread				= Thread.activeCount();
		if(2 < activeThread) {
			log.info("#################################################################");
			log.info("# Create Thread : activeThead count(" + activeThread + ")");
			log.info("#################################################################");
			interfaceService.executeInterface();
		} else {
			log.info("#################################################################");
			log.info("# Not Create Thread : activeThead count(" + activeThread + ")");
			log.info("#################################################################");
		}
		
	}
}
