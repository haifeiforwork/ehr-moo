package com.lgcns.ikep4.lightpack.facility.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cartooletcList")
public class DataCartooletcList {
	protected String cartooletcId;
	protected String cartooletcName;
	
	public DataCartooletcList(String cartooletcId, String cartooletcName) {
		
		super();
		this.cartooletcId = cartooletcId;
		this.cartooletcName = cartooletcName;
	}

	public String getCartooletcId() {
		return cartooletcId;
	}

	public void setCartooletcId(String cartooletcId) {
		this.cartooletcId = cartooletcId;
	}

	public String getCartooletcName() {
		return cartooletcName;
	}

	public void setCartooletcName(String cartooletcName) {
		this.cartooletcName = cartooletcName;
	}
}
