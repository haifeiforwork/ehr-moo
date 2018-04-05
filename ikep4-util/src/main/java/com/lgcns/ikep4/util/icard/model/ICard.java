package com.lgcns.ikep4.util.icard.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * ICardVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ICard.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class ICard implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1270417183766686531L;

	/**
	 * 버젼
	 */
	private String version;

	/**
	 * 성
	 */
	private String familyName;

	/**
	 * 이름
	 */
	private String givenName;

	/**
	 * 전체이름
	 */
	private String fullName;

	/**
	 * 생년월일
	 */
	private String bday;

	/**
	 * 성별
	 */
	private String gender;

	/**
	 * 이메일
	 */
	private String email;

	/**
	 * 소속
	 */
	private String org;

	/**
	 * 부서
	 */
	private String dept;

	/**
	 * 직위
	 */
	private String title;

	/**
	 * 직업
	 */
	private String role;

	/**
	 * 분류
	 */
	private String categories;

	/**
	 * 공개범위
	 */
	private String clazz;

	/**
	 * 메모
	 */
	private String note;

	/**
	 * 주소리스트
	 */
	private List<IAddr> iaddrList;

	/**
	 * 전화번호리스트
	 */
	private List<ITel> itelList;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<IAddr> getIaddrList() {
		return iaddrList;
	}

	public void setIaddrList(List<IAddr> iaddrList) {
		this.iaddrList = iaddrList;
	}

	public List<ITel> getItelList() {
		return itelList;
	}

	public void setItelList(List<ITel> itelList) {
		this.itelList = itelList;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void addIAddrList(IAddr iaddr) {

		if (iaddrList == null) {
			iaddrList = new ArrayList<IAddr>();
		}

		iaddrList.add(iaddr);
	}

	public void addITelList(ITel itel) {

		if (itelList == null) {
			itelList = new ArrayList<ITel>();
		}

		itelList.add(itel);
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
