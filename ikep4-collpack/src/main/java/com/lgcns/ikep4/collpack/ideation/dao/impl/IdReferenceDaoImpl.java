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
import com.lgcns.ikep4.collpack.ideation.dao.IdReferenceDao;
import com.lgcns.ikep4.collpack.ideation.model.IdReference;

/**
 * TODO Javadoc주석작성updateOrderInit
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdReferenceDaoImpl.java 12461 2011-05-20 09:49:00Z loverfairy $
 */
@Repository("idReferenceDao")
public class IdReferenceDaoImpl extends GenericDaoSqlmap<IdReference, String> implements IdReferenceDao {
	
	@Deprecated
	public String create(IdReference obj) {
		return null;
	}
	
	public void create(String itemId,  String registerId) {
		
		IdReference idRecommend = new IdReference();
		idRecommend.setItemId(itemId);
		idRecommend.setRegisterId(registerId);
		
		sqlInsert("collpack.ideation.dao.IdReference.create", idRecommend);
	}
	
	@Deprecated
	public boolean exists(String id) {
		return false;
	}

	public boolean exists(String itemId,  String registerId) {
		
		IdReference idRecommend = new IdReference();
		idRecommend.setItemId(itemId);
		idRecommend.setRegisterId(registerId);
		
		int count = (Integer) sqlSelectForObject("collpack.ideation.dao.IdReference.exists", idRecommend);
		
		boolean isResult = false;
		if (count > 0) {
			isResult =  true;
		} else {
			isResult =  false;
		}
		
		return isResult;
	}
	
	
	@Deprecated
	public IdReference get(String id) {
		return null;
	}

	public List<IdReference> list(String itemId) {
		
		return sqlSelectForList("collpack.ideation.dao.IdReference.list", itemId);
	}
	

	public List<String> listUserId(String itemId) {
		return (List)sqlSelectForListOfObject("collpack.ideation.dao.IdReference.listUserId", itemId);
	}
	
	

	@Deprecated
	public void update(IdReference object) {
	}

	@Deprecated
	public void remove(String id) {
	}

	public void remove(String itemId, String registerId) {
		
		IdReference idRecommend = new IdReference();
		idRecommend.setItemId(itemId);
		idRecommend.setRegisterId(registerId);
		
		sqlDelete("collpack.ideation.dao.IdReference.remove", idRecommend);
	}

	public void removebyItemId(String itemId) {
		sqlDelete("collpack.ideation.dao.IdReference.removebyItemId", itemId);
	}

	
	
}
