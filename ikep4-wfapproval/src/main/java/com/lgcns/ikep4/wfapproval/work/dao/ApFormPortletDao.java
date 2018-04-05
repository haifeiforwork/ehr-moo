/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.wfapproval.work.model.ApFormPortlet;


/**
 * 양식 포틀릿정보 DAO 정의
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormPortletDao.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApFormPortletDao extends GenericDao<ApFormPortlet, String> {

	/**
	 * 포틀릿 목록 가져오기
	 * 
	 * @return
	 */
	public List<ApFormPortlet> listApFormPortlet(ApFormPortlet apFormPortlet);
	
	/**
	 * 데이터 유무
	 * 
	 * @param apFormPortlet
	 * @return
	 */
	public boolean exists(ApFormPortlet apFormPortlet);
	
}
