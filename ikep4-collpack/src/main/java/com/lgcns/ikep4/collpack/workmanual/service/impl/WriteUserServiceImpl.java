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

import com.lgcns.ikep4.collpack.workmanual.dao.WriteUserDao;
import com.lgcns.ikep4.collpack.workmanual.model.WriteUser;
import com.lgcns.ikep4.collpack.workmanual.service.WriteUserService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: WriteUserServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service 
@Transactional
public class WriteUserServiceImpl extends GenericServiceImpl<WriteUser, WriteUser> implements WriteUserService {

	@Autowired
	private WriteUserDao writeUserDao;

	@Deprecated
	public WriteUser create(WriteUser writeUser) {
		writeUserDao.create(writeUser);
		return writeUser;
	}

	public boolean exists(WriteUser writeUser) {
		return writeUserDao.exists(writeUser);
	}

	@Deprecated
	public WriteUser read(WriteUser writeUser) {
		return writeUserDao.get(writeUser);
	}

	@Deprecated
	public void delete(WriteUser writeUser) {
		writeUserDao.remove(writeUser);
	}

	@Deprecated
	public void update(WriteUser writeUser) {
		writeUserDao.update(writeUser);
	}
	////////////////////////////////////
	
	//문서 담당자 정보
	public List<WriteUser> listWriteUser(String categoryId) {
		return writeUserDao.listWriteUser(categoryId);
	}
	//문서 담당자 여부
	public String writeUserYn(String writeUserId) {
		return writeUserDao.writeUserYn(writeUserId);
	}
}
