/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;



import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.wfapproval.work.dao.ApDocDao;
import com.lgcns.ikep4.wfapproval.work.model.ApDoc;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;



/**
 * 품위서 작성  DAO 구현
 * 
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApDocDaoImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Repository("apDocDao")
public class ApDocDaoImpl extends GenericDaoSqlmap<ApDoc, Integer> implements ApDocDao {

	private static final String NAMESPACE = "com.lgcns.ikep4.wfapproval.work.dao.ApDoc.";
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.example.dao.BoardDao#selectAll()
	 */
	//@Cacheable(cacheName = "sampleCache")
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @Cacheable 어노테이션으로 캐시 대상을 명시한다.
	public List<ApDoc> selectAll(ApDoc apdoc) {
		
		return sqlSelectForList(NAMESPACE + "selectAll", apdoc);
	}
	
	public List<ApDoc> selectAllApUser(ApDoc apdoc) {
		
		return sqlSelectForList(NAMESPACE + "selectAllApUser", apdoc);
	}
	
	public List<ApDoc> selectAllApUserList(ApDoc apdoc) {
		
		return sqlSelectForList(NAMESPACE + "selectAllApUserList", apdoc);
	}

	public List<ApDoc> selectAllApProcess(ApDoc apdoc) {
		
		return sqlSelectForList(NAMESPACE + "selectAllApProcess", apdoc);
	}

	public List<ApDoc> selectApProcessUserId(ApDoc apdoc) {
		
		return sqlSelectForList(NAMESPACE + "selectApProcessUserId", apdoc);
	}

	public List<ApDoc> selectApReceive(ApDoc apdoc) {
		
		return sqlSelectForList(NAMESPACE + "selectApReceive", apdoc);
	}

	public List<ApDoc> selectApAuthUser(ApDoc apdoc) {
		
		return sqlSelectForList(NAMESPACE + "selectApAuthUser", apdoc);
	}

	public List<ApDoc> selectApRelations(ApDoc apdoc) {
		
		return sqlSelectForList(NAMESPACE + "selectApRelations", apdoc);
	}

	public int selectCount(ApDoc apdoc) {
		return (Integer)sqlSelectForObject(NAMESPACE + "selectCount", apdoc);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public Integer create(ApDoc apdoc) {
		return (Integer) sqlInsert(NAMESPACE + "insert", apdoc);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public Integer createApUser(ApDoc apdoc) {
		return (Integer) sqlInsert(NAMESPACE + "insertApUser", apdoc);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public Integer createApUserActivity(ApDoc apdoc) {
		return (Integer) sqlInsert(NAMESPACE + "insertApUserActivity", apdoc);
	}

	/*
	 * (non-Javadoc)결재선 저장
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#create(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public Integer createApProcess(ApDoc apdoc) {
		return (Integer) sqlInsert(NAMESPACE + "insertApProcess", apdoc);
	}	
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#exists(java.io.Serializable
	 * )
	 */
	public boolean exists(Integer seq) {
		Integer count = (Integer) sqlSelectForObject("board.selectCount", seq);
		if (count > 0)
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApDoc get(Integer seq) {
		return (ApDoc) sqlSelectForObject("board.select", seq);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#get(java.io.Serializable)
	 */
	public ApDoc getApDoc(ApDoc apdoc) {
		return (ApDoc) sqlSelectForObject(NAMESPACE + "selectApDoc", apdoc);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void remove(Integer seq) {
		sqlDelete("board.delete", seq);
	}
	

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void removeApProcess(String apprId) {
		sqlDelete(NAMESPACE + "deleteApProcess", apprId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void removeApReceive(String apprId) {
		sqlDelete(NAMESPACE + "deleteApReceive", apprId);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#remove(java.io.Serializable
	 * )
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void removeApAuthUser(String apprId) {
		sqlDelete(NAMESPACE + "deleteApAuthUser", apprId);
	}


	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void update(ApDoc apdoc) {
		sqlUpdate(NAMESPACE + "update", apdoc);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.lgcns.ikep4.framework.core.dao.GenericDao#update(java.lang.Object)
	 */
	//@TriggersRemove(cacheName = "sampleCache", removeAll = true)
	// @TriggersRemove 어노테이션으로 변경사항이 있을시 해당 캐시를 초기화한다.
	public void updateApProcess(ApDoc apdoc) {
		sqlUpdate(NAMESPACE + "updateApProcess", apdoc);
	}
	

	//첨부파일 insert
	public String create(FileLink fileLink) {
		return (String) sqlInsert(NAMESPACE +"insertFile", fileLink);

	}
}
