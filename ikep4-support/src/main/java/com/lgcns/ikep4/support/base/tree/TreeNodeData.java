/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.base.tree;

/**
 * Tree Node Data model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: TreeNodeData.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class TreeNodeData {

	/**
	 * 카테고리 명
	 */
	private String title;

	/**
	 * Icon
	 */
	private String icon;


	/**
	 * 기본 생성자
	 */
	public TreeNodeData() {
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
