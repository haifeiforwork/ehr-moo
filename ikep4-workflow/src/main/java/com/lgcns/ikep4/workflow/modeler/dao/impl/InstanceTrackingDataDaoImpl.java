/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.modeler.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.workflow.modeler.dao.InstanceTrackingDataDao;
import com.lgcns.ikep4.workflow.modeler.model.InstanceTrackingData;

/**
 * TODO Javadoc주석작성
 *
 * @author 김동후
 * @version $Id: InstanceTrackingDataDaoImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Repository("InstanceTrackingDataDao")
public class InstanceTrackingDataDaoImpl extends GenericDaoSqlmap<InstanceTrackingData, String> implements InstanceTrackingDataDao {

	@Deprecated
	public InstanceTrackingData get(String instanceId) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public boolean exists(String id) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public String create(InstanceTrackingData object) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public void update(InstanceTrackingData object) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public void remove(String instanceId) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public List<InstanceTrackingData> selectAll() {
		throw new UnsupportedOperationException();
	}
	
	public List<InstanceTrackingData> listInstanceTrackingData(String instanceId) {
		List<InstanceTrackingData> instanceTrackingDataList = (List<InstanceTrackingData>)this.sqlSelectForList("workflow.modeler.dao.InstanceTrackingData.listInstanceTrackingData", instanceId);
		
		return instanceTrackingDataList;
	}
}
