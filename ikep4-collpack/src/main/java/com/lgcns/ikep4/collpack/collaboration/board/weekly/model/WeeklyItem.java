package com.lgcns.ikep4.collpack.collaboration.board.weekly.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;


public class WeeklyItem extends BaseObject {

	private static final long serialVersionUID = 6165586457765881193L;

	public static final String ITEM_TYPE_CODE = "WS";

	/** 에디터 파일 여부. */
	public static final String EDITOR_FILE = "Y";

	/** 첨부 파일 여부. */
	public static final String ATTECHED_FILE = "N";

	/** BBS 첨부 타입. */
	public static final String ITEM_FILE_TYPE = IKepConstant.ITEM_TYPE_CODE_WORK_SPACE_WEEKLY;

	// 게시물아이디
	private String itemId;

	// 주간보고 제목
	@NotEmpty
	@Size(min = 0, max = 1024)
	private String title;

	// 주간보고 내용
	@NotEmpty
	private String contents;

	// 조회 수
	private Integer hitCount = 0;

	// 첨부파일 수
	private Integer attachFileCount = 0;

	// 게시물 삭제여부
	private Integer itemDelete = 0;

	// 등록자 ID
	private String registerId;

	// 등록자
	private String registerName;

	// 등록자 영문명
	private String registerEnglishName;

	// 등록일
	private Date registDate;

	// 수정자 ID
	private String updaterId;

	// 수정자
	private String updaterName;

	// 수정일
	private Date updateDate;

	// 주간보고 취합여부
	private Integer isSummary = 0;

	// 게시물 아이디
	private String[] itemIds;

	// 파일 링크정보
	private List<FileLink> fileLinkList;

	// 파일 정보
	private List<FileData> fileDataList;

	// 회원 ID
	private String memberId;

	// 회원 이름
	private String userName;

	// 회원 영문명
	private String userEnglishName;

	// 회원 레벨
	private String memberLevel;

	// WS ID
	private String workspaceId;

	// 주간보고 기간
	private String weeklyTerm;

	// WS명
	private String workspaceName;

	// 직급명
	private String jobTitleName;

	// 직급 영문명
	private String jobTitleEnglishName;
	
	private String ecmFileId;
	private String ecmFileName;
	private String ecmFilePath;
	private String ecmFileUrl1;
	private String ecmFileUrl2;
	
	private List<FileData> ecmFileDataList;

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
	 * @return the isSummary
	 */
	public Integer getIsSummary() {
		return isSummary;
	}

	/**
	 * @param isSummary the isSummary to set
	 */
	public void setIsSummary(Integer isSummary) {
		this.isSummary = isSummary;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the memberLevel
	 */
	public String getMemberLevel() {
		return memberLevel;
	}

	/**
	 * @param memberLevel the memberLevel to set
	 */
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	/**
	 * @return the weeklyTerm
	 */
	public String getWeeklyTerm() {
		return weeklyTerm;
	}

	/**
	 * @param weeklyTerm the weeklyTerm to set
	 */
	public void setWeeklyTerm(String weeklyTerm) {
		this.weeklyTerm = weeklyTerm;
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
	 * @return the userEnglishName
	 */
	public String getUserEnglishName() {
		return userEnglishName;
	}

	/**
	 * @param userEnglishName the userEnglishName to set
	 */
	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
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