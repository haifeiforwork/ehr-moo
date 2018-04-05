/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.forum.dao.FrLinereplyDao;
import com.lgcns.ikep4.collpack.forum.model.FrLinereply;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrLinereplyDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frLinereplyDao")
public class FrLinereplyDaoImpl extends GenericDaoSqlmap<FrLinereply, String> implements FrLinereplyDao {
	

	public String create(FrLinereply obj) {
		return (String) sqlInsert("collpack.fourm.dao.FrLinereply.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	public FrLinereply get(String id) {
		return (FrLinereply) sqlSelectForObject("collpack.fourm.dao.FrLinereply.get", id);
	}
	

	public FrLinereply getCountes(FrSearch frSearch) {
		return (FrLinereply)sqlSelectForObject("collpack.fourm.dao.FrLinereply.getCountes", frSearch);
	}

	public int getCountByParentId(String linereplyParentId) {
		return (Integer)sqlSelectForObject("collpack.fourm.dao.FrLinereply.getCountByParentId", linereplyParentId);
	}
	
	public List<String> listUserId(String itemId) {
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrLinereply.listUserId", itemId);
	}
	
	public List<String> listUserIdByDiscussionId(String discussionId) {
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrLinereply.listUserIdByDiscussionId", discussionId);
	}


	public int getCountList(FrSearch frSearch) {
		return (Integer)sqlSelectForObject("collpack.fourm.dao.FrLinereply.getCountList", frSearch);
	}

	public int getCountListWithItemDiscussion(FrSearch frSearch) {
		return (Integer)sqlSelectForObject("collpack.fourm.dao.FrLinereply.getCountListWithItemDiscussion", frSearch);
	}
	
	
	
	public List<FrLinereply> list(FrSearch frSearch) {
		
		return sqlSelectForList("collpack.fourm.dao.FrLinereply.list", frSearch);
	}
	
	

	public List<FrLinereply> listWithItemDiscussion(FrSearch frSearch) {
		return sqlSelectForList("collpack.fourm.dao.FrLinereply.listWithItemDiscussion", frSearch);
	}
	

	public List<FrLinereply> listLastWithItemDiscussion(FrSearch frSearch) {
		return sqlSelectForList("collpack.fourm.dao.FrLinereply.listLastWithItemDiscussion", frSearch);
	}

	public int getCountListLastWithItemDiscussion(FrSearch frSearch) {
		return (Integer)sqlSelectForObject("collpack.fourm.dao.FrLinereply.getCountListLastWithItemDiscussion", frSearch);
	}

	public void update(FrLinereply obj) {
		sqlUpdate("collpack.fourm.dao.FrLinereply.update", obj);
	}
	
	public void updateStep(String linereplyGroupId, int step) {
		
		FrLinereply frLinereply = new FrLinereply();
		frLinereply.setLinereplyGroupId(linereplyGroupId);
		frLinereply.setStep(step);
		
		sqlUpdate("collpack.fourm.dao.FrLinereply.updateStep", frLinereply);
	}

	public void updateLinereplyDelete(String linereplyId, int linereplyDelete) {
		
		FrLinereply frLinereply = new FrLinereply();
		frLinereply.setLinereplyId(linereplyId);
		frLinereply.setLinereplyDelete(linereplyDelete);
		
		sqlUpdate("collpack.fourm.dao.FrLinereply.updateLinereplyDelete", frLinereply);
		
	}

	public void updateBestLinereply(String linereplyId) {
		
		sqlUpdate("collpack.fourm.dao.FrLinereply.updateBestLinereply", linereplyId);
		
	}

	public void updateBestLinereplyInit() {
		sqlUpdate("collpack.fourm.dao.FrLinereply.updateBestLinereplyInit");
	}

	public void updateRecommendCount(String linereplyId) {
		sqlUpdate("collpack.fourm.dao.FrLinereply.updateRecommendCount", linereplyId);
	}

	
	public void remove(String linereplyId) {
		sqlDelete("collpack.fourm.dao.FrLinereply.remove", linereplyId);
	}

	public void removeByItemId(String itemId) {
		sqlDelete("collpack.fourm.dao.FrLinereply.removeByItemId", itemId);
	}

	public void removeByDiscussionId(String discussionId) {
		sqlDelete("collpack.fourm.dao.FrLinereply.removeByDiscussionId", discussionId);
	}
	
}
