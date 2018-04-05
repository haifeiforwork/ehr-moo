/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardItemVersion;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.BoardItemVersionSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author happyi1018
 * @version $Id: BoardItemVersionService.java 16487 2011-09-06 01:34:40Z giljae $
 */
@Transactional
public interface BoardItemVersionService {

	/**
	 * 게시물 버전 목록
	 * @param searchCondition
	 * @return
	 */
	public SearchResult<BoardItemVersion> listBoardItemBySearchCondition(BoardItemVersionSearchCondition searchCondition);
	/**
	 * 게시물 버전별 비교
	 * @param vsrsionIds
	 * @return
	 */
	public BoardItemVersion compareBoardItemVersion(List<String> vsrsionIds);
	
	/**
	 * 게시물 선택한 버전으로 되돌리기
	 * @param versionId
	 * @return
	 */
	public int returnBoardItemVersion(String versionId,User user);
	
}
