/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.lightpack.board.service.BoardBatchService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;


/**
 * 지난 게시글 자동 삭제 배치 클래스.
 * 
 * @author 최현식
 * @version $Id: BoardItemDeleteBatch.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class BoardItemDisplayClearBatch extends QuartzJobBean {

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

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

}
