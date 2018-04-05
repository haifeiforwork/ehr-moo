/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.service;

import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * BoardItemService Test 클래스
 *
 * @author 최현식
 * @version $Id: BoardItemFixture.java 16302 2011-08-19 08:43:50Z giljae $
 */
public class BoardItemFixture {

	public static Board creatBoard() {
		Board board = new Board();
		board.setPortalId("portalId");
		board.setBoardParentId(null); /** 부모 게시판 ID */
		board.setSortOrder(0);        /** 같은 부모내의 게시판 형제들간의 순서 */
		board.setBoardName("JUNIT-테스트용-게시판"); /** 게시판 이름 */
		board.setBoardRootId("0");                 /** 게시판 루트 ID*/
		board.setBoardDescription("JUNIT-테스트용-게시판"); /** 게시판 설명*/
		board.setBoardType("0"); /** 게시판 타입*/
		board.setUrl(""); /** URL */
		board.setListType("0"); 	/** 리스트 타입 */
		board.setAnonymous(1); /** 익명 모드 여부 */
		board.setRss(1); /** RSS_ATOM 허용 여부 */
		board.setFileSize(100L); /** 허용 첨부파일 크기 */
		board.setImageFileSize(100L); /** 허용 위지윅에디터내의 이미지 크기 */
		board.setImageWidth(1000); /** 이미지 넓이 */
		board.setPageNum(20); /** 페이지당 레코드 갯수 */
		board.setDocPopup(1); /** 게시글 상세 보기 팝업 모드 */
		board.setReply(1); /** 답글 허용 여부 */
		board.setLinereply(1); /** 댓글 허용 여부 */
		board.setRestrictionType(""); /** 첨부 불가 파일 확장자 */
		board.setReadPermission(1); /** 게시판 읽기 여부 */
		board.setWritePermission(1); /** 게시판 쓰기 여부 */

		ModelBeanUtil.bindRegisterInfo(board, "testuser", "testuser");

		return board;
	}

}
