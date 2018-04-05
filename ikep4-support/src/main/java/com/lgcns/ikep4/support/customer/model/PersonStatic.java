
package com.lgcns.ikep4.support.customer.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class PersonStatic extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 4917522217795802520L;

    /** 고객 아이디 */
    private String id;

    /** 임원 인원수(남자) */
    private int manEmployee1;

    /** 차/부장인원수 (남자)*/
    private int manEmployee2;

    /** 과장/대리 인원수 (남자 )*/
    private int manEmployee3;

    /** 계장/주임/사원 인원수 (남자) */
    private int manEmployee4;

    /** 임원 인원수(여자) */
    private int womanEmployee1;

    /** 차/부장인원수 (여자)*/
    private int womanEmployee2;

    /** 과장/대리 인원수 (여자)*/
    private int womanEmployee3;

    /** 계장/주임/사원 인원수 (여자) */
    private int womanEmployee4;

    /** 임원 인원수(합계) */
    private int totalEmployee1;

    /** 차/부장인원수 (합계)*/
    private int totalEmployee2;

    /** 과장/대리 인원수 (합계)*/
    private int totalEmployee3;

    /** 계장/주임/사원 인원수 (합계) */
    private int totalEmployee4;
    
    
    
    
    public int getTotalEmployee1() {
        return totalEmployee1;
    }

    public void setTotalEmployee1( int totalEmployee1 ) {
        this.totalEmployee1 = totalEmployee1;
    }

    public int getTotalEmployee2() {
        return totalEmployee2;
    }

    public void setTotalEmployee2( int totalEmployee2 ) {
        this.totalEmployee2 = totalEmployee2;
    }

    public int getTotalEmployee3() {
        return totalEmployee3;
    }

    public void setTotalEmployee3( int totalEmployee3 ) {
        this.totalEmployee3 = totalEmployee3;
    }

    public int getTotalEmployee4() {
        return totalEmployee4;
    }

    public void setTotalEmployee4( int totalEmployee4 ) {
        this.totalEmployee4 = totalEmployee4;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public int getManEmployee1() {
        return manEmployee1;
    }

    public void setManEmployee1( int manEmployee1 ) {
        this.manEmployee1 = manEmployee1;
    }

    public int getManEmployee2() {
        return manEmployee2;
    }

    public void setManEmployee2( int manEmployee2 ) {
        this.manEmployee2 = manEmployee2;
    }

    public int getManEmployee3() {
        return manEmployee3;
    }

    public void setManEmployee3( int manEmployee3 ) {
        this.manEmployee3 = manEmployee3;
    }

    public int getManEmployee4() {
        return manEmployee4;
    }

    public void setManEmployee4( int manEmployee4 ) {
        this.manEmployee4 = manEmployee4;
    }

    public int getWomanEmployee1() {
        return womanEmployee1;
    }

    public void setWomanEmployee1( int womanEmployee1 ) {
        this.womanEmployee1 = womanEmployee1;
    }

    public int getWomanEmployee2() {
        return womanEmployee2;
    }

    public void setWomanEmployee2( int womanEmployee2 ) {
        this.womanEmployee2 = womanEmployee2;
    }

    public int getWomanEmployee3() {
        return womanEmployee3;
    }

    public void setWomanEmployee3( int womanEmployee3 ) {
        this.womanEmployee3 = womanEmployee3;
    }

    public int getWomanEmployee4() {
        return womanEmployee4;
    }

    public void setWomanEmployee4( int womanEmployee4 ) {
        this.womanEmployee4 = womanEmployee4;
    }
    
    
}
