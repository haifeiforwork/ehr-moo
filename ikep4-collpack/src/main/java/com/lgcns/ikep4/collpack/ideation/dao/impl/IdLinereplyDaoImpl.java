/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.collpack.ideation.dao.IdLinereplyDao;
import com.lgcns.ikep4.collpack.ideation.model.IdLinereply;
import com.lgcns.ikep4.collpack.ideation.model.IdSearch;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereplyDaoImpl.java 12461 2011-05-20 09:49:00Z loverfairy $
 */
@Repository("idLinereplyDao")
public class IdLinereplyDaoImpl extends GenericDaoSqlmap<IdLinereply, String> implements IdLinereplyDao {
	

	public String create(IdLinereply obj) {
		return (String) sqlInsert("collpack.ideation.dao.IdLinereply.create", obj);
	}

	@Deprecated
	public boolean exists(String id) {
		return false;
	}
	
	public IdLinereply get(String id) {
		return (IdLinereply) sqlSelectForObject("collpack.ideation.dao.IdLinereply.get", id);
	}
	
	public int getCountByParentId(String linereplyParentId) {
		return (Integer)sqlSelectForObject("collpack.ideation.dao.IdLinereply.getCountByParentId", linereplyParentId);
	}
	
	public IdLinereply getCountes(IdSearch idSearch) {
		return (IdLinereply)sqlSelectForObject("collpack.ideation.dao.IdLinereply.getCountes", idSearch);
	}

	
	public List<String> listUserId(String itemId) {
		return (List)sqlSelectForListOfObject("collpack.ideation.dao.IdLinereply.listUserId", itemId);
	}
	
	
	public List<IdLinereply> list(IdSearch idSearch) {
		
		return sqlSelectForList("collpack.ideation.dao.IdLinereply.list", idSearch);
	}
	

	public int getCountList(IdSearch idSearch) {
		return (Integer)sqlSelectForObject("collpack.ideation.dao.IdLinereply.getCountList", idSearch);
	}


	public void update(IdLinereply obj) {
		sqlUpdate("collpack.ideation.dao.IdLinereply.update", obj);
	}
	
	public void updateStep(String linereplyGroupId, int step) {
		
		IdLinereply idLinereply = new IdLinereply();
		idLinereply.setLinereplyGroupId(linereplyGroupId);
		idLinereply.setStep(step);
		
		sqlUpdate("collpack.ideation.dao.IdLinereply.updateStep", idLinereply);
	}

	public void updateLinereplyDelete(String linereplyId, int linereplyDelete) {
		
		IdLinereply idLinereply = new IdLinereply();
		idLinereply.setLinereplyId(linereplyId);
		idLinereply.setLinereplyDelete(linereplyDelete);
		
		sqlUpdate("collpack.ideation.dao.IdLinereply.updateLinereplyDelete", idLinereply);
		
	}


	public void updateRecommendCount(String linereplyId) {
		sqlUpdate("collpack.ideation.dao.IdLinereply.updateRecommendCount", linereplyId);
	}
	
	
	
	public void updateAdoptLinereply(String linereplyId) {
		sqlUpdate("collpack.ideation.dao.IdLinereply.updateAdoptLinereply", linereplyId);
	}

	public void remove(String linereplyId) {
		sqlDelete("collpack.ideation.dao.IdLinereply.remove", linereplyId);
	}

	public void removeByItemId(String itemId) {
		sqlDelete("collpack.ideation.dao.IdLinereply.removeByItemId", itemId);
	}

}
