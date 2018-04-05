package com.lgcns.ikep4.support.customer.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * 고객 기본정보 (구매 패턴 정보)
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class BuyingInfo extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 8604014304233042580L;

    /**
     * sap ID
     */
    private String sapId;
    
    /**
     * 판매조직
     */
    private String sellingGroup;
    
    /**
     * 유통경로
     */
    private String channel; 
    
    /**
     * 제품군
     */
    private String productLine;
   
    /** 고객그룹2 */
    private String customerGroup2;
    
    /** 고객그룹 3 */
    private String customerGroup3;
    
    /** 영업담당자 */
    private String businessEmployee;
    
    

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

    public String getBusinessEmployee() {
        return businessEmployee;
    }

    public void setBusinessEmployee( String businessEmployee ) {
        this.businessEmployee = businessEmployee;
    }

    public String getSapId() {
        return sapId;
    }

    public void setSapId( String sapId ) {
        this.sapId = sapId;
    }

    public String getSellingGroup() {
        return sellingGroup;
    }

    public void setSellingGroup( String sellingGroup ) {
        this.sellingGroup = sellingGroup;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel( String channel ) {
        this.channel = channel;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine( String productLine ) {
        this.productLine = productLine;
    }
    
    
    
    
    
}
