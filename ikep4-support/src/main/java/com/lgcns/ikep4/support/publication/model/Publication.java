package com.lgcns.ikep4.support.publication.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;




/**
 * 
 * 간행물 주소록 정보
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class Publication extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = -783254316442040982L;

    /** 행 번호 */
    private String rowNum;
    
    /** ID*/
    private String id;
    
    /** 간행물 종류
     *  1: 아이엠 무림
     *  2: 좋은 종이
     *  */
    private String type;
    
    /** 회사 이름 */
    private String companyName;
    
    /** 대표 이름 */
    private String name;
    
    /** 주소 */
    private String address;
    
    /** 전화번호 */
    private String telNo;
    
    /** 이메일 */
    private String eMail;
    
    /** 그룹 코드 
     * 1: 제지/지류유통
     * 2: 학교/오피니언
     * 3: 개인
     * 4: 퇴직사우
     * 
     * */
    private String groupId;
    
    /** 그룹 이름 */
    private String groupName;
    
    /** 우편번호 (앞) */
    private String zip1No;
    
    
    /** 우편번호 (뒤) */
    private String zip2No;
    
    /** 부수 */
    private String count;
    
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

    private User user;

    /**등록한 날짜 */
    private Date registDate;

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum( String rowNum ) {
        this.rowNum = rowNum;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName( String companyName ) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo( String telNo ) {
        this.telNo = telNo;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail( String eMail ) {
        this.eMail = eMail;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId( String groupId ) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName( String groupName ) {
        this.groupName = groupName;
    }

    public String getZip1No() {
        return zip1No;
    }

    public void setZip1No( String zip1No ) {
        this.zip1No = zip1No;
    }

    public String getZip2No() {
        return zip2No;
    }

    public void setZip2No( String zip2No ) {
        this.zip2No = zip2No;
    }

    public String getCount() {
        return count;
    }

    public void setCount( String count ) {
        this.count = count;
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

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate( Date registDate ) {
        this.registDate = registDate;
    }
    
    
    
    
    
    
    
    
}
