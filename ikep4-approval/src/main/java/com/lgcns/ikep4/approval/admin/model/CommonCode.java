package com.lgcns.ikep4.approval.admin.model;

import java.util.Arrays;
import java.util.List;

import com.lgcns.ikep4.framework.constant.IKepConstant;

public class CommonCode {
	
	/**
	 * iKEP-User Session Info
	 */
	public static final String IKEP_USER					= "ikep.user";

	// APPROVAL
	public static final String ITEM_TYPE 					= IKepConstant.ITEM_TYPE_CODE_APPROVAL;
	
	// 문서 공통코드
	public static final String APPR_CODE 					= "APPR_CODE";
	
	// 문서유형
	public static final String APPR_CATEGORY 				= "APPR_CATEGORY";
	
	// 보존연한
	public static final String APPR_PERIOD 					= "APPR_PERIOD";
	public static final String APPR_PERIOD_DEFAULT_CODE 	= "APPR_PERIOD_DEFAULT_CODE";
	
	// 문서구분
	public static final String APPR_DOC 					= "APPR_DOC";
	
	// 결제선 수정여부
	public static final int APPR_IS_DEF_LINE_UPDATE 		= 0;
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> PAGE_NUM_LIST = Arrays.asList(
			new Code<Integer>(10, "10"),
			new Code<Integer>(15, "15"),
			new Code<Integer>(20, "20"),
			new Code<Integer>(30, "30"),
			new Code<Integer>(40, "40"),
			new Code<Integer>(50, "50")
	);

	public List<Code<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}
}
