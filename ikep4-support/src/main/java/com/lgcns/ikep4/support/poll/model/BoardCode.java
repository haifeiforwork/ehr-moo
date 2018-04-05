package com.lgcns.ikep4.support.poll.model;

import java.util.Arrays;
import java.util.List;


public class BoardCode {
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
