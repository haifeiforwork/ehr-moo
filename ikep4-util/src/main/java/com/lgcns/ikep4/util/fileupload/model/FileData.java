package com.lgcns.ikep4.util.fileupload.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * FileData
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileData.java 16611 2011-09-23 06:25:26Z giljae $
 */
/**
 * TODO Javadoc주석작성
 * 
 * @author 유승목
 * @version $Id: FileData.java 16611 2011-09-23 06:25:26Z giljae $
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonAutoDetect(value = { JsonMethod.ALL }, fieldVisibility = Visibility.ANY)
public class FileData extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 8543948295241737635L;

	/**
	 * id
	 */
	private String fileId;

	/**
	 * 저장경로
	 */
	private String filePath;

	/**
	 * 실제 저장경로
	 */
	private String filePhysicalPath;

	/**
	 * 저장파일명
	 */
	private String fileName;

	/**
	 * 실제파일명
	 */

	private String fileRealName;

	/**
	 * 파일타입
	 */
	private String fileContentsType;

	/**
	 * 확장자
	 */
	@JsonIgnore
	private String fileExtension;

	/**
	 * 크기
	 */
	private int fileSize;

	/**
	 * 에이터 첨부 파일 여부
	 */
	@JsonIgnore
	private int editorAttach;

	/**
	 * 등록자
	 */
	@JsonIgnore
	private String registerId;

	/**
	 * 등록자명
	 */
	@JsonIgnore
	private String registerName;

	/**
	 * 등록일
	 */
	@JsonIgnore
	private Date registDate;

	/**
	 * 수정자
	 */
	@JsonIgnore
	private String updaterId;

	/**
	 * 수정자명
	 */
	@JsonIgnore
	private String updaterName;

	/**
	 * 수정일
	 */
	@JsonIgnore
	private Date updateDate;

	/**
	 * 아이템 ID
	 */
	@JsonIgnore
	private String itemId;

	/**
	 * 아이템 타입 코드
	 */
	@JsonIgnore
	private String itemTypeCode;

	/**
	 * 섬네일 크기(Width)
	 */
	@JsonIgnore
	private String thumbnailWidthSize;

	/**
	 * 섬네일 크기(Height)
	 */
	@JsonIgnore
	private String thumbnailHeightSize;

	/**
	 * 다운로드 히스토리 Id
	 */
	@JsonIgnore
	private String downloadHistoryId;

	/**
	 * 다운로드 링크 URL
	 */
	@JsonIgnore
	private String downloadLinkUrl;

	/**
	 * 다운로드 클라이언트 Ip
	 */
	@JsonIgnore
	private String downloadIp;

	/**
	 * 참고값
	 */
	@JsonIgnore
	private String targetId;

	private String portalId;

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileRealName() {
		return this.fileRealName;
	}

	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}

	public String getFileContentsType() {
		return this.fileContentsType;
	}

	public void setFileContentsType(String fileContentsType) {
		this.fileContentsType = fileContentsType;
	}

	public String getFileExtension() {
		return this.fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
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

	public String getThumbnailWidthSize() {
		return this.thumbnailWidthSize;
	}

	public void setThumbnailWidthSize(String thumbnailWidthSize) {
		this.thumbnailWidthSize = thumbnailWidthSize;
	}

	public String getThumbnailHeightSize() {
		return this.thumbnailHeightSize;
	}

	public void setThumbnailHeightSize(String thumbnailHeightSize) {
		this.thumbnailHeightSize = thumbnailHeightSize;
	}

	public int getEditorAttach() {
		return this.editorAttach;
	}

	public void setEditorAttach(int editorAttach) {
		this.editorAttach = editorAttach;
	}

	public String getFilePhysicalPath() {
		return this.filePhysicalPath;
	}

	public void setFilePhysicalPath(String filePhysicalPath) {
		this.filePhysicalPath = filePhysicalPath;
	}

	public String getDownloadIp() {
		return this.downloadIp;
	}

	public void setDownloadIp(String downloadIp) {
		this.downloadIp = downloadIp;
	}

	public String getDownloadHistoryId() {
		return this.downloadHistoryId;
	}

	public void setDownloadHistoryId(String downloadHistoryId) {
		this.downloadHistoryId = downloadHistoryId;
	}

	public String getItemTypeCode() {
		return this.itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	// @JsonProperty("fileid")
	public String getFileid() {
		return this.fileId;
	}

	// @JsonProperty("name")
	public String getName() {
		return this.fileRealName;
	}

	// @JsonProperty("name")
	public String getType() {
		return this.fileContentsType;
	}

	// @JsonProperty("size")
	public int getSize() {
		return this.fileSize;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getDownloadLinkUrl() {
		return downloadLinkUrl;
	}

	public void setDownloadLinkUrl(String downloadLinkUrl) {
		this.downloadLinkUrl = downloadLinkUrl;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

}
