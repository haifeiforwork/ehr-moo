/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.expertnetwork.tree;

import com.lgcns.ikep4.support.base.tree.TreeNodeAttr;


/**
 * Tree Node Attr model
 * Expert Network 용
 *
 * @author 우동진 (jins02@nate.com)
 * @version $Id: TreeNodeAttrExpertNetwork.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class TreeNodeAttrExpertNetwork extends TreeNodeAttr{

	/**
	 * 정렬순서
	 */
	private int order;

	/**
	 * Tags (,로 분리된)
	 */
	private String tags;

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

}
