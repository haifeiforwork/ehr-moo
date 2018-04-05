package com.lgcns.ikep4.support.customer.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class RelationCompany extends BaseObject{

    /**
     *
     */
    private static final long serialVersionUID = -877202175978810339L;

    
    /** 고객ID */
    private String id;
    
    /** 관계사 Name*/
    private String relationName;
    
    /** Type */
    private String type;
    
    /** Note */
    private String note;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName( String relationName ) {
        this.relationName = relationName;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote( String note ) {
        this.note = note;
    }
    
    
    
    
}
