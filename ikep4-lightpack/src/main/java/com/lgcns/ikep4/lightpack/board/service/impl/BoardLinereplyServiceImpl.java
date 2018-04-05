/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardLinereplyDao;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardLinereply;
import com.lgcns.ikep4.lightpack.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * BoardLinereplyService 구현체 클래스
 */
@Service
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
	
	@Autowired
	private BoardItemService boardItemService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private MailSendService mailSendService;
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService#
	 * listBoardLinereplyBySearchCondition
	 * (com.lgcns.ikep4.lightpack.board.search.BoardLinereplySearchCondition)
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

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService#
	 * readBoardLinereply(java.lang.String)
	 */
	public BoardLinereply readBoardLinereply(String linereplyId) {
		BoardLinereply boardLinereply = this.boardLinereplyDao.get(linereplyId);

		return boardLinereply;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService#
	 * createBoardLinereply
	 * (com.lgcns.ikep4.lightpack.board.model.BoardLinereply)
	 */
	public String createBoardLinereply(BoardLinereply boardLinereply) {
		final String generatedId = this.idgenService.getNextId();

		boardLinereply.setLinereplyId(generatedId);
		boardLinereply.setLinereplyGroupId(generatedId);

		final String insertedId = this.boardLinereplyDao.create(boardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST,
				boardLinereply.getItemId(), boardLinereply.getContents());

		// 게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount(boardLinereply.getItemId());

		BoardItem boardItem = boardItemService.readBoardItem(boardLinereply.getItemId());
		
		if((boardItem.getBoardId().equals("100010083357")||boardItem.getBoardId().equals("100010089350")||boardItem.getBoardId().equals("100010089362")) && !boardItem.getRegisterId().equals(boardLinereply.getRegisterId())){
			Mail mail = new Mail();
			User user = this.userService.read(boardItem.getRegisterId());
			mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
			mail.setMailTemplatePath("linereplyMailTemplate.vm");
			
			List recipients = new ArrayList();
			HashMap<String, String> recip;
			recip = new HashMap<String, String>();

			recip.put("email", user.getMail());
			recip.put("name", user.getUserName());

			recipients.add(recip);
			mail.setToEmailList(recipients);
			
			Map dataMap = new HashMap();
			
			
			
			Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
	        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
			String url = "";
			url = baseUrl+"/lightpack/board/boardItem/readBoardItemView.do?&docPopup=true&boardId=" + boardItem.getBoardId()+ "&itemId="+boardItem.getItemId();
			
			dataMap.put("regdate", getToday("yyyy-MM-dd"));
			mail.setTitle(boardItem.getRegisterName()+"님이 작성한 게시글에 댓글이 등록되었습니다");
			dataMap.put("register", boardLinereply.getRegisterName());
			
			dataMap.put("content", boardLinereply.getContents());
			dataMap.put("url", url);
			
			User sender = user;
			
			mailSendService.sendMail(mail, dataMap, sender);
		}
		
		return insertedId;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService#
	 * createReplyBoardLinereply
	 * (com.lgcns.ikep4.lightpack.board.model.BoardLinereply)
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
		
		// 스텝값을 업데이트한다.
		this.boardLinereplyDao.updateStep(boardLinereply);

		final String insertedId = this.boardLinereplyDao.create(boardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST,
				boardLinereply.getItemId(), boardLinereply.getContents());

		

		// 게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount(boardLinereply.getItemId());
		
		

		return insertedId;
	}
	
	public String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService#
	 * updateBoardLinereply
	 * (com.lgcns.ikep4.lightpack.board.model.BoardLinereply)
	 */
	public void updateBoardLinereply(BoardLinereply boardLinereply) {
		this.boardLinereplyDao.update(boardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_LINE_REPLY_EDIT,
				boardLinereply.getItemId(), boardLinereply.getContents());

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService#
	 * adminDeleteBoardLinereply(java.lang.String, java.lang.String)
	 */
	public void adminDeleteBoardLinereply(String itemId, String linereplyId) {
		// 댓글을 가져온다.
		BoardLinereply boardLinereply = this.boardLinereplyDao.get(linereplyId);

		Integer childrenCount = this.boardLinereplyDao.countChildren(linereplyId);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS,
				IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, boardLinereply.getItemId(), boardLinereply.getContents());

		if (childrenCount == 0) {
			this.boardLinereplyDao.physicalDelete(linereplyId);

		} else {
			this.boardLinereplyDao.physicalDeleteThreadByLinereplyId(linereplyId);
		}

		// 게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount(itemId);

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService#
	 * userDeleteBoardLinereply
	 * (com.lgcns.ikep4.lightpack.board.model.BoardLinereply)
	 */
	public void userDeleteBoardLinereply(BoardLinereply boardLinereply) {
		// 하위 댓글 개수를 구한다.
		Integer count = this.boardLinereplyDao.countChildren(boardLinereply.getLinereplyId());

		// 하위 댓글이 존재하지 않으면
		if (count == 0) {
			this.adminDeleteBoardLinereply(boardLinereply.getItemId(), boardLinereply.getLinereplyId());

			// 하위 댓글이 존재하면
		} else {
			// 삭제여부 필드를 "1"로 업데이트 한다.
			this.boardLinereplyDao.logicalDelete(boardLinereply);

		}
		// 게시글의 댓글 갯수를 업데이트한다.
		this.boardItemDao.updateLinereplyCount(boardLinereply.getItemId());

	}

}
