
package com.lgcns.ikep4.support.customer.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객 기본정보 
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class Finance extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = -8983561250357155660L;

    private String yearValue;
    
    private String mntValue;
    
    private Integer hitCount;
    
    private String startYear;
    
    private String endYear;
    
    private String perValue;
    
    private String customerInfoId;
    
    private String customerInfoName;
    
    private String yearInfo;
    
    private String divInfo;
    
    private String mnt1;
    
    private String mnt2;
    
    private String mnt3;
    
    private String mnt4;
    
    private String mnt5;
    
    private String mnt6;
    
    private String mnt7;
    
    private String mnt8;
    
    private String mnt9;
    
    private String mnt10;
    
    private String mnt11;
    
    private String mnt12;
    
    private String mnt13;
    
    private String mnt14;
    
    private String mnt15;
    
    private String mnt16;
    
    private String mnt17;
    /** 행 번호 */
    private String rowNum;

    /** 고객 아이디 */
    private String id;
    
    /** 고객 sap아이디 */
    private String sapId;

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
    private String customerGubun;

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

    /** 삭제 플래그 */
    private String deleteFlag;

    /** 기본정보 저장 갯수 */
    private String basicinfoCount;

    /** 주요판매처 저장 갯수 (기본정보 테이블에 속한) */
    private String maincusomerCount;

    /** 인물정보 저장 갯수 (기본정보 테이블에 속한 */
    private String personCount;

    /** 상담이력 저장 갯수 */
    private String counselCount;

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

    private User user;

    /**등록한 날짜 */
    private Date registDate;

    //추가 입력 사항

    /** 업태 */
    private String type;

    /** 종목 */
    private String jijongType;

    /** 이메일 */
    private String eMail;
    
    /** 이동전화 */
    private String cellPhone;

    /** 고객 그룹 2 */

    private String customerGroup2;

    /** 고객 그룹 3 */
    private String customerGroup3;

    
  

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public String getCorporationNo1() {
        return corporationNo1;
    }

    public void setCorporationNo1( String corporationNo1 ) {
        this.corporationNo1 = corporationNo1;
    }

    public String getCorporationNo2() {
        return corporationNo2;
    }

    public void setCorporationNo2( String corporationNo2 ) {
        this.corporationNo2 = corporationNo2;
    }



    public String getRegno() {
        return regno;
    }

    public void setRegno( String regno ) {
        this.regno = regno;
    }

    public String getSapId() {

        return sapId;
    }

    public void setSapId( String sapId ) {
        this.sapId = sapId;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo( String postNo ) {
        this.postNo = postNo;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone( String cellPhone ) {
        this.cellPhone = cellPhone;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getJijongType() {
        return jijongType;
    }

    public void setJijongType( String jijongType ) {
        this.jijongType = jijongType;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail( String eMail ) {
        this.eMail = eMail;
    }

    public String getCustomerGroup2() {
        return customerGroup2;
    }

    public void setCustomerGroup2( String customerGroup2 ) {
        this.customerGroup2 = customerGroup2;
    }

    public String getCustomerGroup3() {
        return customerGroup3;
    }

    public void setCustomerGroup3( String customerGroup3 ) {
        this.customerGroup3 = customerGroup3;
    }

    public String getBasicinfoCount() {
        return basicinfoCount;
    }

    public void setBasicinfoCount( String basicinfoCount ) {
        this.basicinfoCount = basicinfoCount;
    }

    public String getMaincusomerCount() {
        return maincusomerCount;
    }

    public void setMaincusomerCount( String maincusomerCount ) {
        this.maincusomerCount = maincusomerCount;
    }

    public String getPersonCount() {
        return personCount;
    }

    public void setPersonCount( String personCount ) {
        this.personCount = personCount;
    }

    public String getCounselCount() {
        return counselCount;
    }

    public void setCounselCount( String counselCount ) {
        this.counselCount = counselCount;
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

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum( String rowNum ) {
        this.rowNum = rowNum;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getCeoName() {
        return ceoName;
    }

    public void setCeoName( String ceoName ) {
        this.ceoName = ceoName;
    } 

    public String getRegno1() {
        return regno1;
    }

    public void setRegno1( String regno1 ) {
        this.regno1 = regno1;
    }

    public String getRegno2() {
        return regno2;
    }

    public void setRegno2( String regno2 ) {
        this.regno2 = regno2;
    }

    public String getRegno3() {
        return regno3;
    }

    public void setRegno3( String regno3 ) {
        this.regno3 = regno3;
    }

    public String getCustomerGubun() {
        return customerGubun;
    }

    public void setCustomerGubun( String customerGubun ) {
        this.customerGubun = customerGubun;
    }

    public String getCorporationNo() {
        return corporationNo;
    }

    public void setCorporationNo( String corporationNo ) {
        this.corporationNo = corporationNo;
    }

    public String getSangjangFlag() {
        return sangjangFlag;
    }

    public void setSangjangFlag( String sangjangFlag ) {
        this.sangjangFlag = sangjangFlag;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate( String establishDate ) {
        this.establishDate = establishDate;
    }

    public String getMainBank() {
        return mainBank;
    }

    public void setMainBank( String mainBank ) {
        this.mainBank = mainBank;
    }

    public String getSattlingMonth() {
        return sattlingMonth;
    }

    public void setSattlingMonth( String sattlingMonth ) {
        this.sattlingMonth = sattlingMonth;
    }

    public String getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount( String employeeCount ) {
        this.employeeCount = employeeCount;
    }

    public String getSubdivisionGrade() {
        return subdivisionGrade;
    }

    public void setSubdivisionGrade( String subdivisionGrade ) {
        this.subdivisionGrade = subdivisionGrade;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1( String address1 ) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2( String address2 ) {
        this.address2 = address2;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo( String telNo ) {
        this.telNo = telNo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax( String fax ) {
        this.fax = fax;
    }

    public String getEquipmentStoreName() {
        return equipmentStoreName;
    }

    public void setEquipmentStoreName( String equipmentStoreName ) {
        this.equipmentStoreName = equipmentStoreName;
    }

    public String getEquipmentStoreExplan() {
        return equipmentStoreExplan;
    }

    public void setEquipmentStoreExplan( String equipmentStoreExplan ) {
        this.equipmentStoreExplan = equipmentStoreExplan;
    }

    public String getEquipmentPrintName() {
        return equipmentPrintName;
    }

    public void setEquipmentPrintName( String equipmentPrintName ) {
        this.equipmentPrintName = equipmentPrintName;
    }

    public String getEquipmentPrintExplan() {
        return equipmentPrintExplan;
    }

    public void setEquipmentPrintExplan( String equipmentPrintExplan ) {
        this.equipmentPrintExplan = equipmentPrintExplan;
    }

    public String getEquipmentEtcName() {
        return equipmentEtcName;
    }

    public void setEquipmentEtcName( String equipmentEtcName ) {
        this.equipmentEtcName = equipmentEtcName;
    }

    public String getEquipmentEtcExplan() {
        return equipmentEtcExplan;
    }

    public void setEquipmentEtcExplan( String equipmentEtcExplan ) {
        this.equipmentEtcExplan = equipmentEtcExplan;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector( String director ) {
        this.director = director;
    }

    public String getBusinessEmployee() {
        return businessEmployee;
    }

    public void setBusinessEmployee( String businessEmployee ) {
        this.businessEmployee = businessEmployee;
    }

    public String getStockholder() {
        return stockholder;
    }

    public void setStockholder( String stockholder ) {
        this.stockholder = stockholder;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge( String charge ) {
        this.charge = charge;
    }

    public String getSellingAmt() {
        return sellingAmt;
    }

    public void setSellingAmt( String sellingAmt ) {
        this.sellingAmt = sellingAmt;
    }

    public String getBusinessAmt() {
        return businessAmt;
    }

    public void setBusinessAmt( String businessAmt ) {
        this.businessAmt = businessAmt;
    }

    public String getMonthBuying() {
        return monthBuying;
    }

    public void setMonthBuying( String monthBuying ) {
        this.monthBuying = monthBuying;
    }

    public String getCompanyPercent() {
        return companyPercent;
    }

    public void setCompanyPercent( String companyPercent ) {
        this.companyPercent = companyPercent;
    }

    public String getHeadOfficeAddress() {
        return headOfficeAddress;
    }

    public void setHeadOfficeAddress( String headOfficeAddress ) {
        this.headOfficeAddress = headOfficeAddress;
    }

    public String getFactoryAddress1() {
        return factoryAddress1;
    }

    public void setFactoryAddress1( String factoryAddress1 ) {
        this.factoryAddress1 = factoryAddress1;
    }

    public String getFactoryAddress2() {
        return factoryAddress2;
    }

    public void setFactoryAddress2( String factoryAddress2 ) {
        this.factoryAddress2 = factoryAddress2;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag( String deleteFlag ) {
        this.deleteFlag = deleteFlag;
    }

	public String getYearValue() {
		return yearValue;
	}

	public void setYearValue(String yearValue) {
		this.yearValue = yearValue;
	}

	public String getMntValue() {
		return mntValue;
	}

	public void setMntValue(String mntValue) {
		this.mntValue = mntValue;
	}

	public String getPerValue() {
		return perValue;
	}

	public void setPerValue(String perValue) {
		this.perValue = perValue;
	}

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public String getCustomerInfoName() {
		return customerInfoName;
	}

	public void setCustomerInfoName(String customerInfoName) {
		this.customerInfoName = customerInfoName;
	}

	public String getMnt1() {
		return mnt1;
	}

	public void setMnt1(String mnt1) {
		this.mnt1 = mnt1;
	}

	public String getMnt2() {
		return mnt2;
	}

	public void setMnt2(String mnt2) {
		this.mnt2 = mnt2;
	}

	public String getMnt3() {
		return mnt3;
	}

	public void setMnt3(String mnt3) {
		this.mnt3 = mnt3;
	}

	public String getMnt4() {
		return mnt4;
	}

	public void setMnt4(String mnt4) {
		this.mnt4 = mnt4;
	}

	public String getMnt5() {
		return mnt5;
	}

	public void setMnt5(String mnt5) {
		this.mnt5 = mnt5;
	}

	public String getMnt6() {
		return mnt6;
	}

	public void setMnt6(String mnt6) {
		this.mnt6 = mnt6;
	}

	public String getMnt7() {
		return mnt7;
	}

	public void setMnt7(String mnt7) {
		this.mnt7 = mnt7;
	}

	public String getMnt8() {
		return mnt8;
	}

	public void setMnt8(String mnt8) {
		this.mnt8 = mnt8;
	}

	public String getMnt9() {
		return mnt9;
	}

	public void setMnt9(String mnt9) {
		this.mnt9 = mnt9;
	}

	public String getMnt10() {
		return mnt10;
	}

	public void setMnt10(String mnt10) {
		this.mnt10 = mnt10;
	}

	public String getMnt11() {
		return mnt11;
	}

	public void setMnt11(String mnt11) {
		this.mnt11 = mnt11;
	}

	public String getMnt12() {
		return mnt12;
	}

	public void setMnt12(String mnt12) {
		this.mnt12 = mnt12;
	}

	public String getMnt13() {
		return mnt13;
	}

	public void setMnt13(String mnt13) {
		this.mnt13 = mnt13;
	}

	public String getMnt14() {
		return mnt14;
	}

	public void setMnt14(String mnt14) {
		this.mnt14 = mnt14;
	}

	public String getMnt15() {
		return mnt15;
	}

	public void setMnt15(String mnt15) {
		this.mnt15 = mnt15;
	}

	public String getMnt16() {
		return mnt16;
	}

	public void setMnt16(String mnt16) {
		this.mnt16 = mnt16;
	}

	public String getMnt17() {
		return mnt17;
	}

	public void setMnt17(String mnt17) {
		this.mnt17 = mnt17;
	}

	public String getYearInfo() {
		return yearInfo;
	}

	public void setYearInfo(String yearInfo) {
		this.yearInfo = yearInfo;
	}

	public String getDivInfo() {
		return divInfo;
	}

	public void setDivInfo(String divInfo) {
		this.divInfo = divInfo;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

}
