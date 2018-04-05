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
import com.lgcns.ikep4.wfapproval.work.model.ApList;
import com.lgcns.ikep4.wfapproval.work.search.ApListSearchCondition;


/**
 * 결재목록 조회 DAO 정의
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApListDao.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApListDao extends GenericDao<ApList, String> {

	/**
	 * 전체 양식목록 가져오기
	 * 
	 * @return
	 */
	List<ApList> getApSearchList(ApListSearchCondition apListSearchCondition);

	/**
	 * 전체 양식목록 Count 가져오기
	 * 
	 * @return
	 */
	Integer getCountApList(ApListSearchCondition apListSearchCondition);
	
	/**
	 * 품의서 작성 양식목록 가져오기
	 * 
	 * @return
	 */
	//List<ApList> listApFormSelect(ApFormSearchCondition apFormSearchCondition);

	/**
	 * 품의서 작성 양식목록 Count 가져오기
	 * 
	 * @return
	 */
	//Integer countApFormSelect(ApFormSearchCondition apFormSearchCondition);
}
