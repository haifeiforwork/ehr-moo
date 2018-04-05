package com.lgcns.ikep4.collpack.kms.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class KmsLineReplyListBody {
	
	@XmlElement(name="CommentCount")
	public KmsLineReplyListReturnData0 kmsLineReplyListReturnData0;
	
	@XmlElement(name="CommentList")
	public List<KmsLineReplyListReturnData1> kmsLineReplyListReturnData1;

	@XmlElement(name="TotalListInfo")
	public KmsLineReplyListReturnData2 kmsLineReplyListReturnData2;
	
	public KmsLineReplyListBody() {}
	
	public KmsLineReplyListBody(KmsLineReplyListReturnData0 kmsLineReplyListReturnData0
								 ,List<KmsLineReplyListReturnData1> kmsLineReplyListReturnData1
								 ,KmsLineReplyListReturnData2 kmsLineReplyListReturnData2) {
		this.kmsLineReplyListReturnData0 = kmsLineReplyListReturnData0;
		this.kmsLineReplyListReturnData1 = kmsLineReplyListReturnData1;
		this.kmsLineReplyListReturnData2 = kmsLineReplyListReturnData2;
	}
}