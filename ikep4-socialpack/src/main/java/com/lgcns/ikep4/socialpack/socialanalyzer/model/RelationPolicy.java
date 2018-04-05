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
 * @version $Id: RelationPolicy.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class RelationPolicy extends BaseObject {
	private static final long serialVersionUID = 2209082450409512662L;
	
	/**
	 * 포탈_ID 
	 */
	private String  portalId;             
	
	/**
	 * 내가 속한 팀의 팀원인 경우 가중치 
	 */
	private Integer dTeamMemberWeight = 0;    
	
	/**
	 * 내가 속한 팀의 팀장인 경우 가중치 
	 */
	private Integer dTeamLeaderWeight = 0;    
	
	/**
	 * 내가 상대방을 FAVORITE로 지정한 경우의 가중치 
	 */
	private Integer dFavoriteWeight = 0;      
	
	/**
	 * 내가 FOLLOWING 하는 사람인 경우 가중치 
	 */
	private Integer dFollowingWeight = 0;     
	
	/**
	 * 나의 FOLLOWER인 경우의 가중치 
	 */
	private Integer dFollowerWeight = 0;      
	
	/**
	 * 상호 FOLLOWING하는 경우의 가중치 
	 */
	private Integer dBothFollowingWeight = 0; 
	
	/**
	 * 내가 상대방에게 발송한 메일 수에 대한 가중치 
	 */
	private Integer cMailWeight = 0;          
	
	/**
	 * 메일 발송 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer cMailMax = 0;             
	
	/**
	 * 쪽지 발송 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer cMessageMax = 0;          
	
	/**
	 * 내가 상대방에게 발송한 쪽지 수에 대한 가중치 
	 */
	private Integer cMessageWeight = 0;       
	
	/**
	 * SMS 발송 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer cSmsMax = 0;              
	
	/**
	 * 내가 상대방에게 발송한 SMS 수에 대한 가중치 
	 */
	private Integer cSmsWeight = 0;           
	
	/**
	 * 방명록 수 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer cGuestbookMax = 0;        
	
	/**
	 * 내가 상대방의 프로파링에 남긴 방명록 수에 대한 가중치 
	 */
	private Integer cGuestbookWeight = 0;     
	
	/**
	 * 나의 FOLLOWER인지 여부에 대한 가중치 
	 */
	private Integer fFollowerWeight = 0;      
	
	/**
	 * 내가 FOLLOWING 하는 사람 여부에 대한 가중치 
	 */
	private Integer fFollowingWeight = 0;     
	
	/**
	 * 상호 FOLLOWING 여부에 대한 가중치 
	 */
	private Integer fBothFollowingWeight = 0; 
	
	/**
	 * 나의 댓글 수 최대값(이 값을 넘으면 1로 간주) - BBS,Workspace,Cafe,Manual,QnA,Blog,Forum,Idea 
	 */
	private Integer fMyLinereplyMax = 0;      
	
	/**
	 * 내가 상대방의 글에 남긴 댓글 수에 대한 가중치( BBS,Workspace,Cafe,Manual,QnA,Blog,Forum,Idea) 
	 */
	private Integer fMyLinereplyWeight = 0;   
	
	/**
	 * 나의 MENTION 수 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer fMyMentionMax = 0;        
	
	/**
	 * 내가 상대방을 MENTION한 수에 대한 가중치 
	 */
	private Integer fMyMentionWeight = 0;     
	
	/**
	 * 상대방의 댓글 수 최대값(이 값을 넘으면 1로 간주) - BBS,Workspace,Cafe,Manual,QnA,Blog,Forum,Idea 
	 */
	private Integer fYourLinereplyMax = 0;    
	
	/**
	 * 상대방이 나의 글에 남긴 댓글 수에 대한 가중치(BBS,Workspace,Cafe,Manual,QnA,Blog,Forum,Idea) 
	 */
	private Integer fYourLinereplyWeight = 0; 
	
	/**
	 * 상대방의 MENTION 수 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer fYourMentionMax = 0;      
	
	/**
	 * 상대방이 나를 MENTION한 수에 대한 가중치 
	 */
	private Integer fYourMentionWeight = 0;   
	
	/**
	 * 나와 상대방이 같이 참여하는 마이크로블로깅 그룹 수에 대한 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer fBothMbloggroupMax = 0;   
	
	/**
	 * 나와 상대방이 같이 참여하는 마이크로블로깅 그룹 수에 대한 가중치 
	 */
	private Integer fBothMbloggroupWeight = 0;
	
	/**
	 * 같은 전문가 태그 수 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer eExpertTagMax = 0;        
	
	/**
	 * 같은 전문가 태그 수에 대한 가중치 
	 */
	private Integer eExpertTagWeight = 0;     
	
	/**
	 * 같은 관심 태그 수 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer eInterestTagMax = 0;      
	
	/**
	 * 같은 관심 태그 수에 대한 가중치 
	 */
	private Integer eInterestTagWeight = 0;   
	
	/**
	 * 같은 게시물 태그 수 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer eDocTagMax = 0;           
	
	/**
	 * 같은 게시물 태그 수에 대한 가중치 
	 */
	private Integer eDocTagWeight = 0;        
	
	/**
	 * 나와 상대방이 같이 답변을 단 질문 수에 대한 최대값(이 값을 넘으면 1로 간주) 
	 */
	private Integer eBothAnswerMax = 0;       
	
	/**
	 * 나와 상대방이 같이 답변을 단 질문 수에 대한 가중치 
	 */
	private Integer eBothAnswerWeight = 0;    
	
	/**
	 * 등록자 ID 
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
	 * @return the dTeamMemberWeight
	 */
	public Integer getdTeamMemberWeight() {
		return dTeamMemberWeight;
	}
	/**
	 * @param dTeamMemberWeight the dTeamMemberWeight to set
	 */
	public void setdTeamMemberWeight(Integer dTeamMemberWeight) {
		this.dTeamMemberWeight = dTeamMemberWeight;
	}
	/**
	 * @return the dTeamLeaderWeight
	 */
	public Integer getdTeamLeaderWeight() {
		return dTeamLeaderWeight;
	}
	/**
	 * @param dTeamLeaderWeight the dTeamLeaderWeight to set
	 */
	public void setdTeamLeaderWeight(Integer dTeamLeaderWeight) {
		this.dTeamLeaderWeight = dTeamLeaderWeight;
	}
	/**
	 * @return the dFavoriteWeight
	 */
	public Integer getdFavoriteWeight() {
		return dFavoriteWeight;
	}
	/**
	 * @param dFavoriteWeight the dFavoriteWeight to set
	 */
	public void setdFavoriteWeight(Integer dFavoriteWeight) {
		this.dFavoriteWeight = dFavoriteWeight;
	}
	/**
	 * @return the dFollowingWeight
	 */
	public Integer getdFollowingWeight() {
		return dFollowingWeight;
	}
	/**
	 * @param dFollowingWeight the dFollowingWeight to set
	 */
	public void setdFollowingWeight(Integer dFollowingWeight) {
		this.dFollowingWeight = dFollowingWeight;
	}
	/**
	 * @return the dFollowerWeight
	 */
	public Integer getdFollowerWeight() {
		return dFollowerWeight;
	}
	/**
	 * @param dFollowerWeight the dFollowerWeight to set
	 */
	public void setdFollowerWeight(Integer dFollowerWeight) {
		this.dFollowerWeight = dFollowerWeight;
	}
	/**
	 * @return the dBothFollowingWeight
	 */
	public Integer getdBothFollowingWeight() {
		return dBothFollowingWeight;
	}
	/**
	 * @param dBothFollowingWeight the dBothFollowingWeight to set
	 */
	public void setdBothFollowingWeight(Integer dBothFollowingWeight) {
		this.dBothFollowingWeight = dBothFollowingWeight;
	}
	/**
	 * @return the cMailWeight
	 */
	public Integer getcMailWeight() {
		return cMailWeight;
	}
	/**
	 * @param cMailWeight the cMailWeight to set
	 */
	public void setcMailWeight(Integer cMailWeight) {
		this.cMailWeight = cMailWeight;
	}
	/**
	 * @return the cMailMax
	 */
	public Integer getcMailMax() {
		return cMailMax;
	}
	/**
	 * @param cMailMax the cMailMax to set
	 */
	public void setcMailMax(Integer cMailMax) {
		this.cMailMax = cMailMax;
	}
	/**
	 * @return the cMessageMax
	 */
	public Integer getcMessageMax() {
		return cMessageMax;
	}
	/**
	 * @param cMessageMax the cMessageMax to set
	 */
	public void setcMessageMax(Integer cMessageMax) {
		this.cMessageMax = cMessageMax;
	}
	/**
	 * @return the cMessageWeight
	 */
	public Integer getcMessageWeight() {
		return cMessageWeight;
	}
	/**
	 * @param cMessageWeight the cMessageWeight to set
	 */
	public void setcMessageWeight(Integer cMessageWeight) {
		this.cMessageWeight = cMessageWeight;
	}
	/**
	 * @return the cSmsMax
	 */
	public Integer getcSmsMax() {
		return cSmsMax;
	}
	/**
	 * @param cSmsMax the cSmsMax to set
	 */
	public void setcSmsMax(Integer cSmsMax) {
		this.cSmsMax = cSmsMax;
	}
	/**
	 * @return the cSmsWeight
	 */
	public Integer getcSmsWeight() {
		return cSmsWeight;
	}
	/**
	 * @param cSmsWeight the cSmsWeight to set
	 */
	public void setcSmsWeight(Integer cSmsWeight) {
		this.cSmsWeight = cSmsWeight;
	}
	/**
	 * @return the cGuestbookMax
	 */
	public Integer getcGuestbookMax() {
		return cGuestbookMax;
	}
	/**
	 * @param cGuestbookMax the cGuestbookMax to set
	 */
	public void setcGuestbookMax(Integer cGuestbookMax) {
		this.cGuestbookMax = cGuestbookMax;
	}
	/**
	 * @return the cGuestbookWeight
	 */
	public Integer getcGuestbookWeight() {
		return cGuestbookWeight;
	}
	/**
	 * @param cGuestbookWeight the cGuestbookWeight to set
	 */
	public void setcGuestbookWeight(Integer cGuestbookWeight) {
		this.cGuestbookWeight = cGuestbookWeight;
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
	 * @return the fMyLinereplyMax
	 */
	public Integer getfMyLinereplyMax() {
		return fMyLinereplyMax;
	}
	/**
	 * @param fMyLinereplyMax the fMyLinereplyMax to set
	 */
	public void setfMyLinereplyMax(Integer fMyLinereplyMax) {
		this.fMyLinereplyMax = fMyLinereplyMax;
	}
	/**
	 * @return the fMyLinereplyWeight
	 */
	public Integer getfMyLinereplyWeight() {
		return fMyLinereplyWeight;
	}
	/**
	 * @param fMyLinereplyWeight the fMyLinereplyWeight to set
	 */
	public void setfMyLinereplyWeight(Integer fMyLinereplyWeight) {
		this.fMyLinereplyWeight = fMyLinereplyWeight;
	}
	/**
	 * @return the fMyMentionMax
	 */
	public Integer getfMyMentionMax() {
		return fMyMentionMax;
	}
	/**
	 * @param fMyMentionMax the fMyMentionMax to set
	 */
	public void setfMyMentionMax(Integer fMyMentionMax) {
		this.fMyMentionMax = fMyMentionMax;
	}
	/**
	 * @return the fMyMentionWeight
	 */
	public Integer getfMyMentionWeight() {
		return fMyMentionWeight;
	}
	/**
	 * @param fMyMentionWeight the fMyMentionWeight to set
	 */
	public void setfMyMentionWeight(Integer fMyMentionWeight) {
		this.fMyMentionWeight = fMyMentionWeight;
	}
	/**
	 * @return the fYourLinereplyMax
	 */
	public Integer getfYourLinereplyMax() {
		return fYourLinereplyMax;
	}
	/**
	 * @param fYourLinereplyMax the fYourLinereplyMax to set
	 */
	public void setfYourLinereplyMax(Integer fYourLinereplyMax) {
		this.fYourLinereplyMax = fYourLinereplyMax;
	}
	/**
	 * @return the fYourLinereplyWeight
	 */
	public Integer getfYourLinereplyWeight() {
		return fYourLinereplyWeight;
	}
	/**
	 * @param fYourLinereplyWeight the fYourLinereplyWeight to set
	 */
	public void setfYourLinereplyWeight(Integer fYourLinereplyWeight) {
		this.fYourLinereplyWeight = fYourLinereplyWeight;
	}
	/**
	 * @return the fYourMentionMax
	 */
	public Integer getfYourMentionMax() {
		return fYourMentionMax;
	}
	/**
	 * @param fYourMentionMax the fYourMentionMax to set
	 */
	public void setfYourMentionMax(Integer fYourMentionMax) {
		this.fYourMentionMax = fYourMentionMax;
	}
	/**
	 * @return the fYourMentionWeight
	 */
	public Integer getfYourMentionWeight() {
		return fYourMentionWeight;
	}
	/**
	 * @param fYourMentionWeight the fYourMentionWeight to set
	 */
	public void setfYourMentionWeight(Integer fYourMentionWeight) {
		this.fYourMentionWeight = fYourMentionWeight;
	}
	/**
	 * @return the fBothMbloggroupMax
	 */
	public Integer getfBothMbloggroupMax() {
		return fBothMbloggroupMax;
	}
	/**
	 * @param fBothMbloggroupMax the fBothMbloggroupMax to set
	 */
	public void setfBothMbloggroupMax(Integer fBothMbloggroupMax) {
		this.fBothMbloggroupMax = fBothMbloggroupMax;
	}
	/**
	 * @return the fBothMbloggroupWeight
	 */
	public Integer getfBothMbloggroupWeight() {
		return fBothMbloggroupWeight;
	}
	/**
	 * @param fBothMbloggroupWeight the fBothMbloggroupWeight to set
	 */
	public void setfBothMbloggroupWeight(Integer fBothMbloggroupWeight) {
		this.fBothMbloggroupWeight = fBothMbloggroupWeight;
	}
	/**
	 * @return the eExpertTagMax
	 */
	public Integer geteExpertTagMax() {
		return eExpertTagMax;
	}
	/**
	 * @param eExpertTagMax the eExpertTagMax to set
	 */
	public void seteExpertTagMax(Integer eExpertTagMax) {
		this.eExpertTagMax = eExpertTagMax;
	}
	/**
	 * @return the eExpertTagWeight
	 */
	public Integer geteExpertTagWeight() {
		return eExpertTagWeight;
	}
	/**
	 * @param eExpertTagWeight the eExpertTagWeight to set
	 */
	public void seteExpertTagWeight(Integer eExpertTagWeight) {
		this.eExpertTagWeight = eExpertTagWeight;
	}
	/**
	 * @return the eInterestTagMax
	 */
	public Integer geteInterestTagMax() {
		return eInterestTagMax;
	}
	/**
	 * @param eInterestTagMax the eInterestTagMax to set
	 */
	public void seteInterestTagMax(Integer eInterestTagMax) {
		this.eInterestTagMax = eInterestTagMax;
	}
	/**
	 * @return the eInterestTagWeight
	 */
	public Integer geteInterestTagWeight() {
		return eInterestTagWeight;
	}
	/**
	 * @param eInterestTagWeight the eInterestTagWeight to set
	 */
	public void seteInterestTagWeight(Integer eInterestTagWeight) {
		this.eInterestTagWeight = eInterestTagWeight;
	}
	/**
	 * @return the eDocTagMax
	 */
	public Integer geteDocTagMax() {
		return eDocTagMax;
	}
	/**
	 * @param eDocTagMax the eDocTagMax to set
	 */
	public void seteDocTagMax(Integer eDocTagMax) {
		this.eDocTagMax = eDocTagMax;
	}
	/**
	 * @return the eDocTagWeight
	 */
	public Integer geteDocTagWeight() {
		return eDocTagWeight;
	}
	/**
	 * @param eDocTagWeight the eDocTagWeight to set
	 */
	public void seteDocTagWeight(Integer eDocTagWeight) {
		this.eDocTagWeight = eDocTagWeight;
	}
	/**
	 * @return the eBothAnswerMax
	 */
	public Integer geteBothAnswerMax() {
		return eBothAnswerMax;
	}
	/**
	 * @param eBothAnswerMax the eBothAnswerMax to set
	 */
	public void seteBothAnswerMax(Integer eBothAnswerMax) {
		this.eBothAnswerMax = eBothAnswerMax;
	}
	/**
	 * @return the eBothAnswerWeight
	 */
	public Integer geteBothAnswerWeight() {
		return eBothAnswerWeight;
	}
	/**
	 * @param eBothAnswerWeight the eBothAnswerWeight to set
	 */
	public void seteBothAnswerWeight(Integer eBothAnswerWeight) {
		this.eBothAnswerWeight = eBothAnswerWeight;
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
	
}
