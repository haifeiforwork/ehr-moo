/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.validator.constraints.Url;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;

/**
 * 게시판 관리 모델 클래스
 *
 * @author ${user}
 * @version $$Id: Award.java 17315 2012-02-08 04:56:13Z yruyo $$
 */
public class Award extends BaseObject {
	public static final String AWARD_ACL_CLASS_NAME = "Award-Award";

	public static final String AWARD_PERMISSION_CLASS_NAME = "Award";

	public static final String AWARD_RESOURCE_NAME = "Award";

	public static final String READ_PERMISSION_TYPE = "READ";

	public static final String WIRTE_PERMISSION_TYPE = "WIRTE";
	
	public static final String ADMIN_PERMISSION_TYPE = "ADMIN";

	public static final String MANAGE_PERMISSION_TYPE = "MANAGE";

	public static final Integer AWARD_DELETE_SERVICING = 0;

	public static final Integer AWARD_DELETE_WAIT = 1;

	public interface UpdateRequired {}

	public interface LinkTypeAward {}

	public interface CategoryTypeAward {}

	public interface AwardTypeAward {}

	private static final long serialVersionUID = -2982146151591792709L;


	/** 게시판 직계 부모 리스트 (예 고조할아버지, 증조할아버지, 할아버지, 아버지 게시판 )*/
	private List<Award> parentList;

	/** 게시판 자식 리스트 */
	private List<Award> childList;

	/** 게시판 ID*/
	private String awardId;

	/** 부모 게시판 ID */
	private String awardParentId;

	/** 같은 부모내의 게시판 형제들간의 순서 */
	private Integer sortOrder;

	private Integer indentation;

	/** 게시판 이름 문자열 길이 시작값 */
	private final static int FROM_AWARD_NAME_SIZE = 1;

	/** 게시판 이름 문자열 길이 종료값 */
	private final static int TO_AWARD_NAME_SIZE = 200;

	/** 게시판 이름 */
	@NotNull(groups={LinkTypeAward.class, AwardTypeAward.class, CategoryTypeAward.class})
	@NotEmpty(groups={LinkTypeAward.class, AwardTypeAward.class, CategoryTypeAward.class})
	@Size(min=FROM_AWARD_NAME_SIZE, max=TO_AWARD_NAME_SIZE, groups={LinkTypeAward.class, AwardTypeAward.class, CategoryTypeAward.class})
	private String awardName;

	/** 게시판 루트 ID*/
	private String awardRootId;

	/** 게시판 설명 문자열 길이 시작값 */
	@SuppressWarnings("unused")
	private final static int FROM_AWARD_DESCRIPTION_SIZE = 1;

	/** 게시판 설명 문자열 길이 종료값 */
	@SuppressWarnings("unused")
	private final static int TO_AWARD_DESCRIPTION_SIZE = 4000;

	/** 게시판 설명*/
	private String awardDescription;

	/** 
	 * 게시판 타입
	 * Award : 0
	 * Link : 1
	 * Category : 2
	 * */
	@NotNull(groups={LinkTypeAward.class, AwardTypeAward.class, CategoryTypeAward.class})
	@NotEmpty(groups={LinkTypeAward.class, AwardTypeAward.class, CategoryTypeAward.class})
	private String awardType;

	/** URL 문자열 길이 시작값 */
	private final static int FROM_URL_SIZE = 1;

	/** URL 문자열 길이 종료값 */
	private final static int TO_URL_SIZE = 1000;

	/** URL */
	@NotNull(groups=LinkTypeAward.class)
	@NotEmpty(groups=LinkTypeAward.class)
	@Url(groups=LinkTypeAward.class)
	@Size(min=FROM_URL_SIZE, max=TO_URL_SIZE, groups={LinkTypeAward.class})
	private String url;

	/** 리스트 타입 */
	private String listType = "0";

	/** 익명 모드 여부 0익명아님 1익명*/
	private Integer anonymous = 0;

	/** RSS_ATOM 허용 여부 */
	private Integer rss = 0;

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
	private Integer readPermission = 1;

	/** 게시판 쓰기 여부 */
	private Integer writePermission = 1;
	
	private Integer adminPermission = 1;
	
	/** 글 읽기 독서자 설정 여부   0 그냥 1 독서자 설정 **/
	private Integer contentsReadPermission =0;
	
	
	/** 글 읽기 독서자 메일 보내기 설정 여부   0 안보내기 1 보내기 **/
	private Integer contentsReadMail =0;

	/** 링크 게시판 팝업 표시 여부 */
	private Integer awardPopup = 0;

	/** 파일 업로드 옵션 */
	private Integer fileAttachOption = 1;

	/** 파일 허용타입 문자열 길이 시작값 */
	private final static int FROM_ALLOW_TYPE_SIZE = 1;

	/** 파일 허용타입  문자열 길이 종료값 */
	private final static int TO_ALLOW_TYPE_SIZE = 100;

