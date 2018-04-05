/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.board.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 게시글 모델 클래스.
 *
 * @author ${user}
 * @version $$Id: BoardItem.java 16240 2011-08-18 04:08:15Z giljae $$
 */
public class BoardItem extends BaseObject {

	/**
	 * 게시글 생성시 사용하는 ValidationGroup.
	 */
	public interface CreateItemGroup {}

	/**
	 * 답글 생성시 사용하는 ValidationGroup.
	 */
	public interface CreateReplyItemGroup {}

	/**
	 * 게시글 수정시 사용하는 ValidationGroup.
	 */
	public interface ModifyItemGroup {}


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1035752613602705789L;

	/** 에디터 파일 여부. */
	public static final String EDITOR_FILE = "Y";

	/** 첨부 파일 여부. */
	public static final String ATTECHED_FILE = "N";

	/** 게시글 서비스중 */
	public static final Integer STATUS_SERVICING = 0;

	/** 게시글 유저 삭제 */
	public static final Integer STATUS_USER_DELETED = 1;

	/** 게시글 삭제 대기 상태 */
	public static final Integer STATUS_DELETE_WAITING = 2;

	/** BBS 아이템 타입. */
	public static final String ITEM_TYPE = IKepConstant.ITEM_TYPE_CODE_BBS;

	/** 모두 파일. */
	public static final String ALL_FILE = " ";

	/** 포털 ID. */
	private String portalId;

	/** 게시글 ID. */
	private String itemId;

	/** 게시판 ID. */
	@NotNull
	private String boardId;
	
	/** 게시판 이름. */
	private String boardName;

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
	
	/** 게시글의 영구보관  여부( 0 : 영구보관안함, 1 : 영구보관). */
	private Integer itemForever;

	/** 게시글 제목. */
	@NotNull(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
	@NotEmpty(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
	@NotBlank(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
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

	/** 게시시작일. */
	//@DateTimeFormat(pattern="yyyy.MM.dd")
	//@NotNull(groups={CreateItemGroup.class, ModifyItemGroup.class})
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date startDate;

	/** 게시종료일. */
	//@DateTimeFormat(pattern="yyyy.MM.dd")
	//@NotNull(groups={CreateItemGroup.class, ModifyItemGroup.class})
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date endDate;
	
	
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date disStartDate;
	
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date disEndDate;
	
	
	/** 현재일. */
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date toDate;

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
	
	private String displayName;

	/** 등록일. */
	private Date registDate;
	
	/** 임시저장 플래그. */
	private String tempSave;

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
	
	private List<String> ecmFileList;
	
	private List<FileData> ecmFileDataList;

	/** 에디터내의 파일 리스트. */
	private List<FileData> editorFileDataList;

	/** 게시글의 쓰레드로 묶여 있는 글 리스트. */
	private List<BoardItem> replyItemThreadList;

	/** 관련글 리스트. */
	private List<BoardItem> relatedBoardItemList;

	/** 태그 문자열 (ex: xxx,yyy,zzz). */
	///무림제지 태그필수 제외함 2012.10.8
	//@NotNull(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
	//@NotEmpty(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
	//@NotBlank(groups={CreateItemGroup.class, CreateReplyItemGroup.class, ModifyItemGroup.class})
	private String tag;

	/** 태그 리스트. */
	private List<Tag> tagList;

	/** The anonymous. */
	private Integer anonymous;

	/** 작성자. */
	private User user;

	/** 게시판. */
	private Board board;
	
	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;
	
	private String wordId;
	
	//전사공지로 적용될 경우 전사공지게시판id를 갖는다.
	private String companyNoticeBoardId;
	
	//독서자 리스트 //입력용
	private List<String> readerList;
	
	//독서자 리스트 //조회수정용
	private List<BoardItemReader> boardItemReaderList;
	

	//독서자 메일 발송 여부 0발송없음 1발송
	private Integer readerMailFlag;
	
	private String boardType;
	
	private String readYn;
	
	private String readFlg;
	
	private String ecmFileId;
	private String ecmFileName;
	private String ecmFilePath;
	private String ecmFileUrl1;
	private String ecmFileUrl2;
	
	private String workplaces;
	private String workplace;
	private String workplaceName;
	
	private Boolean admin;
	
	private Boolean boardAdmin;

	
	public List<BoardItemReader> getBoardItemReaderList() {
		return boardItemReaderList;
	}

	public void setBoardItemReaderList(List<BoardItemReader> boardItemReaderList) {
		this.boardItemReaderList = boardItemReaderList;
	}

	
	
	public Integer getReaderMailFlag() {
		return readerMailFlag;
	}

	public void setReaderMailFlag(Integer readerMailFlag) {
		this.readerMailFlag = readerMailFlag;
	}

	public List<String> getReaderList() {
		return readerList;
	}

	public void setReaderList(List<String> readerList) {
		this.readerList = readerList;
	}

	public String getCompanyNoticeBoardId() {
		return companyNoticeBoardId;
	}

	public void setCompanyNoticeBoardId(String companyNoticeBoardId) {
		this.companyNoticeBoardId = companyNoticeBoardId;
	}
	
	
	public String getWordId() {
		return wordId;
	}

	public void setWordId(String wordId) {
		this.wordId = wordId;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	private String wordName;

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

	public Integer getItemForever() {
		return this.itemForever;
	}

	public void setItemForever(Integer itemForever) {
		this.itemForever = itemForever;
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
	 * Gets the msie.
	 *
	 * @return the msie
	 */
	public Integer getMsie() {
		return this.msie;
	}
	
	/**
	 * Sets the msie.
	 *
	 * @param msie the new msie
	 */
	public void setMsie(Integer msie) {
		this.msie = msie;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public String getTempSave() {
		return tempSave;
	}

	public void setTempSave(String tempSave) {
		this.tempSave = tempSave;
	}

	public Date getDisStartDate() {
		return disStartDate;
	}

	public void setDisStartDate(Date disStartDate) {
		this.disStartDate = disStartDate;
	}

	public Date getDisEndDate() {
		return disEndDate;
	}

	public void setDisEndDate(Date disEndDate) {
		this.disEndDate = disEndDate;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getReadYn() {
		return readYn;
	}

	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}

	public String getReadFlg() {
		return readFlg;
	}

	public void setReadFlg(String readFlg) {
		this.readFlg = readFlg;
	}

	public List<String> getEcmFileList() {
		return ecmFileList;
	}

	public void setEcmFileList(List<String> ecmFileList) {
		this.ecmFileList = ecmFileList;
	}

	public List<FileData> getEcmFileDataList() {
		return ecmFileDataList;
	}

	public void setEcmFileDataList(List<FileData> ecmFileDataList) {
		this.ecmFileDataList = ecmFileDataList;
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

	public String getWorkplaces() {
		return workplaces;
	}

	public void setWorkplaces(String workplaces) {
		this.workplaces = workplaces;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getWorkplaceName() {
		return workplaceName;
	}

	public void setWorkplaceName(String workplaceName) {
		this.workplaceName = workplaceName;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Boolean getBoardAdmin() {
		return boardAdmin;
	}

	public void setBoardAdmin(Boolean boardAdmin) {
		this.boardAdmin = boardAdmin;
	}

}