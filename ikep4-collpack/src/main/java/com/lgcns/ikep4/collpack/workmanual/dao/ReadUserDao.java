/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.ReadUser;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ReadUserDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface ReadUserDao extends GenericDao<ReadUser, ReadUser> {
	/**
	 * 문서 조회 담당자 정보
	 * @param categoryId
	 * @return
	 */
	public List<ReadUser> listReadUser(String categoryId);
	/**
	 * 카테고리 아이디로 삭제
	 * @param categoryId
	 */
	public void removeByCategoryId(String categoryId);
}