	/** 파일 업로드 허용 타입 */
	@NotNull(groups=AwardTypeAward.class)
	@NotEmpty(groups=AwardTypeAward.class)
	@Size(min=FROM_ALLOW_TYPE_SIZE, max=TO_ALLOW_TYPE_SIZE)
	private String allowType;

	/** 포털 ID */
	private String portalId;

	/** 포틀릿 */
	private Integer portlet;

	/** 게시판 삭제여부 : 1 인경우 배치를 통해서 삭제가 된다.*/
	private Integer awardDelete;

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

	
	/** 읽기 롤 아이디 리스트 */
	private List<Role> readRoleList;
	
	
	/** 읽기 권한 사용자 아이디 리스트 */
	private List<User> readUserList;

	/** 읽기 권한 그룹 리스트 */
	private List<AwardGroup> readGroupList;

	/** 쓰기 사용자 아이디 리스트 */
	private List<User> writeUserList;
	
	private List<User> adminUserList;
	
	private List<AwardGroup> adminGroupList;

	/** 쓰기 그룹 리스트 */
	private List<AwardGroup> writeGroupList;

	
	/** 쓰기 롤 아이디 리스트 */
	private List<Role> writeRoleList;
	
	private List<Role> adminRoleList;
	
	
	/** 계층적 권한 부여 대상 쓰기 그룹 아이디 리스트 */
	private List<String> writeHierarchicalGroupList;
	
	private List<String> adminHierarchicalGroupList;
	
	/** 게시물 이동 사용유무 **/
	private Integer move=1;
	
	/** 게시글 말멀리 사용유무**/
	private Integer wordHead=0;
	
	/** 게시판 영어 이름 */
	@NotNull(groups={LinkTypeAward.class, AwardTypeAward.class, CategoryTypeAward.class})
	@NotEmpty(groups={LinkTypeAward.class, AwardTypeAward.class, CategoryTypeAward.class})
	@Size(min=FROM_AWARD_NAME_SIZE, max=TO_AWARD_NAME_SIZE, groups={LinkTypeAward.class, AwardTypeAward.class, CategoryTypeAward.class})
	private String awardEnglishName;
	
	/** 게시판 영어 설명*/
	private String awardEnglishDescription;
	
	/** 즐겨찾기 포틀릿에서 리스트 갯수*/
	private String viewCount;
	
	/** 모바일에서 사용하는 게시판 여부*/
	private int mobileUse;
	
	/** 모바일에서 작성권한 */
	private int mobileWriteUse;
	
	private int colorUse;
	
	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getAwardId() {
		return this.awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}

	public String getAwardParentId() {
		return this.awardParentId;
	}

