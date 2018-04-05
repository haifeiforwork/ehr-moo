/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.knowledgemonitor.model;

import com.lgcns.ikep4.support.base.constant.CommonConstant;


/**
 * Knowledge Monitor KnowledgeMonitorModule model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: KnowledgeMonitorModule.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class KnowledgeMonitorModule extends KnowledgeMonitorModuleBody {

	/**
	 *
	 */
	private static final long serialVersionUID = -8119905561409395901L;

	/**
	 * 모듈 코드 (콤마(,)로 분리된 문자열)
	 */
	private String moduleCodeCommaString;

	/**
	 * 체크여부
	 */
	private boolean checked;

	/**
	 * 콤마로 분리된 moduleCode 를 배열로 변환
	 * @return
	 */
	public String[] getModuleCodeArray() {
		return moduleCodeCommaString.split(CommonConstant.COMMA_SEPARATER);
	}

	/**
	 * @return the moduleCodeCommaString
	 */
	public String getModuleCodeCommaString() {
		return moduleCodeCommaString;
	}

	/**
	 * @param moduleCodeCommaString the moduleCodeCommaString to set
	 */
	public void setModuleCodeCommaString(String moduleCodeCommaString) {
		this.moduleCodeCommaString = moduleCodeCommaString;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
