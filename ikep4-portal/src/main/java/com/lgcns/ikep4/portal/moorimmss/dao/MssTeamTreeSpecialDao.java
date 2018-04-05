/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.moorimmss.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 팀뷰어 예외 관리
 *
 * @author 신용환(combinet@gmail.com)
 * @version $Id: AlarmDao.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public interface MssTeamTreeSpecialDao extends GenericDao<MssTeamTreeSpecial, String> {





	/** 목록 읽어오기
	 * @param scheduleId
	 * @return
	 */
	List<MssTeamTreeSpecial> getList();
	List<MssTeamTreeSpecial> getList(String userId);
	void deleteMssTeamTreeSpecialList(String[] cid);
	 String create(MssTeamTreeSpecial mssTeamTreeSpecial);

}
