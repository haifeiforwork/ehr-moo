/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.quartzScheduler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.WorkspaceConstants;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 팀 신규 사원 Workspace 신규 자동 생성 배치
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: SyncUserWorkspace.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class SyncUserWorkspace extends QuartzJobBean {

	protected final Log log = LogFactory.getLog(this.getClass());

	private WorkspaceService workspaceService;

	private ACLService aclService;

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

			this.workspaceService = (WorkspaceService) appContext.getBean("workspaceService");
			this.aclService = (ACLService) appContext.getBean("aclService");

			// TODO Auto-generated method stub

			String workspaceAdminId;

			List<User> userList = aclService.listSystemAdminAsAll(WorkspaceConstants.ACL_CLASS_NAME);

			if (userList.isEmpty()) {
				workspaceAdminId = "admin";
			} else {
				workspaceAdminId = userList.get(0).getUserId();
			}

			this.log.debug("\r\n Workspace syncUserWorkspace 배치 프로세스 시작 ----------------------------------------------------");
			workspaceService.syncUserWorkspace(workspaceAdminId);
			this.log.debug("\r\n Workspace syncUserWorkspace 배치 프로세스 End ----------------------------------------------------");
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}
}