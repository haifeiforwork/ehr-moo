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

import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;

/**
 * 워크스페이스 삭제 배치 작업
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: RemoveWorkspace.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class RemoveWorkspace extends QuartzJobBean {
	

	protected final Log log = LogFactory.getLog(this.getClass());
	
	private WorkspaceService workspaceService;

	

	

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.workspaceService = (WorkspaceService) appContext.getBean("workspaceService");

			// TODO Auto-generated method stub
			/**
			 * 1. 삭제된 Workspace 조회
			 * 2. 삭제된 Workspace 내의  게시판 삭제처리
			 * 		1. 삭제된 Workspace 내의  게시판 조회
			 * 		2. 게시판 내의 첨부가 등록된 게시물 조회
			 * 		3. 게시물의 첨부파일 및 첨부 DB 삭제
			 * 		4. 게시판 삭제 프로시져 실행
			 * 3. 삭제된 Workspace 내의  공지사항중 첨부가 있는 게시물 조회
			 * 		1. 공지사항의 첨부 파일 삭제 및 첨부 DB 삭제
			 * 4. 삭제된 Workspace 내의 주간보고 중 첨부가 있는 게시물 조회
			 * 		1. 주간보고의 첨부 파일 삭제 및 첨부 DB 삭제
			 * 5. 해당 Workspace 삭제 프로시져 실행
			 * 2~5 반복
			 */
			
			this.log.debug("\r\n Workspace 삭제 배치 프로세스 시작 ----------------------------------------------------");
			
			this.workspaceService.batchDeleteWorkspace();
			
			this.log.debug("\r\n Workspace 삭제 배치 프로세스 종료 ----------------------------------------------------");
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}