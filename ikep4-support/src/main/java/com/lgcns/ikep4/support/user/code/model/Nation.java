package com.lgcns.ikep4.support.user.code.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class Nation extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -8903072953581306263L;
	
	/**
	 * 다국어 메시지용 리스트
	 */
	@Valid
	private List<I18nMessage> i18nMessageList;
	
	/**
	 * 순번
	 */
	private String num;
	
	/**
	 * 국가 코드
	 */
	@NotNull
	@Size(min = 0, max = 20)
	private String nationCode;

	/**
	 * 국가명
	 */
	@NotNull
	@Size(min = 0, max = 100)
	private String nationName;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private String registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private String updateDate;

	/**
	 * 코드 중복체크용 변수
	 */
	private String codeCheck;

	public List<I18nMessage> getI18nMessageList() {
		return i18nMessageList;
	}

	public void setI18nMessageList(List<I18nMessage> i18nMessageList) {
		this.i18nMessageList = i18nMessageList;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCodeCheck() {
		return codeCheck;
	}

	public void setCodeCheck(String codeCheck) {
		this.codeCheck = codeCheck;
	}

}