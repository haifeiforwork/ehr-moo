/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.base.constant.CommonConstant;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.model.BaseObject;


// TODO: Auto-generated Javadoc
/**
 * 게시글 모델 클래스.
 * 
 * @author ${user}
 * @version $$Id: BoardItem.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public class BoardItem extends BaseObject {

	/**
	 * 게시글 생성시 사용하는 ValidationGroup.
	 */
	public interface CreateItemGroup {
	}

	/**
	 * 답글 생성시 사용하는 ValidationGroup.
	 */
	public interface CreateReplyItemGroup {
	}

	private List<FileData> ecmFileDataList;
	
	/**
	 * 게시글 수정시 사용하는 ValidationGroup.
	 */
	public interface ModifyItemGroup {
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1035752613602705789L;

	/** 에디터 파일 여부. */
	public static final String EDITOR_FILE = "Y";

	/** 첨부 파일 여부. */
	public static final String ATTECHED_FILE = "N";

	/** 게시글 서비스중 */
	public static final Integer STATUS_SERVICING = 0;

	/** BBS 아이템 타입. */
	public static final String ITEM_TYPE = "KMS";

	/** BBS 아이템 타입. */
	public static final String ITEM_FILE_TYPE = "KMS_BD";

	/** 모두 파일. */
	public static final String ALL_FILE = " ";

	/** 포털 ID. */
	private String portalId;

	/** 게시글 ID. */
	private String itemId;

	/** 게시판 ID. */
	@NotNull
	private String boardId;

	/** 부모 게시글 ID. */
	private String itemParentId;

	/** 게시글 그룹 ID. */
	private String itemGroupId;

	/** 같은 부모 아래 형제 순서. */
	private Integer step;

	/** 게시글의 들여쓰기. */
	private Integer indentation;

	/** 게시글의 일련번호. */
	private String itemSeqId;

	/** 게시글 타입. */
	private String itemType;

	/** 게시글의 상단 노출 여부( 0 : 목록상단에 노출하지 않음, 1 : 목록상단에 항상 노출함). */
	private Integer itemDisplay;
	
	private Integer reportReq;

	/** 게시글 제목. */
	@NotNull(groups = { CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class })
	@NotEmpty(groups = { CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class })
	@NotBlank(groups = { CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class })
	private String title;

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
	
	private List<String> targetGroupList;
	
	private List<String> targetList;
	
	private List<BoardItemTarget> targetGroup;
	
	private List<BoardItemTarget> targetUser;
	
	private String targetGroupId;
	
	private String targetGroupName;
	
	private String targetUserId;
	
	private String isGroup;
	
	private String color;
	
	private String caution;

	/** 게시시작일. */
	// @DateTimeFormat(pattern="yyyy.MM.dd")
	// @NotNull(groups={CreateItemGroup.class, ModifyItemGroup.class})
	private Date startDate;

	/** 게시종료일. */
	// @DateTimeFormat(pattern="yyyy.MM.dd")
	// @NotNull(groups={CreateItemGroup.class, ModifyItemGroup.class})
	private Date endDate;

	/**
	 * 게시글 논리적 삭제 여부 (작성자의 의해서 삭제될 때 하위 글이 있으면 itemDelete가 1로 변경되고 삭제된 글이 라고
	 * 표시된다.)
	 */
	private Integer itemDelete;

	/** 게시글 이미지 파일 아이디. */
	private String imageFileId;

	/** 등록자 ID. */
	private String registerId;

	/** 등록자 이름. */
	private String registerName;

	/** 등록일. */
	private Date registDate;
	
	private String subRegisterId;

	private String subRegisterName;

	private Date subRegistDate;

	/** 수정자 Id. */
	private String updaterId;

	/** 게시물 ID. */
	private String updaterName;

	/** 수정자 ID. */
	private Date updateDate;

	/** 게시글 내용. */
	private String contents;

	/** 첨부파일 링크 리스트. */
	private List<FileLink> fileLinkList;

	/** 에디터내의 파일링크 리스트. */
	private List<FileLink> editorFileLinkList;

	/** 첨부파일 리스트. */
	private List<FileData> fileDataList;

	/** 에디터내의 파일 리스트. */
	private List<FileData> editorFileDataList;

	/** 게시글의 쓰레드로 묶여 있는 글 리스트. */
	private List<BoardItem> replyItemThreadList;

	/** 관련글 리스트. */
	private List<BoardItem> relatedBoardItemList;

	/** 태그 문자열 (ex: xxx,yyy,zzz). */
	private String tag;

	/** 태그 리스트. */
	private List<Tag> tagList;
	
	/** 평가정보 */
	private List<BoardAssessItem> boardAssessItemList;

	/** The anonymous. */
	private Integer anonymous;

	/** 작성자. */
	private User user;

	/** 게시판. */
	private Board board;

	/** BBS 아이템 타입. */
	public static final String ITEM_TYPE_CODE = "KMS";

	/** 워크스페이스 아이디 **/
	private String workspaceId;

	/** 게시판 권한을 따름 여부 **/
	private Integer followBoardPermission = 1;

	/** 읽기 권한 **/
	private String readPermission = "1";

	/** 읽기 권한 개별 설정 목록 **/
	private ACLResourcePermission aclReadPermissionList;

	/** 읽기 권한 개별 설정 목록 **/
	private List<String> readPermissionList;

	private String registerEnglishName;

	private String updaterEnglishName;

	/** 하위 그룹 체크여부 **/
	private Integer searchSubFlag;

	/** 게시물 버전 목록 */
	private List<BoardItemVersion> boardItemVersionList;

	/** 게시물 버전 model */
	private BoardItemVersion boardItemVersion;

	private String jobTitleName;

	private String jobTitleEnglishName;

	private String workspaceName;

	private String typeId;

	private String typeName;

	private String typeEnglishName;

	private String timeVal;
	
	private String score;
	
	private String ecmFileId;
	private String ecmFileName;
	private String ecmFilePath;
	private String ecmFileUrl1;
	private String ecmFileUrl2;
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** 게시판 컨텐츠 상태 **/
	private Integer status = 1;

	/** 정보등급 **/
	private String infoGrade;
	
	/** 희망정보등급 **/
	private String hopeGrade;
	
	public String getHopeGrade() {
		return hopeGrade;
	}
	
	public void setHopeGrade(String hopeGrade) {
		this.hopeGrade = hopeGrade;
	}
	
	private String oriStatus;
	private String oriRegistDate;
	

	public String getOriStatus() {
		return oriStatus;
	}

	public void setOriStatus(String oriStatus) {
		this.oriStatus = oriStatus;
	}

	public String getOriRegistDate() {
		return oriRegistDate;
	}

	public void setOriRegistDate(String oriRegistDate) {
		this.oriRegistDate = oriRegistDate;	}

	/** 정보등급 **/
	private Integer mark;
	
	/** 전문가  ID. */
	private String expertId;

	/** 전문가 이름. */
	private String expertName;

	/** 전문가 요청일. */
	private Date expertReqDate;
	
	/** 정보평가자 Id. */
	private String assessorId;

	/** 정보평가자 이름. */
	private String assessorName;

	/** 정보평가자 의견. */
	private String assessorOpinion;
	
	/** 정보평가일. */
	private Date assessDate;
	
	/** 출처. */
	@NotNull(groups = { CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class })
	@NotEmpty(groups = { CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class })
	private String targetSource;
	
	public String getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(String targetSource) {
		this.targetSource = targetSource;
	}

	/** 업무게시판여부 **/
	private Integer isKnowhow;
	
	private String teamName;
	
	private String teamEnglishName;
	
	private String pictureId;
	
	private String profilePictureId;
	
	private String userName;
	
	private String userEnglishName;
	
	private String refItemId;
	
	private List<BoardItem> boardItemList;
	
	private String[] itemIds;

	private int[] assessItems;
	
	private String[] assessNames;

	private int[] assessMarks;
	
	private int[] itemDisplays;
	
	private String itemDisplays_1;
	private String itemDisplays_2;
	private String itemDisplays_3;
	private String itemDisplays_4;
	private String itemDisplays_5;
	private String itemDisplays_6;
	private String itemDisplays_7;
	private String itemDisplays_8;
	private String itemDisplays_9;
	private String itemDisplays_10;
	private String itemDisplays_11;
	private String itemDisplays_12;
	private String itemDisplays_13;
	private String itemDisplays_14;
	private String itemDisplays_15;
	private String itemDisplays_16;
	private String itemDisplays_17;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private String pageGubun;
	private String empNo;
	private String workPlaceName;
	
	private String groupId;
	private String groupName;
	
	private String migrationyn = "N";
	
	private String userId;
	private String categoryId;
	private String categoryName;
	
	
	
	public String getMigrationyn() {
		return migrationyn;
	}

	public void setMigrationyn(String migrationyn) {
		this.migrationyn = migrationyn;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getWorkPlaceName() {
		return workPlaceName;
	}

	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}

	public String getPageGubun() {
		return pageGubun;
	}

	public void setPageGubun(String pageGubun) {
		this.pageGubun = pageGubun;
	}

	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;

	/**
	 * Gets the item id.
	 * 
	 * @return the item id
	 */
	public String getItemId() {
		return this.itemId;
	}

	/**
	 * Sets the item id.
	 * 
	 * @param itemId the new item id
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * Gets the board id.
	 * 
	 * @return the board id
	 */
	public String getBoardId() {
		return this.boardId;
	}

	/**
	 * Sets the board id.
	 * 
	 * @param boardId the new board id
	 */
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	/**
	 * Gets the item parent id.
	 * 
	 * @return the item parent id
	 */
	public String getItemParentId() {
		return this.itemParentId;
	}

	/**
	 * Sets the item parent id.
	 * 
	 * @param itemParentId the new item parent id
	 */
	public void setItemParentId(String itemParentId) {
		this.itemParentId = itemParentId;
	}

	/**
	 * Gets the item group id.
	 * 
	 * @return the item group id
	 */
	public String getItemGroupId() {
		return this.itemGroupId;
	}

	/**
	 * Sets the item group id.
	 * 
	 * @param itemGroupId the new item group id
	 */
	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}

	/**
	 * Gets the step.
	 * 
	 * @return the step
	 */
	public Integer getStep() {
		return this.step;
	}

	/**
	 * Sets the step.
	 * 
	 * @param step the new step
	 */
	public void setStep(Integer step) {
		this.step = step;
	}

	/**
	 * Gets the indentation.
	 * 
	 * @return the indentation
	 */
	public Integer getIndentation() {
		return this.indentation;
	}

	/**
	 * Sets the indentation.
	 * 
	 * @param indentation the new indentation
	 */
	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}

	/**
	 * Gets the item seq id.
	 * 
	 * @return the item seq id
	 */
	public String getItemSeqId() {
		return this.itemSeqId;
	}

	/**
	 * Sets the item seq id.
	 * 
	 * @param itemSeqId the new item seq id
	 */
	public void setItemSeqId(String itemSeqId) {
		this.itemSeqId = itemSeqId;
	}

	/**
	 * Gets the item type.
	 * 
	 * @return the item type
	 */
	public String getItemType() {
		return this.itemType;
	}

	/**
	 * Sets the item type.
	 * 
	 * @param itemType the new item type
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * Gets the item display.
	 * 
	 * @return the item display
	 */
	public Integer getItemDisplay() {
		return this.itemDisplay;
	}

	/**
	 * Sets the item display.
	 * 
	 * @param itemDisplay the new item display
	 */
	public void setItemDisplay(Integer itemDisplay) {
		this.itemDisplay = itemDisplay;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title.
	 * 
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the hit count.
	 * 
	 * @return the hit count
	 */
	public Integer getHitCount() {
		return this.hitCount;
	}

	/**
	 * Sets the hit count.
	 * 
	 * @param hitCount the new hit count
	 */
	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	/**
	 * Gets the recommend count.
	 * 
	 * @return the recommend count
	 */
	public Integer getRecommendCount() {
		return this.recommendCount;
	}

	/**
	 * Sets the recommend count.
	 * 
	 * @param recommendCount the new recommend count
	 */
	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}

	/**
	 * Gets the reply count.
	 * 
	 * @return the reply count
	 */
	public Integer getReplyCount() {
		return this.replyCount;
	}

	/**
	 * Sets the reply count.
	 * 
	 * @param replyCount the new reply count
	 */
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	/**
	 * Gets the linereply count.
	 * 
	 * @return the linereply count
	 */
	public Integer getLinereplyCount() {
		return this.linereplyCount;
	}

	/**
	 * Sets the linereply count.
	 * 
	 * @param linereplyCount the new linereply count
	 */
	public void setLinereplyCount(Integer linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	/**
	 * Gets the attach file count.
	 * 
	 * @return the attach file count
	 */
	public Integer getAttachFileCount() {
		return this.attachFileCount;
	}

	/**
	 * Sets the attach file count.
	 * 
	 * @param attachFileCount the new attach file count
	 */
	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	/**
	 * Gets the start date.
	 * 
	 * @return the start date
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Sets the start date.
	 * 
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 * 
	 * @return the end date
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * Sets the end date.
	 * 
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the item delete.
	 * 
	 * @return the item delete
	 */
	public Integer getItemDelete() {
		return this.itemDelete;
	}

	/**
	 * Sets the item delete.
	 * 
	 * @param itemDelete the new item delete
	 */
	public void setItemDelete(Integer itemDelete) {
		this.itemDelete = itemDelete;
	}

	/**
	 * Gets the register id.
	 * 
	 * @return the register id
	 */
	public String getRegisterId() {
		return this.registerId;
	}

	/**
	 * Sets the register id.
	 * 
	 * @param registerId the new register id
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * Gets the register name.
	 * 
	 * @return the register name
	 */
	public String getRegisterName() {
		return this.registerName;
	}

	/**
	 * Sets the register name.
	 * 
	 * @param registerName the new register name
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * Gets the regist date.
	 * 
	 * @return the regist date
	 */
	public Date getRegistDate() {
		return this.registDate;
	}

	/**
	 * Sets the regist date.
	 * 
	 * @param registDate the new regist date
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * Gets the updater id.
	 * 
	 * @return the updater id
	 */
	public String getUpdaterId() {
		return this.updaterId;
	}

	/**
	 * Sets the updater id.
	 * 
	 * @param updaterId the new updater id
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * Gets the updater name.
	 * 
	 * @return the updater name
	 */
	public String getUpdaterName() {
		return this.updaterName;
	}

	/**
	 * Sets the updater name.
	 * 
	 * @param updaterName the new updater name
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	/**
	 * Gets the update date.
	 * 
	 * @return the update date
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * Sets the update date.
	 * 
	 * @param updateDate the new update date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Gets the contents.
	 * 
	 * @return the contents
	 */
	public String getContents() {
		return this.contents;
	}

	/**
	 * Sets the contents.
	 * 
	 * @param contents the new contents
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * Gets the file link list.
	 * 
	 * @return the file link list
	 */
	public List<FileLink> getFileLinkList() {
		return this.fileLinkList;
	}

	/**
	 * Sets the file link list.
	 * 
	 * @param fileLinkList the new file link list
	 */
	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	/**
	 * Gets the editor file link list.
	 * 
	 * @return the editor file link list
	 */
	public List<FileLink> getEditorFileLinkList() {
		return this.editorFileLinkList;
	}

	/**
	 * Sets the editor file link list.
	 * 
	 * @param editorFileLinkList the new editor file link list
	 */
	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	/**
	 * Gets the file data list.
	 * 
	 * @return the file data list
	 */
	public List<FileData> getFileDataList() {
		return this.fileDataList;
	}

	/**
	 * Sets the file data list.
	 * 
	 * @param fileDataList the new file data list
	 */
	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	/**
	 * Gets the editor file data list.
	 * 
	 * @return the editor file data list
	 */
	public List<FileData> getEditorFileDataList() {
		return this.editorFileDataList;
	}

	/**
	 * Sets the editor file data list.
	 * 
	 * @param editorFileDataList the new editor file data list
	 */
	public void setEditorFileDataList(List<FileData> editorFileDataList) {
		this.editorFileDataList = editorFileDataList;
	}

	/**
	 * Gets the reply item thread list.
	 * 
	 * @return the reply item thread list
	 */
	public List<BoardItem> getReplyItemThreadList() {
		return this.replyItemThreadList;
	}

	/**
	 * Sets the reply item thread list.
	 * 
	 * @param replyItemThreadList the new reply item thread list
	 */
	public void setReplyItemThreadList(List<BoardItem> replyItemThreadList) {
		this.replyItemThreadList = replyItemThreadList;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the tag.
	 * 
	 * @return the tag
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * Sets the tag.
	 * 
	 * @param tag the new tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * Gets the tag list.
	 * 
	 * @return the tag list
	 */
	public List<Tag> getTagList() {
		return this.tagList;
	}

	/**
	 * Sets the tag list.
	 * 
	 * @param tagList the new tag list
	 */
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	/**
	 * Gets the portal id.
	 * 
	 * @return the portal id
	 */
	public String getPortalId() {
		return this.portalId;
	}

	/**
	 * Sets the portal id.
	 * 
	 * @param portalId the new portal id
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * Gets the related board item list.
	 * 
	 * @return the related board item list
	 */
	public List<BoardItem> getRelatedBoardItemList() {
		return this.relatedBoardItemList;
	}

	/**
	 * Sets the related board item list.
	 * 
	 * @param relatedBoardItemList the new related board item list
	 */
	public void setRelatedBoardItemList(List<BoardItem> relatedBoardItemList) {
		this.relatedBoardItemList = relatedBoardItemList;
	}

	/**
	 * Gets the image file id.
	 * 
	 * @return the image file id
	 */
	public String getImageFileId() {
		return this.imageFileId;
	}

	/**
	 * Sets the image file id.
	 * 
	 * @param imageFileId the new image file id
	 */
	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	/**
	 * Gets the board.
	 * 
	 * @return the board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Sets the board.
	 * 
	 * @param board the new board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * Gets the anonymous.
	 * 
	 * @return the anonymous
	 */
	public Integer getAnonymous() {
		return this.anonymous;
	}

	/**
	 * Sets the anonymous.
	 * 
	 * @param anonymous the new anonymous
	 */
	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

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
	 * @return the followBoardPermission
	 */
	public Integer getFollowBoardPermission() {
		return followBoardPermission;
	}

	/**
	 * @param followBoardPermission the followBoardPermission to set
	 */
	public void setFollowBoardPermission(Integer followBoardPermission) {
		this.followBoardPermission = followBoardPermission;
	}

	/**
	 * @return the readPermission
	 */
	public String getReadPermission() {
		return readPermission;
	}

	/**
	 * @param readPermission the readPermission to set
	 */
	public void setReadPermission(String readPermission) {
		this.readPermission = readPermission;
	}

	/**
	 * @return the aclReadPermissionList
	 */
	public ACLResourcePermission getAclReadPermissionList() {
		return aclReadPermissionList;
	}

	/**
	 * @param aclReadPermissionList the aclReadPermissionList to set
	 */
	public void setAclReadPermissionList(ACLResourcePermission aclReadPermissionList) {
		this.aclReadPermissionList = aclReadPermissionList;
	}

	/**
	 * @return the readPermissionList
	 */
	public List<String> getReadPermissionList() {
		return readPermissionList;
	}

	/**
	 * @param readPermissionList the readPermissionList to set
	 */
	public void setReadPermissionList(List<String> readPermissionList) {
		this.readPermissionList = readPermissionList;
	}

	/**
	 * @return the registerEnglishName
	 */
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	/**
	 * @param registerEnglishName the registerEnglishName to set
	 */
	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	/**
	 * @return the updaterEnglishName
	 */
	public String getUpdaterEnglishName() {
		return updaterEnglishName;
	}

	/**
	 * @param updaterEnglishName the updaterEnglishName to set
	 */
	public void setUpdaterEnglishName(String updaterEnglishName) {
		this.updaterEnglishName = updaterEnglishName;
	}

	/**
	 * @return the searchSubFlag
	 */
	public Integer getSearchSubFlag() {
		return searchSubFlag;
	}

	/**
	 * @param searchSubFlag the searchSubFlag to set
	 */
	public void setSearchSubFlag(Integer searchSubFlag) {
		this.searchSubFlag = searchSubFlag;
	}

	/**
	 * @return the boardItemVersionList
	 */
	public List<BoardItemVersion> getBoardItemVersionList() {
		return boardItemVersionList;
	}

	/**
	 * @param boardItemVersionList the boardItemVersionList to set
	 */
	public void setBoardItemVersionList(List<BoardItemVersion> boardItemVersionList) {
		this.boardItemVersionList = boardItemVersionList;
	}

	/**
	 * @return the boardItemVersion
	 */
	public BoardItemVersion getBoardItemVersion() {
		return boardItemVersion;
	}

	/**
	 * @param boardItemVersion the boardItemVersion to set
	 */
	public void setBoardItemVersion(BoardItemVersion boardItemVersion) {
		this.boardItemVersion = boardItemVersion;
	}

	/**
	 * @return the jobTitleName
	 */
	public String getJobTitleName() {
		return jobTitleName;
	}

	/**
	 * @param jobTitleName the jobTitleName to set
	 */
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	/**
	 * @return the jobTitleEnglishName
	 */
	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	/**
	 * @param jobTitleEnglishName the jobTitleEnglishName to set
	 */
	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
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
	 * @return the timeVal
	 */
	public String getTimeVal() {
		return timeVal;
	}

	/**
	 * @param timeVal the timeVal to set
	 */
	public void setTimeVal(String timeVal) {
		this.timeVal = timeVal;
	}

	public Integer getMsie() {
		return msie;
	}

	public void setMsie(Integer msie) {
		this.msie = msie;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getInfoGrade() {
		return infoGrade;
	}

	public void setInfoGrade(String infoGrade) {
		this.infoGrade = infoGrade;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public Date getExpertReqDate() {
		return expertReqDate;
	}

	public void setExpertReqDate(Date expertReqDate) {
		this.expertReqDate = expertReqDate;
	}

	public String getAssessorId() {
		return assessorId;
	}

	public void setAssessorId(String assessorId) {
		this.assessorId = assessorId;
	}

	public String getAssessorName() {
		return assessorName;
	}

	public void setAssessorName(String assessorName) {
		this.assessorName = assessorName;
	}

	public String getAssessorOpinion() {
		return assessorOpinion;
	}

	public void setAssessorOpinion(String assessorOpinion) {
		this.assessorOpinion = assessorOpinion;
	}

	public Date getAssessDate() {
		return assessDate;
	}

	public void setAssessDate(Date assessDate) {
		this.assessDate = assessDate;
	}

	public Integer getIsKnowhow() {
		return isKnowhow;
	}

	public void setIsKnowhow(Integer isKnowhow) {
		this.isKnowhow = isKnowhow;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getProfilePictureId() {
		return profilePictureId;
	}

	public void setProfilePictureId(String profilePictureId) {
		this.profilePictureId = profilePictureId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getRefItemId() {
		return refItemId;
	}

	public void setRefItemId(String refItemId) {
		this.refItemId = refItemId;
	}
	
	public String[] getRefItemIds(){
		
		System.out.println("getRefItemIds:" + refItemId);
		
		if(refItemId !=null && refItemId.contains(CommonConstant.COMMA_SEPARATER)){
			return refItemId.split(CommonConstant.COMMA_SEPARATER);
		}else
			return null;
	}

	public List<BoardItem> getBoardItemList() {
		return boardItemList;
	}

	public void setBoardItemList(List<BoardItem> boardItemList) {
		this.boardItemList = boardItemList;
	}

	public List<BoardAssessItem> getBoardAssessItemList() {
		return boardAssessItemList;
	}

	public void setBoardAssessItemList(List<BoardAssessItem> boardAssessItemList) {
		this.boardAssessItemList = boardAssessItemList;
	}

	public String[] getItemIds() {
		return itemIds;
	}

	public void setItemIds(String[] itemIds) {
		this.itemIds = itemIds;
	}

	public int[] getAssessItems() {
		return assessItems;
	}

	public void setAssessItems(int[] assessItems) {
		this.assessItems = assessItems;
	}

	public String[] getAssessNames() {
		return assessNames;
	}

	public void setAssessNames(String[] assessNames) {
		this.assessNames = assessNames;
	}

	public int[] getAssessMarks() {
		return assessMarks;
	}

	public void setAssessMarks(int[] assessMarks) {
		this.assessMarks = assessMarks;
	}

	public int[] getItemDisplays() {
		return itemDisplays;
	}

	public void setItemDisplays(int[] itemDisplays) {
		this.itemDisplays = itemDisplays;
	}

	public String getItemDisplays_1() {
		return itemDisplays_1;
	}

	public void setItemDisplays_1(String itemDisplays_1) {
		this.itemDisplays_1 = itemDisplays_1;
	}

	public String getItemDisplays_2() {
		return itemDisplays_2;
	}

	public void setItemDisplays_2(String itemDisplays_2) {
		this.itemDisplays_2 = itemDisplays_2;
	}

	public String getItemDisplays_3() {
		return itemDisplays_3;
	}

	public void setItemDisplays_3(String itemDisplays_3) {
		this.itemDisplays_3 = itemDisplays_3;
	}

	public String getItemDisplays_4() {
		return itemDisplays_4;
	}

	public void setItemDisplays_4(String itemDisplays_4) {
		this.itemDisplays_4 = itemDisplays_4;
	}

	public String getItemDisplays_5() {
		return itemDisplays_5;
	}

	public void setItemDisplays_5(String itemDisplays_5) {
		this.itemDisplays_5 = itemDisplays_5;
	}

	public String getItemDisplays_6() {
		return itemDisplays_6;
	}

	public void setItemDisplays_6(String itemDisplays_6) {
		this.itemDisplays_6 = itemDisplays_6;
	}

	public String getItemDisplays_7() {
		return itemDisplays_7;
	}

	public void setItemDisplays_7(String itemDisplays_7) {
		this.itemDisplays_7 = itemDisplays_7;
	}

	public String getItemDisplays_8() {
		return itemDisplays_8;
	}

	public void setItemDisplays_8(String itemDisplays_8) {
		this.itemDisplays_8 = itemDisplays_8;
	}

	public String getItemDisplays_9() {
		return itemDisplays_9;
	}

	public void setItemDisplays_9(String itemDisplays_9) {
		this.itemDisplays_9 = itemDisplays_9;
	}

	public String getItemDisplays_10() {
		return itemDisplays_10;
	}

	public void setItemDisplays_10(String itemDisplays_10) {
		this.itemDisplays_10 = itemDisplays_10;
	}

	public String getItemDisplays_11() {
		return itemDisplays_11;
	}

	public void setItemDisplays_11(String itemDisplays_11) {
		this.itemDisplays_11 = itemDisplays_11;
	}

	public String getItemDisplays_12() {
		return itemDisplays_12;
	}

	public void setItemDisplays_12(String itemDisplays_12) {
		this.itemDisplays_12 = itemDisplays_12;
	}

	public String getItemDisplays_13() {
		return itemDisplays_13;
	}

	public void setItemDisplays_13(String itemDisplays_13) {
		this.itemDisplays_13 = itemDisplays_13;
	}

	public String getItemDisplays_14() {
		return itemDisplays_14;
	}

	public void setItemDisplays_14(String itemDisplays_14) {
		this.itemDisplays_14 = itemDisplays_14;
	}

	public String getItemDisplays_15() {
		return itemDisplays_15;
	}

	public void setItemDisplays_15(String itemDisplays_15) {
		this.itemDisplays_15 = itemDisplays_15;
	}

	public String getItemDisplays_16() {
		return itemDisplays_16;
	}

	public void setItemDisplays_16(String itemDisplays_16) {
		this.itemDisplays_16 = itemDisplays_16;
	}

	public String getItemDisplays_17() {
		return itemDisplays_17;
	}

	public void setItemDisplays_17(String itemDisplays_17) {
		this.itemDisplays_17 = itemDisplays_17;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<String> getTargetGroupList() {
		return targetGroupList;
	}

	public void setTargetGroupList(List<String> targetGroupList) {
		this.targetGroupList = targetGroupList;
	}

	public String getTargetGroupId() {
		return targetGroupId;
	}

	public void setTargetGroupId(String targetGroupId) {
		this.targetGroupId = targetGroupId;
	}

	public String getTargetGroupName() {
		return targetGroupName;
	}

	public void setTargetGroupName(String targetGroupName) {
		this.targetGroupName = targetGroupName;
	}

	public List<BoardItemTarget> getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(List<BoardItemTarget> targetGroup) {
		this.targetGroup = targetGroup;
	}

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

	public List<String> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<String> targetList) {
		this.targetList = targetList;
	}

	public String getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}

	public List<BoardItemTarget> getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(List<BoardItemTarget> targetUser) {
		this.targetUser = targetUser;
	}

	public String getSubRegisterId() {
		return subRegisterId;
	}

	public void setSubRegisterId(String subRegisterId) {
		this.subRegisterId = subRegisterId;
	}

	public String getSubRegisterName() {
		return subRegisterName;
	}

	public void setSubRegisterName(String subRegisterName) {
		this.subRegisterName = subRegisterName;
	}

	public Date getSubRegistDate() {
		return subRegistDate;
	}

	public void setSubRegistDate(Date subRegistDate) {
		this.subRegistDate = subRegistDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCaution() {
		return caution;
	}

	public void setCaution(String caution) {
		this.caution = caution;
	}

	public Integer getReportReq() {
		return reportReq;
	}

	public void setReportReq(Integer reportReq) {
		this.reportReq = reportReq;
	}

	public String getEcmFileId() {
		return ecmFileId;
	}

	public void setEcmFileId(String ecmFileId) {
		this.ecmFileId = ecmFileId;
	}

	public String getEcmFileName() {
		return ecmFileName;
	}

	public void setEcmFileName(String ecmFileName) {
		this.ecmFileName = ecmFileName;
	}

	public String getEcmFilePath() {
		return ecmFilePath;
	}

	public void setEcmFilePath(String ecmFilePath) {
		this.ecmFilePath = ecmFilePath;
	}

	public String getEcmFileUrl1() {
		return ecmFileUrl1;
	}

	public void setEcmFileUrl1(String ecmFileUrl1) {
		this.ecmFileUrl1 = ecmFileUrl1;
	}

	public String getEcmFileUrl2() {
		return ecmFileUrl2;
	}

	public void setEcmFileUrl2(String ecmFileUrl2) {
		this.ecmFileUrl2 = ecmFileUrl2;
	}

	public List<FileData> getEcmFileDataList() {
		return ecmFileDataList;
	}

	public void setEcmFileDataList(List<FileData> ecmFileDataList) {
		this.ecmFileDataList = ecmFileDataList;
	}
	
}