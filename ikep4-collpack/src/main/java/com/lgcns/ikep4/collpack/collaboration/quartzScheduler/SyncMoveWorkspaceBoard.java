/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.quartzScheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;


/**
 * Workspace 게시판 이관 배치
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: SyncMoveWorkspaceBoard.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class SyncMoveWorkspaceBoard extends QuartzJobBean {

	protected final Log log = LogFactory.getLog(this.getClass());

	private BoardAdminService boardAdminService;

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

			this.boardAdminService = (BoardAdminService) appContext.getBean("wsBoardAdminServiceImpl");

			// TODO Auto-generated method stub

			/**
			 * 게시판 이관 권한 적용
			 */
			this.log.debug("\r\n Workspace SyncMigrationBoard 배치 프로세스 시작 ----------------------------------------------------");
			boardAdminService.moveWorkspaceBoard();
			this.log.debug("\r\n Workspace SyncMigrationBoard 배치 프로세스 End ----------------------------------------------------");
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}