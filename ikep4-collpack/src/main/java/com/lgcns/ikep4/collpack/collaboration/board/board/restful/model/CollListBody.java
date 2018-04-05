package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class CollListBody {
	
	@XmlElement(name="CollaborationList")
	public List<CollListReturnData> collListReturnData;

	public CollListBody() {}
	
	public CollListBody(List<CollListReturnData> collListReturnData) {
		this.collListReturnData = collListReturnData;
	} 
	
}