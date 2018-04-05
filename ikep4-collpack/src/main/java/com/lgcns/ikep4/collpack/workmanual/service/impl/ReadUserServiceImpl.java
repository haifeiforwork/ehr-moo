/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.workmanual.dao.ReadUserDao;
import com.lgcns.ikep4.collpack.workmanual.model.ReadUser;
import com.lgcns.ikep4.collpack.workmanual.service.ReadUserService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ReadUserServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service 
@Transactional
public class ReadUserServiceImpl extends GenericServiceImpl<ReadUser, ReadUser> implements ReadUserService {

	@Autowired
	private ReadUserDao readUserDao;

	@Deprecated
	public ReadUser create(ReadUser readUser) {
		return readUserDao.create(readUser);
	}

	public boolean exists(ReadUser readUser) {
		return readUserDao.exists(readUser);
	}

	@Deprecated
	public ReadUser read(ReadUser readUser) {
		return readUserDao.get(readUser);
	}

	@Deprecated
	public void delete(ReadUser readUser) {
		readUserDao.remove(readUser);
	}

	@Deprecated
	public void update(ReadUser readUser) {
		readUserDao.update(readUser);
	}
	////////////////////////////////////

	//문서 조회 담당자 정보
	public List<ReadUser> listReadUser(String categoryId) {
		return readUserDao.listReadUser(categoryId);
	}
}
