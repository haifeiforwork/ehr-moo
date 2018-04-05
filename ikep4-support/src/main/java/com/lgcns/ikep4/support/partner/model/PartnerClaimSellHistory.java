
package com.lgcns.ikep4.support.partner.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;

public class PartnerClaimSellHistory extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = -4506510617328390153L;

    /** 행 번호 */
    private String rowNum;

    /** 품질/클레임 상담이력 순서번호 */
    private int seqNum;
    
    private Integer hitCount;

    /** 고객 id*/
    private String partnerId;

    /** 상담일자 */
    private String counselDate;

    /** 상담자*/
    private String counselor;

    /** 영업담당자*/
    private String salesman;

    /** 고객명*/
    private String partner;

    /** 피상담자*/
    private String client;

    /** 연락처*/
    private String clientPhone;

    /** 가공처명*/
    private String fabrication;

    /** 담당자(가공처)*/
    private String charge;

    /** 연락처(가공처)*/
    private String chargePhone;

    /** 상담 제목*/
    private String subject;

    /** 상담 내용*/
    private String content;

    /** 조치사항 및 조치결과 */
    private String actions;

    /** 클레임 발생현황*/
    private String claims;

    /** 설비 현황*/
    private String facilityHistory;

    /** 사용지종*/
    private String jijong;

    /** 공장 */
    private String factory;
    
    
    private String factoryName;

    /** 평량 */
    private String weight;

    /** 구분 */
    private String claimGubun;
    
    
    private String claimGubunName;

    /** 주요인쇄물*/
    private String mainPrint;

    /** 선호회사*/
    private String mainCompany;

    /** 주요거래처*/
    private String mainConnection;

    /** 댓글 갯수*/
    private Integer linereplyCount;
    
    
    private Integer subCount;
    
    private String subId;
    
    private String itemId;
    
    private Integer qualityClaimSellMoreCount;

    /**삭제 플래그 */
    private String deleteFlag;

    /**수정한 유저id */
    private String updaterId;
    
    private String fax;
    
    private String address;

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
    
    private String purpose;
    
    private String products;

    /**
     * 첨부파일 Link List
     */
    private List<FileLink> fileLinkList;

    /**
     * 첨부 파일 Data Jason 타입 리턴 값
     */
    private String fileDataListJson;

    /**
     * 첨부파일 Data List
     */
    private List<FileData> fileDataList;

    /** 등록한 사용자 정보 */
    private User user;
    
    private String relationCompany;
    
    private String comment1;
    
    private String comment2;
    
    private String directSubId;
    
    private String category;
    private String businessNo;
    private String corporationNo;
    private String zipNo;
    private String email;
    private String sapId;
    private String items;
    private String counselCnt;
    private String partnerName;
    private String ceoName;
    private String sector;
    private String keyMan;
    private String contacts;
    private String mainPhone;
    private String counselType;
    private String requestor;
    private String counselTitle;
    private String counselContent;
    private String commentCnt1;
    private String commentCnt2;
    
    
    private String keyman;
    
    private String regCnt;
    private String regSubCnt;
    private String comCnt1;
    private String comCnt2;
    
    private String userId;
    private String userName;
    private String teamName;
    private String jobTitleName;

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }
    
    public String getFactory() {
        return factory;
    }

    public void setFactory( String factory ) {
        this.factory = factory;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight( String weight ) {
        this.weight = weight;
    }

    public String getClaimGubun() {
        return claimGubun;
    }

    public void setClaimGubun( String claimGubun ) {
        this.claimGubun = claimGubun;
    }

    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum( int seqNum ) {
        this.seqNum = seqNum;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId( String partnerId ) {
        this.partnerId = partnerId;
    }

    public String getCounselDate() {
        return counselDate;
    }

    public void setCounselDate( String counselDate ) {
        this.counselDate = counselDate;
    }

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor( String counselor ) {
        this.counselor = counselor;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman( String salesman ) {
        this.salesman = salesman;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner( String partner ) {
        this.partner = partner;
    }

    public String getClient() {
        return client;
    }

    public void setClient( String client ) {
        this.client = client;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone( String clientPhone ) {
        this.clientPhone = clientPhone;
    }

    public String getFabrication() {
        return fabrication;
    }

    public void setFabrication( String fabrication ) {
        this.fabrication = fabrication;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge( String charge ) {
        this.charge = charge;
    }

    public String getChargePhone() {
        return chargePhone;
    }

    public void setChargePhone( String chargePhone ) {
        this.chargePhone = chargePhone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject( String subject ) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public String getActions() {
        return actions;
    }

    public void setActions( String actions ) {
        this.actions = actions;
    }

    public String getClaims() {
        return claims;
    }

    public void setClaims( String claims ) {
        this.claims = claims;
    }

    public String getFacilityHistory() {
        return facilityHistory;
    }

    public void setFacilityHistory( String facilityHistory ) {
        this.facilityHistory = facilityHistory;
    }

    public String getJijong() {
        return jijong;
    }

    public void setJijong( String jijong ) {
        this.jijong = jijong;
    }

    public String getMainPrint() {
        return mainPrint;
    }

    public void setMainPrint( String mainPrint ) {
        this.mainPrint = mainPrint;
    }

    public String getMainCompany() {
        return mainCompany;
    }

    public void setMainCompany( String mainCompany ) {
        this.mainCompany = mainCompany;
    }

    public String getMainConnection() {
        return mainConnection;
    }

    public void setMainConnection( String mainConnection ) {
        this.mainConnection = mainConnection;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum( String rowNum ) {
        this.rowNum = rowNum;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag( String deleteFlag ) {
        this.deleteFlag = deleteFlag;
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

    public List<FileLink> getFileLinkList() {
        return fileLinkList;
    }

    public void setFileLinkList( List<FileLink> fileLinkList ) {
        this.fileLinkList = fileLinkList;
    }

    public String getFileDataListJson() {
        return fileDataListJson;
    }

    public void setFileDataListJson( String fileDataListJson ) {
        this.fileDataListJson = fileDataListJson;
    }

    public List<FileData> getFileDataList() {
        return fileDataList;
    }

    public void setFileDataList( List<FileData> fileDataList ) {
        this.fileDataList = fileDataList;
    }

    public Integer getLinereplyCount() {
        return linereplyCount;
    }

    public void setLinereplyCount( Integer linereplyCount ) {
        this.linereplyCount = linereplyCount;
    }

	public Integer getQualityClaimSellMoreCount() {
		return qualityClaimSellMoreCount;
	}

	public void setQualityClaimSellMoreCount(Integer qualityClaimSellMoreCount) {
		this.qualityClaimSellMoreCount = qualityClaimSellMoreCount;
	}

	public Integer getSubCount() {
		return subCount;
	}

	public void setSubCount(Integer subCount) {
		this.subCount = subCount;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getClaimGubunName() {
		return claimGubunName;
	}

	public void setClaimGubunName(String claimGubunName) {
		this.claimGubunName = claimGubunName;
	}

	public String getRelationCompany() {
		return relationCompany;
	}

	public void setRelationCompany(String relationCompany) {
		this.relationCompany = relationCompany;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
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

	public String getDirectSubId() {
		return directSubId;
	}

	public void setDirectSubId(String directSubId) {
		this.directSubId = directSubId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getCorporationNo() {
		return corporationNo;
	}

	public void setCorporationNo(String corporationNo) {
		this.corporationNo = corporationNo;
	}

	public String getCounselCnt() {
		return counselCnt;
	}

	public void setCounselCnt(String counselCnt) {
		this.counselCnt = counselCnt;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
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

	public String getCounselContent() {
		return counselContent;
	}

	public void setCounselContent(String counselContent) {
		this.counselContent = counselContent;
	}

	public String getKeyman() {
		return keyman;
	}

	public void setKeyman(String keyman) {
		this.keyman = keyman;
	}

	public String getCommentCnt1() {
		return commentCnt1;
	}

	public void setCommentCnt1(String commentCnt1) {
		this.commentCnt1 = commentCnt1;
	}

	public String getCommentCnt2() {
		return commentCnt2;
	}

	public void setCommentCnt2(String commentCnt2) {
		this.commentCnt2 = commentCnt2;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

}
