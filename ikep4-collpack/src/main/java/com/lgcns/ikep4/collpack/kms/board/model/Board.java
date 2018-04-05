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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.collpack.kms.board.model.BoardGroup;
import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.validator.constraints.Url;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;

/**
 * 게시판 관리 모델 클래스
 *
 * @author ${user}
 * @version $$Id: Board.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public class Board extends BaseObject {

	public static final String BOARD_ACL_CLASS_NAME = "KMS-BD";

	public static final String BOARD_ITEM_ACL_CLASS_NAME = "KMS-BD-Item";

	public static final String BOARD_PERMISSION_CLASS_NAME = "BBS";

	public static final String KMS_MANAGER = "KMS";

	public static final String BOARD_RESOURCE_NAME = "BBS";

	public static final String READ_PERMISSION_TYPE = "READ";

	public static final String WIRTE_PERMISSION_TYPE = "WIRTE";

	public static final String MANAGE_PERMISSION_TYPE = "MANAGE";

	public static final Integer ANONYMOUS_BOARD = 1;
	
	public static final String DEFAULT_BOARD_ROOT_ID = "0";
	
	public static final String BD_KNOWHOW = "0";
	
	public static final String BD_INFO ="1";

	public interface UpdateRequired {}

	public interface LinkTypeBoard {}

	public interface CategoryTypeBoard {}

	public interface BoardTypeBoard {}

	private static final long serialVersionUID = -2982146151591792709L;
	
	private String boardMapName;
	
	public String getBoardMapName() {
		return boardMapName;
	}

	public void setBoardMapName(String boardMapName) {
		this.boardMapName = boardMapName;
	}

	/** 업무게시판 여부 0:업무, 1:일반정보*/
	private String isKnowhow;

	public String getIsKnowhow() {
		return isKnowhow;
	}

	public void setIsKnowhow(String isKnowhow) {
		this.isKnowhow = isKnowhow;
	}
	
	/** 게시판 직계 부모 리스트 (예 고조할아버지, 증조할아버지, 할아버지, 아버지 게시판 )*/
	private List<Board> parentList;

	/** 게시판 자식 리스트 */
	private List<Board> childList;

	/** 게시판 ID*/
	private String boardId;
	
	/** 업무게시판 여부 0:업무, 1:일반정보*/
	private String isKnowHow;

	public String getIsKnowHow() {
		return isKnowHow;
	}

	public void setIsKnowHow(String isKnowHow) {
		this.isKnowHow = isKnowHow;
	}

	/** workspace 아이디 **/
	private String workspaceId;

	/** 부모 게시판 ID */
	private String boardParentId;

	/** 같은 부모내의 게시판 형제들간의 순서 */
	private Integer sortOrder;

	private Integer indentation;

	/** 게시판 이름 */
	@NotNull(groups={LinkTypeBoard.class, BoardTypeBoard.class, CategoryTypeBoard.class})
	@NotEmpty(groups={LinkTypeBoard.class, BoardTypeBoard.class, CategoryTypeBoard.class})
	@Size(min=1, max=200, groups={LinkTypeBoard.class, BoardTypeBoard.class, CategoryTypeBoard.class})
	private String boardName;

	/** 게시판 루트 ID*/
	private String boardRootId;

	/** 게시판 설명*/
	@NotNull(groups={LinkTypeBoard.class, BoardTypeBoard.class})
	@NotEmpty(groups={LinkTypeBoard.class, BoardTypeBoard.class})
	@Size(min=1, max=500, groups={LinkTypeBoard.class, BoardTypeBoard.class, CategoryTypeBoard.class})
	private String description;

	/** 게시판 타입*/
	@NotNull(groups={LinkTypeBoard.class, BoardTypeBoard.class, CategoryTypeBoard.class})
	@NotEmpty(groups={LinkTypeBoard.class, BoardTypeBoard.class, CategoryTypeBoard.class})
	private String boardType;

	/** URL */
	@NotNull(groups=LinkTypeBoard.class)
	@NotEmpty(groups=LinkTypeBoard.class)
	@Url(groups=LinkTypeBoard.class)
	@Size(min=1, max=1000, groups={LinkTypeBoard.class})
	private String url;

	/** 리스트 타입 */
	private String listType = "0";

	/** 익명 모드 여부 */
	private Integer anonymous = 0;

	/** RSS_ATOM 허용 여부 */
	private Integer rss = 1;

	/** 허용 첨부파일 크기 */
	private Long fileSize;

	/** 허용 위지윅에디터내의 이미지 크기 */
	private Long imageFileSize;

	/** 이미지 넓이 */
	private Integer imageWidth;

	/** 페이지당 레코드 갯수 */
	private Integer pageNum;

	/** 게시글 상세 보기 팝업 모드 */
	private Integer docPopup = 0;

	/** 답글 허용 여부 */
	private Integer reply = 1;

	/** 댓글 허용 여부 */
	private Integer linereply = 1;

	/** 첨부 불가 파일 확장자 */
	private String restrictionType;

	/** 게시판 읽기 여부 */
	private String readPermission="2";

	/** 게시판 쓰기 여부 */
	private String writePermission="2";

	/** 링크 게시판 팝업 표시 여부 */
	private Integer boardPopup = 0;

	/** 파일 업로드 옵션 */
	private Integer fileAttachOption = 1;

	/** 파일 업로드 허용 타입 */
	@NotNull(groups=BoardTypeBoard.class)
	@NotEmpty(groups=BoardTypeBoard.class)
	@Size(min=1, max=100)
	private String allowType;

	/** 포털 ID */
	private String portalId;

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

	/** 롤 아이디 리스트 */
	private List<Role> roleList;

	/** 읽기 권한 사용자 아이디 리스트 */
	private List<User> readUserList;

	/** 읽기 권한 그룹 리스트 */
	private List<BoardGroup> readGroupList;

	/** 쓰기 사용자 아이디 리스트 */
	private List<User> writeUserList;

	/** 쓰기 그룹 리스트 */
	private List<BoardGroup> writeGroupList;

	/** 계층적 권한 부여 대상 쓰기 그룹 아이디 리스트 */
	private List<String> writeHierarchicalGroupList;


	/** 게시판 url 팝업 여부 **/
	private Integer urlPopup=0;

	/** 버전사용유무 **/
	private Integer versionManage=0;

	/** 위키 사용유부 **/
	private Integer wiki=0;

	/** 게시물 이동 사용유무 **/
	private Integer move=1;

	/** 조회권한 목록 **/
	private ACLResourcePermission aclReadPermissionList;
	/** 조회 권한 목록 **/
	private List<String> readPermissionList;

	/** 조회권한과 동일유무 */
	private String sameReadPermission;

	/** 쓰기 권한 목록 **/
	private ACLResourcePermission aclWritePermissionList;
	/** 쓰기 권한 목록 **/
	private List<String> writePermissionList;

	/** 게시판을 수정할 경우 게시판 내 문서에 대한 권한도 같이 수정하도록 요청된 상태값
	 * ( N : 문서권한 변경 없음, A : 문서권한 변경 요청, E : 문서권한 변경 완료)
	 **/
	private String itemPermissionUpdate;

	/* 게시물 삭제 유무 0: 사용, 1: 삭제 */
	private int isDelete;

	private Integer readSearchSubFlag;

	private Integer writeSearchSubFlag;

	/** 동맹중에서 공유할 동맹 목록 **/
	private List<String> allianceIds;
	/**
	 * 태그
	 */
	private String tag;

	/**
	 * 태그
	 */
	private List<Tag> tagList;

	/**
	 * @return the parentList
	 */
	public List<Board> getParentList() {
		return this.parentList;
	}

	/**
	 * @param parentList the parentList to set
	 */
	public void setParentList(List<Board> parentList) {
		this.parentList = parentList;
	}

	/**
	 * @return the childList
	 */
	public List<Board> getChildList() {
		return this.childList;
	}

	/**
	 * @param childList the childList to set
	 */
	public void setChildList(List<Board> childList) {
		this.childList = childList;
	}

	/**
	 * @return the boardId
	 */
	public String getBoardId() {
		return this.boardId;
	}

	/**
	 * @param boardId the boardId to set
	 */
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	/**
	 * @return the workspaceId
	 */
	public String getWorkspaceId() {
		return this.workspaceId;
	}

	/**
	 * @param workspaceId the workspaceId to set
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	/**
	 * @return the boardParentId
	 */
	public String getBoardParentId() {
		return this.boardParentId;
	}

	/**
	 * @param boardParentId the boardParentId to set
	 */
	public void setBoardParentId(String boardParentId) {
		this.boardParentId = boardParentId;
	}

	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the indentation
	 */
	public Integer getIndentation() {
		return this.indentation;
	}

	/**
	 * @param indentation the indentation to set
	 */
	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}

	/**
	 * @return the boardName
	 */
	public String getBoardName() {
		return this.boardName;
	}

	/**
	 * @param boardName the boardName to set
	 */
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	/**
	 * @return the boardRootId
	 */
	public String getBoardRootId() {
		return this.boardRootId;
	}

	/**
	 * @param boardRootId the boardRootId to set
	 */
	public void setBoardRootId(String boardRootId) {
		this.boardRootId = boardRootId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the boardType
	 */
	public String getBoardType() {
		return this.boardType;
	}

	/**
	 * @param boardType the boardType to set
	 */
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the listType
	 */
	public String getListType() {
		return this.listType;
	}

	/**
	 * @param listType the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}

	/**
	 * @return the anonymous
	 */
	public Integer getAnonymous() {
		return this.anonymous;
	}

	/**
	 * @param anonymous the anonymous to set
	 */
	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	/**
	 * @return the rss
	 */
	public Integer getRss() {
		return this.rss;
	}

	/**
	 * @param rss the rss to set
	 */
	public void setRss(Integer rss) {
		this.rss = rss;
	}

	/**
	 * @return the fileSize
	 */
	public Long getFileSize() {
		return this.fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the imageFileSize
	 */
	public Long getImageFileSize() {
		return this.imageFileSize;
	}

	/**
	 * @param imageFileSize the imageFileSize to set
	 */
	public void setImageFileSize(Long imageFileSize) {
		this.imageFileSize = imageFileSize;
	}

	/**
	 * @return the imageWidth
	 */
	public Integer getImageWidth() {
		return this.imageWidth;
	}

	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return this.pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the docPopup
	 */
	public Integer getDocPopup() {
		return this.docPopup;
	}

	/**
	 * @param docPopup the docPopup to set
	 */
	public void setDocPopup(Integer docPopup) {
		this.docPopup = docPopup;
	}

	/**
	 * @return the reply
	 */
	public Integer getReply() {
		return this.reply;
	}

	/**
	 * @param reply the reply to set
	 */
	public void setReply(Integer reply) {
		this.reply = reply;
	}

	/**
	 * @return the linereply
	 */
	public Integer getLinereply() {
		return this.linereply;
	}

	/**
	 * @param linereply the linereply to set
	 */
	public void setLinereply(Integer linereply) {
		this.linereply = linereply;
	}

	/**
	 * @return the restrictionType
	 */
	public String getRestrictionType() {
		return this.restrictionType;
	}

	/**
	 * @param restrictionType the restrictionType to set
	 */
	public void setRestrictionType(String restrictionType) {
		this.restrictionType = restrictionType;
	}

	/**
	 * @return the boardPopup
	 */
	public Integer getBoardPopup() {
		return this.boardPopup;
	}

	/**
	 * @param boardPopup the boardPopup to set
	 */
	public void setBoardPopup(Integer boardPopup) {
		this.boardPopup = boardPopup;
	}

	/**
	 * @return the fileAttachOption
	 */
	public Integer getFileAttachOption() {
		return this.fileAttachOption;
	}

	/**
	 * @param fileAttachOption the fileAttachOption to set
	 */
	public void setFileAttachOption(Integer fileAttachOption) {
		this.fileAttachOption = fileAttachOption;
	}

	/**
	 * @return the allowType
	 */
	public String getAllowType() {
		return this.allowType;
	}

	/**
	 * @param allowType the allowType to set
	 */
	public void setAllowType(String allowType) {
		this.allowType = allowType;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return this.portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return this.registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return this.registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return this.registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return this.updaterId;
	}

	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return this.updaterName;
	}

	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the hasChildren
	 */
	public Integer getHasChildren() {
		return this.hasChildren;
	}

	/**
	 * @param hasChildren the hasChildren to set
	 */
	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}

	/**
	 * @return the roleList
	 */
	public List<Role> getRoleList() {
		return this.roleList;
	}

	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return the readUserList
	 */
	public List<User> getReadUserList() {
		return this.readUserList;
	}

	/**
	 * @param readUserList the readUserList to set
	 */
	public void setReadUserList(List<User> readUserList) {
		this.readUserList = readUserList;
	}

	/**
	 * @return the readGroupList
	 */
	public List<BoardGroup> getReadGroupList() {
		return this.readGroupList;
	}

	/**
	 * @param readGroupList the readGroupList to set
	 */
	public void setReadGroupList(List<BoardGroup> readGroupList) {
		this.readGroupList = readGroupList;
	}

	/**
	 * @return the writeUserList
	 */
	public List<User> getWriteUserList() {
		return this.writeUserList;
	}

	/**
	 * @param writeUserList the writeUserList to set
	 */
	public void setWriteUserList(List<User> writeUserList) {
		this.writeUserList = writeUserList;
	}

	/**
	 * @return the writeGroupList
	 */
	public List<BoardGroup> getWriteGroupList() {
		return this.writeGroupList;
	}

	/**
	 * @param writeGroupList the writeGroupList to set
	 */
	public void setWriteGroupList(List<BoardGroup> writeGroupList) {
		this.writeGroupList = writeGroupList;
	}

	/**
	 * @return the writeHierarchicalGroupList
	 */
	public List<String> getWriteHierarchicalGroupList() {
		return this.writeHierarchicalGroupList;
	}

	/**
	 * @param writeHierarchicalGroupList the writeHierarchicalGroupList to set
	 */
	public void setWriteHierarchicalGroupList(List<String> writeHierarchicalGroupList) {
		this.writeHierarchicalGroupList = writeHierarchicalGroupList;
	}

	/**
	 * @return the urlPopup
	 */
	public Integer getUrlPopup() {
		return this.urlPopup;
	}

	/**
	 * @param urlPopup the urlPopup to set
	 */
	public void setUrlPopup(Integer urlPopup) {
		this.urlPopup = urlPopup;
	}

	/**
	 * @return the versionManage
	 */
	public Integer getVersionManage() {
		return this.versionManage;
	}

	/**
	 * @param versionManage the versionManage to set
	 */
	public void setVersionManage(Integer versionManage) {
		this.versionManage = versionManage;
	}

	/**
	 * @return the wiki
	 */
	public Integer getWiki() {
		return this.wiki;
	}

	/**
	 * @param wiki the wiki to set
	 */
	public void setWiki(Integer wiki) {
		this.wiki = wiki;
	}

	/**
	 * @return the move
	 */
	public Integer getMove() {
		return this.move;
	}

	/**
	 * @param move the move to set
	 */
	public void setMove(Integer move) {
		this.move = move;
	}

	/**
	 * @return the aclReadPermissionList
	 */
	public ACLResourcePermission getAclReadPermissionList() {
		return this.aclReadPermissionList;
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
		return this.readPermissionList;
	}

	/**
	 * @param readPermissionList the readPermissionList to set
	 */
	public void setReadPermissionList(List<String> readPermissionList) {
		this.readPermissionList = readPermissionList;
	}

	/**
	 * @return the sameReadPermission
	 */
	public String getSameReadPermission() {
		return this.sameReadPermission;
	}

	/**
	 * @param sameReadPermission the sameReadPermission to set
	 */
	public void setSameReadPermission(String sameReadPermission) {
		this.sameReadPermission = sameReadPermission;
	}

	/**
	 * @return the aclWritePermissionList
	 */
	public ACLResourcePermission getAclWritePermissionList() {
		return this.aclWritePermissionList;
	}

	/**
	 * @param aclWritePermissionList the aclWritePermissionList to set
	 */
	public void setAclWritePermissionList(ACLResourcePermission aclWritePermissionList) {
		this.aclWritePermissionList = aclWritePermissionList;
	}

	/**
	 * @return the writePermissionList
	 */
	public List<String> getWritePermissionList() {
		return this.writePermissionList;
	}

	/**
	 * @param writePermissionList the writePermissionList to set
	 */
	public void setWritePermissionList(List<String> writePermissionList) {
		this.writePermissionList = writePermissionList;
	}

	/**
	 * @return the itemPermissionUpdate
	 */
	public String getItemPermissionUpdate() {
		return this.itemPermissionUpdate;
	}

	/**
	 * @param itemPermissionUpdate the itemPermissionUpdate to set
	 */
	public void setItemPermissionUpdate(String itemPermissionUpdate) {
		this.itemPermissionUpdate = itemPermissionUpdate;
	}

	/**
	 * @return the isDelete
	 */
	public int getIsDelete() {
		return this.isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * @return the readSearchSubFlag
	 */
	public Integer getReadSearchSubFlag() {
		return this.readSearchSubFlag;
	}

	/**
	 * @param readSearchSubFlag the readSearchSubFlag to set
	 */
	public void setReadSearchSubFlag(Integer readSearchSubFlag) {
		this.readSearchSubFlag = readSearchSubFlag;
	}

	/**
	 * @return the writeSearchSubFlag
	 */
	public Integer getWriteSearchSubFlag() {
		return this.writeSearchSubFlag;
	}

	/**
	 * @param writeSearchSubFlag the writeSearchSubFlag to set
	 */
	public void setWriteSearchSubFlag(Integer writeSearchSubFlag) {
		this.writeSearchSubFlag = writeSearchSubFlag;
	}

	/**
	 * @return the allianceIds
	 */
	public List<String> getAllianceIds() {
		return this.allianceIds;
	}

	/**
	 * @param allianceIds the allianceIds to set
	 */
	public void setAllianceIds(List<String> allianceIds) {
		this.allianceIds = allianceIds;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the tagList
	 */
	public List<Tag> getTagList() {
		return this.tagList;
	}

	/**
	 * @param tagList the tagList to set
	 */
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	/**
	 * @return the readPermission
	 */
	public String getReadPermission() {
		return this.readPermission;
	}

	/**
	 * @param readPermission the readPermission to set
	 */
	public void setReadPermission(String readPermission) {
		this.readPermission = readPermission;
	}

	/**
	 * @return the writePermission
	 */
	public String getWritePermission() {
		return this.writePermission;
	}

	/**
	 * @param writePermission the writePermission to set
	 */
	public void setWritePermission(String writePermission) {
		this.writePermission = writePermission;
	}



}