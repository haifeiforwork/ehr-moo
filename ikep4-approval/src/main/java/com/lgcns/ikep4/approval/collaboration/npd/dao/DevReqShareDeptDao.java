/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.npd.dao;

import java.util.List;

import com.lgcns.ikep4.approval.collaboration.npd.model.DevReqShareDept;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * 참조부서 DAO
 * 
 * @author pjh
 * @version $Id$
 */
public interface DevReqShareDeptDao extends GenericDao<DevReqShareDept, String> {
	
	/**
	 * 참조부서 목록조회
	 * @param DevReqShareDept
	 * @return
	 * @throws Exception
	 */
	public List<DevReqShareDept> getDevReqShareDept( String mgntNo) throws Exception;
	
	/**
	 * 참조부서 등록
	 * @param DevReqShareDept
	 * @throws Exception
	 */
	public void insertDevReqShareDept( DevReqShareDept DevReqShareDept) throws Exception;
	
	/**
	 * 참조부서 삭제
	 * @param DevReqShareDept
	 * @throws Exception
	 */
	public void deleteDevReqShareDept( String mgntNo) throws Exception;
}
