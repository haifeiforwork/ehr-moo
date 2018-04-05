/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util.user.member.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.util.user.member.model.User;

/**
 * 사용자 관리 서비스
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: UserService.java 19178 2012-06-11 07:46:13Z malboru80 $
 */
@Transactional
public interface UsersService extends GenericService<User, String> {

	public User read(String id);
		
}
