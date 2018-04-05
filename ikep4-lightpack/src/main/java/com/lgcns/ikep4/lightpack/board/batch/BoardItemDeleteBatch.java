/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.batch;

import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.service.BoardBatchService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;


/**
 * 지난 게시글 자동 삭제 배치 클래스.
 * 
 * @author 최현식
 * @version $Id: BoardItemDeleteBatch.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class BoardItemDeleteBatch extends QuartzJobBean {

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());

	/** 게시판 배치 서비스 클래스. */
	private BoardBatchService boardBatchService;

	/** 게시물 서비스 클래스. */
	private BoardItemService boardItemService;

	/** 권한 서비스 클래스 */
	private ACLService aclService;

	/** 상수-반복시 가져와야 하는 갯수 */
	private final static Integer REPEAD_COUNT = 20;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org
	 * .quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.boardBatchService = (BoardBatchService) appContext.getBean("boardBatchServiceImpl");
			this.boardItemService = (BoardItemService) appContext.getBean("boardItemServiceImpl");
			this.aclService = (ACLService) appContext.getBean("aclService");

			/*
			 * 게시판의 알림기간 만료된 글들을 알림해제처리한다.
			 */
			this.boardBatchService.updateDisplayBoardParentItem();
			this.boardBatchService.updateDisplayBoardItem();

			/*
			 * 삭제 대상 게시글을 목록을 정해진 갯수만 큼 가져와서 삭제를 한다. 게시글의 삭제의 경우 시간이 많이 소모됨으로
			 * 정해진 갯수만큼 돌린다.
			 */
			this.log.debug("\r\n 게시물 삭제 배치 프로세스 시작 ----------------------------------------------------");

			Boolean exsistItem = Boolean.TRUE;

			List<BoardItem> deleteBoardItemList = null;

			Board board = null;

			while (true) {
				StringBuffer buffer = new StringBuffer();

				// 삭제 대상이 되는 게시판을 가져온다.
				board = this.boardBatchService.nextDeletedBoard();

				if (board == null) {
					break;
				}

				do {
					// 삭제 대상 게시글 목록 > 원본 게시글 만 가져옴
					// listByBoardItemOfDeletedBoard
					// WHERE BOARD_ID = #boardId# AND INDENTATION = 0
					deleteBoardItemList = this.boardBatchService.listByBoardItemOfDeletedBoard(board.getBoardId(),
							REPEAD_COUNT);

					buffer.append("\r\n[삭제대상 목록]");
					buffer.append("\r\n-------------------------------------------------------------------------------------");

					for (BoardItem boardItem : deleteBoardItemList) {
						buffer.append("\r\n- 게시판 ID :");
						buffer.append(boardItem.getBoardId());
						buffer.append(", 게시물 ID :");
						buffer.append(boardItem.getItemId());
						buffer.append(", 종료일 :");
						buffer.append(DateFormatUtils.format(boardItem.getEndDate(), "yyyy.MM.dd"));
					}

					this.log.debug(buffer.toString());

					buffer.delete(0, buffer.length());

					buffer.append("\r\n[삭제 작업 진행]");
					buffer.append("\r\n-------------------------------------------------------------------------------------");

					this.log.debug(buffer.toString());
					buffer.delete(0, buffer.length());

					if (deleteBoardItemList != null && !deleteBoardItemList.isEmpty()) {
						for (BoardItem boardItem : deleteBoardItemList) {
							buffer.append("\r\n삭제처리중 ...... ");
							buffer.append("게시글 ID :");
							buffer.append(boardItem.getItemId());

							this.log.debug(buffer.toString());
							buffer.delete(0, buffer.length());

							try {
								this.boardItemService.deleteBoardItemThread(boardItem);
								buffer.append("\r\n삭제완료 ...... ");

								this.log.debug(buffer.toString());
								buffer.delete(0, buffer.length());

							} catch (Exception ex) {
								buffer.append("\r\n삭제실패...... ");

								this.log.debug(buffer.toString());
								buffer.delete(0, buffer.length());

								this.log.error(ex.getStackTrace());

								exsistItem = Boolean.FALSE;
							}
						}

					} else {
						exsistItem = Boolean.FALSE;
					}
				} while (exsistItem);

				// 게시판 삭제
				this.boardBatchService.physicalBoardDelete(board.getBoardId());

				// 권한을 삭제한다.
				this.aclService.deleteSystemPermission(Board.BOARD_ACL_CLASS_NAME, board.getBoardId());

			}

			exsistItem = Boolean.TRUE;

			do {
				StringBuffer buffer = new StringBuffer();

				// 게시기간이 지난 게시물 목록
				// WHERE ITEM_ID = ITEM_GROUP_ID
				// AND END_DATE < DATEADD(DD, 1, CURRENT_TIMESTAMP)
				deleteBoardItemList = this.boardBatchService.listBatchDeletePassedBoardItem(REPEAD_COUNT);

				if (deleteBoardItemList == null || deleteBoardItemList.isEmpty()) {
					// 삭제 게시물
					// WHERE ITEM_DELETE = 2
					deleteBoardItemList = this.boardBatchService.listBatchDeleteStatusBoardItem(REPEAD_COUNT);
				}

				buffer.append("\r\n[삭제대상 목록]");
				buffer.append("\r\n-------------------------------------------------------------------------------------");

				for (BoardItem boardItem : deleteBoardItemList) {
					buffer.append("\r\n- 게시판 ID :");
					buffer.append(boardItem.getBoardId());
					buffer.append(", 게시물 ID :");
					buffer.append(boardItem.getItemId());
					buffer.append(", 종료일 :");
					buffer.append(DateFormatUtils.format(boardItem.getEndDate(), "yyyy.MM.dd"));
				}

				this.log.debug(buffer.toString());

				buffer.delete(0, buffer.length());

				buffer.append("\r\n[삭제 작업 진행]");
				buffer.append("\r\n-------------------------------------------------------------------------------------");

				this.log.debug(buffer.toString());
				buffer.delete(0, buffer.length());

				if (deleteBoardItemList != null && !deleteBoardItemList.isEmpty()) {
					for (BoardItem boardItem : deleteBoardItemList) {
						buffer.append("\r\n삭제처리중 ...... ");
						buffer.append("게시글 ID :");
						buffer.append(boardItem.getItemId());

						this.log.debug(buffer.toString());
						buffer.delete(0, buffer.length());

						try {
							this.boardItemService.deleteBoardItemThread(boardItem);
							buffer.append("\r\n삭제완료 ...... ");

							this.log.debug(buffer.toString());
							buffer.delete(0, buffer.length());

						} catch (Exception ex) {
							buffer.append("\r\n삭제실패...... ");

							this.log.debug(buffer.toString());
							buffer.delete(0, buffer.length());

							this.log.error(ex.getStackTrace());

							exsistItem = Boolean.FALSE;
						}
					}

				} else {
					exsistItem = Boolean.FALSE;
				}

			} while (exsistItem);

			this.log.debug("\r\n--------------------------------------------------- 게시물 삭제 배치 프로세스 종료 ");
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

}
