/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.forum.dao.FrItemScoreDao;
import com.lgcns.ikep4.collpack.forum.model.FrItemScore;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemScoreDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frItemScoreDao")
public class FrItemScoreDaoImpl extends GenericDaoSqlmap<FrItemScore, String> implements FrItemScoreDao {
	

	public String create(FrItemScore obj) {
		return (String) sqlInsert("collpack.fourm.dao.FrItemScore.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	@Deprecated
	public FrItemScore get(String id) {
		return null;
	}


	@Deprecated
	public void update(FrItemScore obj) {
	}
	
	
	public void remove(String id) {
		sqlDelete("collpack.fourm.dao.FrItemScore.remove", id);
	}

	public void removeAll() {
		sqlDelete("collpack.fourm.dao.FrItemScore.removeAll");
		
	}

	public void removeCount(int count) {
		sqlDelete("collpack.fourm.dao.FrItemScore.removeCount", count);
	}

	public void removeByDiscussionId(String discussionId) {
		sqlDelete("collpack.fourm.dao.FrItemScore.removeByDiscussionId", discussionId);
	}
	
	
	
	
}
