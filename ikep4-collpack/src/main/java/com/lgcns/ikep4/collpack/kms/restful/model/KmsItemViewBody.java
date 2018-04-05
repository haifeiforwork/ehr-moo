package com.lgcns.ikep4.collpack.kms.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class KmsItemViewBody {
	
	@XmlElement(name="BoradDetail")
	public KmsItemViewReturnData0 kmsItemViewReturnData0;

	@XmlElement(name="AttachList")
	public List<KmsItemViewReturnData1> kmsItemViewReturnData1;
	
	@XmlElement(name="CategoryList")
	public List<KmsItemViewReturnData2> kmsItemViewReturnData2;
	
	public KmsItemViewBody() {}
	
	public KmsItemViewBody(KmsItemViewReturnData0 kmsItemViewReturnData0, List<KmsItemViewReturnData1> kmsItemViewReturnData1) {
		this.kmsItemViewReturnData0 = kmsItemViewReturnData0;
		this.kmsItemViewReturnData1 = kmsItemViewReturnData1;
	}

	public KmsItemViewBody(KmsItemViewReturnData0 kmsItemViewReturnData0, List<KmsItemViewReturnData1> kmsItemViewReturnData1 , List<KmsItemViewReturnData2> kmsItemViewReturnData2) {
		this.kmsItemViewReturnData0 = kmsItemViewReturnData0;
		this.kmsItemViewReturnData1 = kmsItemViewReturnData1;
		this.kmsItemViewReturnData2 = kmsItemViewReturnData2;
	}
}