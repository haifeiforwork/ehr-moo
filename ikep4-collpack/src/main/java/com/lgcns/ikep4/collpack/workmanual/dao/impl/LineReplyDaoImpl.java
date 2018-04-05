/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.workmanual.dao.LineReplyDao;
import com.lgcns.ikep4.collpack.workmanual.model.LineReply;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;


/**
 * DAO 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: LineReplyDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository
public class LineReplyDaoImpl extends GenericDaoSqlmap<LineReply, String> implements LineReplyDao {
	private static final String NAMESPACE = "collpack.workmanual.dao.lineReply.";
	
	public String create(LineReply lineReply) {
		sqlInsert(NAMESPACE + "create", lineReply);
		return lineReply.getLinereplyId();
	}

	public boolean exists(String lineReplyId) {
		Integer count = (Integer) sqlSelectForObject(NAMESPACE + "count", lineReplyId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public LineReply get(String lineReplyId) {
		return (LineReply) sqlSelectForObject(NAMESPACE + "get", lineReplyId);
	}

	public void remove(String lineReplyId) {
		sqlDelete(NAMESPACE + "remove", lineReplyId);
	}

	public void update(LineReply lineReply) {
		sqlUpdate(NAMESPACE + "update", lineReply);
	}
	////////////////////////////////////

	//댓글 정보 조회수
	public Integer countLineReply(String manualId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countLineReply", manualId);
	}
	//댓글 정보 조회
	public List<LineReply> listLineReply(ManualSearchCondition manualSearchCondition) {
		return sqlSelectForList(NAMESPACE + "listLineReply", manualSearchCondition); 
	}
	//스텝 증가
	public void updateStep(LineReply lineReply) {
		sqlUpdate(NAMESPACE + "updateStep", lineReply);
	}
	//하위 글 조회수
	public Integer countChildren(String linereplyId) {
		return (Integer) sqlSelectForObject(NAMESPACE + "countChildren", linereplyId);
	}
	//관련글 모두 삭제
	public void removeAll(String linereplyId) {
		sqlDelete(NAMESPACE + "removeAll", linereplyId);
	}
	//매뉴얼 관련글 모두 삭제
	public void removeByManualId(String manualId) {
		sqlDelete(NAMESPACE + "removeByManualId", manualId);
	}
}