	public void setAwardParentId(String awardParentId) {
		this.awardParentId = awardParentId;
	}

	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getAwardName() {
		return this.awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getAwardRootId() {
		return this.awardRootId;
	}

	public void setAwardRootId(String awardRootId) {
		this.awardRootId = awardRootId;
	}

	public String getAwardDescription() {
		return this.awardDescription;
	}

	public void setAwardDescription(String awardDescription) {
		this.awardDescription = awardDescription;
	}

	public String getAwardType() {
		return this.awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getListType() {
		return this.listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public Integer getAnonymous() {
		return this.anonymous;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public Integer getRss() {
		return this.rss;
	}

	public void setRss(Integer rss) {
		this.rss = rss;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Long getImageFileSize() {
		return this.imageFileSize;
	}

	public void setImageFileSize(Long imageFileSize) {
		this.imageFileSize = imageFileSize;
	}

	public Integer getImageWidth() {
		return this.imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getDocPopup() {
		return this.docPopup;
	}

	public void setDocPopup(Integer docPopup) {
		this.docPopup = docPopup;
	}

	public Integer getReply() {
		return this.reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}

	public Integer getLinereply() {
		return this.linereply;
	}

	public void setLinereply(Integer linereply) {
		this.linereply = linereply;
	}

	public String getRestrictionType() {
		return this.restrictionType;
	}

	public void setRestrictionType(String restrictionType) {
		this.restrictionType = restrictionType;
	}

	public Integer getReadPermission() {
		return this.readPermission;
	}

	public void setReadPermission(Integer readPermission) {
		this.readPermission = readPermission;
	}

	public Integer getWritePermission() {
		return this.writePermission;
	}

	public void setWritePermission(Integer writePermission) {
		this.writePermission = writePermission;
	}

	public String getPortalId() {
		return this.portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
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

	public List<Award> getParentList() {
		return this.parentList;
	}

	public void setParentList(List<Award> parentList) {
		this.parentList = parentList;
	}

	public List<Award> getChildList() {
		return this.childList;
	}

	public void setChildList(List<Award> childList) {
		this.childList = childList;
	}

	public Integer getHasChildren() {
		return this.hasChildren;
	}

	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List<Role> getRoleList() {
		return this.roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<User> getReadUserList() {
		return this.readUserList;
	}

	public void setReadUserList(List<User> readUserList) {
		this.readUserList = readUserList;
	}

	public List<AwardGroup> getReadGroupList() {
		return this.readGroupList;
	}

	public void setReadGroupList(List<AwardGroup> readGroupList) {
		this.readGroupList = readGroupList;
	}

	public List<User> getWriteUserList() {
		return this.writeUserList;
	}

	public void setWriteUserList(List<User> writeUserList) {
		this.writeUserList = writeUserList;
	}

	public List<AwardGroup> getWriteGroupList() {
		return this.writeGroupList;
	}

	public void setWriteGroupList(List<AwardGroup> writeGroupList) {
		this.writeGroupList = writeGroupList;
	}

	public Integer getIndentation() {
		return this.indentation;
	}

	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}

	public Integer getAwardPopup() {
		return this.awardPopup;
	}

	public void setAwardPopup(Integer awardPopup) {
		this.awardPopup = awardPopup;
	}

	public Integer getFileAttachOption() {
		return this.fileAttachOption;
	}

	public void setFileAttachOption(Integer fileAttachOption) {
		this.fileAttachOption = fileAttachOption;
	}

	public String getAllowType() {
		return this.allowType;
	}

	public void setAllowType(String allowType) {
		this.allowType = allowType;
	}

	public List<String> getWriteHierarchicalGroupList() {
		return this.writeHierarchicalGroupList;
	}

	public void setWriteHierarchicalGroupList(List<String> writeHierarchicalGroupList) {
		this.writeHierarchicalGroupList = writeHierarchicalGroupList;
	}

	public Integer getPortlet() {
		return this.portlet;
	}

	public void setPortlet(Integer portlet) {
		this.portlet = portlet;
	}

	public Integer getAwardDelete() {
		return this.awardDelete;
	}

	public void setAwardDelete(Integer awardDelete) {
		this.awardDelete = awardDelete;
	}

	public String getAwardEnglishName() {
		return awardEnglishName;
	}

	public void setAwardEnglishName(String awardEnglishName) {
		this.awardEnglishName = awardEnglishName;
	}

	public String getAwardEnglishDescription() {
		return awardEnglishDescription;
	}

	public void setAwardEnglishDescription(String awardEnglishDescription) {
		this.awardEnglishDescription = awardEnglishDescription;
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
	
	public Integer getWordHead() {
		return wordHead;
	}

	public void setWordHead(Integer wordHead) {
		this.wordHead = wordHead;
	}

	public Integer getContentsReadPermission() {
		return contentsReadPermission;
	}

	public void setContentsReadPermission(Integer contentsReadPermission) {
		this.contentsReadPermission = contentsReadPermission;
	}

	public Integer getContentsReadMail() {
		return contentsReadMail;
	}

	public void setContentsReadMail(Integer contentsReadMail) {
		this.contentsReadMail = contentsReadMail;
	}

	public List<Role> getReadRoleList() {
		return readRoleList;
	}

	public void setReadRoleList(List<Role> readRoleList) {
		this.readRoleList = readRoleList;
	}

	public List<Role> getWriteRoleList() {
		return writeRoleList;
	}

	public void setWriteRoleList(List<Role> writeRoleList) {
		this.writeRoleList = writeRoleList;
	}

	public int getMobileUse() {
		return mobileUse;
	}

	public void setMobileUse(int mobileUse) {
		this.mobileUse = mobileUse;
	}

	public int getMobileWriteUse() {
		return mobileWriteUse;
	}

	public void setMobileWriteUse(int mobileWriteUse) {
		this.mobileWriteUse = mobileWriteUse;
	}

	public int getColorUse() {
		return colorUse;
	}

	public void setColorUse(int colorUse) {
		this.colorUse = colorUse;
	}

	public Integer getAdminPermission() {
		return adminPermission;
	}

	public void setAdminPermission(Integer adminPermission) {
		this.adminPermission = adminPermission;
	}

	public List<User> getAdminUserList() {
		return adminUserList;
	}

	public void setAdminUserList(List<User> adminUserList) {
		this.adminUserList = adminUserList;
	}

	public List<AwardGroup> getAdminGroupList() {
		return adminGroupList;
	}

	public void setAdminGroupList(List<AwardGroup> adminGroupList) {
		this.adminGroupList = adminGroupList;
	}

	public List<String> getAdminHierarchicalGroupList() {
		return adminHierarchicalGroupList;
	}

	public void setAdminHierarchicalGroupList(
			List<String> adminHierarchicalGroupList) {
		this.adminHierarchicalGroupList = adminHierarchicalGroupList;
	}

	public List<Role> getAdminRoleList() {
		return adminRoleList;
	}

	public void setAdminRoleList(List<Role> adminRoleList) {
		this.adminRoleList = adminRoleList;
	}
	

}