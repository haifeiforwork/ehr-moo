
package com.lgcns.ikep4.support.partner.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

public class PartnerManInfoItem extends BaseObject {
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

    private String partnerId;

    /** sap 고객 ID */
    private String sapId;

    /** 고객사명  */
    private String partnerName;

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
    
    /** 고객 아이디 */
    private String id;
    
    /** 고객 이름(고객사명) */
    private String name;

    /** 대표자 이름 */
    private String ceoName;
    
    /** 사업자 등록번호1 */
    private String regno;
    
    
    /** 사업자 등록번호1 */
    private String regno1;
    
    /** 사업자 등록번호2 */
    private String regno2;
    
    /** 사업자 등록번호3 */
    private String regno3;

    /** 고객 구분 (업종)*/
    private String partnerGubun;

    /** 법인번호 */
    private String corporationNo;
    /** 법인번호1 */
    private String corporationNo1;
    /** 법인번호2 */
    private String corporationNo2;


    /** 상장여부 */
    private String sangjangFlag;

    /** 창립일 */
    private String establishDate;

    /** 주거래은행 */
    private String mainBank;

    /** 결산월 */
    private String sattlingMonth;

    /** 종업원 수 */
    private String employeeCount;

    /** 세분화 등급 */
    private String subdivisionGrade;
    
    /** 우편번호 */
    private String postNo;

    /** 주소1 */
    private String address1;

    /** 주소 2 */
    private String address2;

    /** 대표전화 */
    private String telNo;

    /** fax */
    private String fax;

    /** 설비 (창고) 명 */
    private String equipmentStoreName;

    /** 창고 상세 설명 */
    private String equipmentStoreExplan;

    /** 인쇄기 명 */
    private String equipmentPrintName;

    /** 인쇄기 상세설명 */
    private String equipmentPrintExplan;

    /** 설비(기타) 명 */
    private String equipmentEtcName;

    /** 설비 상세 설명  */
    private String equipmentEtcExplan;

    /** 대표 이사명 */
    private String director;

    /** 담당 영업사원 */
    private String businessEmployee;

    /** 주주구성 */
    private String stockholder;

    /** 거래담당자 */
    private String charge;

    /** 매출액 */
    private String sellingAmt;

    /** 영업이익 */
    private String businessAmt;

    /** 월평균 구매량 */
    private String monthBuying;

    /** 당사비율 */
    private String companyPercent;

    /** 본사주소 */
    private String headOfficeAddress;

    /** 공장 1주소 */
    private String factoryAddress1;

    /** 공장 2주소 */
    private String factoryAddress2;

    /** 기본정보 저장 갯수 */
    private String basicinfoCount;

    /** 주요판매처 저장 갯수 (기본정보 테이블에 속한) */
    private String maincusomerCount;

    /** 인물정보 저장 갯수 (기본정보 테이블에 속한 */
    private String personCount;

    /** 상담이력 저장 갯수 */
    private String counselCount;


    //추가 입력 사항

    /** 업태 */
    private String type;

    /** 종목 */
    private String jijongType;

    /** 이메일 */
    private String ceoEmail;
    
    /** 고객 그룹 2 */

    private String partnerGroup2;

    /** 고객 그룹 3 */
    private String partnerGroup3;
    
    private String divCode;
    
    private String category;
    private String businessNo;
    private String zipNo;
    private String items;
    private String sector;
    private String keyMan;
    private String contacts;
    private String mainPhone;
    private String requestor;
    private String purpose;
    private String address;
    private String joinDate;
    private String leaveDate;
    private String moveDate;
    
