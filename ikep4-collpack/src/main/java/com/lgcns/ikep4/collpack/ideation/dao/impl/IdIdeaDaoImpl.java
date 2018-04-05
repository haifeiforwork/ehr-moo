/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.collpack.ideation.dao.IdIdeaDao;
import com.lgcns.ikep4.collpack.ideation.model.IdIdea;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdIdeaDaoImpl.java 12461 2011-05-20 09:49:00Z loverfairy $
 */
@Repository("idIdeaDao")
public class IdIdeaDaoImpl extends GenericDaoSqlmap<IdIdea, String> implements IdIdeaDao {
	

	public String create(IdIdea obj) {
		return (String) sqlInsert("collpack.ideation.dao.IdIdea.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	public IdIdea get(String id) {
		return (IdIdea) sqlSelectForObject("collpack.ideation.dao.IdIdea.get", id);
	}
	
	public IdIdea getCountes(IdSearch idSearch) {
		return (IdIdea) sqlSelectForObject("collpack.ideation.dao.IdIdea.getCountes", idSearch);
	}
	
	public List<IdIdea> list(IdSearch idSearch) {
		
		return sqlSelectForList("collpack.ideation.dao.IdIdea.list", idSearch);
	}
	
	public int getCountList(IdSearch idSearch) {
		return (Integer) sqlSelectForObject("collpack.ideation.dao.IdIdea.getCountList", idSearch);
	}


	public void update(IdIdea obj) {
		sqlUpdate("collpack.ideation.dao.IdIdea.update", obj);
	}
	
	
	public void updateBestItem(String itemId) {
		sqlUpdate("collpack.ideation.dao.IdIdea.updateBestItem", itemId);
	}

	public void updateBestItemInit() {
		sqlUpdate("collpack.ideation.dao.IdIdea.updateBestItemInit");
		
	}

	public void updateHitCount(String itemId) {
		sqlUpdate("collpack.ideation.dao.IdIdea.updateHitCount", itemId);
	}

	public void updateLinereplyCount(String itemId) {
		sqlUpdate("collpack.ideation.dao.IdIdea.updateLinereplyCount", itemId);
	}


	public void updateFavoriteCount(String itemId, int favoriteCount) {
		
		IdIdea idIdea = new IdIdea();
		idIdea.setItemId(itemId);
		idIdea.setFavoriteCount(favoriteCount);
		
		sqlUpdate("collpack.ideation.dao.IdIdea.updateFavoriteCount", idIdea);
	}
	
	

	public void updateRecommendItem(String itemId, int recommendItem) {
		
		IdIdea idIdea = new IdIdea();
		idIdea.setItemId(itemId);
		idIdea.setRecommendItem(recommendItem);
		
		sqlUpdate("collpack.ideation.dao.IdIdea.updateRecommendItem", idIdea);
		
	}

	public void updateAdoptItem(String itemId, int adoptItem) {
		
		IdIdea idIdea = new IdIdea();
		idIdea.setItemId(itemId);
		idIdea.setAdoptItem(adoptItem);
		
		sqlUpdate("collpack.ideation.dao.IdIdea.updateAdoptItem", idIdea);
	}

	public void updateBusinessItem(String itemId, String businessItem) {
		
		IdIdea idIdea = new IdIdea();
		idIdea.setItemId(itemId);
		idIdea.setBusinessItem(businessItem);
		
		sqlUpdate("collpack.ideation.dao.IdIdea.updateBusinessItem", idIdea);
	}

	public void updateExamination(String itemId, String examinationComment) {
		
		IdIdea idIdea = new IdIdea();
		idIdea.setItemId(itemId);
		idIdea.setExaminationComment(examinationComment);
		
		sqlUpdate("collpack.ideation.dao.IdIdea.updateExamination", idIdea);
	}

	public void updateRecommendCount(String itemId) {
		sqlUpdate("collpack.ideation.dao.IdIdea.updateRecommendCount", itemId);
	}


	public void updateAdoptCount(String itemId) {
		sqlUpdate("collpack.ideation.dao.IdIdea.updateAdoptCount", itemId);
	}

	public void updateMailCount(String itemId) {
		sqlUpdate("collpack.ideation.dao.IdIdea.updateMailCount", itemId);
	}

	public void updateMblogCount(String itemId) {
		sqlUpdate("collpack.ideation.dao.IdIdea.updateMblogCount", itemId);
	}

	public void remove(String qnaId) {
		sqlDelete("collpack.ideation.dao.IdIdea.remove", qnaId);
	}

	public int getFavorite(String userId, String itemTypeCode) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("itemTypeCode", itemTypeCode);
		return (Integer) sqlSelectForObject("collpack.ideation.dao.IdIdea.getFavorite", map);
	}
	
	public void updateCategoryId(String beforeCategoryId, String afterCategoryId) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("beforeCategoryId", beforeCategoryId);
		map.put("afterCategoryId", afterCategoryId);
		
		sqlUpdate("collpack.ideation.dao.IdIdea.updateCategoryId", map);
	}
}
