package com.lgcns.ikep4.approval.admin.model;

import java.util.Arrays;
import java.util.List;

public class ApprDefLineCode {
	
	


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
	private static final List<Code<Integer>> FILE_SIZE_LIST = Arrays.asList(
			new Code<Integer>(1, "1"),
			new Code<Integer>(2, "2"),
			new Code<Integer>(5, "5"),
			new Code<Integer>(10, "10"),
			new Code<Integer>(20, "20") 
	);
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> IMAGE_FILE_SIZE_LIST = Arrays.asList(
			new Code<Integer>(1000, "1000"),
			new Code<Integer>(2000, "2000"),
			new Code<Integer>(3000, "3000"),
			new Code<Integer>(4000, "4000"),
			new Code<Integer>(5000, "5000") 
	);
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> IMAGE_WIDTH_LIST = Arrays.asList(
			new Code<Integer>(300, "300"),
			new Code<Integer>(500, "500"),
			new Code<Integer>(800, "800"),
			new Code<Integer>(1024, "1024"),
			new Code<Integer>(2048, "2048") 
	);
	
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> USE_UNUSE_LIST = Arrays.asList(
			new Code<Integer>(1, "ui.approval.common.apprDefLine.code.use"),
			new Code<Integer>(0, "ui.approval.common.apprDefLine.code.unuse")
	);

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> DEF_LINE_TYPE_LIST = Arrays.asList(
			new Code<Integer>(1, "ui.approval.common.apprDefLine.code.collAppr"),
			new Code<Integer>(0, "ui.approval.common.apprDefLine.code.innerAppr")
	);

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> APPR_TYPE_LIST = Arrays.asList(
			new Code<Integer>(0, "ui.approval.common.apprDefLine.code.appr"),
			new Code<Integer>(1, "ui.approval.common.apprDefLine.code.agree"),
			new Code<Integer>(2, "ui.approval.common.apprDefLine.code.choiceAgree"),
			new Code<Integer>(3, "ui.approval.common.apprDefLine.code.receive")
	);
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> APPROVER_WAY_LIST = Arrays.asList(
			new Code<Integer>(0, "ui.approval.common.apprDefLine.code.direct"),
			new Code<Integer>(1, "ui.approval.common.apprDefLine.code.indirect")
	);
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> APPROVER_TYPE_LIST = Arrays.asList(
			new Code<Integer>(0, "ui.approval.common.apprDefLine.code.user"),
			new Code<Integer>(1, "ui.approval.common.apprDefLine.code.dept"),
			new Code<Integer>(2, "ui.approval.common.apprDefLine.code.role")
	);
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> APPR_STATUS_LIST = Arrays.asList(
			new Code<Integer>(0, "ui.approval.common.apprDefLine.code.pending"),
			new Code<Integer>(1, "ui.approval.common.apprDefLine.code.progress"),
			new Code<Integer>(2, "ui.approval.common.apprDefLine.code.approval"),
			new Code<Integer>(3, "ui.approval.common.apprDefLine.code.rejectMsg"),
			new Code<Integer>(4, "ui.approval.common.apprDefLine.code.agreement")
	);	
	
	public List<Code<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}

	public List<Code<Integer>> getFileSizeList() {
		return FILE_SIZE_LIST;
	}

	public List<Code<Integer>> getImageFileSizeList() {
		return IMAGE_FILE_SIZE_LIST;
	}

	public List<Code<Integer>> getImageWidthList() {
		return IMAGE_WIDTH_LIST;
	}

	public List<Code<Integer>> getUseUnuseList() {
		return USE_UNUSE_LIST;
	}  
	
	public List<Code<Integer>> getDefLineTypeList() {
		return DEF_LINE_TYPE_LIST;
	}

	public List<Code<Integer>> getApprTypeList() {
		return APPR_TYPE_LIST;
	}

	public List<Code<Integer>> getApproverWayList() {
		return APPROVER_WAY_LIST;
	}

	public List<Code<Integer>> getApproverTypeList() {
		return APPROVER_TYPE_LIST;
	}  
	public List<Code<Integer>> getApprStatusList() {
		return APPR_STATUS_LIST;
	}  	
}
