/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.dao;

import java.util.List;

import com.lgcns.ikep4.collpack.workmanual.model.LineReply;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * DAO 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: LineReplyDao.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface LineReplyDao extends GenericDao<LineReply, String> {
	/**
	 *댓글 정보 조회수
	 * @param manualId
	 * @return
	 */
	public Integer countLineReply(String manualId);
	/**
	 *댓글 정보 조회
	 * @param manualSearchCondition
	 * @return
	 */
	public List<LineReply> listLineReply(ManualSearchCondition manualSearchCondition);
	/**
	 *스텝 증가
	 * @param lineReply
	 */
	public void updateStep(LineReply lineReply);
	/**
	 *하위 글 조회수
	 * @param linereplyId
	 * @return
	 */
	public Integer countChildren(String linereplyId);
	/**
	 *관련글 모두 삭제
	 * @param linereplyId
	 */
	public void removeAll(String linereplyId);
	/**
	 *매뉴얼 관련글 모두 삭제
	 * @param manualId
	 */
	public void removeByManualId(String manualId);
}
