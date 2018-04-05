package com.lgcns.ikep4.collpack.collaboration.board.board.model;

import java.util.Arrays;
import java.util.List;


public class BoardCode {

	@SuppressWarnings("unchecked")
	private final List<Code<String>> followBoardPermission = Arrays.asList(new Code<String>("1",
			"message.collpack.collaboration.common.boardItem.permission.sameBoard"), new Code<String>("0",
			"message.collpack.collaboration.common.boardItem.permission.additional"));

	@SuppressWarnings("unchecked")
	private final List<Code<String>> boardTypeList = Arrays.asList(new Code<String>("0",
			"message.collpack.collaboration.common.board.boardType.board"), new Code<String>("1",
			"message.collpack.collaboration.common.board.boardType.dummy"), new Code<String>("2",
			"message.collpack.collaboration.common.board.boardType.urlLink"));

	@SuppressWarnings("unchecked")
	private final List<Code<String>> listTypeList = Arrays.asList(new Code<String>("0",
			"message.collpack.collaboration.common.searchCondition.listType.listType"), new Code<String>("1",
			"message.collpack.collaboration.common.searchCondition.listType.simpleType"), new Code<String>("2",
			"message.collpack.collaboration.common.searchCondition.listType.summaryType"), new Code<String>("3",
			"message.collpack.collaboration.common.searchCondition.listType.galleryType"));

	@SuppressWarnings("unchecked")
	private final List<Code<String>> permissionList = Arrays.asList(new Code<String>("1",
			"message.collpack.collaboration.board.boardAdmin.createBoardView.detail.readPermission.all"), new Code<String>(
			"2", "message.collpack.collaboration.board.boardAdmin.createBoardView.detail.readPermission.associate"),
			new Code<String>("3",
					"message.collpack.collaboration.board.boardAdmin.createBoardView.detail.readPermission.member"),
			new Code<String>("4",
					"message.collpack.collaboration.board.boardAdmin.createBoardView.detail.readPermission.individual"));
	
	@SuppressWarnings("unchecked")
	private final List<Code<String>> kmsPermissionList = Arrays.asList(new Code<String>("1",
			"message.collpack.collaboration.board.boardAdmin.createBoardView.detail.readPermission.all"), 
			new Code<String>("4",
					"message.collpack.collaboration.board.boardAdmin.createBoardView.detail.readPermission.individual"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> pageNumList = Arrays.asList(new Code<Integer>(10, "10"), new Code<Integer>(15,
			"15"), new Code<Integer>(20, "20"), new Code<Integer>(30, "30"), new Code<Integer>(40, "40"),
			new Code<Integer>(50, "50"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> fileSizeList = Arrays.asList(new Code<Integer>(1 * 1024 * 1024, "1"),
			new Code<Integer>(2 * 1024 * 1024, "2"), new Code<Integer>(5 * 1024 * 1024, "5"), new Code<Integer>(
					10 * 1024 * 1024, "10"), new Code<Integer>(20 * 1024 * 1024, "20"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> imageFileSizeList = Arrays.asList(new Code<Integer>(1000 * 1024, "1000"),
			new Code<Integer>(2000 * 1024, "2000"), new Code<Integer>(3000 * 1024, "3000"), new Code<Integer>(
					4000 * 1024, "4000"), new Code<Integer>(5000 * 1024, "5000"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> imageWidthList = Arrays.asList(new Code<Integer>(300, "300"), new Code<Integer>(
			500, "500"), new Code<Integer>(800, "800"), new Code<Integer>(1024, "1024"),
			new Code<Integer>(2048, "2048"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> yesNoList = Arrays.asList(new Code<Integer>(1, "ui.lightpack.commoon.code.yes"),
			new Code<Integer>(0, "ui.lightpack.commoon.code.no"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> approvalRejectList = Arrays.asList(new Code<Integer>(1,
			"ui.lightpack.commoon.code.approval"), new Code<Integer>(0, "ui.lightpack.commoon.code.reject"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> useUnuseList = Arrays.asList(
			new Code<Integer>(1, "ui.lightpack.commoon.code.use"), new Code<Integer>(0,
					"ui.lightpack.commoon.code.unuse"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> openClosedList = Arrays.asList(new Code<Integer>(1, "ui.ikep4.common.code.open"),
			new Code<Integer>(0, "ui.ikep4.common.code.closed"));

	@SuppressWarnings("unchecked")
	private final List<Code<Integer>> attachFileOptionList = Arrays.asList(new Code<Integer>(1,
			"ui.ikep4.common.code.fileType"), new Code<Integer>(0, "ui.ikep4.common.code.fileExtension"));

	@SuppressWarnings("unchecked")
	private final List<Code<String>> attachFileTypeList = Arrays.asList(new Code<String>("txt",
			"ui.ikep4.common.code.fileType.txt"), new Code<String>("img", "ui.ikep4.common.code.fileType.img"),
			new Code<String>("doc", "ui.ikep4.common.code.fileType.doc"), new Code<String>("ado",
					"ui.ikep4.common.code.fileType.ado"), new Code<String>("vod", "ui.ikep4.common.code.fileType.vod"),
			new Code<String>("comp", "ui.ikep4.common.code.fileType.comp"), new Code<String>("app",
					"ui.ikep4.common.code.fileType.app"));

	/**
	 * @return the boardTypeList
	 */
	public List<Code<String>> getBoardTypeList() {
		return boardTypeList;
	}

	/**
	 * @return the listTypeList
	 */
	public List<Code<String>> getListTypeList() {
		return listTypeList;
	}

	/**
	 * @return the permissionList
	 */
	public List<Code<String>> getPermissionList() {
		return permissionList;
	}
	
	/**
	 * @return the permissionList
	 */
	public List<Code<String>> getKmsPermissionList() {
		return kmsPermissionList;
	}

	/**
	 * @return the pageNumList
	 */
	public List<Code<Integer>> getPageNumList() {
		return pageNumList;
	}

	/**
	 * @return the fileSizeList
	 */
	public List<Code<Integer>> getFileSizeList() {
		return fileSizeList;
	}

	/**
	 * @return the imageFileSizeList
	 */
	public List<Code<Integer>> getImageFileSizeList() {
		return imageFileSizeList;
	}

	/**
	 * @return the imageWidthList
	 */
	public List<Code<Integer>> getImageWidthList() {
		return imageWidthList;
	}

	/**
	 * @return the yesNoList
	 */
	public List<Code<Integer>> getYesNoList() {
		return yesNoList;
	}

	/**
	 * @return the approvalRejectList
	 */
	public List<Code<Integer>> getApprovalRejectList() {
		return approvalRejectList;
	}

	/**
	 * @return the useUnuseList
	 */
	public List<Code<Integer>> getUseUnuseList() {
		return useUnuseList;
	}

	/**
	 * @return the itemPermissionList
	 */
	public List<Code<String>> getFollowBoardPermission() {
		return followBoardPermission;
	}

	/**
	 * @return the attachFileOptionList
	 */
	public List<Code<Integer>> getAttachFileOptionList() {
		return attachFileOptionList;
	}

	/**
	 * @return the attachFileTypeList
	 */
	public List<Code<String>> getAttachFileTypeList() {
		return attachFileTypeList;
	}

	/**
	 * @return the openClosedList
	 */
	public List<Code<Integer>> getOpenClosedList() {
		return openClosedList;
	}

}
