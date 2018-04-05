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

import com.lgcns.ikep4.collpack.forum.dao.FrItemDao;
import com.lgcns.ikep4.collpack.forum.model.FrItem;
import com.lgcns.ikep4.collpack.forum.model.FrSearch;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItemDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("frItemDao")
public class FrItemDaoImpl extends GenericDaoSqlmap<FrItem, String> implements FrItemDao {
	

	public String create(FrItem obj) {
		return (String) sqlInsert("collpack.fourm.dao.FrItem.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	
	public List<String> listUserId(String discussionId) {
		return (List)sqlSelectForListOfObject("collpack.fourm.dao.FrItem.listUserId", discussionId);
	}

	public FrItem getCountes(FrSearch frSearch) {
		return (FrItem) sqlSelectForObject("collpack.fourm.dao.FrItem.getCountes", frSearch);
	}


	public FrItem get(String id) {
		return (FrItem) sqlSelectForObject("collpack.fourm.dao.FrItem.get", id);
	}

	public int getCountList(FrSearch frSearch) {
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrItem.getCountList", frSearch);
	}

	public int getCountListWithDiscussion(FrSearch frSearch) {
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrItem.getCountListWithDiscussion", frSearch);
	}

	public int getCountListPopular(FrSearch frSearch) {
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrItem.getCountListPopular", frSearch);
	}

	public List<FrItem> list(FrSearch frSearch) {
		
		return sqlSelectForList("collpack.fourm.dao.FrItem.list", frSearch);
	}
	


	public List<FrItem> listWithDiscussion(FrSearch frSearch) {
		return sqlSelectForList("collpack.fourm.dao.FrItem.listWithDiscussion", frSearch);
	}

	
	public List<FrItem> listPopular(FrSearch frSearch) {
		return sqlSelectForList("collpack.fourm.dao.FrItem.listPopular", frSearch);
	}

	public List<FrItem> listItemRandomCategory(FrSearch frSearch) {
		return sqlSelectForList("collpack.fourm.dao.FrItem.listItemRandomCategory", frSearch);
	}
	

	public List<FrItem> listLastWithDiscussion(FrSearch frSearch) {
		return sqlSelectForList("collpack.fourm.dao.FrItem.listLastWithDiscussion", frSearch);
	}

	public int getCountListLastWithDiscussion(FrSearch frSearch) {
		return (Integer) sqlSelectForObject("collpack.fourm.dao.FrItem.getCountListLastWithDiscussion", frSearch);
	}
	

	public void update(FrItem obj) {
		sqlUpdate("collpack.fourm.dao.FrItem.update", obj);
	}
	
	
	public void updateBestItem(String itemId) {
		sqlUpdate("collpack.fourm.dao.FrItem.updateBestItem", itemId);
	}

	public void updateBestItemInit() {
		sqlUpdate("collpack.fourm.dao.FrItem.updateBestItemInit");
		
	}

	public void updateHitCount(String itemId) {
		sqlUpdate("collpack.fourm.dao.FrItem.updateHitCount", itemId);
	}

	public void updateLinereplyCount(String itemId) {
		sqlUpdate("collpack.fourm.dao.FrItem.updateLinereplyCount", itemId);
	}

	public void updateAgreementCount(String itemId) {
		sqlUpdate("collpack.fourm.dao.FrItem.updateAgreementCount", itemId);
	}

	public void updateOppositionCount(String itemId) {
		sqlUpdate("collpack.fourm.dao.FrItem.updateOppositionCount", itemId);
	}

	public void updateFavoriteCount(String itemId, int favoriteCount) {
		
		FrItem frItem = new FrItem();
		frItem.setItemId(itemId);
		frItem.setFavoriteCount(favoriteCount);
		
		sqlUpdate("collpack.fourm.dao.FrItem.updateFavoriteCount", frItem);
	}

	public void remove(String qnaId) {
		sqlDelete("collpack.fourm.dao.FrItem.remove", qnaId);
	}

	public void removeByDiscussionId(String discussionId) {
		sqlDelete("collpack.fourm.dao.FrItem.removeByDiscussionId", discussionId);
		
	}
	
	
	
	
}
