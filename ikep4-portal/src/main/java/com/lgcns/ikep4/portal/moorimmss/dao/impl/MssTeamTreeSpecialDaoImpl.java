/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.moorimmss.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.front.dao.UserCountDao;
import com.lgcns.ikep4.portal.moorimmss.dao.MssTeamTreeSpecialDao;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 팀뷰어 예외 관리
 *
 */
@Repository
public class MssTeamTreeSpecialDaoImpl extends GenericDaoSqlmap<MssTeamTreeSpecial, String> implements MssTeamTreeSpecialDao {


	private static final String NAMESPACE = "portal.moorimmss.dao.mssTeamTreeSpecial.";

	/** 목록 읽어오기
	 * @param scheduleId
	 * @return
	 */
	public List<MssTeamTreeSpecial> getList(){

			return sqlSelectForList(NAMESPACE + "getList");

	}
	public List<MssTeamTreeSpecial> getList(String userId){

		return sqlSelectForList(NAMESPACE + "getListUser" ,userId);

}

	public String create(MssTeamTreeSpecial mssTeamTreeSpecial) {
		this.sqlInsert(NAMESPACE + "create", mssTeamTreeSpecial);
		return mssTeamTreeSpecial.getItemId();
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public MssTeamTreeSpecial get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(MssTeamTreeSpecial arg0) {
		// TODO Auto-generated method stub
		
	}
	public void deleteMssTeamTreeSpecialList(String[] cid) {
		sqlDelete(NAMESPACE + "deleteMssTeamTreeSpecialListMultiCid", cid);
	}



}
