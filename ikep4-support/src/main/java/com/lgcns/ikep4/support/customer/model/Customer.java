
package com.lgcns.ikep4.support.customer.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class Customer extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 7797282683732917257L;

    /** 고객 아이디 */
    private String id;

    /** 고객 명 */
    private String name;

    /** 사업자 등록번호 */
    private String regno;

    /** 대표자 */
    private String director;

    public String getRegno() {
        return regno;
    }

    public void setRegno( String regno ) {
        this.regno = regno;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector( String director ) {
        this.director = director;
    }

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

}
