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
import com.lgcns.ikep4.wfapproval.work.model.ApReceive;



/**
 * 수신처 DAO 정의
 * 
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApReceiveDao.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApReceiveDao extends GenericDao<ApReceive, Integer> {
	/**
	 * 모든 수신처 반환
	 * 
	 * @return
	 */
	public List<ApReceive> selectAll(ApReceive apreceive);
	
	/**
	 * 수신처 개수
	 * 
	 * @return
	 */
	public int selectCount(ApReceive apreceive);
	
	/**
	 * 결재문서 열람권한등록
	 * 
	 * @return
	 */
	public Integer createAuthUser(ApReceive apreceive);
	
	/**
	 * 기결제참조문서 등록
	 * 
	 * @return
	 */
	public Integer createRelations(ApReceive apreceive);
	
}
