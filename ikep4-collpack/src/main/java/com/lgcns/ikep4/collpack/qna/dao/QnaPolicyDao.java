/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.qna.model.QnaPolicy;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaPolicyDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface QnaPolicyDao extends GenericDao<QnaPolicy, String>  {
	
	
	/**
	 * 질문 정책
	 * @param portalId TODO
	 * 
	 * @return
	 */
	List<QnaPolicy> listPolicy(String portalId);
	
	/**
	 * policy 개수
	 * @return
	 */
	int readCount(String portalId);
	
	

	/**
	 * policy 개수
	 * @return
	 */
	List<String> listPotalId();
	
	
}