    private String regCnt;
    private String regSubCnt;
    private String comCnt1;
    private String comCnt2;
    
    
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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId( String partnerId ) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName( String partnerName ) {
        this.partnerName = partnerName;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public String getRegno1() {
		return regno1;
	}

	public void setRegno1(String regno1) {
		this.regno1 = regno1;
	}

	public String getRegno2() {
		return regno2;
	}

	public void setRegno2(String regno2) {
		this.regno2 = regno2;
	}

	public String getRegno3() {
		return regno3;
	}

	public void setRegno3(String regno3) {
		this.regno3 = regno3;
	}

	public String getPartnerGubun() {
		return partnerGubun;
	}

	public void setPartnerGubun(String partnerGubun) {
		this.partnerGubun = partnerGubun;
	}

	public String getCorporationNo() {
		return corporationNo;
	}

	public void setCorporationNo(String corporationNo) {
		this.corporationNo = corporationNo;
	}

	public String getCorporationNo1() {
		return corporationNo1;
	}

	public void setCorporationNo1(String corporationNo1) {
		this.corporationNo1 = corporationNo1;
	}

	public String getCorporationNo2() {
		return corporationNo2;
	}

	public void setCorporationNo2(String corporationNo2) {
		this.corporationNo2 = corporationNo2;
	}

	public String getSangjangFlag() {
		return sangjangFlag;
	}

	public void setSangjangFlag(String sangjangFlag) {
		this.sangjangFlag = sangjangFlag;
	}

	public String getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(String establishDate) {
		this.establishDate = establishDate;
	}

	public String getMainBank() {
		return mainBank;
	}

	public void setMainBank(String mainBank) {
		this.mainBank = mainBank;
	}

	public String getSattlingMonth() {
		return sattlingMonth;
	}

	public void setSattlingMonth(String sattlingMonth) {
		this.sattlingMonth = sattlingMonth;
	}

	public String getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(String employeeCount) {
		this.employeeCount = employeeCount;
	}

	public String getSubdivisionGrade() {
		return subdivisionGrade;
	}

	public void setSubdivisionGrade(String subdivisionGrade) {
		this.subdivisionGrade = subdivisionGrade;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEquipmentStoreName() {
		return equipmentStoreName;
	}

	public void setEquipmentStoreName(String equipmentStoreName) {
		this.equipmentStoreName = equipmentStoreName;
	}

	public String getEquipmentStoreExplan() {
		return equipmentStoreExplan;
	}

	public void setEquipmentStoreExplan(String equipmentStoreExplan) {
		this.equipmentStoreExplan = equipmentStoreExplan;
	}

	public String getEquipmentPrintName() {
		return equipmentPrintName;
	}

	public void setEquipmentPrintName(String equipmentPrintName) {
		this.equipmentPrintName = equipmentPrintName;
	}

	public String getEquipmentPrintExplan() {
		return equipmentPrintExplan;
	}

	public void setEquipmentPrintExplan(String equipmentPrintExplan) {
		this.equipmentPrintExplan = equipmentPrintExplan;
	}

	public String getEquipmentEtcName() {
		return equipmentEtcName;
	}

	public void setEquipmentEtcName(String equipmentEtcName) {
		this.equipmentEtcName = equipmentEtcName;
	}

	public String getEquipmentEtcExplan() {
		return equipmentEtcExplan;
	}

	public void setEquipmentEtcExplan(String equipmentEtcExplan) {
		this.equipmentEtcExplan = equipmentEtcExplan;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getBusinessEmployee() {
		return businessEmployee;
	}

	public void setBusinessEmployee(String businessEmployee) {
		this.businessEmployee = businessEmployee;
	}

	public String getStockholder() {
		return stockholder;
	}

	public void setStockholder(String stockholder) {
		this.stockholder = stockholder;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getSellingAmt() {
		return sellingAmt;
	}

	public void setSellingAmt(String sellingAmt) {
		this.sellingAmt = sellingAmt;
	}

	public String getBusinessAmt() {
		return businessAmt;
	}

	public void setBusinessAmt(String businessAmt) {
		this.businessAmt = businessAmt;
	}

	public String getMonthBuying() {
		return monthBuying;
	}

	public void setMonthBuying(String monthBuying) {
		this.monthBuying = monthBuying;
	}

	public String getCompanyPercent() {
		return companyPercent;
	}

	public void setCompanyPercent(String companyPercent) {
		this.companyPercent = companyPercent;
	}

	public String getHeadOfficeAddress() {
		return headOfficeAddress;
	}

	public void setHeadOfficeAddress(String headOfficeAddress) {
		this.headOfficeAddress = headOfficeAddress;
	}

	public String getFactoryAddress1() {
		return factoryAddress1;
	}

	public void setFactoryAddress1(String factoryAddress1) {
		this.factoryAddress1 = factoryAddress1;
	}

	public String getFactoryAddress2() {
		return factoryAddress2;
	}

	public void setFactoryAddress2(String factoryAddress2) {
		this.factoryAddress2 = factoryAddress2;
	}

	public String getBasicinfoCount() {
		return basicinfoCount;
	}

	public void setBasicinfoCount(String basicinfoCount) {
		this.basicinfoCount = basicinfoCount;
	}

	public String getMaincusomerCount() {
		return maincusomerCount;
	}

	public void setMaincusomerCount(String maincusomerCount) {
		this.maincusomerCount = maincusomerCount;
	}

	public String getPersonCount() {
		return personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}

	public String getCounselCount() {
		return counselCount;
	}

	public void setCounselCount(String counselCount) {
		this.counselCount = counselCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJijongType() {
		return jijongType;
	}

	public void setJijongType(String jijongType) {
		this.jijongType = jijongType;
	}


	public String getPartnerGroup2() {
		return partnerGroup2;
	}

	public void setPartnerGroup2(String partnerGroup2) {
		this.partnerGroup2 = partnerGroup2;
	}

	public String getPartnerGroup3() {
		return partnerGroup3;
	}

	public void setPartnerGroup3(String partnerGroup3) {
		this.partnerGroup3 = partnerGroup3;
	}

	public String getDivCode() {
		return divCode;
	}

	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getZipNo() {
		return zipNo;
	}

	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getKeyMan() {
		return keyMan;
	}

	public void setKeyMan(String keyMan) {
		this.keyMan = keyMan;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMainPhone() {
		return mainPhone;
	}

	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCeoEmail() {
		return ceoEmail;
	}

	public void setCeoEmail(String ceoEmail) {
		this.ceoEmail = ceoEmail;
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

	public String getRegCnt() {
		return regCnt;
	}

	public void setRegCnt(String regCnt) {
		this.regCnt = regCnt;
	}

	public String getRegSubCnt() {
		return regSubCnt;
	}

	public void setRegSubCnt(String regSubCnt) {
		this.regSubCnt = regSubCnt;
	}

	public String getComCnt1() {
		return comCnt1;
	}

	public void setComCnt1(String comCnt1) {
		this.comCnt1 = comCnt1;
	}

	public String getComCnt2() {
		return comCnt2;
	}

	public void setComCnt2(String comCnt2) {
		this.comCnt2 = comCnt2;
	}


}