package com.lgcns.ikep4.lightpack.cafe.cafe.model;

import java.util.Arrays;
import java.util.List;


/**
 * CafeCode
 * 
 * @author 유승목
 * @version $Id: CafeCode.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class CafeCode {

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> CAFE_STATUS_LIST = Arrays.asList(new Code<String>("WO", "WO"), // 개설신청
			new Code<String>("O", "O"), // 개설승인
			new Code<String>("WC", "WC"), // 폐쇄신청
			new Code<String>("C", "C"), // 폐쇄
			new Code<String>("WR", "WR") // 개설반려
			);

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> MEMBER_LEVEL_LIST = Arrays.asList(new Code<String>("1", "1"), // 시샵
			new Code<String>("2", "2"), // 운영진
			new Code<String>("3", "3"), // 정회원
			new Code<String>("4", "4"), // 준회원
			new Code<String>("5", "5") // 가입대기
			);

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> MEMBER_JOIN_TYPE = Arrays.asList(new Code<String>("0", "0"), // 본인
																											// 가입
			new Code<String>("1", "1") // 시샵 가입
			);

	@SuppressWarnings("unchecked")
	private static final List<Code<String>> LIST_TYPE_LIST = Arrays.asList(new Code<String>("D-LT",
			"ui.communication.commoon.searchCondition.listType.defaultLayoutList"), new Code<String>("D-SY",
			"ui.communication.commoon.searchCondition.listType.defaultLayoutSummary"), new Code<String>("D-GY",
			"ui.communication.commoon.searchCondition.listType.defaultLayoutGallery"), new Code<String>("2F-LT",
			"ui.communication.commoon.searchCondition.listType.2FrameLayoutList"), new Code<String>("2F-SY",
			"ui.communication.commoon.searchCondition.listType.2FrameLayoutSummary"), new Code<String>("2F-GY",
			"ui.communication.commoon.searchCondition.listType.2FrameLayoutGallery"));

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> PAGE_NUM_LIST = Arrays.asList(new Code<Integer>(10, "10"),
			new Code<Integer>(15, "15"), new Code<Integer>(20, "20"), new Code<Integer>(30, "30"), new Code<Integer>(
					40, "40"), new Code<Integer>(50, "50"));

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> FILE_SIZE_LIST = Arrays.asList(new Code<Integer>(1, "1"),
			new Code<Integer>(2, "2"), new Code<Integer>(5, "5"), new Code<Integer>(10, "10"), new Code<Integer>(20,
					"20"));

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> IMAGE_FILE_SIZE_LIST = Arrays.asList(new Code<Integer>(1000, "1000"),
			new Code<Integer>(2000, "2000"), new Code<Integer>(3000, "3000"), new Code<Integer>(4000, "4000"),
			new Code<Integer>(5000, "5000"));

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> IMAGE_WIDTH_LIST = Arrays.asList(new Code<Integer>(300, "300"),
			new Code<Integer>(500, "500"), new Code<Integer>(800, "800"), new Code<Integer>(1024, "1024"),
			new Code<Integer>(2048, "2048"));

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> YES_NO_LIST = Arrays.asList(new Code<Integer>(1,
			"ui.communication.commoon.code.yes"), new Code<Integer>(0, "ui.communication.commoon.code.no"));

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> APPROVAL_REJECT_LIST = Arrays.asList(new Code<Integer>(1,
			"ui.communication.commoon.code.approval"), new Code<Integer>(0, "ui.communication.commoon.code.reject"));

	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> USE_UNUSE_LIST = Arrays.asList(new Code<Integer>(1,
			"ui.communication.commoon.code.use"), new Code<Integer>(0, "ui.communication.commoon.code.unuse"));

	public List<Code<String>> getCafeStatusList() {
		return CAFE_STATUS_LIST;
	}

	public List<Code<String>> getMemberLevelList() {
		return MEMBER_LEVEL_LIST;
	}

	public List<Code<String>> getMemberJoinType() {
		return MEMBER_JOIN_TYPE;
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
