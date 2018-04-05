package com.lgcns.ikep4.lightpack.facility.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class BodyCartooletcDetail {
	@XmlElement(name="ReserveTimeList")
	private List<DataReserveTime> reserveTimeList;
	
	public BodyCartooletcDetail() {
		super();
		
	}
	
	public BodyCartooletcDetail(List<DataReserveTime> reserveTimeList) {
		super();
		
		this.reserveTimeList = reserveTimeList;
	}
}
