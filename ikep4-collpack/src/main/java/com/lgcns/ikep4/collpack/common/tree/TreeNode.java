/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.common.tree;

/**
 * Tree Node model (더이상 사용하지 않음) support-common.base 로 이전됨
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: TreeNode.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class TreeNode {

	/**
	 * 카테고리 항목
	 */
	private TreeNodeData data;

	/**
	 * 카테고리 속성
	 */
	private TreeNodeAttr attr;

	/**
	 * 자식노드 펼침 상태
	 */
	private String state;


	/**
	 * @return the data
	 */
	public TreeNodeData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(TreeNodeData data) {
		this.data = data;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the attr
	 */
	public TreeNodeAttr getAttr() {
		return attr;
	}

	/**
	 * @param attr the attr to set
	 */
	public void setAttr(TreeNodeAttr attr) {
		this.attr = attr;
	}

}
