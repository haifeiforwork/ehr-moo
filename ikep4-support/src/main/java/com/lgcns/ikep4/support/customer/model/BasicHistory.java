package com.lgcns.ikep4.support.customer.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 고객 기본정보 (연혁 정보)
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class BasicHistory extends BaseObject{

    /**
     *
     */
    private static final long serialVersionUID = 4442541467163218219L;

    
    private String id;
    
    private String yearDate;
    
    private String yearContent;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getYearDate() {
        return yearDate;
    }

    public void setYearDate( String yearDate ) {
        this.yearDate = yearDate;
    }

    public String getYearContent() {
        return yearContent;
    }

    public void setYearContent( String yearContent ) {
        this.yearContent = yearContent;
    }
    
    
}
