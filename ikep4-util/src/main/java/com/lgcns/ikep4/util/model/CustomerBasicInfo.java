package com.lgcns.ikep4.util.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * 고객 기본정보 모델
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class CustomerBasicInfo extends BaseObject{

    /**
     *
     */
    private static final long serialVersionUID = 4151991722201137931L;
    
    /**
     * sapID
     */
    private String sapId;
    
    /**
     * 고객사명 
     */
    private String name;
    
    /**
     * 사업자등록번호
     */
    private String regno;
    
    /**
     * 법인등록번호
     */
    private String corporationNo;
    
    /**
     * 대표이사(대표자)
     */
    private String director;
    
    /**
     * 업태
     */
    private String type;
    
    /**
     * 종목
     */
    private String jijongType;
    
    /**
     * 우편번호
     */
    private String postNo;
    
    /**
     * 번지/호수(주소2)
     */
    private String address2;
    
    /**
     * 주소1(시)
     */
    private String address1;
    
    /**
     * 전화
     */
    private String telNo;
    
    /**
     * 팩스
     */
    private String fax;
    
    /**
     * 이메일
     */
    private String eMail;

    public String getSapId() {
        return sapId;
    }

    public void setSapId( String sapId ) {
        this.sapId = sapId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno( String regno ) {
        this.regno = regno;
    }

    public String getCorporationNo() {
        return corporationNo;
    }

    public void setCorporationNo( String corporationNo ) {
        this.corporationNo = corporationNo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector( String director ) {
        this.director = director;
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

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo( String postNo ) {
        this.postNo = postNo;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2( String address2 ) {
        this.address2 = address2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1( String address1 ) {
        this.address1 = address1;
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

    public String geteMail() {
        return eMail;
    }

    public void seteMail( String eMail ) {
        this.eMail = eMail;
    }
    
    
    
    
}
