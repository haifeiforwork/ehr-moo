/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItemVersion;
import com.lgcns.ikep4.lightpack.cafe.board.search.BoardItemVersionSearchCondition;


/**
 * TODO Javadoc주석작성
 * 
 * @author happyi1018
 * @version $Id: BoardItemVersionDao.java 16240 2011-08-18 04:08:15Z giljae $
 */
public interface BoardItemVersionDao extends GenericDao<BoardItemVersion, String> {

	/**
	 * 게시물 버전 목록
	 * 
	 * @param searchCondition
	 * @return
	 */
	List<BoardItemVersion> listBySearchCondition(BoardItemVersionSearchCondition searchCondition);

	/**
	 * 게시물 버전 목록 갯수
	 * 
	 * @param searchCondition
	 * @return
	 */
	Integer countBySearchCondition(BoardItemVersionSearchCondition searchCondition);

	/**
	 * 버전 게시물 조회
	 * 
	 * @param versionId
	 * @return
	 */
	public BoardItemVersion getVersionItem(String versionId);

	/**
	 * 버전 게시물 조회 - 버전 최근 1개
	 */
	public BoardItemVersion getMaxVersionId(String itemId);

	/**
	 * 버전 게시물 삭제
	 * 
	 * @param itemId
	 */
	public void physicalDelete(String itemId);

	/**
	 * 버전 게시물 하위 정보 삭제
	 * 
	 * @param itemId
	 */
	public void physicalDeleteThread(String itemId);

}
