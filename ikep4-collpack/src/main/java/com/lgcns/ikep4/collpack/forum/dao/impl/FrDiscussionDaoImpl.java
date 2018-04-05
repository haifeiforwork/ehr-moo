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

import com.lgcns.ikep4.collpack.forum.dao.FrDiscussionDao;
import com.lgcns.ikep4.collpack.forum.model.FrDiscussion;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrDiscussionDaoImpl.java 16903 2011-10-25 01:37:27Z giljae $
 */
@Repository("frDiscussionDao")
public class FrDiscussionDaoImpl extends GenericDaoSqlmap<FrDiscussion, String> implements FrDiscussionDao {
	
	
	public String create(FrDiscussion frDiscussion) {
		return (String) sqlInsert("collpack.fourm.dao.FrDiscussion.create", frDiscussion);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	public int getCountByUserId(String registerId) {
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrDiscussion.getCountByUserId", registerId);
	}

	public int getCountList(FrSearch frSearch) {
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrDiscussion.getCountList", frSearch);
	}

	public int getCountListPopular(FrSearch frSearch) {
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrDiscussion.getCountListPopular", frSearch);
	}

	public List<FrDiscussion> list(FrSearch frSearch) {
		
		return sqlSelectForList("collpack.fourm.dao.FrDiscussion.list", frSearch);
	}

	public List<FrDiscussion> listPopular(FrSearch frSearch) {
		return sqlSelectForList("collpack.fourm.dao.FrDiscussion.listPopular", frSearch);
	}

	public FrDiscussion get(String discussionId) {
		return (FrDiscussion) sqlSelectForObject("collpack.fourm.dao.FrDiscussion.get", discussionId);
	}

	public void update(FrDiscussion obj) {
		sqlUpdate("collpack.fourm.dao.FrDiscussion.update", obj);
	}
	

	public void updateHitCount(String discussionId) {
		sqlUpdate("collpack.fourm.dao.FrDiscussion.updateHitCount", discussionId);
	}

	public void updateItemCount(String discussionId) {
		sqlUpdate("collpack.fourm.dao.FrDiscussion.updateItemCount", discussionId);
	}
	

	public void updateLinereplyCount(String discussionId) {
		sqlUpdate("collpack.fourm.dao.FrDiscussion.updateLinereplyCount", discussionId);
	}

	public void updateParticipationCount(String discussionId) {
		sqlUpdate("collpack.fourm.dao.FrDiscussion.updateParticipationCount", discussionId);
	}
	
	
	public void remove(String discussionId) {
		sqlDelete("collpack.fourm.dao.FrDiscussion.remove", discussionId);
	}

	
	public void updateMailCount(String discussionId) {
		sqlUpdate("collpack.fourm.dao.FrDiscussion.updateMailCount", discussionId);
	}



	public void updateMblogCount(String discussionId) {
		sqlUpdate("collpack.fourm.dao.FrDiscussion.updateMblogCount", discussionId);
	}

	public List<FrDiscussion> listDiscussionByReference(String userId, int endRowIndex) {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setUserId(userId);
		frSearch.setEndRowIndex(endRowIndex);
		
		return sqlSelectForList("collpack.fourm.dao.FrDiscussion.listDiscussionByReference", frSearch);
	}
	
	public List<FrDiscussion> listDiscussionByItemCount(int endRowIndex, String limitDate) {
		
		FrSearch frSearch = new FrSearch();
		frSearch.setEndRowIndex(endRowIndex);
		frSearch.setLimitDate(limitDate);
		
		return sqlSelectForList("collpack.fourm.dao.FrDiscussion.listDiscussionByItemCount", frSearch);
	}
	
}
