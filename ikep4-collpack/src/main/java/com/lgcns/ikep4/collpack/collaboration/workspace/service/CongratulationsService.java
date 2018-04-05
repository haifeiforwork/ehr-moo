/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.Congratulations;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 포틀릿 축하합니다 관리 서비스
 * 
 * @author 박철종(yruyo@cnspartner.com)
 * @version $Id: CongratulationsService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface CongratulationsService extends GenericService<Congratulations, String> {

	/**
	 * 기념일 정보 조회 ( 팀원중  생일,결혼기념일)
	 * @param workspaceId
	 * @param user
	 * @param resultColumn
	 * @return
	 */
	public List<Congratulations> listCongratulations(String workspaceId, User user,String resultColumn);

	
}
