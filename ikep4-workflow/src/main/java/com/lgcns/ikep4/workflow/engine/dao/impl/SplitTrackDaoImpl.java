/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.TriggersRemove;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.engine.dao.InterfaceDao;
import com.lgcns.ikep4.workflow.engine.dao.SplitTrackDao;
import com.lgcns.ikep4.workflow.engine.dao.WorkListDao;
import com.lgcns.ikep4.workflow.engine.model.InterfaceBean;
import com.lgcns.ikep4.workflow.engine.model.SplitTrackBean;
import com.lgcns.ikep4.workflow.engine.model.WorkItemBean;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: InterfaceDaoImpl 오후 3:50:45 
 */
@Repository("splitTrackDao")
public class SplitTrackDaoImpl extends GenericDaoSqlmap<SplitTrackBean, String> implements SplitTrackDao {

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	public String create(SplitTrackBean object) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable)
	 */
	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public SplitTrackBean get(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.SplitTrackDao#insertSplitTrack(com.lgcns.ikep4.workflow.engine.model.SplitTrackBean)
	 */
	public void insertSplitTrack(SplitTrackBean splitTrackBean) {
		// TODO Auto-generated method stub
		sqlInsert("com.lgcns.ikep4.workflow.engine.model.SplitTrackBean.insertSplitTrack", splitTrackBean);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.workflow.engine.dao.SplitTrackDao#updateSplitTrack(com.lgcns.ikep4.workflow.engine.model.SplitTrackBean)
	 */
	public void updateSplitTrack(SplitTrackBean splitTrackBean) {
		// TODO Auto-generated method stub
		sqlUpdate("com.lgcns.ikep4.workflow.engine.model.SplitTrackBean.updateSplitTrack", splitTrackBean);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable)
	 */
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	public void update(SplitTrackBean object) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}