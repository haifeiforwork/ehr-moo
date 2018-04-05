package com.lgcns.ikep4.support.message.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class PortletMessage extends BaseObject {
	
	private static final long serialVersionUID = -8166115917284410167L;

	/**
     * 등록자 ID
     */
	private String registerId;

    /**
     * List 수
     */
    private Integer listNum;

    /**
     * 등록자명
     */
    private String registerName;

    /**
     * 등록일자
     */
    private Date registDate;

    /**
     * 수정자 ID
     */
    private String updaterId;

    /**
     * 수정자 명
     */
    private String updaterName;

    /**
     * 수정일자
     */
    private Date updateDate;

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public Integer getListNum() {
		return listNum;
	}

	public void setListNum(Integer listNum) {
		this.listNum = listNum;
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
}
