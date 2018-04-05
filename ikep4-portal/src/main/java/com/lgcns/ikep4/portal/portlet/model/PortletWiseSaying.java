package com.lgcns.ikep4.portal.portlet.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * RSS PORTLET 모델
 *
 * @author 박철종
 * @version $Id: PortletWiseSaying.java 17295 2012-02-01 08:01:56Z yu_hs $
 */
public class PortletWiseSaying extends BaseObject{
	
	/**
	 *
	 */
	private static final long serialVersionUID = -8456919881495460227L;
	
	/**
	 * 순번
	 */
	private String num;
	
	private String wiseSayingId;
	
	@NotEmpty
	@Size(max = 100)
	private String writerName;
	
	@Size(max = 100)
	private String writerEnglishName;
	
	@Size(max = 1000)
	private String wiseSayingContents;
	
	@Size(max = 1000)
	private String wiseSayingEnglishContents;
	
	private String imageFileId;
	
	private String registerId;
	
	private String registerName;
	
	private String registDate;
	
	private String updaterId;
	
	private String updaterName;
	
	private String updateDate;
	
	private FileData fileData;
	
	private boolean attachCheck;
	
	private String imageFileYn;
	
	private int usage = 1;
	
	/**
	 * 엑셀저장시 입력성공여부
	 */
	private String successYn;
	
	/**
	 * 엑셀저장시 에러메시지
	 */
	private String errMsg;

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return the wiseSayingId
	 */
	public String getWiseSayingId() {
		return wiseSayingId;
	}

	/**
	 * @param wiseSayingId the wiseSayingId to set
	 */
	public void setWiseSayingId(String wiseSayingId) {
		this.wiseSayingId = wiseSayingId;
	}

	/**
	 * @return the writerName
	 */
	public String getWriterName() {
		return writerName;
	}

	/**
	 * @param writerName the writerName to set
	 */
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	/**
	 * @return the writerEnglishName
	 */
	public String getWriterEnglishName() {
		return writerEnglishName;
	}

	/**
	 * @param writerEnglishName the writerEnglishName to set
	 */
	public void setWriterEnglishName(String writerEnglishName) {
		this.writerEnglishName = writerEnglishName;
	}

	/**
	 * @return the wiseSayingContents
	 */
	public String getWiseSayingContents() {
		return wiseSayingContents;
	}

	/**
	 * @param wiseSayingContents the wiseSayingContents to set
	 */
	public void setWiseSayingContents(String wiseSayingContents) {
		this.wiseSayingContents = wiseSayingContents;
	}

	/**
	 * @return the wiseSayingEnglishContents
	 */
	public String getWiseSayingEnglishContents() {
		return wiseSayingEnglishContents;
	}

	/**
	 * @param wiseSayingEnglishContents the wiseSayingEnglishContents to set
	 */
	public void setWiseSayingEnglishContents(String wiseSayingEnglishContents) {
		this.wiseSayingEnglishContents = wiseSayingEnglishContents;
	}

	/**
	 * @return the imageFileId
	 */
	public String getImageFileId() {
		return imageFileId;
	}

	/**
	 * @param imageFileId the imageFileId to set
	 */
	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
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
		return registerName;
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
	public String getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
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
		return updaterName;
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
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the fileData
	 */
	public FileData getFileData() {
		return fileData;
	}

	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(FileData fileData) {
		this.fileData = fileData;
	}

	/**
	 * @return the attachCheck
	 */
	public boolean getAttachCheck() {
		return attachCheck;
	}

	/**
	 * @param attachCheck the attachCheck to set
	 */
	public void setAttachCheck(boolean attachCheck) {
		this.attachCheck = attachCheck;
	}

	/**
	 * @return the successYn
	 */
	public String getSuccessYn() {
		return successYn;
	}

	/**
	 * @param successYn the successYn to set
	 */
	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the imageFileYn
	 */
	public String getImageFileYn() {
		return imageFileYn;
	}

	/**
	 * @param imageFileYn the imageFileYn to set
	 */
	public void setImageFileYn(String imageFileYn) {
		this.imageFileYn = imageFileYn;
	}
	
	/**
	 * @return the usage
	 */
	public int getUsage() {
		return usage;
	}

	/**
	 * @param usage the usage to set
	 */
	public void setUsage(int usage) {
		this.usage = usage;
	}
	
}