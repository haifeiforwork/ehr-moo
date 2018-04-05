
package com.lgcns.ikep4.support.partner.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객 기본정보 
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class Partner extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = -8983561250357155660L;

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

    private String partnerGroup2;

    /** 고객 그룹 3 */
    private String partnerGroup3;
    
    private String divCode;
    
    private int seqNum;
    
    private Integer hitCount;

    
  

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

    public String getPartnerGroup2() {
        return partnerGroup2;
    }

    public void setPartnerGroup2( String partnerGroup2 ) {
        this.partnerGroup2 = partnerGroup2;
    }

    public String getPartnerGroup3() {
        return partnerGroup3;
    }

    public void setPartnerGroup3( String partnerGroup3 ) {
        this.partnerGroup3 = partnerGroup3;
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

    public String getPartnerGubun() {
        return partnerGubun;
    }

    public void setPartnerGubun( String partnerGubun ) {
        this.partnerGubun = partnerGubun;
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

	public String getDivCode() {
		return divCode;
	}

	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}

	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 포탈 이름
	 */
	@NotEmpty
	@Size(max = 100)
	private String portalName;
	
	/**
	 * 설명
	 */
	@NotEmpty
	@Size(max = 250)
	private String description;
	
	/**
	 * 기본 포탈 여부(0:기본아님, 1:기본포탈)
	 */
	private int defaultOption;
	
	/**
	 * 로그인 배경 이미지 파일 ID
	 */
	private String loginImageId;
	
	/**
	 * 메인화면 로고 이미지 파일 ID
	 */
	private String logoImageId;
	
	/**
	 * 호스트 (IP 또는 도메인주소)
	 */
	@NotEmpty
	@Size(max = 500)
	private String portalHost;
	
	/**
	 * 루트 경로 정보
	 */
	@NotEmpty
	@Size(max = 2000)
	private String portalPath;
	
	/**
	 * 현재 서비스 여부(0:비활성화, 1:활성화)
	 */
	private int active;
	
	
	/**
	 * 디폴트 로케일 코드
	 */
	private String defaultLocaleCode;
	
	/**
	 * 디폴트 로케일 이름
	 */
	private String defaultLocaleName;
	
	/**
	 * 관리자
	 */
	private String adminUsers;
	
	/**
	 * 추가 관리자
	 */
	private String addAdmins;
	
	/**
	 * 삭제 관리자
	 */
	private String deleteAdmins;
	
	/**
	 * 포탈 호스트 별칭
	 */
	private String portalHostAlias;
	
	/**
	 * 공유할 포탈 ID
	 */
	private String sharePortalId;
	
	/**
	 * 포틀릿 공유 플래그
	 */
	private String sharePortlet;
	
	/**
	 * 메뉴 공우 플래그
	 */
	private String shareMenu;
	
	/**
	 * 퀵메뉴 공유 플래그
	 */
	private String shareQuickMenu;
	
	/**
	 * 시스템 공유 플래그
	 */
	private String shareSystem;
	
	/**
	 * 롤 공유 플래그
	 */
	private String shareRole;
	
	/**
	 * 롤타입 공유 플래그
	 */
	private String shareRoleType;
	
	/**
	 * 호칭 공유 플래그
	 */
	private String shareJobTitle; 
	
	/**
	 * 직위 공유 플래그
	 */
	private String shareJobPosition;
	
	/**
	 * 직급 공유 플래그
	 */
	private String shareJobRank;
	
	/**
	 * 직군 공유 플래그
	 */
	private String shareJobClass;
	
	/**
	 * 직책 공유 플래그
	 */
	private String shareJobDuty;
	
	/**
	 * 캐시 사용 여부
	 */
	private String cacheYn;
	
	public String getCacheYn() {
		return cacheYn;
	}

	public void setCacheYn(String cacheYn) {
		this.cacheYn = cacheYn;
	}

	public String getDefaultLocaleName() {
		return defaultLocaleName;
	}

	public void setDefaultLocaleName(String defaultLocaleName) {
		this.defaultLocaleName = defaultLocaleName;
	}

	public String getDefaultLocaleCode() {
		return defaultLocaleCode;
	}

	public void setDefaultLocaleCode(String defaultLocaleCode) {
		this.defaultLocaleCode = defaultLocaleCode;
	}

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
	 * @return the portalName
	 */
	public String getPortalName() {
		return portalName;
	}

	/**
	 * @param portalName the portalName to set
	 */
	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the defaultOption
	 */
	public int getDefaultOption() {
		return defaultOption;
	}

	/**
	 * @param defaultOption the defaultOption to set
	 */
	public void setDefaultOption(int defaultOption) {
		this.defaultOption = defaultOption;
	}

	/**
	 * @return the loginImageId
	 */
	public String getLoginImageId() {
		return loginImageId;
	}

	/**
	 * @param loginImageId the loginImageId to set
	 */
	public void setLoginImageId(String loginImageId) {
		this.loginImageId = loginImageId;
	}

	/**
	 * @return the logoImageId
	 */
	public String getLogoImageId() {
		return logoImageId;
	}

	/**
	 * @param logoImageId the logoImageId to set
	 */
	public void setLogoImageId(String logoImageId) {
		this.logoImageId = logoImageId;
	}

	/**
	 * @return the portalHost
	 */
	public String getPortalHost() {
		return portalHost;
	}

	/**
	 * @param portalHost the portalHost to set
	 */
	public void setPortalHost(String portalHost) {
		this.portalHost = portalHost;
	}

	/**
	 * @return the portalPath
	 */
	public String getPortalPath() {
		return portalPath;
	}

	/**
	 * @param portalPath the portalPath to set
	 */
	public void setPortalPath(String portalPath) {
		this.portalPath = portalPath;
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}


	/**
	 * @return the adminUsers
	 */
	public String getAdminUsers() {
		return adminUsers;
	}

	/**
	 * @param adminUsers the adminUsers to set
	 */
	public void setAdminUsers(String adminUsers) {
		this.adminUsers = adminUsers;
	}

	/**
	 * @return the addAdmins
	 */
	public String getAddAdmins() {
		return addAdmins;
	}

	/**
	 * @param addAdmins the addAdmins to set
	 */
	public void setAddAdmins(String addAdmins) {
		this.addAdmins = addAdmins;
	}

	/**
	 * @return the deleteAdmins
	 */
	public String getDeleteAdmins() {
		return deleteAdmins;
	}

	/**
	 * @param deleteAdmins the deleteAdmins to set
	 */
	public void setDeleteAdmins(String deleteAdmins) {
		this.deleteAdmins = deleteAdmins;
	}

	public String getPortalHostAlias() {
		return portalHostAlias;
	}

	public void setPortalHostAlias(String portalHostAlias) {
		this.portalHostAlias = portalHostAlias;
	}

	public String getSharePortalId() {
		return sharePortalId;
	}

	public void setSharePortalId(String sharePortalId) {
		this.sharePortalId = sharePortalId;
	}

	public String getSharePortlet() {
		return sharePortlet;
	}

	public void setSharePortlet(String sharePortlet) {
		this.sharePortlet = sharePortlet;
	}

	public String getShareMenu() {
		return shareMenu;
	}

	public void setShareMenu(String shareMenu) {
		this.shareMenu = shareMenu;
	}

	public String getShareQuickMenu() {
		return shareQuickMenu;
	}

	public void setShareQuickMenu(String shareQuickMenu) {
		this.shareQuickMenu = shareQuickMenu;
	}

	public String getShareSystem() {
		return shareSystem;
	}

	public void setShareSystem(String shareSystem) {
		this.shareSystem = shareSystem;
	}

	public String getShareRole() {
		return shareRole;
	}

	public void setShareRole(String shareRole) {
		this.shareRole = shareRole;
	}

	public String getShareRoleType() {
		return shareRoleType;
	}

	public void setShareRoleType(String shareRoleType) {
		this.shareRoleType = shareRoleType;
	}

	public String getShareJobTitle() {
		return shareJobTitle;
	}

	public void setShareJobTitle(String shareJobTitle) {
		this.shareJobTitle = shareJobTitle;
	}

	public String getShareJobPosition() {
		return shareJobPosition;
	}

	public void setShareJobPosition(String shareJobPosition) {
		this.shareJobPosition = shareJobPosition;
	}

	public String getShareJobRank() {
		return shareJobRank;
	}

	public void setShareJobRank(String shareJobRank) {
		this.shareJobRank = shareJobRank;
	}

	public String getShareJobClass() {
		return shareJobClass;
	}

	public void setShareJobClass(String shareJobClass) {
		this.shareJobClass = shareJobClass;
	}

	public String getShareJobDuty() {
		return shareJobDuty;
	}

	public void setShareJobDuty(String shareJobDuty) {
		this.shareJobDuty = shareJobDuty;
	}

}
