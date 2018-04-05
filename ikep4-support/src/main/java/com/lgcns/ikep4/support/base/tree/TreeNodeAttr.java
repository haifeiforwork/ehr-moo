/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.base.tree;

/**
 * Tree Node Attr model
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: TreeNodeAttr.java 16273 2011-08-18 07:07:47Z giljae $
 */
public class TreeNodeAttr {

	/**
	 * 카테고리 ID
	 */
	private String id;

	/**
	 * 부모 카테고리 ID
	 */
	private String pid;

	/**
	 * 기본 생성자
	 */
	public TreeNodeAttr() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

}
