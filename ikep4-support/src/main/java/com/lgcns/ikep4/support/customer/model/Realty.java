
package com.lgcns.ikep4.support.customer.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객정보 고객별 상담내역 정보 
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class Realty extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = -9051656307554207818L;

    /** 행 번호 */
    private String rowNum;

    /**  상담이력 순서번호 */
    private int seqNum;

    /** 고객 id*/
    private String customerId;
    
    private Integer hitCount;
    
    private String customerName;
    
    /** sap 고객 id */
    private String sapId;
   
    /** 고객 이름 */
    private String customer;

    /** 상담일자 */
    private String counselDate;

    /** 상담자*/
    private String counselor;

    /** 상담자 부서 **/
    private String counselDept;

    /** 피상담자*/
    private String client;

    /** 피상담자 부서*/
    private String clientDept;

    /** 상담 제목*/
    private String subject;

    /** 상담 내용*/
    private String content;

    /**삭제 플래그 */
    private String deleteFlag;
    
    /** 댓글 수 */
    private Integer linereplyCount;

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
    
    private String no;  
    private String division; 
    private String address;  
    private String owner;    
    private String relation; 
    private String pyeongSpace;   
    private String pyeongBuilding;
    private String meterSpace;    
    private String meterBuilding; 
    private String meterSum; 
    private String totalSum; 
    private String rightDate;
    private String collateral;    
    private String rightSum; 
    private String debt;
    private String note;

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

    public String getCounselDept() {
        return counselDept;
    }

    public void setCounselDept( String counselDept ) {
        this.counselDept = counselDept;
    }

    public String getClient() {
        return client;
    }

    public void setClient( String client ) {
        this.client = client;
    }

    public String getClientDept() {
        return clientDept;
    }

    public void setClientDept( String clientDept ) {
        this.clientDept = clientDept;
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

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum( String rowNum ) {
        this.rowNum = rowNum;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer( String customer ) {
        this.customer = customer;
    }

    public Integer getLinereplyCount() {
        return linereplyCount;
    }

    public void setLinereplyCount( Integer linereplyCount ) {
        this.linereplyCount = linereplyCount;
    }

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getPyeongSpace() {
		return pyeongSpace;
	}

	public void setPyeongSpace(String pyeongSpace) {
		this.pyeongSpace = pyeongSpace;
	}

	public String getPyeongBuilding() {
		return pyeongBuilding;
	}

	public void setPyeongBuilding(String pyeongBuilding) {
		this.pyeongBuilding = pyeongBuilding;
	}

	public String getMeterSpace() {
		return meterSpace;
	}

	public void setMeterSpace(String meterSpace) {
		this.meterSpace = meterSpace;
	}

	public String getMeterBuilding() {
		return meterBuilding;
	}

	public void setMeterBuilding(String meterBuilding) {
		this.meterBuilding = meterBuilding;
	}

	public String getMeterSum() {
		return meterSum;
	}

	public void setMeterSum(String meterSum) {
		this.meterSum = meterSum;
	}

	public String getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(String totalSum) {
		this.totalSum = totalSum;
	}

	public String getCollateral() {
		return collateral;
	}

	public void setCollateral(String collateral) {
		this.collateral = collateral;
	}

	public String getRightSum() {
		return rightSum;
	}

	public void setRightSum(String rightSum) {
		this.rightSum = rightSum;
	}

	public String getDebt() {
		return debt;
	}

	public void setDebt(String debt) {
		this.debt = debt;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRightDate() {
		return rightDate;
	}

	public void setRightDate(String rightDate) {
		this.rightDate = rightDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

}
