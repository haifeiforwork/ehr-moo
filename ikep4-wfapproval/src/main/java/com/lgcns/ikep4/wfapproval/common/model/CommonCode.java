package com.lgcns.ikep4.wfapproval.common.model;

import java.util.Arrays;
import java.util.List;

public class CommonCode {
	
	//-------------------------------------------------------------------
	//	User : iKEP Session Info
	//-------------------------------------------------------------------
	/**
	 * iKEP-User Session Info
	 */
	public static final String IKEP_USER		= "ikep.user";
	/**
	 * iKEP-Portal Session Info
	 */
	public static final String IKEP_PORTAL		= "ikep.portal";
	//-------------------------------------------------------------------
	//	ApForm
	//-------------------------------------------------------------------
	/**
	 * 양식유형 목록
	 */
	public static final String FORM_CLASS_CD 	= "FORM_CLASS_CD";
	/**
	 * 양식구분 목록
	 */
	public static final String FORM_TYPE_CD 	= "FORM_TYPE_CD";
	//-------------------------------------------------------------------
	//	ApFormTpl
	//-------------------------------------------------------------------
	/**
	 * 결재요청시 통보대상
	 */
	public static final String MAIL_REQ_CD 		= "MAIL_REQ_CD";
	/**
	 * 결재완료시 통보대상
	 */
	public static final String MAIL_END_CD 		= "MAIL_END_CD";
	/**
	 * 결재요청시 통보방법
	 */
	public static final String MAIL_REQ_WAY_CD 	= "MAIL_REQ_WAY_CD";
	/**
	 * 결재완료시 통보방법
	 */
	public static final String MAIL_END_WAY_CD 	= "MAIL_END_WAY_CD";
	/**
	 * 합의
	 */
	public static final String DISCUSS_CD 		= "DISCUSS_CD";
	/**
	 * 보존연한
	 */
	public static final String APPR_PERIOD_CD 	= "APPR_PERIOD_CD";
	/**
	 * 결재구분
	 */
	public static final String APPR_TYPE_CD 	= "APPR_TYPE_CD";
	/**
	 * 결재문서종류
	 */
	public static final String APPR_DOC_CD 		= "APPR_DOC_CD";
	/**
	 * 문서상태
	 */
	public static final String APPR_DOC_STATE	= "APPR_DOC_STATE";	
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> LIST_TYPE_LIST = Arrays.asList(
			new Code<String>("D-LT",  "ui.wfapproval.common.searchCondition.listType.defaultLayoutList"),
			new Code<String>("D-SY",  "ui.wfapproval.common.searchCondition.listType.defaultLayoutSummary"),
			new Code<String>("D-GY",  "ui.wfapproval.common.searchCondition.listType.defaultLayoutGallery"),
			new Code<String>("2F-LT", "ui.wfapproval.common.searchCondition.listType.2FrameLayoutList"),
			new Code<String>("2F-SY", "ui.wfapproval.common.searchCondition.listType.2FrameLayoutSummary"),
			new Code<String>("2F-GY", "ui.wfapproval.common.searchCondition.listType.2FrameLayoutGallery") 
	);
	
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
	private static final List<Code<Integer>> YES_NO_LIST = Arrays.asList(
			new Code<Integer>(1, "ui.wfapproval.common.code.yes"),
			new Code<Integer>(0, "ui.wfapproval.common.code.no")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> APPROVAL_REJECT_LIST = Arrays.asList(
			new Code<Integer>(1, "ui.wfapproval.common.code.approval"),
			new Code<Integer>(0, "ui.wfapproval.common.code.reject")
	); 
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> USE_UNUSE_LIST = Arrays.asList(
			new Code<Integer>(1, "ui.wfapproval.common.code.use"),
			new Code<Integer>(0, "ui.wfapproval.common.code.unuse")
	);
	
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> USE_ADMIN_SELECT_LIST = Arrays.asList(
			new Code<String>("U", "ui.wfapproval.common.code.user"),
			new Code<String>("A", "ui.wfapproval.common.code.admin")
	);

	/**
	 * @return the useAdminSelectList
	 */
	public List<Code<String>> getUseAdminSelectList() {
		return USE_ADMIN_SELECT_LIST;
	}

	public List<Code<String>> getListTypeList() {
		return LIST_TYPE_LIST;
	}

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

	public List<Code<Integer>> getYesNoList() {
		return YES_NO_LIST;
	}  
	public List<Code<Integer>> getUseUnuseList() {
		return USE_UNUSE_LIST;
	}  
	
	public List<Code<Integer>> getApprovalRejectList() {
		return APPROVAL_REJECT_LIST;
	}  
}
