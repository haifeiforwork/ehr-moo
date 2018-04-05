
package com.lgcns.ikep4.support.customer.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class ManFamily extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 8749792410515987285L;

    /** 인물id */
    private String itemId;

    /** 가족id */
    private String familyNum;

    /** 가족이름 */
    private String familyName;

    /** 가족관계 */
    private String familyRelation;
    
    /**수정한 유저id */
    private String updaterId;

    /**수정한 날짜 */
    private Date updateDate;

    /**등록한 유저id */
    private String registerId;

    /**등록한 날짜 */
    private Date registDate;

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

    public String getItemId() {
        return itemId;
    }

    public void setItemId( String itemId ) {
        this.itemId = itemId;
    }

    public String getFamilyNum() {
        return familyNum;
    }

    public void setFamilyNum( String familyNum ) {
        this.familyNum = familyNum;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName( String familyName ) {
        this.familyName = familyName;
    }

    public String getFamilyRelation() {
        return familyRelation;
    }

    public void setFamilyRelation( String familyRelation ) {
        this.familyRelation = familyRelation;
    }

}
