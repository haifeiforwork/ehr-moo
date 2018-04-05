
package com.lgcns.ikep4.support.partner.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객정보 댓글 정보
 *  
 * @author SongHeeJung
 * @version $Id$
 */
public class PartnerQualitySub extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 6132417717368958173L;

    /**
     * 댓글 ID
     */
    private String linereplyId;
    
    
    private String partnerName;
    
    private String subPartnerName;

    /**
     * 아이템 Type
     */
    private String itemType;

    /**
     * 아이템 ID
     */
    private String itemId;

    /**
     * 댓글 Thread의 그룹 ID
     */
    private String linereplyGroupId;
    
    private String rowNum;

    /**
     * 부모 Id
     */
    private String linereplyParentId;

    /**
     * 같은 Thread 그룹에 속해있는 댓글들 간의 순서. 같은 Thread에서 단순 정렬 순서
     */
    private int step = 0;

    /**
     * 댓글 Thread 표시할때 들여쓰기의 숫자(댓글의 경우 최대 3depth로 제한)
     */
    private int indentation = 0;

    /**
     * 토론 의견 내용
     */
    @NotEmpty
    @Size( min = 1, max = 1500 )
    private String contents;

    @NotEmpty
    @Size( min = 1, max = 1500 )
    private String comment1;
    
    @NotEmpty
    @Size( min = 1, max = 1500 )
    private String comment2;
    /**
     * 등록자 Id
     */
    private String registerId;

    /**
     * 등록자 이름
     */
    private String registerName;

    private String commentuser1;
    
    private String commentuser2;
    
    private String commentuserTeam1;
    
    private String commentuserTeam2;
    
    private String commentAuthUser1;
    
    private String commentAuthUser2;
    /**
     * 등록날짜
     */
    private Date registDate;
    
    private String commentRegisterId1;
    
    private Date commentRegistDate1;
    
    private String commentRegisterId2;
    
    private Date commentRegistDate2;

    /**
     * 수정자 Id
     */
    private String updaterId;

    /**
     * 수정자 이름
     */
    private String updaterName;

    /**
     * 수정일시
     */
    private Date updateDate;

    /**
     * 댓글 삭제여부
     */
    private int linereplyDelete;

    /**
     * 댓글 추천 수
     */
    private int recommendCount;

    /**
     * 팀이름
     */
    private String teamName;

    /**
     * 직책이름
     */
    private String jobTitleName;

    /**
     * 사용자 영어이름
     */
    private String userEnglishName;

    /**
     * 직급 영어이름
     * @return
     */
    private String jobTitleEnglishName;

    /**
     * 팀 영어이름
     * @return
     */
    private String teamEnglishName;

    /**
     * 사진파일 경로 (큰이미지)
     */
    private String picturePath;

    /**
     * 사진파일 경로 (작은이미지)
     */
    private String profilePicturePath;

    /** 작성자  */
    private User user;
    
    
    /** 댓글 갯수 */
    private String linereplyCount;
    
    private String counselDateSub;
    
    private String mcounselDateSub;
    
    private String counselDate;
    
    private String counselorSub;
    
    private String counselor;
    
    private String jijongSub;
    
    private String factorySub;
    
    private String mfactorySub;

    private String factorySubName;
    
    private String mfactorySubName;
    
    private String claimGubunSub;
    
    private String claimGubunSubName;
    
    private String mclaimGubunSubName;
    
    private String mclaimGubunSub;
    
    private String weightSub;
    
    private String subjectSub;
    
    private String masterSub;
    
    private String counselType;
    private String requestor;
    private String counselTitle;
    private String counselContents;
    
    public String getQualityClaimSellMoreCount() {
        return linereplyCount;
    }

    public void setQualityClaimSellMoreCount( String linereplyCount ) {
        this.linereplyCount = linereplyCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public String getQualityClaimSellMoreId() {
        return linereplyId;
    }

    public void setQualityClaimSellMoreId( String linereplyId ) {
        this.linereplyId = linereplyId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId( String itemId ) {
        this.itemId = itemId;
    }

    public String getQualityClaimSellMoreGroupId() {
        return linereplyGroupId;
    }

    public void setQualityClaimSellMoreGroupId( String linereplyGroupId ) {
        this.linereplyGroupId = linereplyGroupId;
    }

    public String getQualityClaimSellMoreParentId() {
        return linereplyParentId;
    }

    public void setQualityClaimSellMoreParentId( String linereplyParentId ) {
        this.linereplyParentId = linereplyParentId;
    }

    public int getStep() {
        return step;
    }

    public void setStep( int step ) {
        this.step = step;
    }

    public int getIndentation() {
        return indentation;
    }

    public void setIndentation( int indentation ) {
        this.indentation = indentation;
    }

    public String getContents() {
        return contents;
    }

    public void setContents( String contents ) {
        this.contents = contents;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId( String registerId ) {
        this.registerId = registerId;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName( String registerName ) {
        this.registerName = registerName;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate( Date registDate ) {
        this.registDate = registDate;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId( String updaterId ) {
        this.updaterId = updaterId;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName( String updaterName ) {
        this.updaterName = updaterName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate( Date updateDate ) {
        this.updateDate = updateDate;
    }

    public int getQualityClaimSellMoreDelete() {
        return linereplyDelete;
    }

    public void setQualityClaimSellMoreDelete( int linereplyDelete ) {
        this.linereplyDelete = linereplyDelete;
    }

    public int getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount( int recommendCount ) {
        this.recommendCount = recommendCount;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName( String teamName ) {
        this.teamName = teamName;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName( String jobTitleName ) {
        this.jobTitleName = jobTitleName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType( String itemType ) {
        this.itemType = itemType;
    }

    public String getUserEnglishName() {
        return userEnglishName;
    }

    public void setUserEnglishName( String userEnglishName ) {
        this.userEnglishName = userEnglishName;
    }

    public String getJobTitleEnglishName() {
        return jobTitleEnglishName;
    }

    public void setJobTitleEnglishName( String jobTitleEnglishName ) {
        this.jobTitleEnglishName = jobTitleEnglishName;
    }

    public String getTeamEnglishName() {
        return teamEnglishName;
    }

    public void setTeamEnglishName( String teamEnglishName ) {
        this.teamEnglishName = teamEnglishName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath( String picturePath ) {
        this.picturePath = picturePath;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath( String profilePicturePath ) {
        this.profilePicturePath = profilePicturePath;
    }

	public String getLinereplyId() {
		return linereplyId;
	}

	public void setLinereplyId(String linereplyId) {
		this.linereplyId = linereplyId;
	}

	public String getLinereplyGroupId() {
		return linereplyGroupId;
	}

	public void setLinereplyGroupId(String linereplyGroupId) {
		this.linereplyGroupId = linereplyGroupId;
	}

	public String getLinereplyParentId() {
		return linereplyParentId;
	}

	public void setLinereplyParentId(String linereplyParentId) {
		this.linereplyParentId = linereplyParentId;
	}

	public int getLinereplyDelete() {
		return linereplyDelete;
	}

	public void setLinereplyDelete(int linereplyDelete) {
		this.linereplyDelete = linereplyDelete;
	}

	public String getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(String linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCounselDateSub() {
		return counselDateSub;
	}

	public void setCounselDateSub(String counselDateSub) {
		this.counselDateSub = counselDateSub;
	}

	public String getCounselorSub() {
		return counselorSub;
	}

	public void setCounselorSub(String counselorSub) {
		this.counselorSub = counselorSub;
	}

	public String getJijongSub() {
		return jijongSub;
	}

	public void setJijongSub(String jijongSub) {
		this.jijongSub = jijongSub;
	}

	public String getFactorySub() {
		return factorySub;
	}

	public void setFactorySub(String factorySub) {
		this.factorySub = factorySub;
	}

	public String getMcounselDateSub() {
		return mcounselDateSub;
	}

	public void setMcounselDateSub(String mcounselDateSub) {
		this.mcounselDateSub = mcounselDateSub;
	}

	public String getFactorySubName() {
		return factorySubName;
	}

	public void setFactorySubName(String factorySubName) {
		this.factorySubName = factorySubName;
	}

	public String getMfactorySub() {
		return mfactorySub;
	}

	public void setMfactorySub(String mfactorySub) {
		this.mfactorySub = mfactorySub;
	}

	public String getMfactorySubName() {
		return mfactorySubName;
	}

	public void setMfactorySubName(String mfactorySubName) {
		this.mfactorySubName = mfactorySubName;
	}

	public String getClaimGubunSub() {
		return claimGubunSub;
	}

	public void setClaimGubunSub(String claimGubunSub) {
		this.claimGubunSub = claimGubunSub;
	}

	public String getClaimGubunSubName() {
		return claimGubunSubName;
	}

	public void setClaimGubunSubName(String claimGubunSubName) {
		this.claimGubunSubName = claimGubunSubName;
	}

	public String getMclaimGubunSubName() {
		return mclaimGubunSubName;
	}

	public void setMclaimGubunSubName(String mclaimGubunSubName) {
		this.mclaimGubunSubName = mclaimGubunSubName;
	}

	public String getMclaimGubunSub() {
		return mclaimGubunSub;
	}

	public void setMclaimGubunSub(String mclaimGubunSub) {
		this.mclaimGubunSub = mclaimGubunSub;
	}

	public String getWeightSub() {
		return weightSub;
	}

	public void setWeightSub(String weightSub) {
		this.weightSub = weightSub;
	}

	public String getSubjectSub() {
		return subjectSub;
	}

	public void setSubjectSub(String subjectSub) {
		this.subjectSub = subjectSub;
	}

	public String getMasterSub() {
		return masterSub;
	}

	public void setMasterSub(String masterSub) {
		this.masterSub = masterSub;
	}

	public String getComment1() {
		return comment1;
	}

	public void setComment1(String comment1) {
		this.comment1 = comment1;
	}

	public String getComment2() {
		return comment2;
	}

	public void setComment2(String comment2) {
		this.comment2 = comment2;
	}

	public String getCommentRegisterId1() {
		return commentRegisterId1;
	}

	public void setCommentRegisterId1(String commentRegisterId1) {
		this.commentRegisterId1 = commentRegisterId1;
	}

	public Date getCommentRegistDate1() {
		return commentRegistDate1;
	}

	public void setCommentRegistDate1(Date commentRegistDate1) {
		this.commentRegistDate1 = commentRegistDate1;
	}

	public String getCommentRegisterId2() {
		return commentRegisterId2;
	}

	public void setCommentRegisterId2(String commentRegisterId2) {
		this.commentRegisterId2 = commentRegisterId2;
	}

	public Date getCommentRegistDate2() {
		return commentRegistDate2;
	}

	public void setCommentRegistDate2(Date commentRegistDate2) {
		this.commentRegistDate2 = commentRegistDate2;
	}

	public String getCommentuser1() {
		return commentuser1;
	}

	public void setCommentuser1(String commentuser1) {
		this.commentuser1 = commentuser1;
	}

	public String getCommentuser2() {
		return commentuser2;
	}

	public void setCommentuser2(String commentuser2) {
		this.commentuser2 = commentuser2;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getCommentuserTeam1() {
		return commentuserTeam1;
	}

	public void setCommentuserTeam1(String commentuserTeam1) {
		this.commentuserTeam1 = commentuserTeam1;
	}

	public String getCommentuserTeam2() {
		return commentuserTeam2;
	}

	public void setCommentuserTeam2(String commentuserTeam2) {
		this.commentuserTeam2 = commentuserTeam2;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getCounselType() {
		return counselType;
	}

	public void setCounselType(String counselType) {
		this.counselType = counselType;
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public String getCounselTitle() {
		return counselTitle;
	}

	public void setCounselTitle(String counselTitle) {
		this.counselTitle = counselTitle;
	}

	public String getCounselContents() {
		return counselContents;
	}

	public void setCounselContents(String counselContents) {
		this.counselContents = counselContents;
	}

	public String getCounselDate() {
		return counselDate;
	}

	public void setCounselDate(String counselDate) {
		this.counselDate = counselDate;
	}

	public String getCounselor() {
		return counselor;
	}

	public void setCounselor(String counselor) {
		this.counselor = counselor;
	}

	public String getCommentAuthUser1() {
		return commentAuthUser1;
	}

	public void setCommentAuthUser1(String commentAuthUser1) {
		this.commentAuthUser1 = commentAuthUser1;
	}

	public String getCommentAuthUser2() {
		return commentAuthUser2;
	}

	public void setCommentAuthUser2(String commentAuthUser2) {
		this.commentAuthUser2 = commentAuthUser2;
	}

	public String getSubPartnerName() {
		return subPartnerName;
	}

	public void setSubPartnerName(String subPartnerName) {
		this.subPartnerName = subPartnerName;
	}

}
