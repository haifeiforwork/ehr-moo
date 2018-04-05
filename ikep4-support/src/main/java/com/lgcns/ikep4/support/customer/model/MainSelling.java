
package com.lgcns.ikep4.support.customer.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

public class MainSelling extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 3238442811117181583L;

    /**rowNum*/
    private String rowNum;
    
    /**주요 판매처 ID */
    private int seqNum;

    /**고객 Id */
    private String customerId;
    /**
     * sap 고객 ID
     */
    private String sapId;
    

    /**주요 판매처 순서 번호 */
    private int sellingId;

    /**로케일 flag */
    private String inoutFlag = "D";

    /**판매처 이름 */
    private String sellingName;

    /**판매처 타입*/
    private String sellingType;

    /**판매처 주소 */
    private String address;

    /**fax */
    private String fax;

    /**tel */
    private String tel;

    /**판매처 구매 담당자 */
    private String buyingEmployee;

    /**거래형태 */
    private String dealForm;

    /**구매량*/
    private String buyingAmt;

    /**구매지종*/
    private String pName;

    /**삭제 플래그 */
    private String deleteFlag;

    /** 고객 이름 */
    private String customer;

    /**판매처 카운트 */
    private String sellingCnt;

    /**고객 담당자(자사직원) */
    private String chargeEmployee;

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
    
    private Integer hitCount;
    
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

    public int getSellingId() {
        return sellingId;
    }

    public void setSellingId( int sellingId ) {
        this.sellingId = sellingId;
    }

    public String getInoutFlag() {
        return inoutFlag;
    }

    public void setInoutFlag( String inoutFlag ) {
        this.inoutFlag = inoutFlag;
    }

    public String getSellingName() {
        return sellingName;
    }

    public void setSellingName( String sellingName ) {
        this.sellingName = sellingName;
    }

    public String getSellingType() {
        return sellingType;
    }

    public void setSellingType( String sellingType ) {
        this.sellingType = sellingType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax( String fax ) {
        this.fax = fax;
    }

    public String getTel() {
        return tel;
    }

    public void setTel( String tel ) {
        this.tel = tel;
    }

    public String getBuyingEmployee() {
        return buyingEmployee;
    }

    public void setBuyingEmployee( String buyingEmployee ) {
        this.buyingEmployee = buyingEmployee;
    }

    public String getDealForm() {
        return dealForm;
    }

    public void setDealForm( String dealForm ) {
        this.dealForm = dealForm;
    }

    public String getBuyingAmt() {
        return buyingAmt;
    }

    public void setBuyingAmt( String buyingAmt ) {
        this.buyingAmt = buyingAmt;
    }

    public String getpName() {
        return pName;
    }

    public void setpName( String pName ) {
        this.pName = pName;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag( String deleteFlag ) {
        this.deleteFlag = deleteFlag;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer( String customer ) {
        this.customer = customer;
    }

    public String getSellingCnt() {
        return sellingCnt;
    }

    public void setSellingCnt( String sellingCnt ) {
        this.sellingCnt = sellingCnt;
    }

    public String getChargeEmployee() {
        return chargeEmployee;
    }

    public void setChargeEmployee( String chargeEmployee ) {
        this.chargeEmployee = chargeEmployee;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum( String rowNum ) {
        this.rowNum = rowNum;
    }

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}
    
    

}
