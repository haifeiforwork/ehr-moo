/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.wfapproval.work.dao.ApProcessViewDao;
import com.lgcns.ikep4.wfapproval.work.model.ApProcessViewData;

/**
 * 결재 목록조회  Master DAO 구현
 * 
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApProcessViewDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("ApProcessViewDao")
public class ApProcessViewDaoImpl extends GenericDaoSqlmap<ApProcessViewData, String> implements ApProcessViewDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.wfapproval.work.dao.ApProcessViewData.";

	@Deprecated
	public ApProcessViewData get(String apprId) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public boolean exists(String id) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public String create(ApProcessViewData object) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public void update(ApProcessViewData object) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public void remove(String instanceId) {
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public List<ApProcessViewData> selectAll() {
		throw new UnsupportedOperationException();
	}
	
	public List<ApProcessViewData> listApProcessViewData(String apprId) {
		List<ApProcessViewData> apProcessViewDataList = (List<ApProcessViewData>)this.sqlSelectForList( NAMESPACE + "listApProcessViewData", apprId);
		
		return apProcessViewDataList;
	}
}
