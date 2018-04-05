/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialityPolicy.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class SocialityPolicy extends BaseObject {
	private static final long serialVersionUID = 203564042631876453L;
	
	/**
	 * 포탈ID 
	 */
	private String  portalId;            
	
	/**
	 * INFLUENCE INDEX의 FOLLOWER 수에 대한 가중치 
	 */
	private Integer iFollowerWeight = 0;      
	
	/**
	 * INFLUENCE INDEX의 RETWEET 수에 대한 가중치 
	 */
	private Integer iRetweetWeight = 0;      
	
	/**
	 * INFLUENCE INDEX의 TWEET 수에 대한 가중치 
	 */
	private Integer iTweetWeight = 0;        
	
	/**
	 * INFLUENCE INDEX의 블로그 방문 수에 대한 가중치 
	 */
	private Integer iBlogVisitWeight = 0;    
	
	/**
	 * INFLUENCE VALUE( SUM(항목*가중치)) 의 최대값, 최대값 이상인 경우 무조건 최대값으로 간주 
	 */
	private Integer iMaxValue = 0;           
	
	/**
	 * FELLOWSHIP INDEX의 FOLLOWER수에 대한 가중치 
	 */
	private Integer fFollowerWeight = 0;     
	
	/**
	 * FELLOWSHIP INDEX의 FOLLOWING수에 대한 가중치 
	 */
	private Integer fFollowingWeight = 0;    
	
	/**
	 * FELLOWSHIP INDEX의 상호 FOLLOWING 수에 대한 가중치 
	 */
	private Integer fBothFollowingWeight = 0;
	
	/**
	 * FELLOWSHIP INDEX의 마이크로블로깅 참여 그룹 수에 대한 가중치 
	 */
	private Integer fMblogGroupWeight = 0;   
	
	/**
	 * FELLOWSHIP INDEX의 방명록 수에 대한 가중치 
	 */
	private Integer fGuestbookWeight = 0;    
	
	/**
	 * FELLOWSHIP VALUE( SUM(항목*가중치)) 의 최대값, 최대값 이상인 경우 무조건 최대값으로 간주 
	 */
	private Integer fMaxValue = 0;           
	
	/**
	 * 등록자ID 
	 */
	private String  registerId;          
	
	/**
	 * 등록자 이름 
	 */
	private String  registerName;        
	
	/**
	 * 등록일 
	 */
	private Date    registDate;          
	
	/**
	 * 수정자 ID 
	 */
	private String  updaterId;           
	
	/**
	 * 수정자 이름 
	 */
	private String  updaterName;         
	
	/**
	 * 수정일 
	 */
	private Date    updateDate;      
	
	/**
	 * INFLUENCE INDEX의 프로파일 방문 수에 대한 가중치 
	 */
	private Integer iProfileVisitWeight = 0;  

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
	 * @return the iFollowerWeight
	 */
	public Integer getiFollowerWeight() {
		return iFollowerWeight;
	}
	/**
	 * @param iFollowerWeight the iFollowerWeight to set
	 */
	public void setiFollowerWeight(Integer iFollowerWeight) {
		this.iFollowerWeight = iFollowerWeight;
	}
	/**
	 * @return the iRetweetWeight
	 */
	public Integer getiRetweetWeight() {
		return iRetweetWeight;
	}
	/**
	 * @param iRetweetWeight the iRetweetWeight to set
	 */
	public void setiRetweetWeight(Integer iRetweetWeight) {
		this.iRetweetWeight = iRetweetWeight;
	}
	/**
	 * @return the iTweetWeight
	 */
	public Integer getiTweetWeight() {
		return iTweetWeight;
	}
	/**
	 * @param iTweetWeight the iTweetWeight to set
	 */
	public void setiTweetWeight(Integer iTweetWeight) {
		this.iTweetWeight = iTweetWeight;
	}
	/**
	 * @return the iBlogVisitWeight
	 */
	public Integer getiBlogVisitWeight() {
		return iBlogVisitWeight;
	}
	/**
	 * @param iBlogVisitWeight the iBlogVisitWeight to set
	 */
	public void setiBlogVisitWeight(Integer iBlogVisitWeight) {
		this.iBlogVisitWeight = iBlogVisitWeight;
	}
	/**
	 * @return the iMaxValue
	 */
	public Integer getiMaxValue() {
		return iMaxValue;
	}
	/**
	 * @param iMaxValue the iMaxValue to set
	 */
	public void setiMaxValue(Integer iMaxValue) {
		this.iMaxValue = iMaxValue;
	}
	/**
	 * @return the fFollowerWeight
	 */
	public Integer getfFollowerWeight() {
		return fFollowerWeight;
	}
	/**
	 * @param fFollowerWeight the fFollowerWeight to set
	 */
	public void setfFollowerWeight(Integer fFollowerWeight) {
		this.fFollowerWeight = fFollowerWeight;
	}
	/**
	 * @return the fFollowingWeight
	 */
	public Integer getfFollowingWeight() {
		return fFollowingWeight;
	}
	/**
	 * @param fFollowingWeight the fFollowingWeight to set
	 */
	public void setfFollowingWeight(Integer fFollowingWeight) {
		this.fFollowingWeight = fFollowingWeight;
	}
	/**
	 * @return the fBothFollowingWeight
	 */
	public Integer getfBothFollowingWeight() {
		return fBothFollowingWeight;
	}
	/**
	 * @param fBothFollowingWeight the fBothFollowingWeight to set
	 */
	public void setfBothFollowingWeight(Integer fBothFollowingWeight) {
		this.fBothFollowingWeight = fBothFollowingWeight;
	}
	/**
	 * @return the fMblogGroupWeight
	 */
	public Integer getfMblogGroupWeight() {
		return fMblogGroupWeight;
	}
	/**
	 * @param fMblogGroupWeight the fMblogGroupWeight to set
	 */
	public void setfMblogGroupWeight(Integer fMblogGroupWeight) {
		this.fMblogGroupWeight = fMblogGroupWeight;
	}
	/**
	 * @return the fGuestbookWeight
	 */
	public Integer getfGuestbookWeight() {
		return fGuestbookWeight;
	}
	/**
	 * @param fGuestbookWeight the fGuestbookWeight to set
	 */
	public void setfGuestbookWeight(Integer fGuestbookWeight) {
		this.fGuestbookWeight = fGuestbookWeight;
	}
	/**
	 * @return the fMaxValue
	 */
	public Integer getfMaxValue() {
		return fMaxValue;
	}
	/**
	 * @param fMaxValue the fMaxValue to set
	 */
	public void setfMaxValue(Integer fMaxValue) {
		this.fMaxValue = fMaxValue;
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
	public Date getRegistDate() {
		return registDate;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the iProfileVisitWeight
	 */
	public Integer getiProfileVisitWeight() {
		return iProfileVisitWeight;
	}
	/**
	 * @param iProfileVisitWeight the iProfileVisitWeight to set
	 */
	public void setiProfileVisitWeight(Integer iProfileVisitWeight) {
		this.iProfileVisitWeight = iProfileVisitWeight;
	} 
}
