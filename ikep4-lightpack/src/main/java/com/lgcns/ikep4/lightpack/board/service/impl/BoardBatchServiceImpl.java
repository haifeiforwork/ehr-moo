/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.lightpack.board.dao.BoardDao;
import com.lgcns.ikep4.lightpack.board.dao.BoardItemDao;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.service.BoardBatchService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 게시판 배치 서비스 클래스
 *
 * @author 최현식
 * @version $Id: BoardBatchServiceImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Service
public class BoardBatchServiceImpl implements BoardBatchService {

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private BoardItemDao boardItemDao;
	
	@Autowired
	private MailSendService mailSendService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardBatchService#adminDeleteBoardItemForBatch(java.lang.String)
	 */
	public void adminDeleteBoardItemForBatch(String itemId) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardBatchService#listDeletePassedBoardItem(java.lang.Integer)
	 */
	public List<BoardItem> listBatchDeletePassedBoardItem(Integer getCount) {
		return this.boardItemDao.listBatchDeletePassedBoardItem(getCount);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardBatchService#listDeleteStatusBoardItem(java.lang.Integer)
	 */
	public List<BoardItem> listBatchDeleteStatusBoardItem(Integer getCount) {
		return this.boardItemDao.listBatchDeleteStatusBoardItem(getCount);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardBatchService#listBatchBoardItemInDeletedBoard(java.lang.Integer)
	 */
	public List<BoardItem> listByBoardItemOfDeletedBoard(String boardId, Integer getCount) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("boardId", boardId);
		parameter.put("getCount", getCount);

		return this.boardItemDao.listByBoardItemOfDeletedBoard(parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardBatchService#nextDeletedBoard()
	 */
	public Board nextDeletedBoard() {
		return this.boardDao.nextDeletedBoard();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.board.service.BoardBatchService#deleteBoard(java.lang.String)
	 */
	public void physicalBoardDelete(String boardId) {
		this.boardDao.physicalDelete(boardId);
	}
	
	public void updateDisplayBoardParentItem(){
		this.boardDao.updateDisplayBoardParentItem();
	}
	
	public void updateDisplayBoardItem(){
		this.boardDao.updateDisplayBoardItem();
	}
	
	@Async
	public void sendUserMail(List<BoardItemReader>  boardItemReaderList,String contents,User user,String wordName){
		for (BoardItemReader boardItemReader : boardItemReaderList) {
	  	   if(StringUtil.isEmpty(boardItemReader.getReaderMail())){//메일주소 없는 사용자는 메일 보내지 않음.
			}else{
		
				if(!StringUtil.isEmpty(boardItemReader.getReaderPassword())){//패스워드 없는 자는 퇴사자 일것입니다.
					HashMap<String, String> recip= new HashMap<String, String>();
					recip.put("email", boardItemReader.getReaderMail());
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ boardItemReader.getReaderMail():"+boardItemReader.getReaderMail());
					
					
					Mail mail = new Mail();
					mail.setMailType(MailConstant.MAIL_TYPE_HTML);
					if(!StringUtil.isEmpty(wordName)){
						mail.setTitle("["+wordName+"] 공지사항이 등록되었습니다.");
					}else{
						mail.setTitle("[전사공지] 공지사항이 등록되었습니다.");
					}
					mail.setContent(contents);
					
					
					List recipients = new ArrayList();
					recipients.add(recip);
					
					/*
					Mail mail = new Mail();
					mail.setMailType(MailConstant.MAIL_TYPE_HTML);
					mail.setTitle("[전사공지] 공지사항이 등록되었습니다.");

					String linkUrl;
					try {
						linkUrl = commonprop.getProperty("ikep4.baseUrl")+"/messengerLogin.do?gubun=2&itemId="+boardItem.getBoardItemId()+"&j_username="+boardItemReader.getReaderId()+"&j_password="+URLEncoder.encode(boardItemReader.getReaderPassword(), "UTF-8");
						mail.setContent("<br><br>아래의 DocLink Icon을 Double Click하면 회람 문서로 이동합니다.<br><br><a href=\""+linkUrl+"\"><img src=\""+baseUrl+"/base/images/icon/ic_note_b.png\" border=\"0\"></a>");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					*/
					
					if(recipients.size() > 0) {
						mail.setToEmailList(recipients);
						Map dataMap = new HashMap();
						mailSendService.sendMailNotice(mail, dataMap, user);

					}
					
				}
			}
		}
	}
}
