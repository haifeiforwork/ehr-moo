package com.lgcns.ikep4.collpack.kms.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class KmsItemListBody {
	
	@XmlElement(name="KmsItemList")
	public List<KmsItemListReturnData0> kmsItemListReturnData0;

	@XmlElement(name="TotalListInfo")
	public KmsItemListReturnData1 kmsItemListReturnData1;
	
	@XmlElement(name="Category")
	public List< KmsItemListReturnData2> kmsItemListReturnData2;
	
	public KmsItemListBody() {}
	
	public KmsItemListBody(List<KmsItemListReturnData0> kmsItemListReturnData0, KmsItemListReturnData1 kmsItemListReturnData1,  List< KmsItemListReturnData2> kmsItemListReturnData2) {
		this.kmsItemListReturnData0 = kmsItemListReturnData0;
		this.kmsItemListReturnData1 = kmsItemListReturnData1;
		this.kmsItemListReturnData2 = kmsItemListReturnData2;
	}
}