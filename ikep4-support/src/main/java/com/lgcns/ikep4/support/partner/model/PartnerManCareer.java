
package com.lgcns.ikep4.support.partner.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class PartnerManCareer extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = -4579806685418581303L;

    /** 인물id */
    private String itemId;

    /** 경력id */
    private String careerNum;

    /** 주요경력 */
    private String mainCareer;

    /** 이전경력 */
    private String preCareer;

    /** 주요역할 */
    private String mainBusiness;
    
    /**수정한 유저id */
    private String updaterId;

    /**수정한 날짜 */
    private Date updateDate;

    /**등록한 유저id */
    private String registerId;

    /**등록한 날짜 */
    private Date registDate;

    private String joinDate;
    private String leaveDate;
    private String moveDate;
    
    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId( String updaterId ) {
        this.updaterId = updaterId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate( Date updateDate ) {
        this.updateDate = updateDate;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId( String registerId ) {
        this.registerId = registerId;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate( Date registDate ) {
        this.registDate = registDate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId( String itemId ) {
        this.itemId = itemId;
    }

    public String getCareerNum() {
        return careerNum;
    }

    public void setCareerNum( String careerNum ) {
        this.careerNum = careerNum;
    }

    public String getMainCareer() {
        return mainCareer;
    }

    public void setMainCareer( String mainCareer ) {
        this.mainCareer = mainCareer;
    }

    public String getPreCareer() {
        return preCareer;
    }

    public void setPreCareer( String preCareer ) {
        this.preCareer = preCareer;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness( String mainBusiness ) {
        this.mainBusiness = mainBusiness;
    }

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getMoveDate() {
		return moveDate;
	}

	public void setMoveDate(String moveDate) {
		this.moveDate = moveDate;
	}

}
