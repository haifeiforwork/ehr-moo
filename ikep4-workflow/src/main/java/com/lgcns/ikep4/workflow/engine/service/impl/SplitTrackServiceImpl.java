/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lgcns.ikep4.workflow.engine.dao.InterfaceDao;
import com.lgcns.ikep4.workflow.engine.dao.impl.InterfaceDaoImpl;
import com.lgcns.ikep4.workflow.engine.model.InterfaceBean;
import com.lgcns.ikep4.workflow.engine.model.SplitTrackBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.workflow.engine.service.InterfaceService;
import com.lgcns.ikep4.workflow.engine.service.SplitTrackService;
import com.lgcns.ikep4.workflow.engine.util.XmlService;



/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: SplitTrackServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $ SplitTrackServiceImpl.java 오후 2:38:19
 */
@Service("SplitTrackService")
public class SplitTrackServiceImpl implements SplitTrackService {
	
	Log log = LogFactory.getLog(SplitTrackServiceImpl.class);

	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#create(java.lang.Object)
	 */
	public String create(SplitTrackBean object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#delete(java.io.Serializable)
	 */
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.service.SplitTrackService#insertSplitTrack(com.lgcns.ikep4.workflow.engine.model.SplitTrackBean)
	 */
	public void insertSplitTrack(SplitTrackBean splitTrackBean) {
		// TODO Auto-generated method stub
		
	}
	 
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#read(java.io.Serializable)
	 */
	public SplitTrackBean read(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.service.GenericService#update(java.lang.Object)
	 */
	public void update(SplitTrackBean object) {
		// TODO Auto-generated method stub
		
	}
	
	
}
