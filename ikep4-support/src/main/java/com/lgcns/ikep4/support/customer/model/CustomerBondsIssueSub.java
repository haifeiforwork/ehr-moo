
package com.lgcns.ikep4.support.customer.model;

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
public class CustomerBondsIssueSub extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 6132417717368958173L;

    /**
     * 댓글 ID
     */
    private String linereplyId;

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

    /**
     * 등록자 Id
     */
    private String registerId;

    /**
     * 등록자 이름
     */
    private String registerName;

    /**
     * 등록날짜
     */
    private Date registDate;

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
    
    private String counselorSub;
    
    private String jijongSub;
    
    private String factorySub;
    
    private String mfactorySub;

    private String factorySubName;
    
    private String mfactorySubName;
    
    private String claimGubunSub;
    
    private String claimGubunSubName;
    
    private String mclaimGubunSubName;
    
    private String mclaimGubunSub;
    
    private String subjectSub;
    
    private String masterSub;
    
    public String getBondsIssueSubCount() {
        return linereplyCount;
    }

    public void setBondsIssueSubCount( String linereplyCount ) {
        this.linereplyCount = linereplyCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public String getBondsIssueSubId() {
        return linereplyId;
    }

    public void setBondsIssueSubId( String linereplyId ) {
        this.linereplyId = linereplyId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId( String itemId ) {
        this.itemId = itemId;
    }

    public String getBondsIssueSubGroupId() {
        return linereplyGroupId;
    }

    public void setBondsIssueSubGroupId( String linereplyGroupId ) {
        this.linereplyGroupId = linereplyGroupId;
    }

    public String getBondsIssueSubParentId() {
        return linereplyParentId;
    }

    public void setBondsIssueSubParentId( String linereplyParentId ) {
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

    public int getBondsIssueSubDelete() {
        return linereplyDelete;
    }

    public void setBondsIssueSubDelete( int linereplyDelete ) {
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

	public String getSubjectSub() {
		return subjectSub;
	}

	public void setSubjectSub(String subjectSub) {
		this.subjectSub = subjectSub;
	}

	public String getMclaimGubunSub() {
		return mclaimGubunSub;
	}

	public void setMclaimGubunSub(String mclaimGubunSub) {
		this.mclaimGubunSub = mclaimGubunSub;
	}

	public String getMasterSub() {
		return masterSub;
	}

	public void setMasterSub(String masterSub) {
		this.masterSub = masterSub;
	}

}
