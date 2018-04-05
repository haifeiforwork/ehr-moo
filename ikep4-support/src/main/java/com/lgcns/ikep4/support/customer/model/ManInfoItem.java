
package com.lgcns.ikep4.support.customer.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

public class ManInfoItem extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = -7234525030546983997L;

    /** 유저 정보 삭제 상태 */
    public static final String STATUS_DELETE_FLAG = "N";

    /** 행 번호 */
    private String rowNum;

    /** 인물 ID */
    private int seqNum;

    /** 고객 id */
    private String customerId;

    /** sap 고객 ID */
    private String sapId;

    /** 고객사명  */
    private String customerName;

    /** 인물이름  */
    private String man;

    /** 직위  */
    private String jobTitle;

    /** 전화번호 */
    private String phone;

    /** 휴대폰번호 */
    private String cellPhone;

    /** 군경력 */
    private String army;

    /** 출신지역 */
    private String nativeArea;

    /** 출신고교 */
    private String nativeHighschool;

    /** 출신대학 */
    private String nativeUniversity;

    /** 출신교(기타) */
    private String nativeEtcSchool;

    /** 집주소 */
    private String homeAddress;

    /** 이메일 */
    private String email;

    /** 사진 파일명 */
    private String sajinName;

    /** 사진 파일명( sys) */
    private String sajinSys;

    /** 생년월일 */
    private String birthday;

    /** 종교 */
    private String religion;

    /** 흡연여부 */
    private String cigarette;

    /** 취미 */
    private String hobby;

    /** 주량 */
    private String drink;

    /** 기타 */
    private String etc;

    /** 삭제여부 */
    private String deleteFlag;

    /** 로케일 */
    private String oversea;

    /** 가족번호 */
    private String familyNum;

    /** 가족관계 */
    private String familyRelation;

    /** 가족이름 */
    private String familyName;

    /** 경력번호 */
    private String careernum;

    /** 주요경력 */
    private String mainCareer;

    /** 이전경력 */
    private String preCareer;

    /** 조직 내 주요업무 */
    private String mainBusiness;

    /**수정한 유저id */
    private String updaterId;

    /**수정한 유저이름 */
    private String updaterName;

    /**수정한 날짜 */
    private Date updateDate;

    /**등록한 유저id */
    private String registerId;

    /**등록한 유저이름 */
    private String registerName;

    /**등록한 날짜 */
    private Date registDate;

    /** 등록한 사용자 정보 */
    private User user;
    
    private Integer hitCount;

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public String getSapId() {
        return sapId;
    }

    public void setSapId( String sapId ) {
        this.sapId = sapId;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName( String updaterName ) {
        this.updaterName = updaterName;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName( String registerName ) {
        this.registerName = registerName;
    }

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

    public String getSajinName() {
        return sajinName;
    }

    public void setSajinName( String sajinName ) {
        this.sajinName = sajinName;
    }

    public String getSajinSys() {
        return sajinSys;
    }

    public void setSajinSys( String sajinSys ) {
        this.sajinSys = sajinSys;
    }

    public String getCareernum() {
        return careernum;
    }

    public void setCareernum( String careernum ) {
        this.careernum = careernum;
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

    public String getFamilyNum() {
        return familyNum;
    }

    public void setFamilyNum( String familyNum ) {
        this.familyNum = familyNum;
    }

    public String getFamilyRelation() {
        return familyRelation;
    }

    public void setFamilyRelation( String familyRelation ) {
        this.familyRelation = familyRelation;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName( String familyName ) {
        this.familyName = familyName;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc( String etc ) {
        this.etc = etc;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId( String customerId ) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName( String customerName ) {
        this.customerName = customerName;
    }

    public String getMan() {
        return man;
    }

    public void setMan( String man ) {
        this.man = man;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle( String jobTitle ) {
        this.jobTitle = jobTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone( String cellPhone ) {
        this.cellPhone = cellPhone;
    }

    public String getArmy() {
        return army;
    }

    public void setArmy( String army ) {
        this.army = army;
    }

    public String getNativeArea() {
        return nativeArea;
    }

    public void setNativeArea( String nativeArea ) {
        this.nativeArea = nativeArea;
    }

    public String getNativeHighschool() {
        return nativeHighschool;
    }

    public void setNativeHighschool( String nativeHighschool ) {
        this.nativeHighschool = nativeHighschool;
    }

    public String getNativeUniversity() {
        return nativeUniversity;
    }

    public void setNativeUniversity( String nativeUniversity ) {
        this.nativeUniversity = nativeUniversity;
    }

    public String getNativeEtcSchool() {
        return nativeEtcSchool;
    }

    public void setNativeEtcSchool( String nativeEtcSchool ) {
        this.nativeEtcSchool = nativeEtcSchool;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress( String homeAddress ) {
        this.homeAddress = homeAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday( String birthday ) {
        this.birthday = birthday;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion( String religion ) {
        this.religion = religion;
    }

    public String getCigarette() {
        return cigarette;
    }

    public void setCigarette( String cigarette ) {
        this.cigarette = cigarette;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby( String hobby ) {
        this.hobby = hobby;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink( String drink ) {
        this.drink = drink;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum( String rowNum ) {
        this.rowNum = rowNum;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum( int seqNum ) {
        this.seqNum = seqNum;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag( String deleteFlag ) {
        this.deleteFlag = deleteFlag;
    }

    public String getOversea() {
        return oversea;
    }

    public void setOversea( String oversea ) {
        this.oversea = oversea;
    }

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

}