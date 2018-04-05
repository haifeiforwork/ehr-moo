/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.lightpack.award.dao.AwardDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemReaderDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.AwardItemReader;
import com.lgcns.ikep4.lightpack.award.search.AwardItemReaderSearchCondition;
import com.lgcns.ikep4.lightpack.award.search.AwardItemSearchCondition;
import com.lgcns.ikep4.lightpack.award.search.AwardLinereplySearchCondition;

/**
 * AwardDao 구현체 클래스
 */
@Repository
public class AwardItemReaderDaoImpl extends GenericDaoSqlmap<AwardItemReader, String> implements AwardItemReaderDao {

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "lightpack.award.dao.awardItemReader.";

	public String create(AwardItemReader awardItemReader) {
		this.sqlInsert(NAMESPACE + "create", awardItemReader);
		return awardItemReader.getItemId();
	}

	
	public Integer readerFlag(AwardItemReader awardItemReader) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "readerFlag", awardItemReader);
	}
	
	public Integer selectReaderCount(AwardItemReader awardItemReader) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "selectReaderCount", awardItemReader);
	}
	
	public Integer countReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countReaderBySearchCondition", searchCondtion);
	}
	
	public Integer countAwardBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countAwardBySearchCondition", searchCondtion);
	}
	
	public Integer countNoReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countNoReaderBySearchCondition", searchCondtion);
	}
	
	public Integer checkReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "checkReaderBySearchCondition", searchCondtion);
	}
	
	public List<AwardItemReader> listReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listReaderBySearchCondition", searchCondtion);
	}
	
	public List<AwardItemReader> listAwardBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listAwardBySearchCondition", searchCondtion);
	}
	
	public List<AwardItemReader> listNoReaderBySearchCondition(AwardItemReaderSearchCondition searchCondtion) {
		return this.sqlSelectForList(NAMESPACE + "listNoReaderBySearchCondition", searchCondtion);
	}
	
	public void deleteReader(String id) {
		this.sqlDelete(NAMESPACE + "deleteReader", id);
	}
	
	
	public List<AwardItemReader> listAwardItemReader(String  awardItemId) {
		return this.sqlSelectForList(NAMESPACE + "listAwardItemReader", awardItemId);
	}
	
	public List<AwardItemReader> listAwardItemAllNoti() {
		return this.sqlSelectForList(NAMESPACE + "listAwardItemAllNoti");
	}
	
	public List<AwardItemReader> listReaderAllMail(String  awardItemId) {
		return this.sqlSelectForList(NAMESPACE + "listReaderAllMail", awardItemId);
	}
	
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public AwardItemReader get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(AwardItemReader arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateMailWaitYn(String itemId) {
		this.sqlUpdate(NAMESPACE + "updateMailWaitYn", itemId);
	}
	
	public List<AwardItemReader> listAwardItemAllNotiInstant(String itemId) {
		return this.sqlSelectForList(NAMESPACE + "listAwardItemAllNotiInstant", itemId);
	}


}
