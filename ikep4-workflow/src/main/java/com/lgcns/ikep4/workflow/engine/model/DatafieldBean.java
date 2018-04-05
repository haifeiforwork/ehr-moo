/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.workflow.engine.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * TODO Javadoc주석작성
 *
 * @author 박철순(sniper28@naver.com)
 * @version $Id: DatafieldBean.java 오후 2:32:14 
 */
public class DatafieldBean extends BaseObject {

	private String datafieldId = "";

	private String datafieldName = "";

	private String datafieldType = "";

	private String datafieldValue = "";

	private String arrayType = "false";
	
	private String useIndex	= "";

	private Map<String, String> arrayHsh;
	
	private List<String> arrayList;
	
	public DatafieldBean() {

	}






	/**TODO Javadoc주석작성
	 * @param datafieldId
	 * @param datafieldName
	 * @param datafieldType
	 * @param datafieldValue
	 * @param arrayType
	 * @param useIndex
	 * @param arrayHsh
	 * @param arrayList
	 */
	public DatafieldBean(String datafieldId, String datafieldName, String datafieldType, String datafieldValue,
			String arrayType, String useIndex,Map<String, String> arrayHsh, List arrayList) {
		super();
		this.datafieldId = datafieldId;
		this.datafieldName = datafieldName;
		this.datafieldType = datafieldType;
		this.datafieldValue = datafieldValue;
		this.arrayType = arrayType;
		this.useIndex = useIndex;
		this.arrayHsh = arrayHsh;
		this.arrayList = arrayList;
	}






	/**
	 * @return the datafieldId
	 */
	public String getDatafieldId() {
		return datafieldId;
	}

	/**
	 * @return the datafieldName
	 */
	public String getDatafieldName() {
		return datafieldName;
	}

	/**
	 * @return the datafieldType
	 */
	public String getDatafieldType() {
		return datafieldType;
	}

	/**
	 * @return the datafieldValue
	 */
	public String getDatafieldValue() {
		return datafieldValue;
	}

	/**
	 * @return the arrayType
	 */
	public String getArrayType() {
		return arrayType;
	}

	/**
	 * @return the useIndex
	 */
	public String getUseIndex() {
		return useIndex;
	}	
	
	/**
	 * TODO Javadoc주석작성
	 * @param paramKey
	 * @return
	 */
	public String getArrayData(String paramKey) {
		return arrayHsh.get(paramKey).toString();
	}
	
	/**
	 * TODO Javadoc주석작성
	 * @return
	 */
	public Map<String, String> getArrayHash() {
		return arrayHsh;
	}
	
	
	
	
	
	/**
	 * @return the arrayList
	 */
	public List<String> getArrayList() {
		return arrayList;
	}
	
	public String getArrayData(int index) {
		return arrayList.get(index).toString();
	}	


	/**
	 * @param datafieldId the datafieldId to set
	 */
	public void setDatafieldId(String datafieldId) {
		this.datafieldId = datafieldId;
	}

	/**
	 * @param datafieldName the datafieldName to set
	 */
	public void setDatafieldName(String datafieldName) {
		this.datafieldName = datafieldName;
	}

	/**
	 * @param datafieldType the datafieldType to set
	 */
	public void setDatafieldType(String datafieldType) {
		this.datafieldType = datafieldType;
	}

	/**
	 * @param datafieldValue the datafieldValue to set
	 */
	public void setDatafieldValue(String datafieldValue) {
		this.datafieldValue = datafieldValue;
	}

	/**
	 * @param arrayType the arrayType to set
	 */
	public void setArrayType(String arrayType) {
		this.arrayType = arrayType;
	}
	
	/**
	 * @param useIndex the useIndex to set
	 */
	public void setUseIndex(String useIndex) {
		this.useIndex = useIndex;
	}
	

	/**
	 * TODO Javadoc주석작성
	 * @param paramKey
	 * @param paramValue
	 */
	public void addArrayData(String paramKey, String paramValue) {
		arrayHsh.put(paramKey, paramValue);
	}
	
	/**
	 * @param arrayList the arrayList to set
	 */
	public void setArrayList(List<String> arrayList) {
		this.arrayList = arrayList;
	}
	
	public void addArrayData(String paramValue) {
		arrayList.add(paramValue);
	}
	
}
