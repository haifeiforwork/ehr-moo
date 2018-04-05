package com.lgcns.ikep4.collpack.kms.site.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;


public class SiteItem extends BaseObject {

	private static final long serialVersionUID = 6165586457765881193L;

	public static final String ITEM_TYPE_CODE = "KMS";

	/** 에디터 파일 여부. */
	public static final String EDITOR_FILE = "Y";

	/** 첨부 파일 여부. */
	public static final String ATTECHED_FILE = "N";

	/** BBS 첨부 타입. */
//	public static final String ITEM_FILE_TYPE = IKepConstant.ITEM_TYPE_CODE_WORK_SPACE_ANNOUNCE;
	public static final String ITEM_FILE_TYPE = "KMS_ST";

	/**
	 * 게시물 아이디
	 */
	private String itemId;

	// 게시물 아이디
	private String[] itemIds;

	// 게시물 고유번호
	private String itemSeqId;

	// 게시물 공지여부
	private Integer itemDisplay = 0;

	// 게시물 제목
	@NotEmpty
	@Size(min = 0, max = 1024)
	private String title;

	// 게시물 내용
	@NotEmpty
	private String contents;

	// 게시물 조회수
	private Integer hitCount = 0;

	// 게시물 첨부파일 갯수
	private Integer attachFileCount = 0;

	// 게시 종료일
	private Date endDate;

	// 게시물 삭제여부
	private Integer itemDelete = 0;

	// 게시물 등록자 아이디
	private String registerId;

	// 게시물 등록자명
	private String registerName;

	// 게시물 등록자 영문명
	private String registerEnglishName;

	// 게시물 등록일
	private Date registDate;

	// 게시물 수정자 아이디
	private String updaterId;

	// 게시물 수정자 명
	private String updaterName;

	// 게시물 수정일
	private Date updateDate;

	// 게시물 첨부 링크정보
	private List<FileLink> fileLinkList;

	// 게시물 첨부 데이타 정보
	private List<FileData> fileDataList;

	// 워크스페이스 아이디
//	private String workspaceId;

	// 워크스페이스명
//	private String workspaceName;

	// 게시물 소속 워크스페이스 등록여부 1: 등록,0: 공유
	private String isOwner;

	// 게시물 공유 목록
	private Integer shareCount;

	// 게시물 공유 플레그
	private Integer shareFlagCount;

	// 그룹 전체 이름
	private String fullPath;

	// 태그
	private String tag;

	// 태그 목록
	private List<Tag> tagList;

	// 포탈아이디
	private String portalId;

	// 그룹 아이디
	private String groupId;

	// 그룹명
	private String groupName;

	// 그룹레벨
	private int groupLevel;

	// 워크스페이스 존재여부
	private int hasWorkspace;

	// 등록자 직급명
	private String jobTitleName;

	// 등록자 직급 영문명
	private String jobTitleEnglishName;

	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;

	/** 에디터내의 파일 리스트. */
	private List<FileData> editorFileDataList;

	/** 에디터내의 파일링크 리스트. */
	private List<FileLink> editorFileLinkList;

	public String getItemTypeCode() {
		return ITEM_TYPE_CODE;
	}

	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemSeqId() {
		return itemSeqId;
	}

	public void setItemSeqId(String itemSeqId) {
		this.itemSeqId = itemSeqId;
	}

	public Integer getItemDisplay() {
		return itemDisplay;
	}

	public void setItemDisplay(Integer itemDisplay) {
		this.itemDisplay = itemDisplay;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public Integer getAttachFileCount() {
		return attachFileCount;
	}

	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getItemDelete() {
		return itemDelete;
	}

	public void setItemDelete(Integer itemDelete) {
		this.itemDelete = itemDelete;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

//	/**
//	 * @return the workspaceId
//	 */
//	public String getWorkspaceId() {
//		return workspaceId;
//	}
//
//	/**
//	 * @param workspaceId the workspaceId to set
//	 */
//	public void setWorkspaceId(String workspaceId) {
//		this.workspaceId = workspaceId;
//	}

	/**
	 * @return the isOwner
	 */
	public String getIsOwner() {
		return isOwner;
	}

	/**
	 * @param isOwner the isOwner to set
	 */
	public void setIsOwner(String isOwner) {
		this.isOwner = isOwner;
	}

//	/**
//	 * @return the workspaceName
//	 */
//	public String getWorkspaceName() {
//		return workspaceName;
//	}
//
//	/**
//	 * @param workspaceName the workspaceName to set
//	 */
//	public void setWorkspaceName(String workspaceName) {
//		this.workspaceName = workspaceName;
//	}

	/**
	 * @return the shareCount
	 */
	public Integer getShareCount() {
		return shareCount;
	}

	/**
	 * @param shareCount the shareCount to set
	 */
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	/**
	 * @return the itemIds
	 */
	public String[] getItemIds() {
		return itemIds;
	}

	/**
	 * @param itemIds the itemIds to set
	 */
	public void setItemIds(String[] itemIds) {
		System.arraycopy(itemIds, 0, this.itemIds, 0, itemIds.length);
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the shareFlagCount
	 */
	public Integer getShareFlagCount() {
		return shareFlagCount;
	}

	/**
	 * @param shareFlagCount the shareFlagCount to set
	 */
	public void setShareFlagCount(Integer shareFlagCount) {
		this.shareFlagCount = shareFlagCount;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
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
		return tagList;
	}

	/**
	 * @param tagList the tagList to set
	 */
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	/**
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}

	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the hasWorkspace
	 */
	public int getHasWorkspace() {
		return hasWorkspace;
	}

	/**
	 * @param hasWorkspace the hasWorkspace to set
	 */
	public void setHasWorkspace(int hasWorkspace) {
		this.hasWorkspace = hasWorkspace;
	}

	/**
	 * @return the groupLevel
	 */
	public int getGroupLevel() {
		return groupLevel;
	}

	/**
	 * @param groupLevel the groupLevel to set
	 */
	public void setGroupLevel(int groupLevel) {
		this.groupLevel = groupLevel;
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
	 * @return the msie
	 */
	public Integer getMsie() {
		return msie;
	}

	/**
	 * @param msie the msie to set
	 */
	public void setMsie(Integer msie) {
		this.msie = msie;
	}

	/**
	 * @return the editorFileDataList
	 */
	public List<FileData> getEditorFileDataList() {
		return editorFileDataList;
	}

	/**
	 * @param editorFileDataList the editorFileDataList to set
	 */
	public void setEditorFileDataList(List<FileData> editorFileDataList) {
		this.editorFileDataList = editorFileDataList;
	}

	/**
	 * @return the editorFileLinkList
	 */
	public List<FileLink> getEditorFileLinkList() {
		return editorFileLinkList;
	}

	/**
	 * @param editorFileLinkList the editorFileLinkList to set
	 */
	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

}