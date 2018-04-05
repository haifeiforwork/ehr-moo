/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.note.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 * 
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: NoteShare.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class NoteShare extends BaseObject {

/**	
 * 게시판 정보
 */

	/** BBS 아이템 타입. */
	public static final String ITEM_TYPE_BBS = IKepConstant.ITEM_TYPE_CODE_BBS;

	/** BBS 아이템 타입. */
	public static final String ITEM_TYPE_WS = IKepConstant.ITEM_TYPE_CODE_WORK_SPACE_BOARD;

	/** BBS 아이템 타입. */
	public static final String ITEM_TYPE_CK = IKepConstant.ITEM_TYPE_CODE_BBS;

	/** 포털 ID. */
	private String portalId;

	/** 게시판 ID*/
	private String boardId;
	
	/** 게시판 이름 */
	private String boardName;

	/** 게시판 설명*/
	private String boardDescription;

	/** 버전사용유무 **/
	private Integer versionManage=0;

	/** 
	 * 게시판 타입
	 * Board : 0
	 * Link : 1
	 * Category : 2
	 * */
	private String boardType;

	/** 게시판 영어 이름 */
	private String boardEnglishName;
	
	/** 게시판 영어 설명*/
	private String boardEnglishDescription;

	/** 게시글 ID. */
	private String itemId;

	/** 부모 게시글 ID. */
	private String itemParentId;

	/** 게시글 그룹 ID. */
	private String itemGroupId;

	/** 게시글의 일련번호. */
	private String itemSeqId;

	/** 게시글 타입. */
	private String itemType;

	/** 게시글의 상단 노출 여부( 0 : 목록상단에 노출하지 않음, 1 : 목록상단에 항상 노출함). */
	private Integer itemDisplay;

	/** 게시글 제목. */
	private String title;
	
	/** 같은 부모 아래 형제 순서. */
	private Integer step;

	/** 게시글의 들여쓰기. */
	private Integer indentation;

	/** 게시글 조회수. */
	private Integer hitCount;

	/** 게시글 추천수. */
	private Integer recommendCount;

	/** 답글수. */
	private Integer replyCount;

	/** 댓글수. */
	private Integer linereplyCount;

	/** 첨부파일수. */
	private Integer attachFileCount;

	/** 태그 문자열 (ex: xxx,yyy,zzz). */
	private String tag;

	/** 게시시작일. */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date startDate;

	/** 게시종료일. */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date endDate;

	/**
	 * 게시글 논리적 삭제 여부 (작성자의 의해서 삭제될 때 하위 글이 있으면 itemDelete가 1로 변경되고 삭제된 글이 라고
	 * 표시된다.)
	 */
	private Integer itemDelete;

	/** 게시글 내용. */
	private String contents;

	/** 게시글 내용. */
	private String textContents;

	/** 게시글 이미지 파일 아이디. */
	private String imageFileId;
	
	/** 등록자 ID. */
	private String registerId;

	/** 등록자 이름. */
	private String registerName;

	/** 등록일. */
	private Date registDate;

	/** 수정자 Id. */
	private String updaterId;

	/** 게시물 ID. */
	private String updaterName;

	/** 수정자 ID. */
	private Date updateDate;

	/** 자식 노드 갯수 */
	private Integer hasChildren;

	/** 게시판 권한을 따름 여부 **/
	private Integer followBoardPermission = 1;

	/** 읽기 권한 **/
	private String readPermission = "1";

	public String getPortalId() {
		return this.portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getBoardId() {
		return this.boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getBoardName() {
		return this.boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getBoardDescription() {
		return this.boardDescription;
	}

	public void setBoardDescription(String boardDescription) {
		this.boardDescription = boardDescription;
	}

	public Integer getVersionManage() {
		return this.versionManage;
	}

	public void setVersionManage(Integer versionManage) {
		this.versionManage = versionManage;
	}
	public String getBoardType() {
		return this.boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getBoardEnglishName() {
		return boardEnglishName;
	}

	public void setBoardEnglishName(String boardEnglishName) {
		this.boardEnglishName = boardEnglishName;
	}

	public String getBoardEnglishDescription() {
		return boardEnglishDescription;
	}

	public void setBoardEnglishDescription(String boardEnglishDescription) {
		this.boardEnglishDescription = boardEnglishDescription;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getItemParentId() {
		return this.itemParentId;
	}

	public void setItemParentId(String itemParentId) {
		this.itemParentId = itemParentId;
	}

	public String getItemGroupId() {
		return this.itemGroupId;
	}

	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}

	public Integer getItemDisplay() {
		return this.itemDisplay;
	}

	public void setItemDisplay(Integer itemDisplay) {
		this.itemDisplay = itemDisplay;
	}

	public String getItemSeqId() {
		return this.itemSeqId;
	}

	public void setItemSeqId(String itemSeqId) {
		this.itemSeqId = itemSeqId;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStep() {
		return this.step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getHitCount() {
		return this.hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public Integer getIndentation() {
		return this.indentation;
	}

	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}

	public Integer getHasChildren() {
		return this.hasChildren;
	}

	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}

	public Integer getRecommendCount() {
		return this.recommendCount;
	}

	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}

	public Integer getReplyCount() {
		return this.replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getLinereplyCount() {
		return this.linereplyCount;
	}

	public void setLinereplyCount(Integer linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	public Integer getAttachFileCount() {
		return this.attachFileCount;
	}

	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	public String getTag() {
		return this.tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getItemDelete() {
		return this.itemDelete;
	}

	public void setItemDelete(Integer itemDelete) {
		this.itemDelete = itemDelete;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTextContents() {
		return this.textContents;
	}

	public void setTextContents(String textContents) {
		this.textContents = textContents;
	}

	public String getImageFileId() {
		return this.imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}
	public String getRegisterId() {
		return this.registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return this.registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return this.registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return this.updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return this.updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getFollowBoardPermission() {
		return followBoardPermission;
	}

	public void setFollowBoardPermission(Integer followBoardPermission) {
		this.followBoardPermission = followBoardPermission;
	}

	public String getReadPermission() {
		return readPermission;
	}

	public void setReadPermission(String readPermission) {
		this.readPermission = readPermission;
	}

/**	
 * 워크스페이스 정보
 */
	/**
	 * 워크스페이스 ID
	 */
	private String workspaceId;

	/**
	 * 워크스페이스 명
	 */
	private String workspaceName;
	
	/**
	 * 워크스페이스 타입(O:조직, C:Cop, T:TFT, I:Informal)
	 */
	private String typeId;

	/**
	 * 워크스페이스 타입명
	 */
	private String typeName;

	/**
	 * 타입 영문명
	 */
	private String typeEnglishName;

	/**
	 * WS 디폴트 여부
	 */
	private int isDefault;

	/**
	 * @return the workspaceId
	 */
	public String getWorkspaceId() {
		return workspaceId;
	}

	/**
	 * @param workspaceId the workspaceId to set
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	/**
	 * @return the workspaceName
	 */
	public String getWorkspaceName() {
		return workspaceName;
	}

	/**
	 * @param workspaceName the workspaceName to set
	 */
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the isDefault
	 */
	public int getIsDefault() {
		return isDefault;
	}

	/**
	 * @return the typeEnglishName
	 */
	public String getTypeEnglishName() {
		return typeEnglishName;
	}

	/**
	 * @param typeEnglishName the typeEnglishName to set
	 */
	public void setTypeEnglishName(String typeEnglishName) {
		this.typeEnglishName = typeEnglishName;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

}
