
package com.lgcns.ikep4.util.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class CustomerBuyingInfo extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 672892186891048697L;

    /**
     * sap 고객 id
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

    /**
     * 고객그룹2
     */
    private String customerGroup2;

    /**
     * 고객그룹3
     */
    private String customerGroup3;

    /**
     * 담당영업사원
     */
    private String businessEmployee;

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

}
