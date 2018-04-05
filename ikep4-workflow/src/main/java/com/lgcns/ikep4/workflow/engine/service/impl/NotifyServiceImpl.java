/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.service.impl;

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
import com.lgcns.ikep4.workflow.engine.dao.NotifyDao;
import com.lgcns.ikep4.workflow.engine.dao.ServiceDao;
import com.lgcns.ikep4.workflow.engine.dao.impl.InterfaceDaoImpl;
import com.lgcns.ikep4.workflow.engine.model.InterfaceBean;
import com.lgcns.ikep4.workflow.engine.model.NotifyBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;
import com.lgcns.ikep4.workflow.engine.service.InstanceService;
import com.lgcns.ikep4.workflow.engine.service.InterfaceService;
import com.lgcns.ikep4.workflow.engine.service.NotifyService;
import com.lgcns.ikep4.workflow.engine.util.XmlService;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순 (sniper28@naver.com)
 * @version $Id: NotifyServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $ InterfaceServiceImpl.java 오후 7:57:53
 */
@Service("notifyService")
public class NotifyServiceImpl implements NotifyService {
	
	Log log = LogFactory.getLog(NotifyServiceImpl.class);
	
	@Autowired
	private NotifyDao notifyDao;	

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.service.NotifyService#selectNotify(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<NotifyBean> selectNotify(NotifyBean notifyBean) {
		return notifyDao.selectNotify(notifyBean);
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @param processId
	 * @param processVer
	 * @param activityId
	 * @return
	 */
	public int deleteNotify(NotifyBean notifyBean) {
		return notifyDao.deleteNotify(notifyBean);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.service.NotifyService#insertNotify(com.lgcns.ikep4.workflow.engine.model.NotifyBean)
	 */
	public void insertNotify(NotifyBean notifyBean) {
		// TODO Auto-generated method stub
		notifyDao.insertNotify(notifyBean);
	}
}
