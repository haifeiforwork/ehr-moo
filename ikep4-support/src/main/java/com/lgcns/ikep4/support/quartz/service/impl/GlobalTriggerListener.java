/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.quartz.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;


/**
 * Quartz Global Trigger Listener
 * 
 * @author 주길재
 * @version $Id: GlobalTriggerListener.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class GlobalTriggerListener implements TriggerListener {
	protected final Log log = LogFactory.getLog(GlobalTriggerListener.class);

	String listenerType = "Non global";

	public void setListenerType(String listenerType) {
		this.listenerType = listenerType;
	}

	public void triggerFired(Trigger trigger, JobExecutionContext ctx) {
		log.info("====================Trigger Fired!======================");
		log.info("[GlobalTriggerListener] TriggerName : " + trigger.getName());
		log.info("[GlobalTriggerListener] Trigger Run Time : " + new Date());
		log.info("[GlobalTriggerListener] Trigger End Time : " + trigger.getEndTime());
		log.info("=======================================================");
	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext ctx) {
		log.info("====================Trigger Veto!======================");
		log.info("[GlobalTriggerListener] TriggerName : " + trigger.getName());
		log.info("[GlobalTriggerListener] Trigger Run Time : " + new Date());
		log.info("[GlobalTriggerListener] Trigger End Time : " + trigger.getEndTime());
		log.info("=======================================================");
		return false;
	}

	public void triggerComplete(Trigger trigger, JobExecutionContext ctx, int arg) {
		log.info("====================Trigger Complete!======================");
		log.info("[GlobalTriggerListener] TriggerName : " + trigger.getName());
		log.info("[GlobalTriggerListener] Trigger Run Time : " + new Date());
		log.info("[GlobalTriggerListener] Trigger End Time : " + trigger.getEndTime());
		log.info("=======================================================");
	}

	public void triggerMisfired(Trigger trigger) {
		log.info("====================Trigger MisFired!======================");
		log.info("[GlobalTriggerListener] TriggerName : " + trigger.getName());
		log.info("[GlobalTriggerListener] Trigger Run Time : " + new Date());
		log.info("[GlobalTriggerListener] Trigger End Time : " + trigger.getEndTime());
		log.info("=======================================================");
	}

	public String getName() {
		return "GlobalTriggerListener";
	}
}
