package com.lgcns.ikep4.support.customer.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class MainPerson extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 3293846188075594370L;

    
    private String id;
    
    private String name;
    
    private String jikwe;
    
    private String tel;
    
    private String eMail;
    
    private String officerFlag;
    
    private String keymanFlag;

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

    public String getJikwe() {
        return jikwe;
    }

    public void setJikwe( String jikwe ) {
        this.jikwe = jikwe;
    }

    public String getTel() {
        return tel;
    }

    public void setTel( String tel ) {
        this.tel = tel;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail( String eMail ) {
        this.eMail = eMail;
    }

    public String getOfficerFlag() {
        return officerFlag;
    }

    public void setOfficerFlag( String officerFlag ) {
        this.officerFlag = officerFlag;
    }

    public String getKeymanFlag() {
        return keymanFlag;
    }

    public void setKeymanFlag( String keymanFlag ) {
        this.keymanFlag = keymanFlag;
    }
    
    
    
    
    
}
