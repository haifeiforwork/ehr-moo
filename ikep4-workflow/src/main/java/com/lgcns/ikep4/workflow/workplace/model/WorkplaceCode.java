/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.workplace.model;

import java.util.Arrays;
import java.util.List;

/**
 * TODO Javadoc주석작성
 *
 * @author 이재경
 * @version $Id: WorkplaceCode.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class WorkplaceCode {

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> PAGE_NUM_LIST = Arrays.asList(
			new Code<Integer>(10, "10"),
			new Code<Integer>(15, "15"),
			new Code<Integer>(20, "20"),
			new Code<Integer>(30, "30"),
			new Code<Integer>(40, "40"),
			new Code<Integer>(50, "50")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> YES_NO_LIST = Arrays.asList(
			new Code<Integer>(1, "ui.communication.commoon.code.yes"),
			new Code<Integer>(0, "ui.communication.commoon.code.no")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> USE_UNUSE_LIST = Arrays.asList(
			new Code<Integer>(1, "ui.communication.commoon.code.use"),
			new Code<Integer>(0, "ui.communication.commoon.code.unuse")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> WORK_SEARCH_CLASS_LIST = Arrays.asList(
			new Code<String>("PROCESS",  "ui.workflow.workplace.worklist.process"),
			new Code<String>("ACTIVITY",  "ui.workflow.workplace.worklist.activity"),
			new Code<String>("TITLE",  "ui.workflow.workplace.worklist.title")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> PROC_SEARCH_CLASS_LIST = Arrays.asList(
			new Code<String>("PROCESS",  "ui.workflow.workplace.worklist.process"),
			new Code<String>("ACTIVITY",  "ui.workflow.workplace.worklist.activity")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> DELEGATE_CLASS_LIST = Arrays.asList(
			new Code<String>("Y",  "ui.workflow.workplace.personal.setting"),
			new Code<String>("N",  "ui.workflow.workplace.personal.unsetting")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> SEARCH_PERIOD_LIST = Arrays.asList(
			new Code<String>("0",  "ui.workflow.workplace.statistics.today"),
			new Code<String>("7",  "ui.workflow.workplace.statistics.week"),
			new Code<String>("30",  "ui.workflow.workplace.statistics.1month"),
			new Code<String>("60",  "ui.workflow.workplace.statistics.2month")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> SEARCH_SMS_CONDITION_LIST = Arrays.asList(
			new Code<String>("CONTENT",  	"ui.workflow.admin.smsAdministration.smsHistoryList.contents"),
			new Code<String>("RECEIVER",	"ui.workflow.admin.smsAdministration.smsHistoryList.receiver"),
			new Code<String>("PHONENO",  	"ui.workflow.admin.smsAdministration.smsHistoryList.receiverphone")
	);

	public List<Code<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}

	public List<Code<Integer>> getYesNoList() {
		return YES_NO_LIST;
	}  
	public List<Code<Integer>> getUseUnuseList() {
		return USE_UNUSE_LIST;
	}  
	
	public List<Code<String>> getProcSearchClassList() {
		return PROC_SEARCH_CLASS_LIST;
	}
	
	public List<Code<String>> getWorkSearchClassList() {
		return WORK_SEARCH_CLASS_LIST;
	}
	
	public List<Code<String>> getDelegateClassList() {
		return DELEGATE_CLASS_LIST;
	}
	
	public List<Code<String>> getSearchPeriodList() {
		return SEARCH_PERIOD_LIST;
	}
	
	public List<Code<String>> getSearchSmsConditionList() {
		return SEARCH_SMS_CONDITION_LIST;
	}
}