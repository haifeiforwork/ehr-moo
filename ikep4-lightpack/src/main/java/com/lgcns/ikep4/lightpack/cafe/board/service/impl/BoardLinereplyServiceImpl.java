/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.board.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.cafe.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.cafe.board.model.BoardLinereply;
import com.lgcns.ikep4.lightpack.cafe.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardLinereplyService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardLinereplyService 구현체 클래스
 */
@Service("cfBoardLinereplyServiceImpl")
@Transactional
public class BoardLinereplyServiceImpl implements BoardLinereplyService {

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	/** The board linereply dao. */
	@Autowired
	private BoardLinereplyDao boardLinereplyDao;

	/** The board item dao. */
	@Autowired
	private BoardItemDao boardItemDao;

	@Autowired
	private ActivityStreamService activityStreamService;

	/**
	 * 게시물 댓글 목록
	 */
	public SearchResult<BoardLinereply> listBoardLinereplyBySearchCondition(BoardLinereplySearchCondition searchCondtion) {
		Integer count = this.boardLinereplyDao.countBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<BoardLinereply> result = null;

		if (searchCondtion.isEmptyRecord()) {
			result = new SearchResult<BoardLinereply>(searchCondtion);

		} else {
			List<BoardLinereply> boardLinereplyList = this.boardLinereplyDao.listBySearchCondition(searchCondtion);

			result = new SearchResult<BoardLinereply>(boardLinereplyList, searchCondtion);
		}

		return result;
	}

	/**
	 * 게시물 댓글 조회
	 */
	public BoardLinereply readBoardLinereply(String linereplyId) {
		BoardLinereply boardLinereply = this.boardLinereplyDao.get(linereplyId);

		return boardLinereply;
	}

	/**
	 * 댓글 등록
	 */
	public String createBoardLinereply(BoardLinereply boardLinereply) {
		final String generatedId = this.idgenService.getNextId();

		boardLinereply.setLinereplyId(generatedId);
		boardLinereply.setLinereplyGroupId(generatedId);

		final String insertedId = this.boardLinereplyDao.create(boardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST,
				boardLinereply.getItemId(), boardLinereply.getContents());

		// 게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount(boardLinereply.getItemId());

		return insertedId;
	}

	/**
	 * 댓글에 대한 댓글 등록
	 */
	public String createReplyBoardLinereply(BoardLinereply boardLinereply) {

		BoardLinereply parent = this.boardLinereplyDao.get(boardLinereply.getLinereplyParentId());

		boardLinereply.setItemId(parent.getItemId());
		boardLinereply.setLinereplyGroupId(parent.getLinereplyGroupId());
		boardLinereply.setIndentation(parent.getIndentation() + 1);
		boardLinereply.setLinereplyDelete(0);
		boardLinereply.setStep(parent.getStep() + 1);

		final String generatedId = this.idgenService.getNextId();

		boardLinereply.setLinereplyId(generatedId);

		final String insertedId = this.boardLinereplyDao.create(boardLinereply);

		// 스텝값을 업데이트한다.
		this.boardLinereplyDao.updateStep(boardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST,
				boardLinereply.getItemId(), boardLinereply.getContents());

		// 게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount(boardLinereply.getItemId());

		return insertedId;
	}

	/**
	 * 댓글 수정
	 */
	public void updateBoardLinereply(BoardLinereply boardLinereply) {
		this.boardLinereplyDao.update(boardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE, IKepConstant.ACTIVITY_CODE_LINE_REPLY_EDIT,
				boardLinereply.getItemId(), boardLinereply.getContents());

	}

	/**
	 * 댓글 삭제 - 관리자
	 */
	public void adminDeleteBoardLinereply(String itemId, String linereplyId) {
		// 댓글을 가져온다.
		BoardLinereply boardLinereply = this.boardLinereplyDao.get(linereplyId);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE,
				IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, boardLinereply.getItemId(), boardLinereply.getContents());

		this.boardLinereplyDao.physicalDeleteThreadByLinereplyId(linereplyId);

		// 게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount(itemId);

	}

	/**
	 * 댓글 삭제 - 사용자
	 */
	public void userDeleteBoardLinereply(BoardLinereply boardLinereply) {
		// 하위 댓글 개수를 구한다.
		Integer count = this.boardLinereplyDao.countChildren(boardLinereply.getLinereplyId());

		// 하위 댓글이 존재하지 않으면
		if (count == 0) {
			// Activity Stream 댓글 등록
			this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_CAFE,
					IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, boardLinereply.getItemId(),
					boardLinereply.getContents());

			// 물리적으로 삭제한다.
			this.boardLinereplyDao.physicalDeleteThreadByLinereplyId(boardLinereply.getLinereplyId());

			// 하위 댓글이 존재하면
		} else {
			// 삭제여부 필드를 "1"로 업데이트 한다.
			this.boardLinereplyDao.logicalDelete(boardLinereply);

		}
		// 게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount(boardLinereply.getItemId());

	}

}
