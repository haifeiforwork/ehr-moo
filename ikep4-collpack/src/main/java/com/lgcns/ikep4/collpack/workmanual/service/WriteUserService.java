/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.WriteUser;
import com.lgcns.ikep4.framework.core.service.GenericService;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: WriteUserService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface WriteUserService extends GenericService<WriteUser, WriteUser> {
	/**
	 * 문서 담당자 정보
	 * @param categoryId
	 * @return
	 */
	public List<WriteUser> listWriteUser(String categoryId);
	/**
	 * 문서 담당자 여부
	 * @param writeUserId
	 * @return
	 */
	public String writeUserYn(String writeUserId);
}
