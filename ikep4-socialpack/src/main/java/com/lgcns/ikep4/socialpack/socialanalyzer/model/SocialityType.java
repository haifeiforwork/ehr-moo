/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialanalyzer.model;

import java.util.Date;


/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: SocialityType.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class SocialityType extends SocialityTypePk {
	private static final long serialVersionUID = -8686298250088408827L;
   
	/**
	 * 타입명 
	 */
	private String  typeName;    
	
	/**
	 * Sociality 타입별 가중치 
	 */
	private Integer typeWeight = 0;  
	
	/**
	 * 유효 개월수 
	 */
	private Integer validMonth = 0;  
	
	/**
	 * 등록자ID 
	 */
	private String  registerId;  
	
	/**
	 * 등록자명 
	 */
	private String  registerName;
	
	/**
	 * 등록일 
	 */
	private Date    registDate;  
	
	/**
	 * 수정자 ID 
	 */
	private String  updaterId;   
	
	/**
	 * 수정자 이름 
	 */
	private String  updaterName; 
	
	/**
	 * 수정 일 
	 */
	private Date    updateDate;
	
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return the typeWeight
	 */
	public Integer getTypeWeight() {
		return typeWeight;
	}
	/**
	 * @param typeWeight the typeWeight to set
	 */
	public void setTypeWeight(Integer typeWeight) {
		this.typeWeight = typeWeight;
	}
	/**
	 * @return the validMonth
	 */
	public Integer getValidMonth() {
		return validMonth;
	}
	/**
	 * @param validMonth the validMonth to set
	 */
	public void setValidMonth(Integer validMonth) {
		this.validMonth = validMonth;
	}
	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}
	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	/**
	 * @return the registerName
	 */
	public String getRegisterName() {
		return registerName;
	}
	/**
	 * @param registerName the registerName to set
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}
	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	/**
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}
	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}
	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}
	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	} 
}
