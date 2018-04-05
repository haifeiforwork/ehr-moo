
package com.lgcns.ikep4.support.customer.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;

public class QualityClaimSellHistory extends BaseObject {

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
    private String customerId;

    /** 상담일자 */
    private String counselDate;

    /** 상담자*/
    private String counselor;

    /** 영업담당자*/
    private String salesman;

    /** 고객명*/
    private String customer;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId( String customerId ) {
        this.customerId = customerId;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer( String customer ) {
        this.customer = customer;
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

}
