package com.lgcns.ikep4.collpack.kms.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData0")
public class KmsLineReplyListReturnData0 {
	
	/**
	 * 댓글수
	 */
	public int itemCommentCount = 0;
		
	public KmsLineReplyListReturnData0() {}
	
	public KmsLineReplyListReturnData0(int itemCommentCount) {
		this.itemCommentCount = itemCommentCount;
	}
	
}