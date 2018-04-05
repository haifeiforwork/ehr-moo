package com.lgcns.ikep4.portal.portlet.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;

/**
 * Portlet Innovation Model
 *
 * @author 임종상
 * @version $Id: PortletInnovation.java 19525 2012-06-27 05:34:59Z shinyong $
 */
public class PortletInnovation extends BaseObject{

	private static final long serialVersionUID = -2574879052330921622L;

	/**
	 * 이미지파일 존재 여부
	 */
	private boolean innovationImageYn;
	
	/**
	 * 포틀릿 설정 ID
	 */
	private String portletConfigId;
	
	/**
	 * 이미지 파일 ID
	 */
	private String imageFileId;
	
	/**
	 * 이미지 URL(게시물 URL)
	 */
	private String url;
	
	/**
	 * 타겟(INNER:내부, WINDOW: 외부)
	 */
	private String target;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;
	
	/**
	 * 등록자 이름
	 */
	private String registerName;
	
	/**
	 * 등록일
	 */
	private Date registDate;
	
	/**
	 * 수정일 ID
	 */
	private String updaterId;
	
	/**
	 * 수정자 이름
	 */
	private String updaterName;
	
	/**
	 * 수정일
	 */
	private Date updateDate;
	
	/**
	 * 사진 파일 데이터 
	 */
	private FileData fileData;
	
	public boolean getInnovationImageYn() {
		return innovationImageYn;
	}
	
	public void setInnovationImageYn(boolean innovationImageYn) {
		this.innovationImageYn = innovationImageYn;
	}
	
	public String getPortletConfigId() {
		return portletConfigId;
	}

	public void setPortletConfigId(String portletConfigId) {
		this.portletConfigId = portletConfigId;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

	public void setFileData(FileData fileData) {
		this.fileData = fileData;
	}

	public FileData getFileData() {
		return fileData;
	}
}
