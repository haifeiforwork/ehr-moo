/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApForm;
import com.lgcns.ikep4.wfapproval.admin.search.ApFormSearchCondition;


/**
 * 결재 양식관리 Master DAO 정의
 * 
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApFormDao.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApFormDao extends GenericDao<ApForm, String> {

	/**
	 * 전체 양식목록 가져오기
	 * 
	 * @return
	 */
	public List<ApForm> getApFormListAll();

	/**
	 * 품의서 작성 양식목록 가져오기
	 * 
	 * @return
	 */
	public List<ApForm> getApFormList();

	/**
	 * 전체 양식목록 가져오기
	 * 
	 * @return
	 */
	List<ApForm> listApFormAll(ApFormSearchCondition apFormSearchCondition);

	/**
	 * 전체 양식목록 Count 가져오기
	 * 
	 * @return
	 */
	Integer countApFormAll(ApFormSearchCondition apFormSearchCondition);

	/**
	 * 품의서 작성 양식목록 가져오기
	 * 
	 * @return
	 */
	List<ApForm> listApFormSelect(ApFormSearchCondition apFormSearchCondition);

	/**
	 * 품의서 작성 양식목록 Count 가져오기
	 * 
	 * @return
	 */
	Integer countApFormSelect(ApFormSearchCondition apFormSearchCondition);
}
