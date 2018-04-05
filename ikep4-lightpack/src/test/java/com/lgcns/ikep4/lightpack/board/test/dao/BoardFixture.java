/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.test.dao;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardLinereply;

/**
 * BoardFixture 클래스
 *
 * @author 최현식
 * @version $Id: BoardFixture.java 16302 2011-08-19 08:43:50Z giljae $
 */
public class BoardFixture {
	public final static Board fixtureBoard(String generatedId) {
		Board board = new Board();

		board.setBoardId(generatedId);
		board.setBoardParentId(null);
		board.setSortOrder(0);
		board.setBoardName("초이-테스트");
		board.setBoardRootId("0");
		board.setBoardDescription("초이-테스트-설명");
		board.setBoardType("2");
		board.setUrl("http://xxxxx.com");
		board.setListType("0");
		board.setAnonymous(1);
		board.setRss(1);
		board.setFileSize(4000L);
		board.setImageFileSize(5000L);
		board.setImageWidth(6000);
		board.setPageNum(10);
		board.setDocPopup(1);
		board.setReply(1);
		board.setLinereply(1);
		board.setRestrictionType("exe^com");
		board.setReadPermission(1);
		board.setWritePermission(1);
		board.setPortalId("0");
		board.setRegisterId("admin");
		board.setRegisterName("admin");
		board.setUpdaterId("admin");
		board.setUpdaterName("admin");
		board.setBoardDelete(0);

		return board;
	}

	public final static BoardItem fixtureBoardItem(String boardId, String generatedId) {
		BoardItem boardItem = new BoardItem();

		boardItem.setBoardId(boardId);
		boardItem.setItemParentId(null);
		boardItem.setItemGroupId(generatedId);
		boardItem.setStep(0);
		boardItem.setIndentation(0);
		boardItem.setItemType("TP");
		boardItem.setItemDisplay(0);
		boardItem.setTitle("TITLE");
		boardItem.setHitCount(0);
		boardItem.setRecommendCount(0);
		boardItem.setReplyCount(0);
		boardItem.setLinereplyCount(0);
		boardItem.setAttachFileCount(0);
		boardItem.setStartDate(DateUtils.addDays(new Date(), -5));
		boardItem.setEndDate(DateUtils.addDays(new Date(), +5));
		boardItem.setItemDelete(0);
		boardItem.setContents("werwwwwwwwwwwwwwwwwwwwwww");
		boardItem.setRegisterId("admin");
		boardItem.setRegisterName("admin");
		boardItem.setUpdaterId("admin");
		boardItem.setUpdaterName("admin");

		boardItem.setItemId(generatedId);

		return boardItem;
	}
	public final static BoardLinereply fixtureBoardLinereply(String itemId, String generatedId) {

		BoardLinereply boardLinereply = new BoardLinereply();

		boardLinereply.setLinereplyId(generatedId);
		boardLinereply.setItemId(itemId);
		boardLinereply.setLinereplyGroupId(generatedId);		//댓글 그룹 ID
		boardLinereply.setLinereplyParentId(null);		//댓글 부모 ID(하위 댓글이 없는 경우 댓글 ID와 동일)
		boardLinereply.setStep(0);		            //같은 댓글 그룹 Thread에 속해있는 댓글들 간의 순서. 같은 Thread에서 단순 정렬 순서
		boardLinereply.setIndentation(0);		    //댓글 Thread의 LEVEL. Thread 표시할때 들여쓰기의 숫자
		boardLinereply.setContents("zzzzz");		//댓글 내용
		boardLinereply.setLinereplyDelete(0);		//댓글 삭제 여부( 0 :  삭제전 댓글, 1 : 삭제된 댓글)
		boardLinereply.setRegisterId("admin");
		boardLinereply.setRegisterName("admin");
		boardLinereply.setUpdaterId("admin");
		boardLinereply.setUpdaterName("admin");

		return boardLinereply;
	}
}
