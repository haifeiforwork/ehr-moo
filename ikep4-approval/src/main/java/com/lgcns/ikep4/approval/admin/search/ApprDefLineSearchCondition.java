/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.search;

import com.lgcns.ikep4.framework.web.SearchCondition;

/**
 * 결재선 검색정보
 *
 * @author 
 * @version $Id$
 */
public class ApprDefLineSearchCondition extends SearchCondition {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String defLineId;
	
	private String defLineName;
	
	private String defLineType;
	
	private String defLineWay;
	

	private String searchColumn;
	
	private String searchWord;
	
	private	String portalId;
	
	private	String	systemId;
	
	private String systemName;
	
	@Override
	public Integer getDefaultPagePerRecord() {
		return 10;
	}

	/**
	 * @return the defLineId
	 */
	public String getDefLineId() {
		return defLineId;
	}

	/**
	 * @param defLineId the defLineId to set
	 */
	public void setDefLineId(String defLineId) {
		this.defLineId = defLineId;
	}

	/**
	 * @return the defLineName
	 */
	public String getDefLineName() {
		return defLineName;
	}

	/**
	 * @param defLineName the defLineName to set
	 */
	public void setDefLineName(String defLineName) {
		this.defLineName = defLineName;
	}

	/**
	 * @return the defLineType
	 */
	public String getDefLineType() {
		return defLineType;
	}

	/**
	 * @param defLineType the defLineType to set
	 */
	public void setDefLineType(String defLineType) {
		this.defLineType = defLineType;
	}

	/**
	 * @return the defLineWay
	 */
	public String getDefLineWay() {
		return defLineWay;
	}

	/**
	 * @param defLineWay the defLineWay to set
	 */
	public void setDefLineWay(String defLineWay) {
		this.defLineWay = defLineWay;
	}

	/**
	 * @return the searchColumn
	 */
	public String getSearchColumn() {
		return searchColumn;
	}

	/**
	 * @param searchColumn the searchColumn to set
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}



	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}
