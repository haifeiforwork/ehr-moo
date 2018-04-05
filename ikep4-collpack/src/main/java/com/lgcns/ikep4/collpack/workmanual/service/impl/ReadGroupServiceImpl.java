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

import com.lgcns.ikep4.collpack.workmanual.dao.ReadGroupDao;
import com.lgcns.ikep4.collpack.workmanual.model.ReadGroup;
import com.lgcns.ikep4.collpack.workmanual.service.ReadGroupService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ReadGroupServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service 
@Transactional
public class ReadGroupServiceImpl extends GenericServiceImpl<ReadGroup, ReadGroup> implements ReadGroupService {

	@Autowired
	private ReadGroupDao readGroupDao;

	@Deprecated
	public ReadGroup create(ReadGroup readGroup) {
		return readGroupDao.create(readGroup);
	}

	public boolean exists(ReadGroup readGroup) {
		return readGroupDao.exists(readGroup);
	}

	@Deprecated
	public ReadGroup read(ReadGroup readGroup) {
		return readGroupDao.get(readGroup);
	}

	@Deprecated
	public void delete(ReadGroup readGroup) {
		readGroupDao.remove(readGroup);
	}

	@Deprecated
	public void update(ReadGroup readGroup) {
		readGroupDao.update(readGroup);
	}
	////////////////////////////////////

	//문서 조회 조직 정보
	public List<ReadGroup> listReadGroup(String categoryId, String portalId) {
		ReadGroup readGroup = new ReadGroup();
		readGroup.setCategoryId(categoryId);
		readGroup.setPortalId(portalId);
		return readGroupDao.listReadGroup(readGroup);
	}
}
