package com.lgcns.ikep4.lightpack.facility.restful.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BodyCartooletcList {
	@XmlElement(name="CartooletcList")
	private List<DataCartooletcList> cartooletcList;
	
	public BodyCartooletcList() {
		this.cartooletcList = new ArrayList<DataCartooletcList>();
	}
	
	public BodyCartooletcList(List<DataCartooletcList> cartooletcList) {
		super();
		this.cartooletcList = cartooletcList;
	}
	
	public void addCartooletc(DataCartooletcList cartooletc) {
		this.cartooletcList.add(cartooletc);
	}
}
